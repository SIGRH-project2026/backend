package sn.gainde2000.backenmfpai.mappers.serviceutilisateur;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.UserManager;

import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UserManagerRequestDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.UserManagerResponseDTO;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abdou Karim CISSOKHO
 * @created 02/02/2024-10:52
 * @project backend_mfpai
 */

@Mapper
public interface UserManagerMapper extends EntityMapper<UserManager, UserManagerRequestDTO, UserManagerResponseDTO> {

    @Named("ignoreTraining")
    UserManagerResponseDTO toDtoWithout(UserManager userManager);

    default List<UserManagerResponseDTO> toDtoList(List<UserManager> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<UserManagerResponseDTO> toDtoPage(Page<UserManager> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<UserManagerResponseDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
