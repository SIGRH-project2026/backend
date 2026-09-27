package sn.gainde2000.backenmfpai.mappers.servicecarriere.FicheEtablissement;


import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.FicheSynoptique;
import sn.gainde2000.backenmfpai.mappers.EntityMapper;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.FicheEtablissement.FicheSynoptiqueDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.FicheEtablissement.FicheSynoptiqueRDTO;



@Mapper
public interface IFicheSynoptiqueMapper extends EntityMapper<FicheSynoptique, FicheSynoptiqueDTO, FicheSynoptiqueRDTO> {
    FicheSynoptique toEntity(FicheSynoptiqueDTO ficheSynoptiqueDTO);
    FicheSynoptiqueRDTO toDto(FicheSynoptique ficheSynoptique);

}
