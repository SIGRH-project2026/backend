package sn.gainde2000.backenmfpai.services.implementations.serviceformation.expressionbesoin;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.StatutCampagne;
import sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin.StatutCampagneMapper;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.StatutCampagneRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.expressionbesoin.IStatutCampagne;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.expressionbesoin.StatutCampagneRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatutCampagneService implements IStatutCampagne {
    private final StatutCampagneRepository statutCampagneRepository;
    private final StatutCampagneMapper statutCampagneMapper;

    /**
     * création statut campagne
     * @param campagneRequestDTO
     * @return
     */
    @Override
    @Transactional
    public Response<Object> saveStatutCampagne(StatutCampagneRequestDTO campagneRequestDTO) {
        if (statutCampagneRepository.findStatutCampagneByCode(campagneRequestDTO.getCode()).isPresent())
            return Response.ok().setMessage("Campagne existe déjà.");
        return Response.ok().setPayload(statutCampagneRepository.save(statutCampagneMapper.map(campagneRequestDTO)))
                .setMessage("Création Statut Campagne réussie avec succès.");
    }

    /**
     * recupèrer statut campagne via code
     * @param code
     * @return
     */
    @Override
    public Response<Object> findByCode(String code) {
        Optional<StatutCampagne> statutCampagne = statutCampagneRepository.findStatutCampagneByCode(code);
        if (statutCampagne.isEmpty())
            return Response.ok().setMessage("Statut Campagne n'existe pas.");
        return Response.ok().setPayload(statutCampagne.get());
    }

    @Override
    public Response<Object> getAllStatutCampagne() {

        return Response.ok().setPayload(statutCampagneRepository.findStatutCampagneByIsDeletedFalse())
                .setMessage("Liste statut campagne");
    }
}
