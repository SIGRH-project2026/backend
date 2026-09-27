package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Avancement;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.IAvancementRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IAvancement;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;

import java.util.List;
import java.util.Optional;

public class AvancementImpl implements IAvancement {

    @Autowired
    private IAvancementRepository iAvancementRepository;

    @Override
    public Avancement getOneAvancement(long id) {
        return iAvancementRepository.findById(id).orElseThrow(
                () -> new MFPAIException(MFPAIMessage.NOT_FOUND, "with id = " + id)
        );
    }

    @Override
    public List<Avancement> getAllAvancement() {

        return null;
    }

    @Override
    public Avancement deleteAvancement(long id) {
        Optional<Avancement> avancementOptional = iAvancementRepository.findById(id);
        if(avancementOptional.isPresent()){
            Avancement avancement = avancementOptional.get();
            avancement.setIsDeleted(true);
            return avancement;
        }else{
            throw new EntityNotFoundException("agent introuvable");
        }
    }

    @Override
    public Avancement createAvance(Avancement avancement) {
        return null;
    }

    @Override
    public Avancement updateAvancement(Avancement avancement) {
        return null;
    }
}
