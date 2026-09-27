package sn.gainde2000.backenmfpai.services.implementations.serviceformation.stage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DisciplineStage;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.DisciplineRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.IDisciplineStage;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.stage.DisciplineStageRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisciplineStageService implements IDisciplineStage {
    private final DisciplineRepository disciplineRepository;
    @Override
    public Response<Object> getAllDisciplineStage() {
        return Response.ok().setPayload(disciplineRepository.findAll()).setMessage("Liste disciplines");

    }

    /**
     * enregistrer discipline de stage
     * @param disciplineStageRequest
     * @return
     */
    @Override
    public Response<Object> saveDisciplineSTage(DisciplineStageRequest disciplineStageRequest) {
        Optional<DisciplineStage> optionalDisciplineStage = disciplineRepository.findByCode(disciplineStageRequest.getCode());
        if (optionalDisciplineStage.isPresent())
            return Response.exception().setMessage("Discpline de stage existe déjà");

        DisciplineStage disciplineStage = new DisciplineStage();
        disciplineStage.setCode(disciplineStageRequest.getCode());
        disciplineStage.setLibelle(disciplineStageRequest.getCode());
        disciplineRepository.save(disciplineStage);
        return Response.ok().setMessage("discipline de stage enregistré avec succès.");
    }

}
