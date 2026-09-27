package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.BesoinEnPersonnel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.Filiere;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.BesoinEnPersonnel.FiliereMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.BesoinEnPersonnel.FiliereRepository;

import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.BesoinEnPersonnel.IFiliere;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.FiliereDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FiliereImpl implements IFiliere {
    private final FiliereRepository filiereRepository;
    private final FiliereMapper filiereMapper;

    @Override
    @Transactional
    public Response<Object> createFiliere(FiliereDTO filiereDTO) {
        try {

            Filiere filiere = filiereMapper.toEntity(filiereDTO);
            filiere = filiereRepository.save(filiere);

            return Response.ok()
                    .setMessage("Liste des filiéres")
                    .setPayload(filiereMapper.toDto(filiere));

        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur c'est produit lors de la création de la filiére : " +
                            e);
        }
    }

    @Override
    public Response<Object> listeFiliere() {
        try {
            List<Filiere> filieres = filiereRepository.findAll();

            return Response.ok()
                    .setMessage("Liste des filiéres")
                    .setPayload(filiereMapper.toDtoList(filieres));
        } catch (Exception e) {
            return Response.exception().setMessage("Erreur lors de la recupération de la liste des filiéres");
        }

    }
}
