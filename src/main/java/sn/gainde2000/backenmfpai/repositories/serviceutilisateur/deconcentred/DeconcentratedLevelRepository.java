
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Abdou Karim CISSOKHO
 * @created 24/01/2024-12:36
 * @project backend_mfpai
 */
public interface DeconcentratedLevelRepository extends JpaRepository<DeconcentratedLevel, Long>, QuerydslPredicateExecutor<DeconcentratedLevel> {

    Optional<DeconcentratedLevel> findByMatricule(String matricule);

    List<DeconcentratedLevel> findByMatriculeOrderById(String matricule);

    @Query("select d.matricule from DeconcentratedLevel d where d.matricule is not null and d.matricule <> '' "
            + "group by d.matricule having count(d) > 1")
    List<String> findDuplicateMatricules();

    List<DeconcentratedLevel> findByEtablissement_Code(String code);

    List<DeconcentratedLevel> findByTypeSystemeEnseignement_Code(String code);


    @Query(value ="SELECT deco.* FROM schema_utilisateur.td_deconcentratedlevel as deco " +
            " inner join schema_utilisateur.tp_typeetablissement as etablissement ON etablissement.eta_id = deco.etablissement_id " +
            " inner join schema_utilisateur.tr_user_profile as truser On truser.user_id = deco.id " +
            " INNER join schema_utilisateur.tp_profile as profile ON profile.pro_id = truser.profile_id " +
            " where etablissement.eta_code = ?1 and profile.pro_code = 'Professeur' " +
            " ORDER BY id DESC ", nativeQuery = true)
    List<DeconcentratedLevel> getDeconnectedByProfilEtablissement(String code);

    List<DeconcentratedLevel>  findDeconcentratedLevelByIefAndProfilsContains(IEF ief, Profile profil);
    List<DeconcentratedLevel>  findDeconcentratedLevelByIaAndProfilsContains(IA ia, Profile profil);
    List<DeconcentratedLevel>  findDeconcentratedLevelByEtablissementAndProfilsContains(Etablissement etablissement, Profile profil);

 /*   @Query("SELECT dl FROM DeconcentratedLevel dl JOIN dl.profils p WHERE dl.ia =:ia AND p =:profil")
    DeconcentratedLevel findDeconcentratedLevelByIaAndProfilsContains(@Param("ia")IA ia, @Param("profil") Profile profil);

    @Query("SELECT dl FROM DeconcentratedLevel dl JOIN dl.profils p WHERE dl.ief =:ief AND p =:profil")
    DeconcentratedLevel findDeconcentratedLevelByIefAndProfilsContains(@Param("ief") IEF ief,@Param("profil") Profile profil);

    @Query("SELECT dl FROM DeconcentratedLevel dl JOIN dl.profils p WHERE dl.etablissement =:etablissement AND p =:profil")
    DeconcentratedLevel findDeconcentratedLevelByEtablissementAndProfilsContains(@Param("etablissement")Etablissement etablissement, @Param("profil") Profile profil);
*/

    /* Cheikh Oumar*/

    @Query(value = """
            SELECT uti.* FROM schema_utilisateur.td_deconcentratedlevel as uti
 
            JOIN schema_utilisateur.tr_user_profile userpro ON  uti.id = userpro.user_id
 
            JOIN  schema_utilisateur.tp_profile as pro  ON pro.pro_id = userpro.profile_id
 
            where pro.pro_libelle = 'Formateur EFF'
            """, nativeQuery = true)
    List<DeconcentratedLevel> findByProfileCode();
}
