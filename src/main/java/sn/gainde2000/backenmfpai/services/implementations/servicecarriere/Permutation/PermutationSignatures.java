package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Permutation;

import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Circuit de signature d'une demande de permutation.
 *
 * Chaque agent a une seule demande courante (File.fileCode commençant par DEMANDEUR ou RECEVEUR) :
 * chaque niveau (chef d'établissement, IEF, IA) télécharge la demande de son agent, la signe
 * et la recharge ; la version signée remplace la précédente. Le code indique le dernier niveau signataire :
 * DEMANDEUR (soumise par l'agent), DEMANDEUR_SIGNE (chef d'établissement), DEMANDEUR_SIGNE_IEF, DEMANDEUR_SIGNE_IA.
 * L'IEF et l'IA joignent en plus un bordereau de transmission (BORDEREAU_IEF_..., BORDEREAU_IA_...).
 */
public final class PermutationSignatures {

    public static final String DEMANDEUR = "DEMANDEUR";
    public static final String RECEVEUR = "RECEVEUR";

    private PermutationSignatures() {
    }

    /** Niveau signataire du profil : CE, IEF, IA ou null si le profil ne signe pas. */
    public static String niveau(String codeProfil) {
        switch (codeProfil) {
            case "Chef-etablissement":
            case "Chef-cfp":
            case "Chef-EFF":
                return "CE";
            case "Représentant-IEF":
                return "IEF";
            case "Representant-IA":
                return "IA";
            default:
                return null;
        }
    }

    /** Code de la demande signée d'un agent (DEMANDEUR/RECEVEUR) à un niveau donné. */
    public static String codeSigne(String agent, String niveau) {
        return "CE".equals(niveau) ? agent + "_SIGNE" : agent + "_SIGNE_" + niveau;
    }

    /** Agents (DEMANDEUR et/ou RECEVEUR) relevant de l'établissement, de l'IEF ou de l'IA de l'acteur. */
    public static List<String> agentsCouverts(Permutation permutation, DeconcentratedLevel acteur, String niveau) {
        List<String> agents = new ArrayList<>();
        if (acteur == null || niveau == null)
            return agents;
        switch (niveau) {
            case "CE":
                ajouterSiMemeCode(agents, DEMANDEUR, acteur.getEtablissement() != null ? acteur.getEtablissement().getCode() : null,
                        permutation.getEtablissementDemandeur() != null ? permutation.getEtablissementDemandeur().getCode() : null);
                ajouterSiMemeCode(agents, RECEVEUR, acteur.getEtablissement() != null ? acteur.getEtablissement().getCode() : null,
                        permutation.getEtablissementReceveur() != null ? permutation.getEtablissementReceveur().getCode() : null);
                break;
            case "IEF":
                ajouterSiMemeCode(agents, DEMANDEUR, acteur.getIef() != null ? acteur.getIef().getCode() : null,
                        permutation.getIefDemandeur() != null ? permutation.getIefDemandeur().getCode() : null);
                ajouterSiMemeCode(agents, RECEVEUR, acteur.getIef() != null ? acteur.getIef().getCode() : null,
                        permutation.getIefReceveur() != null ? permutation.getIefReceveur().getCode() : null);
                break;
            case "IA":
                ajouterSiMemeCode(agents, DEMANDEUR, acteur.getIa() != null ? acteur.getIa().getCode() : null,
                        permutation.getIaDemandeur() != null ? permutation.getIaDemandeur().getCode() : null);
                ajouterSiMemeCode(agents, RECEVEUR, acteur.getIa() != null ? acteur.getIa().getCode() : null,
                        permutation.getIaReceveur() != null ? permutation.getIaReceveur().getCode() : null);
                break;
            default:
                break;
        }
        return agents;
    }

    /** Vrai si la demande de l'agent a été signée au niveau donné. */
    public static boolean estSignee(Permutation permutation, String agent, String niveau) {
        String code = codeSigne(agent, niveau);
        return permutation.getPieceJointes() != null
                && permutation.getPieceJointes().stream().map(File::getFileCode).anyMatch(code::equals);
    }

    /** Code du bordereau de transmission d'un acteur IEF/IA pour les agents qu'il couvre. */
    public static String codeBordereau(String niveau, List<String> agents) {
        return "BORDEREAU_" + niveau + "_" + String.join("_", agents);
    }

    private static void ajouterSiMemeCode(List<String> agents, String agent, String codeActeur, String codePermutation) {
        if (codeActeur != null && Objects.equals(codeActeur, codePermutation))
            agents.add(agent);
    }
}
