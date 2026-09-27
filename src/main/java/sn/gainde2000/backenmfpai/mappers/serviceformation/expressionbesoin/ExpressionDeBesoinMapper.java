package sn.gainde2000.backenmfpai.mappers.serviceformation.expressionbesoin;

import org.mapstruct.Mapper;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.ExpressionDeBesoin;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.expressionbesoin.ExpressionDeBesoinResponseDTO;

@Mapper(componentModel = "spring")
public interface ExpressionDeBesoinMapper {
    ExpressionDeBesoin map(ExpressionDeBesoinDTO expressionDeBesoinDTO);

    ExpressionDeBesoinDTO map(ExpressionDeBesoin expressionDeBesoin);

    ExpressionDeBesoinResponseDTO mapToExpressionDeBesoinResponseDTO(ExpressionDeBesoin expressionDeBesoin);
}
