-- Les ids des tables de paramétrage étaient calculés à la main (max + 1) sans avancer
-- les séquences Hibernate. Les créations utilisent désormais les séquences : on les place
-- au-delà du plus grand id existant. Ne jamais diminuer une séquence déjà en avance.
-- Requête unique (pas de bloc DO : Spring découpe les scripts sur ';').
-- Une séquence ou une table absente (base neuve, avant création par Hibernate) est ignorée.
SELECT v.seq_name,
       CASE WHEN to_regclass(v.seq_name) IS NOT NULL AND to_regclass(v.table_name) IS NOT NULL THEN
           setval(
               v.seq_name::regclass,
               GREATEST(
                   (xpath('/row/m/text()', query_to_xml(format('SELECT COALESCE(MAX(%I), 98) AS m FROM %s', v.id_col, v.table_name), false, true, '')))[1]::text::bigint,
                   (xpath('/row/m/text()', query_to_xml(format('SELECT last_value AS m FROM %s', v.seq_name), false, true, '')))[1]::text::bigint
               ) + 2,
               true
           )
       END AS new_value
FROM (VALUES
        ('public.seq_etablissement', 'eta_id', 'schema_utilisateur.tp_typeetablissement'),
        ('public.seq_direction', 'dir_id', 'schema_utilisateur.tp_direction'),
        ('public.seq_division', 'div_id', 'schema_utilisateur.tp_division'),
        ('public.seq_bureau', 'bur_id', 'schema_utilisateur.tp_bureau'),
        ('public.seq_ia', 'ia_id', 'schema_utilisateur.tp_ia'),
        ('public.seq_ief', 'seq_ief', 'schema_utilisateur.tp_ief'),
        ('public.seq_speciality', 'spe_id', 'schema_utilisateur.tp_speciality'),
        ('public.seq_fonction', 'fon_id', 'schema_utilisateur.tp_fonction'),
        ('public.seq_speciality_etablissement', 'spec_etab_id', 'schema_utilisateur.tr_speciality_etablissement')
     ) AS v(seq_name, id_col, table_name);
