package sn.gainde2000.backenmfpai.web.dtos.responses.serviceformation.courrier;

import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceformation.courrier.TypeDemandeCourrier;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.repositories.serviceformation.courrier.NomTypeCourrier;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CourrierResponse {
    private long id;
    private String reference;
    private LocalDate createdAt;
    private TypeDemandeCourrier typeDemande;
    private Direction direction;
    private Division division;
    private Services service;
    private Bureau bureau;
    private String statut;
    private String otherField;
    private NomTypeCourrier nomTypeCourrier;

}
