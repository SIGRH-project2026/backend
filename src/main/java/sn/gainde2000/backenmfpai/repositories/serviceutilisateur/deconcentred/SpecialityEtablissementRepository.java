package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.SpecialityEtablissement;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/08/2024-16:21
 * @project backend_mfpai
 */

public interface SpecialityEtablissementRepository extends JpaRepository<SpecialityEtablissement, Long>, QuerydslPredicateExecutor<SpecialityEtablissement> {

    List<SpecialityEtablissement> findByEtablissement_Code(String codeEtablissement);
    List<SpecialityEtablissement> findByEtablissement_Id(Long EtabId);

}
