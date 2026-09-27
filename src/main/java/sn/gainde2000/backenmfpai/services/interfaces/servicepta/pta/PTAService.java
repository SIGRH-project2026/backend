package sn.gainde2000.backenmfpai.services.interfaces.servicepta.pta;

import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicepta.pta.*;

import sn.gainde2000.backenmfpai.exceptions.MFPAIResponse;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.pta.*;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.pta.ActionPTAResponseDTO;

import java.time.LocalDate;
import java.util.List;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/05/2024-14:32
 * @project backend_mfpai
 */
public interface PTAService {

    ActionPTA addActionPTA(ActionPTARequestDTO dto);
    ActionPTA updateActionPTA(Long id, ActionPTARequestDTO dto);
    ActionPTA addActionPTASingle(ActionPTARequestSingleDTO dto);
    ActionPTA getActionPTA(Long id);
    List<ActionPTA> getListActionPTA(Long id);
    ActionPTA deleteActionPTA(Long id);


    // Resultat pta
    ResultPTA addResultPTA(ResultPTARequestDTO dto);
    ResultPTA updateResultPTA(Long id, ResultPTARequestDTO dto);
    ResultPTA getResultPTA(Long id);

    //plan de travail
    PlanDeTravail addInitialPTA(InitialPTARequestDTO dto);
    PlanDeTravail getPlanDeTravail(Long id);


    // sous action pta
    SubActionPTA addSubActionPTA(SubActionPTAReqDTO dto);
    SubActionPTA updateSubActionPTA(Long id, SubActionPTAReqDTO dto);
    SubActionPTA getSubActionPTA(Long id);


    // mode de calcule
    ModeCalcul addModeCalcul(ModeCalculRequestDTO dto);
    ModeCalcul getModeCalcul(Long id);


    // Realisation reporting
    ReportRealisation addReportRealisation(MultipartFile[] files, ReportRealisationDTO dto);
    ReportRealisation getReportRealisation(Long id);




    Response<Object> getAllResultPTA(int page, int size ,Long id);

    Response<Object> getPTAWithFilterAdvanced(int page, int size, String filter, String numPta,
                                              String libelle, String responssable, String division, String debut, String fin);



    Response<Object> getSubAction(Long id, int page, int size, String filter);


    Response<Object> getStaPTA();



}
