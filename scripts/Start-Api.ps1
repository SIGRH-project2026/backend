param([string]$JavaHome)
$ErrorActionPreference = 'Stop'
$apiRoot = Split-Path $PSScriptRoot -Parent
if ($JavaHome) { $env:JAVA_HOME = $JavaHome }
if (-not (Test-Path -LiteralPath (Join-Path $apiRoot '.env'))) { throw 'Create backend/.env from .env.example first.' }
Push-Location $apiRoot
try {
    & mvn spring-boot:run '-Dspring-boot.run.arguments=--spring.config.location=classpath:application-env.yaml'
    if ($LASTEXITCODE -ne 0) { throw 'API startup failed.' }
} finally { Pop-Location }
