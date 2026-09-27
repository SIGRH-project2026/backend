-- Persiste le motif saisi lors d'une demande de permutation.
-- ALTER TABLE IF EXISTS permet aussi le démarrage d'une base vide. Hibernate
-- créera ensuite la table dans les environnements configurés en ddl-auto=update.
ALTER TABLE IF EXISTS schema_carriere.td_permutation
    ADD COLUMN IF NOT EXISTS motif_permutation VARCHAR(500);
