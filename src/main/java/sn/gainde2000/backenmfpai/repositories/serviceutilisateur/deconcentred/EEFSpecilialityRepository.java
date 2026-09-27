
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.CFP;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEF;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.EEFSpeciality;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:56
 * @project backend_mfpai
 */
public interface EEFSpecilialityRepository extends JpaRepository<EEFSpeciality, Long>, QuerydslPredicateExecutor<EEFSpeciality> {


    Optional<EEFSpeciality> findByCode(String code);

    List<EEFSpeciality> findByEef_Code(String code);
}
