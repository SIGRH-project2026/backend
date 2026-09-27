package sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Résultat d'un import en masse d'utilisateurs (niveau déconcentré).
 * Retourne un récapitulatif ligne par ligne afin d'informer l'admin
 * des lignes importées et des lignes en erreur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserImportResultDTO {

    /** Nombre total de lignes de données traitées (hors en-tête). */
    private int total;

    /** Nombre de lignes importées avec succès. */
    private int imported;

    /** Nombre de lignes en échec. */
    private int failed;

    /** Détails des lignes en échec. */
    @Builder.Default
    private List<RowError> errors = new ArrayList<>();

    public void addError(RowError rowError) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(rowError);
        this.failed++;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RowError {
        /** Numéro de la ligne dans le fichier (1 = en-tête). */
        private int ligne;
        private String matricule;
        private String prenom;
        private String nom;
        private String sexe;
        private String etablissement;
        private String typeSystemeEnseignement;
        private String ief;
        private String ia;
        /** Service / rattachement (utilisé pour l'import du niveau central). */
        private String service;
        private String message;

        public static RowErrorBuilder builder() {
            return new RowErrorBuilder();
        }

        public static class RowErrorBuilder {
            private final RowError rowError = new RowError();

            public RowErrorBuilder ligne(int ligne) {
                rowError.setLigne(ligne);
                return this;
            }

            public RowErrorBuilder matricule(String matricule) {
                rowError.setMatricule(matricule);
                return this;
            }

            public RowErrorBuilder prenom(String prenom) {
                rowError.setPrenom(prenom);
                return this;
            }

            public RowErrorBuilder nom(String nom) {
                rowError.setNom(nom);
                return this;
            }

            public RowErrorBuilder sexe(String sexe) {
                rowError.setSexe(sexe);
                return this;
            }

            public RowErrorBuilder etablissement(String etablissement) {
                rowError.setEtablissement(etablissement);
                return this;
            }

            public RowErrorBuilder typeSystemeEnseignement(String typeSystemeEnseignement) {
                rowError.setTypeSystemeEnseignement(typeSystemeEnseignement);
                return this;
            }

            public RowErrorBuilder ief(String ief) {
                rowError.setIef(ief);
                return this;
            }

            public RowErrorBuilder ia(String ia) {
                rowError.setIa(ia);
                return this;
            }

            public RowErrorBuilder service(String service) {
                rowError.setService(service);
                return this;
            }

            public RowErrorBuilder message(String message) {
                rowError.setMessage(message);
                return this;
            }

            public RowError build() {
                return rowError;
            }
        }
    }
}
