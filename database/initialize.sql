\set ON_ERROR_STOP on
\getenv app_user SIGRH_NEW_USER
\getenv owner_role SIGRH_NEW_OWNER
BEGIN;
SELECT format('SET ROLE %I', :'owner_role') \gexec
\ir schema.sql
\if :seed_navigation
\ir navigation.sql
\endif
\ir ../src/main/resources/db/migration/V2__add_motif_permutation.sql
\ir ../src/main/resources/db/migration/V3__sync_dossier_agent_sequence.sql
\ir ../src/main/resources/db/migration/V4__notification_readers.sql
REVOKE CREATE ON SCHEMA public FROM PUBLIC;
SELECT format('GRANT USAGE ON SCHEMA %I TO %I', nspname, :'app_user')
  FROM pg_namespace WHERE nspname = 'public' OR nspname LIKE 'schema_%' \gexec
SELECT format('GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA %I TO %I', nspname, :'app_user')
  FROM pg_namespace WHERE nspname = 'public' OR nspname LIKE 'schema_%' \gexec
SELECT format('GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA %I TO %I', nspname, :'app_user')
  FROM pg_namespace WHERE nspname = 'public' OR nspname LIKE 'schema_%' \gexec
SELECT format('ALTER DEFAULT PRIVILEGES FOR ROLE %I IN SCHEMA %I GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO %I', :'owner_role', nspname, :'app_user')
  FROM pg_namespace WHERE nspname = 'public' OR nspname LIKE 'schema_%' \gexec
SELECT format('ALTER DEFAULT PRIVILEGES FOR ROLE %I IN SCHEMA %I GRANT USAGE, SELECT ON SEQUENCES TO %I', :'owner_role', nspname, :'app_user')
  FROM pg_namespace WHERE nspname = 'public' OR nspname LIKE 'schema_%' \gexec
COMMIT;
