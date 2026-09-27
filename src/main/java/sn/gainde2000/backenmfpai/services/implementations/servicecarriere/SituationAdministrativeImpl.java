package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.SituationAdministrative;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.ISituationAdministrativeRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.ISituationAdministrative;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Data
public class SituationAdministrativeImpl implements ISituationAdministrative {

    private final ISituationAdministrativeRepository iSituationAdministrativeRepository;

    @Override
    public SituationAdministrative AddSituation(SituationAdministrative situationAdministrative) {
        return iSituationAdministrativeRepository.save(situationAdministrative);
    }

    @Override
    public SituationAdministrative deleteSituation(long id) {
        Optional<SituationAdministrative> situationAdministrativeOptional = iSituationAdministrativeRepository.findById(id);
        if (situationAdministrativeOptional.isPresent()){
           SituationAdministrative situationAdministrative = situationAdministrativeOptional.get();
           situationAdministrative.setIsDeleted(true);
           return iSituationAdministrativeRepository.save(situationAdministrative);
        }else{
            throw new EntityNotFoundException("agent introuvable");
        }
    }

    @Override
    public SituationAdministrative getOneSituation(Long id) {
        return iSituationAdministrativeRepository.findById(id).orElseThrow(
                () -> new MFPAIException(MFPAIMessage.NOT_FOUND, "with id = " + id));
    }

    @Override
    public List<SituationAdministrative> getAllSituations() {
        return null;
    }

    @Override
    public SituationAdministrative updateSituationAdministrative(SituationAdministrative situationAdministrative) {
        return null;
    }
}
