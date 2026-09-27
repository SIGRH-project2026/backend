
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;

/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:29
 * @project backend_mfpai
 */
public interface EtablissementRepository
        extends JpaRepository<Etablissement, Long>, QuerydslPredicateExecutor<Etablissement> {
    List<Etablissement> findByIef_Code(String code);

    List<Etablissement> findByIa_Code(String code);
    //List<Etablissement> findByIa_CodeAndStructure_Code(String codeIA, String codeStruc);
    List<Etablissement> findByStructure_Code( String code);
    List<Etablissement> findByTypeEtablissement_Code( String code);
    // LISTE DES ETABLISSEMENTS PAR TYPE DE SYSTEME D'ENSEIGNEMENT
    List<Etablissement> findByTypeSystemeEnseignement_Code( String code);

    Optional<Etablissement> findByCode(String code);

    List<Etablissement> findByLabelIgnoreCase(String label);

    @Query("select eta from Etablissement eta where eta.typeEtablissement =: type ")
    List<Etablissement> getListEtablissement(@Param("type") String type);

    @Query("select eta from Etablissement eta where eta.typeSystemeEnseignement =: typesystemeEnseignement ")
    List<Etablissement> getListEtablissementByTypeSystemeEnseignement(@Param("typesystemeEnseignement") String typesystemeEnseignement);

    //indicateur
    @Query("SELECT COUNT(etab) FROM Etablissement etab WHERE etab.ief.code = :codeIef ")
    long countAllEtablissementIef(String codeIef);
    @Query("SELECT COUNT(etab) FROM Etablissement etab WHERE etab.ia.code = :codeIa ")
    long countAllEtablissementIa(String codeIa);
    @Query("SELECT COUNT(etab) FROM Etablissement etab ")
    long countAllEtablissemen();
}
