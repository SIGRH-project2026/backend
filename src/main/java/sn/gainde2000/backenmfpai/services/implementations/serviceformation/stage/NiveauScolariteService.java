package sn.gainde2000.backenmfpai.services.implementations.serviceformation.stage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.NiveauScolaireRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceformation.stage.INiveauScolarite;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

@Service
@RequiredArgsConstructor
public class NiveauScolariteService implements INiveauScolarite {
    private final NiveauScolaireRepository niveauScolaireRepository;

    /**
     * recuperation niveau de scolarite
     * @return
     */
    @Override
    public Response<Object> getAllNiveauScolarite() {
        return Response.ok().setPayload(niveauScolaireRepository.findAll()).setMessage("Liste des niveaux de scolarité.");
    }
}
