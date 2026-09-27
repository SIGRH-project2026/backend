package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.domain.Specification;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Diplomes;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-12:16
 * @project backend_mfpai
 */
public class DiplomeSpecifications {

    // Filtrer par le code
    public static Specification<Diplomes> hasCode(String code) {
        return (root, query, criteriaBuilder) -> {
            if (code != null && !code.isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + code.toLowerCase() + "%");
            }
            return null;
        };
    }

    // Filtrer par le label
    public static Specification<Diplomes> hasLabel(String label) {
        return (root, query, criteriaBuilder) -> {
            if (label != null && !label.isEmpty()) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("label")), "%" + label.toLowerCase() + "%");
            }
            return null;
        };
    }
}
