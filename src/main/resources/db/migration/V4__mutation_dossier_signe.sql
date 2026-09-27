ALTER TABLE schema_carriere.td_mutation ADD COLUMN IF NOT EXISTS mut_dossier_signe varchar(255);
ALTER TABLE schema_carriere.td_traitementmutation ADD COLUMN IF NOT EXISTS traitmut_dossier_signe varchar(255);
