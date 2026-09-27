package sn.gainde2000.backenmfpai.services.interfaces.servicecarriere;
import org.springframework.data.domain.Page;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Avancement;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DossierAgent;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DossierAgentRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DossierAgentResponseDto;
import java.io.IOException;
import java.util.List;
/**
 * @author bsdieme
 */
public interface IDossierAgent {
    public DossierAgent createDossier(DossierAgentRequestDto dto) throws IOException;
    public Page<DossierAgent> getAllDossierAgent(int page, int size, String adresse, String matricule, String nom, String prenom);
    public  DossierAgent deleteDossier(Long id);
    public DossierAgentResponseDto getOneDossierAgent(Long id);
    DossierAgentResponseDto getDossierCurrentUser();
    public DossierAgentResponseDto rechercheDossierAgent(String matricule);
    public List<Diplome> getDiplomesByMatricule(String matricule, int page, int size);
   // public List<Avancement> getAvancementsByMatricule(String matricule, int page, int size);
    public boolean hasDossier(String matricule);
    public DossierAgentResponseDto createEmptyDossierForUser(Utilisateur user);
    

}
