package sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.parametrage;


import org.springframework.data.domain.Page;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Diplomes;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Fonction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.*;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.DiplomeReqDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.RegionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.BureauDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DirectionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.DivisionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.centrale.FonctionDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.parametrage.deconnected.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.Parametrage.DiplomeBaseDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 06/08/2024-08:39
 * @project backend_mfpai
 */
public interface ParametrageService {

    // REGION
    Region addRegion(RegionDTO dto);
    Region updateRegion(Long id,RegionDTO dto);
    Region getRegion(Long id);
    Response<Object> getPageRegion(int page, int size,String filter);


    // DIRECTION
    Direction addDirection(DirectionDTO dto);
    Direction updateDirection(Long id,DirectionDTO dto);
    Direction getDirection(Long id);
    Direction activateDirection(Long id);
    Response<Object> getPageDirection(int page, int size,String filter,  Boolean  statut);


    // DIVISION
    Division addDivision(DivisionDTO dto);
    Division updateDivision(Long id,DivisionDTO dto);
    Division getDivision(Long id);
    Division activateDivision(Long id);
    Response<Object> getPageDivision(int page, int size,String filter,  Boolean  statut);

    // BUREAU
    Bureau addBureau(BureauDTO dto);
    Bureau updateBureau(Long id,BureauDTO dto);
    Bureau getBureau(Long id);
    Bureau activateBureau(Long id);
    Response<Object> getPageBureau(int page, int size,String filter, Boolean  statut);

    // IA
    IA addIA(IADTO dto);
    IA updateIA(Long id,  IADTO dto);
    IA getIA(Long id);
    IA activateIA(Long id);
    Response<Object> getPageIA(int page, int size, String filter,  Boolean  statut);

    
    // IEF
    IEF addIEF(IEFDTO dto);
    IEF updateIEF(Long id, IEFDTO dto);
    IEF getIEF(Long id);
    IEF activateIEF(Long id);
    Response<Object> getPageIEF(int page, int size, String filter,  Boolean  statut);


    // Etablissement
    Etablissement addEtablissement( EtablissementDTO dto);
    Etablissement updateEtablissement(Long id, EtablissementDTO dto);
    Etablissement getEtablissement(Long id);
    Etablissement activateEtablissement(Long id);
    Response<Object> getPageEtablissement(int page, int size, String filter, Boolean  statut);


    // Speciality
    Speciality addSpeciality(SpecialityDTO dto);
    Speciality updateSpeciality(Long id, SpecialityDTO dto);
    Speciality getSpeciality(Long id);
    Speciality activateSpeciality(Long id);
    Response<Object> getPageSpeciality(int page, int size, String filter, Boolean statut);


    // Fonction
    Fonction addFonction(FonctionDTO dto);
    Fonction updateFonction(Long id, FonctionDTO dto);
    Fonction getFonction(Long id);
    Fonction activateFonction(Long id);
    Response<Object> getPageFonction(int page, int size, String filter, Boolean statut);

    // Speciality
    SpecialityEtablissement addSpecialityEtablissement(SpecialityEtablissementDTO dto);
    SpecialityEtablissement updateSpecialityEtablissement(Long id, SpecialityEtablissementDTO dto);
    SpecialityEtablissement getSpecialityEtablissement(Long id);
    SpecialityEtablissement activateSpecialityEtablissement(Long id);
    Response<Object> getPageSpecialityEtablissement(int page, int size, String filter, Boolean statut);



    // Diplome
    Diplomes addDiplome(DiplomeReqDTO dto);
    Diplomes updateDiplome(Long id, DiplomeReqDTO dto);
    Diplomes getDiplome(Long id);
    Diplomes activateDiplome(Long id);
    Response<Object> getPageDiplome(int page, int size, String filter, Boolean statut, String typeDiplome);


    Page<DiplomeBaseDTO> getDiplomesPage(String codeFilter, String labelFilter, int page, int size);



}
