package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.SpecialiteEEF;

import java.util.List;

/**
 * @author Abdou Karim CISSOKHO
 * @created 16/05/2024-18:58
 * @project backend_mfpai
 */
public interface SpecialiteEEFRepository extends JpaRepository<SpecialiteEEF, Long>, QueryByExampleExecutor<SpecialiteEEF> {

    List<SpecialiteEEF> findByEtablissement_Code(String specialite);
    List<SpecialiteEEF> findBySpeciality_Code(String specialite);
}
