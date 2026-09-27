package sn.gainde2000.backenmfpai.services.implementations.serviceformation.stage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.AttestationStage;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.AttestationStageRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.DemandeStageRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IAttestionStage;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.AttestationStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttestationService implements IAttestionStage {
    private final DemandeStageRepository demandeStageRepository;
    private final CentralLevelRepository centralLevelRepository;
    private final AttestationStageRepository attestationStageRepository;
    private final IUtilisateur iUtilisateur;

    /**
     * enregistrer attestation de stage
     * @param attestationStageRequest
     * @return
     */
    @Override
    public Response<Object> saveAttestionStage(AttestationStageRequest attestationStageRequest) {
        Optional<DemandeStage> optionalDemandeStage = demandeStageRepository.findById(attestationStageRequest.getDemandeStageId());
        if (optionalDemandeStage.isEmpty())
            return Response.exception().setMessage("Demande de stage inéxistante.");
        CentralLevel centralLevel = centralLevelRepository.findByEmail(iUtilisateur.getCurrentUser().getEmail()).orElse(null);
        AttestationStage attestationStage = new AttestationStage();
        attestationStage.setDemandeStage(optionalDemandeStage.get());
        attestationStage.setUtilisateur(centralLevel);
        attestationStage.setCommentaire(attestationStage.getCommentaire());
        optionalDemandeStage.get().setHaveAttestation(true);
        demandeStageRepository.save(optionalDemandeStage.get());
        return Response.ok().setPayload(attestationStageRepository.save(attestationStage)).setMessage("Attestation de stage enregistrée avec succès.");
    }
}
