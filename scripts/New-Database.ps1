param(
    [string]$AdminUser = 'postgres',
    [switch]$SeedNavigation,
    [string]$AdministratorEmail
)
$ErrorActionPreference = 'Stop'
if ($AdministratorEmail -and -not $SeedNavigation) { throw 'AdministratorEmail requires SeedNavigation.' }
$apiRoot = Split-Path $PSScriptRoot -Parent
$settings = @{}
Get-Content -LiteralPath (Join-Path $apiRoot '.env') | ForEach-Object {
    if ($_ -match '^([A-Z_]+)=(.*)$') { $settings[$matches[1]] = $matches[2] }
}
foreach ($key in @('DB_HOST','DB_PORT','DB_NAME','DB_USERNAME','DB_PASSWORD')) {
    if (-not $settings[$key]) { throw "Missing $key in backend/.env" }
}
foreach ($key in @('DB_NAME','DB_USERNAME')) {
    if ($settings[$key] -notmatch '^[a-z][a-z0-9_]{2,40}$') { throw "Invalid $key" }
}
if ($settings.DB_NAME -in @('postgres','template0','template1')) { throw 'A new application database name is required.' }
if ($settings.DB_PASSWORD.Length -lt 24 -or $settings.DB_PASSWORD -like 'CHANGE_ME*') { throw 'Set a random DB_PASSWORD (at least 24 characters).' }
$psql = (Get-Command psql -ErrorAction Stop).Source
$envNames = @('PGPASSWORD','SIGRH_NEW_DB','SIGRH_NEW_USER','SIGRH_NEW_PASSWORD','SIGRH_NEW_OWNER','SIGRH_ADMIN_EMAIL','SIGRH_ADMIN_PASSWORD')
$previous = @{}
foreach ($name in $envNames) { $previous[$name] = [Environment]::GetEnvironmentVariable($name, 'Process') }
try {
    $password = Read-Host 'PostgreSQL administrator password' -AsSecureString
    $credential = New-Object System.Net.NetworkCredential('', $password)
    $env:PGPASSWORD = $credential.Password
    $env:SIGRH_NEW_DB = $settings.DB_NAME
    $env:SIGRH_NEW_USER = $settings.DB_USERNAME
    $env:SIGRH_NEW_PASSWORD = $settings.DB_PASSWORD
    $env:SIGRH_NEW_OWNER = $settings.DB_NAME + '_owner'
    $connection = @('-X', '-w', '-h', $settings.DB_HOST, '-p', $settings.DB_PORT, '-U', $AdminUser, '-v', 'ON_ERROR_STOP=1')
    & $psql @connection -d postgres -f (Join-Path $apiRoot 'database/provision.sql')
    if ($LASTEXITCODE -ne 0) { throw 'Provisioning stopped. No existing database is overwritten.' }
    $seed = if ($SeedNavigation) { 'true' } else { 'false' }
    & $psql @connection -d $settings.DB_NAME -v "seed_navigation=$seed" -f (Join-Path $apiRoot 'database/initialize.sql')
    if ($LASTEXITCODE -ne 0) { throw 'Schema transaction failed. The newly created database and roles remain for inspection; no automatic deletion is performed.' }
    if ($AdministratorEmail) {
        $newPassword = Read-Host 'New SIGRH administrator password (16 to 72 bytes)' -AsSecureString
        try {
            $env:SIGRH_ADMIN_EMAIL = $AdministratorEmail
            $env:SIGRH_ADMIN_PASSWORD = (New-Object System.Net.NetworkCredential('', $newPassword)).Password
            & $psql @connection -d $settings.DB_NAME -f (Join-Path $apiRoot 'database/administrator.sql')
            if ($LASTEXITCODE -ne 0) { throw 'Administrator initialization failed. Database remains available for inspection.' }
        } finally { $newPassword.Dispose() }
    }
    Write-Host 'New database initialized with a restricted application role. Existing databases were preserved.'
} finally {
    foreach ($name in $envNames) { [Environment]::SetEnvironmentVariable($name, $previous[$name], 'Process') }
    $credential = $null
    if ($password) { $password.Dispose() }
}
