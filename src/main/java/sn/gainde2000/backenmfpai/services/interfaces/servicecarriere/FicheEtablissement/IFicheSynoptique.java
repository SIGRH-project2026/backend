package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.FicheEtablissement;

import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.*;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.FicheEtablissement.FicheSynoptiqueDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;

public interface IFicheSynoptique {
    Response<Object> createFicheSynoptique (FicheSynoptiqueDTO ficheSynoptiqueDTO);
    Response<Object> updateFicheSynoptique(FicheSynoptique ficheSynoptique);
    Response<Object> getMaFicheSynoptique (Long chefEtablissementUserId);


    Response<Object> addFiliere(Long idFiche, List<FiliereDiscipline> filiereDiscipline);
    Response<Object> addClasse(Long idFiche, List<ClasseProfDiscipline> classeProfDiscipline);
    Response<Object> getMaFicheSynoptiqueByCodeEtab(String codeEtablissement);
    Response<Object> HorairesProfs(String codeEtab, int page, int size, String matricule, String nomComplet) ;
    Response<Object> discplinesAyantDeficit(String codeEtab, int page, int size, String disciplineName);
    Response<Object> discplinesAyantDeficitAllEtablisselent( int page, int size, String disciplineName);
    Response<Object> ETablissementDeficit(String disciplineName);
    Response<Object> listEtablissementAyantDeficitSurUneDiscipline(int page, int size, Long idDsiscipline);
    Response<Object> addSerie(Long idFiche, List<SerieNiveauDiscipline> serieNiveauDisciplines);
    Response<Object> addClasseSerie(Long idFiche, List<SerieClasseProfDiscipline> serieClasseProfDisciplines);
    }
