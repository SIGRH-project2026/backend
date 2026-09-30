# API SIGRH autonome

## Configuration

`application-env.yaml` est la configuration portable, versionnee sans secrets. Elle importe `backend/.env` comme un fichier Java properties. Le `.env` local a ete genere avec des secrets aleatoires et est ignore par Git. Ne pas ajouter de guillemets autour des valeurs. Pour les chemins Windows, preferer `/` a `\`.

Le lancement utilise explicitement `spring.config.location=classpath:application-env.yaml` : il ne depend pas des anciens `application.yaml` / `application-localhost.yaml` locaux. Les variables du processus peuvent remplacer les valeurs du `.env`. Ne jamais placer DB_PASSWORD, JWT_SECRET ou MAIL_PASSWORD dans Angular.

Renseigner le serveur SMTP avant de tester activation et mot de passe oublie. `ALLOWED_ORIGINS` contient des origines HTTP(S) exactes, sans chemin, separees par des virgules. Utiliser HTTPS et `DB_SSLMODE=verify-full` avec un certificat de confiance hors environnement local. L'API conserve `/api/v1/mfpai` par compatibilite, configurable via API_CONTEXT_PATH.

Reference : [configuration externe Spring Boot](https://docs.spring.io/spring-boot/reference/features/external-config.html).

## Nouvelle base, sans effacer l'ancienne

Prerequis : PostgreSQL et psql disponibles. L'execution se fait sur le serveur configure dans `.env`. Un compte PostgreSQL d'administration est demande au lancement, son mot de passe n'est ni ecrit dans le script ni affiche.

Depuis `backend` :

```powershell
.\scripts\New-Database.ps1 -SeedNavigation -AdministratorEmail admin@votre-domaine.sn
```

Le script refuse tout nom de base ou de role deja existant. Il cree un proprietaire sans connexion, un compte applicatif sans droits de creation de tables, puis le schema et les migrations. `-SeedNavigation` charge uniquement les profils et menus, sans comptes ni dossiers historiques. Sans cette option, les tables restent vides. `-AdministratorEmail` cree le premier administrateur avec le mot de passe saisi de facon masquee; il exige une base sans comptes et le chargement des menus. Le mot de passe est hache avec bcrypt, jamais stocke en clair. [Reference PostgreSQL pgcrypto](https://www.postgresql.org/docs/18/pgcrypto.html).

Les referentiels metier (etablissements, corps, grades, statuts, etc.) doivent etre importes ou saisis avant les parcours de demande. Ils ne sont pas automatiquement copies depuis les donnees historiques. Une migration complete des donnees est un choix distinct.

La creation du schema est transactionnelle. En cas d'echec, le script conserve la nouvelle base et les roles pour diagnostic; il ne les supprime pas automatiquement. Pour les migrations suivantes, executer les scripts avec le role proprietaire via un compte d'administration. Le compte applicatif ne peut pas effectuer les migrations.

## Demarrage

```powershell
.\scripts\Start-Api.ps1 -JavaHome 'C:\Program Files\Java\jdk-17'
```

Ou, depuis `backend` :

```text
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.config.location=classpath:application-env.yaml
```

Les scripts PowerShell doivent etre autorises par la politique de votre poste. Aucune modification de cette politique n'est faite par ce projet. L'alternative Maven et les scripts SQL restent utilisables directement. Le compte SQL de l'application est distinct du compte administrateur SIGRH.

## Integration d'une autre application

L'integration passe par HTTP/JSON, sans acces direct a PostgreSQL. Ajouter l'origine du client web a ALLOWED_ORIGINS et configurer son URL d'API. Les clients serveur ne dependent pas de CORS.

- `POST /auth/login` avec `{ "login": "...", "password": "..." }` renvoie `payload.token` et `payload.refreshToken`.
- Les routes privees attendent `Authorization: Bearer <payload.token>`.
- `POST /auth/refresh` avec `{ "token": "<refreshToken>" }` renouvelle le jeton d'acces.
- HTTP 401 signifie authentification manquante/invalide; HTTP 403 signifie droit insuffisant.
- La specification `/v3/api-docs` peut etre activee via API_DOCS_ENABLED=true et reste reservee aux administrateurs.

Les comptes desactives sont refuses. Les jetons d'acces, de renouvellement et de mail sont distingues; les anciennes sessions doivent se reconnecter apres ce deploiement. Les mots de passe ne sont plus journalises lors de la creation des comptes. Les notifications WebSocket transportent uniquement un signal de rafraichissement.

## Limites de la verification

Ces protections ne constituent pas une certification de securite de toute l'application. Il reste a verifier les droits metier et l'appartenance des dossiers sur chaque endpoint, a faire evoluer les liens historiques de reinitialisation (qui transportent encore un mot de passe provisoire dans un JWT lisible), a revoir le stockage navigateur des jetons, et a auditer les dependances. Les sauvegardes et pieces jointes necessitent leurs propres controles d'acces, chiffrement au repos et politique de conservation. La base partage un role technique applicatif : les droits de chaque agent sont controles par l'API, pas par une politique SQL RLS.

Ne pas exposer cette installation sur Internet avant ces verifications et la configuration HTTPS. Ne pas interpreter la separation des interfaces comme une autorisation metier.

## Verification effectuee

- 17 tests unitaires / MVC ont passe pour notifications, JWT, CORS, droits d'acces et authentification.
- La creation SQL a ete executee sur deux bases PostgreSQL 18 temporaires; le schema a ete aligne sur les entites actuelles, puis valide par Hibernate avec le compte applicatif restreint (1 test d'integration supplementaire).
- Le compte applicatif ne peut pas creer de tables. La creation initiale refuse une base existante. Le mot de passe administrateur a ete verifie hache.
- Les applications frontoffice et backoffice ont ete compilees en mode development; le backoffice a ete compile sans source maps avec un seul worker et 1536 Mo de heap Node. Des avertissements Angular preexistants subsistent. La compilation production et les parcours metier complets ne sont pas encore valides.
- Aucune base metier existante n'a ete remplacee. L'instance de test a ete arretee. La reprise des donnees et le perimetre de l'espace agents restent a definir.
