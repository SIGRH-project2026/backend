\set ON_ERROR_STOP on
\getenv db_name SIGRH_NEW_DB
\getenv app_user SIGRH_NEW_USER
\getenv app_password SIGRH_NEW_PASSWORD
\getenv owner_role SIGRH_NEW_OWNER
-- Fail before any mutation if a target already exists; never drop or overwrite.
SELECT NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = :'db_name')
   AND NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname IN (:'app_user', :'owner_role')) AS available \gset
\if :available
SELECT format('CREATE ROLE %I NOLOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE', :'owner_role') \gexec
SELECT format('CREATE ROLE %I LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT PASSWORD %L', :'app_user', :'app_password') \gexec
SELECT format('CREATE DATABASE %I OWNER %I', :'db_name', :'owner_role') \gexec
SELECT format('REVOKE ALL ON DATABASE %I FROM PUBLIC', :'db_name') \gexec
SELECT format('GRANT CONNECT ON DATABASE %I TO %I', :'db_name', :'app_user') \gexec
\else
-- ON_ERROR_STOP returns a nonzero code. No existing database or role is changed.
DO $$ BEGIN RAISE EXCEPTION 'Target database or role already exists. Choose new names.'; END $$;
\endif
