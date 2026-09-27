package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.ParametreCorpsGradePk;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/07/2024-12:59
 * @project backend_mfpai
 */
public interface ParametreCorpsGradePkRepository extends JpaRepository<ParametreCorpsGradePk, Long> {

    List<ParametreCorpsGradePk> findByParametreCorpsGrade_Id(Long id);
    List<ParametreCorpsGradePk> findByParametreCorpsGrade_IdAndGrade_Label(Long id, String label);

    @Query(value = "SELECT pcg.grade_id,pcg.speciality_id, pcg.type_matricule_id, pcg.params_cg_id, gra.grade_code FROM schema_utilisateur.td_parametre_corpsgrade_pk  as pcg " +
            " inner join schema_utilisateur.tp_grade as gra  On gra.grade_id = pcg.grade_id " +
            " where  pcg.speciality_id = ?1  and  gra.grade_libelle = ?2 ", nativeQuery = true)
    ParametreCorpsGradePk getParamFromGradeLabel(Long id, String label);

    ParametreCorpsGradePk findByGrade_Id(Long id);


    @Query(value = " SELECT pcg.grade_id,pcg.speciality_id, pcg.type_matricule_id, pcg.params_cg_id, mat.type_mat_code, cop.cor_libelle " +
            " FROM schema_utilisateur.td_parametre_corpsgrade_pk  as pcg " +
            " inner join schema_utilisateur.tp_type_matricule as mat  On mat.type_mat_id = pcg.type_matricule_id " +
            " inner join schema_utilisateur.td_parametre_corpsgrade as pac On pac.params_cg_id = pcg.speciality_id " +
            " inner join schema_utilisateur.tp_corps as cop On cop.cor_id = pac.corps_grade_id " +
            " where mat.type_mat_code = ?1 and cop.cor_libelle= ?2  ", nativeQuery = true)
    Optional<ParametreCorpsGradePk> getParamFromMatriculeCode(String codeMat, String codeCor);

}
