package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.List;
import java.util.Optional;

/**
 * @author G2k R&D
 */

public interface IUtilisateurRepository
        extends JpaRepository<Utilisateur, Long>, QuerydslPredicateExecutor<Utilisateur> {
    Optional<Utilisateur> findUtilisateurByEmail(String username);
    Optional<Utilisateur> findUtilisateurByEmailIgnoreCase(String username);

    @Query("select u from Utilisateur u where " +
            "upper(replace(replace(trim(u.matricule), '/', ''), ' ', '')) = " +
            "upper(replace(replace(trim(:matricule), '/', ''), ' ', ''))")
    Optional<Utilisateur> findUtilisateurByNormalizedMatricule(@Param("matricule") String matricule);
    Optional<Utilisateur> findUtilisateurByEmailAndStatus(String username, Boolean status);

    Optional<Utilisateur> findUtilisateurByCni(String username);
    List<Utilisateur> findAllByCni(String cni);

    Optional<Utilisateur> findUtilisateurByTelephone(String telephone);
    List<Utilisateur> findAllByTelephone(String telephone);

    @Query("SELECT u FROM Utilisateur u JOIN u.profils p WHERE p.code IN :profileCodes")
    List<Utilisateur> findByProfileCodes(List<String> profileCodes);

    Optional<Utilisateur> findUtilisateurByMatricule(String matricule);
    List<Utilisateur> findAllByMatricule(String matricule);
    List<Utilisateur> findAllByEmail(String email);
    Optional<Utilisateur> findUtilisateurByMatriculeIgnoreCase(String matricule);

    @Query("select u.matricule from Utilisateur u where u.matricule is not null")
    List<String> findAllMatricules();

    @Query("select u.email from Utilisateur u where u.email is not null")
    List<String> findAllEmails();

    @Query("select u.cni from Utilisateur u where u.cni is not null")
    List<String> findAllCnis();

}
