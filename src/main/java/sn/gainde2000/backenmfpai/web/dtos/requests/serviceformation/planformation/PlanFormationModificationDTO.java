package sn.gainde2000.backenmfpai.web.dtos.requests.serviceformation.planformation;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class PlanFormationModificationDTO {
    private String titre;
    private String commentaire;
    private Date datePublication;
    private Date dateDebut;
    private Date dateFin;
    private List<Long> fichiersAAjouter;
    private List<Long> fichiersASupprimer;
}
