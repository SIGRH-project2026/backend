package sn.gainde2000.backenmfpai.services.interfaces.serviceformation.formation;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.OffreTechniqueFinanciere;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.formation.OffreTechniqueFinanciereDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public interface IOffreTechniqueFinanciereService {

    OffreTechniqueFinanciere createOffreTechniqueFinanciere(MultipartFile[] files,
            OffreTechniqueFinanciereDTO offreTechniqueFinanciereDTO);

    List<OffreTechniqueFinanciereDTO> getOffreTechniqueFinanciereByFormationId(Long formationId);

    void deleteOffreTechniqueFinanciere(Long id);

    List<OffreTechniqueFinanciere> getAllOffreTechniqueFinanciere();

    OffreTechniqueFinanciereDTO updateOffreTechniqueStatus(Long id, String newStatutOffreCode);

}