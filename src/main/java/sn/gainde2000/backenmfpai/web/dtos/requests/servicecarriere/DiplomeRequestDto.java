package sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;

import java.io.File;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor

public class DiplomeRequestDto {

    private Long id;
    private String dipNom;

    //private File filename;
    private LocalDate dipDateObtention;
    private DossierAgent dossierAgent;
}
