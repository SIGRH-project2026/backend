package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.Optional;

//@Repository
public interface IDossierAgentRepository extends JpaRepository<DossierAgent,Long>, QuerydslPredicateExecutor<DossierAgent> {

   // Optional<DossierAgent> findByDeconcentredLevel(DeconcentratedLevel dec);
    Optional<DossierAgent> findByUtilisateur(Utilisateur user);

    // Nouvelle méthode pour les statistiques
    long countByIsDeleted(boolean isDeleted);

}