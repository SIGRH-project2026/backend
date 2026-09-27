package sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Direction;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Services;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.UtilisateurDTO;

/**
 * @author Abdou Karim CISSOKHO
 * @created 23/01/2024-15:20
 * @project backend_mfpai
 */


@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CentralLevelDTO  extends UtilisateurDTO {

     private Division division;
     private Services service;
     private Bureau bureau;
     private Direction direction;




}
