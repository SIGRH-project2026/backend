package sn.gainde2000.backenmfpai.repositories.servicecarriere.FicheEtablissement;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.Classe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.ClasseProfDiscipline;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.FicheSynoptique;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.Optional;

public interface IFicheSynoptiqueRepository extends JpaRepository<FicheSynoptique, Long>, QuerydslPredicateExecutor<FicheSynoptique> {

    Optional<FicheSynoptique> findFicheSynoptiqueByChefEtablissemnt(DeconcentratedLevel chefEtablissemnt);
    Optional<FicheSynoptique> findFicheSynoptiqueByChefEtablissemnt_EtablissementCode( String chefEtablissemnt_etablissement_code);
    Boolean findFicheSynoptiqueByChefEtablissemnt_Etablissement_Id(Long id);
}
