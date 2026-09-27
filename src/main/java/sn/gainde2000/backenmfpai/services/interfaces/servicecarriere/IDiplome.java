package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;

import org.springframework.data.domain.Page;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DiplomeRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DiplomeResponseDto;

/**
 * @author bsdieme
 */

public interface IDiplome {

    DiplomeResponseDto getOneDiplome(Long id);

    Page<Diplome> getAllDiplomes(int page, int size, String filter, boolean sortByDescending);

    DiplomeResponseDto deleteDiplome(Long id);

    Diplome createDiplome (DiplomeRequestDto dto);

    Diplome updateDiplome(DiplomeRequestDto dto);
}
