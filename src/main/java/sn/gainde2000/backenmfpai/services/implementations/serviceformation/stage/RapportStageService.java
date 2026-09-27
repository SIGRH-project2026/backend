package sn.gainde2000.backenmfpai.services.implementations.serviceformation.stage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.RapportStage;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.DemandeStageRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.RapportStageRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IRapportStage;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.RapportStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RapportStageService implements IRapportStage {
    private final RapportStageRepository rapportStageRepository;
    private final IUtilisateur iUtilisateur;
    private final CentralLevelRepository centralLevelRepository;
    private final DemandeStageRepository demandeStageRepository;

    /**
     * enregistrer rapport de stage
     * @param rapportStageRequest
     * @return
     */
    @Override
    public Response<Object> saveRapportStage(RapportStageRequest rapportStageRequest) {
        Optional<DemandeStage> demandeStage = demandeStageRepository.findById(rapportStageRequest.getDemandeStageId());
        if (demandeStage.isEmpty())
            return Response.exception().setMessage("Demande de stage inéxistante");
        CentralLevel centralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).orElse(null);
        RapportStage rapportStage = new RapportStage();
        rapportStage.setUtilisateur(centralLevel);
        rapportStage.setDemandeStage(demandeStage.get());
        rapportStage.setCommentaire(rapportStage.getCommentaire());
        demandeStage.get().setHaveRapport(true);
        demandeStageRepository.save(demandeStage.get());
        rapportStageRepository.save(rapportStage);
        return Response.ok().setMessage("Demande Stage imputée avec succès.").setPayload(rapportStageRepository.save(rapportStage));
    }
}
