package sn.gainde2000.backenmfpai.entities.serviceutilisateur;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/04/2025-12:08
 * @project backend_mfpai
 */


@MappedSuperclass
@Getter
@Setter
public abstract class DiplomeBase {
    @Id
    private Long id;
    private String code;
    private String label;
}