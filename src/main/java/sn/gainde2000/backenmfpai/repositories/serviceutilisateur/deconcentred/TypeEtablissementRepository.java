
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.TypeEtablissement;


import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:29
 * @project backend_mfpai
 */
public interface TypeEtablissementRepository
        extends JpaRepository<TypeEtablissement, Long>, QuerydslPredicateExecutor<TypeEtablissement> {

    Optional<TypeEtablissement> findByCode(String code);


}
