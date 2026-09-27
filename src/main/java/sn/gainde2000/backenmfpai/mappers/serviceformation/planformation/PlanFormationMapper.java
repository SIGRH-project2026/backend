package sn.gainde2000.backenmfpai.mappers.serviceformation.planformation;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;

import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation.PlanFormationDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.planformation.PlanFormationResDTO;

@Mapper
public interface PlanFormationMapper extends EntityMapper<PlanFormation, PlanFormationDTO, PlanFormationResDTO> {

    // @Named("mapUtilisateurDTOToUtilisateur")
    // static Utilisateur mapUtilisateurDTOToUtilisateur(UtilistateurDTO
    // utilisateurDTO) {
    // // Ajoutez votre logique de conversion ici
    // Utilisateur utilisateur = new Utilisateur();
    // utilisateur.setNom(utilisateurDTO.getNom());
    // // Autres attributs à mapper
    // return utilisateur;
    // }

    default List<PlanFormationResDTO> toDtoList(List<PlanFormation> entityList) {
        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<PlanFormationResDTO> toDtoPage(Page<PlanFormation> entityPage) {
        List<PlanFormationResDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    default StatutPlanFormation mapStatutPlanFormationIdToStatutPlanFormation(Long statutPlanFormationId) {
        if (statutPlanFormationId == null) {
            return null;
        }

        StatutPlanFormation statutPlanFormation = new StatutPlanFormation();
        statutPlanFormation.setId(statutPlanFormationId);
        return statutPlanFormation;
    }

    @Named("ignoreTraining")
    PlanFormationResDTO toDtoWithout(PlanFormation planFormation);

    @Override
    // @Mapping(target = "utilisateur", source = "utilisateur", qualifiedByName =
    // "mapUtilisateurDTOToUtilisateur")
    PlanFormation toEntity(PlanFormationDTO dto);

    @Override
    PlanFormationResDTO toDto(PlanFormation planFormation);

}
