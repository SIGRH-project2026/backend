package sn.gainde2000.backenmfpai.mappers.servicesociale;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicesociale.PriseEnCharge;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Actes.ActeDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicesociale.PriseEnChargeDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicesociale.PriseEnChargeResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface PriseEnChargeMapper {

    PriseEnCharge toEntity(PriseEnChargeDTO priseEnChargeDTO);
    PriseEnChargeResponseDTO toDto(PriseEnCharge priseEnCharge);
    List<PriseEnChargeResponseDTO> toDtoList(List<PriseEnCharge> priseEnCharges);
}
