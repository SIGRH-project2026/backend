-- Les imports/restaurations historiques peuvent avancer la clé primaire sans
-- avancer la séquence Hibernate. Ne jamais diminuer une séquence déjà en avance.
SELECT setval(
    'public.seq_dossier_agent',
    GREATEST(
        (SELECT COALESCE(MAX(dos_id), 98) FROM schema_carriere.td_dossieragent),
        (SELECT last_value FROM public.seq_dossier_agent)
    ) + 2,
    true
);

SELECT setval(
    'public.seq_situation_administrative',
    GREATEST(
        (SELECT COALESCE(MAX(sit_id), 98) FROM schema_carriere.td_situation_administrative),
        (SELECT last_value FROM public.seq_situation_administrative)
    ) + 2,
    true
);

SELECT setval(
    'public.seq_diplome',
    GREATEST(
        (SELECT COALESCE(MAX(dip_id), 98) FROM schema_carriere.td_diplome),
        (SELECT last_value FROM public.seq_diplome)
    ) + 2,
    true
);

SELECT setval(
    'public.seq_etat_civil',
    GREATEST(
        (SELECT COALESCE(MAX(etat_id), 98) FROM schema_carriere.td_etatcivil),
        (SELECT last_value FROM public.seq_etat_civil)
    ) + 2,
    true
);

SELECT setval(
    'public.seq_file',
    GREATEST(
        (SELECT COALESCE(MAX(id), 98) FROM schema_utilisateur.td_file),
        (SELECT last_value FROM public.seq_file)
    ) + 2,
    true
);
