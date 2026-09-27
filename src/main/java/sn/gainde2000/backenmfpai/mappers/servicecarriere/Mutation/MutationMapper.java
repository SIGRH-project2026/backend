package sn.gainde2000.backenmfpai.mappers.servicecarriere.Mutation;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.MutationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelRDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.MutationRDTO;

import java.util.List;

@Mapper
public interface MutationMapper extends EntityMapper<Mutation, MutationDTO, MutationRDTO> {
    Mutation toEntity(MutationDTO mutationDTO);
    MutationRDTO toDto(Mutation mutation);
    List<MutationRDTO> toDtoList(List<Mutation> mutations);
}
