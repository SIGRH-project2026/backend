\set ON_ERROR_STOP on
\getenv admin_email SIGRH_ADMIN_EMAIL
\getenv admin_password SIGRH_ADMIN_PASSWORD
BEGIN;
-- Run only once on a fresh database, through the PostgreSQL administrator account.
LOCK TABLE schema_utilisateur.td_usermanager, schema_utilisateur.td_centrallevel,
    schema_utilisateur.td_deconcentratedlevel IN EXCLUSIVE MODE;
SELECT NOT EXISTS (SELECT 1 FROM schema_utilisateur.td_usermanager)
   AND NOT EXISTS (SELECT 1 FROM schema_utilisateur.td_centrallevel)
   AND NOT EXISTS (SELECT 1 FROM schema_utilisateur.td_deconcentratedlevel)
   AND EXISTS (SELECT 1 FROM schema_utilisateur.tp_profile WHERE pro_code = 'Admin-General')
   AND length(:'admin_email') > 3 AND octet_length(:'admin_password') BETWEEN 16 AND 72 AS can_bootstrap \gset
\if :can_bootstrap
CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO schema_utilisateur.td_usermanager
    (id, user_pmail, user_lastname, user_firstname, user_password, user_status, uti_firstlog, user_isdeleted, user_user_type)
VALUES (nextval('public.seq_user'), :'admin_email', 'Administrateur', 'SIGRH',
    crypt(:'admin_password', gen_salt('bf', 12)), true, false, false, 'MANAGER')
RETURNING id AS administrator_id \gset
INSERT INTO schema_utilisateur.tr_user_profile(user_id, profile_id)
    SELECT :administrator_id, pro_id FROM schema_utilisateur.tp_profile WHERE pro_code = 'Admin-General';
COMMIT;
\else
DO $$ BEGIN RAISE EXCEPTION 'Bootstrap requires an empty database, navigation seed, email and a password of 16 to 72 bytes.'; END $$;
\endif
