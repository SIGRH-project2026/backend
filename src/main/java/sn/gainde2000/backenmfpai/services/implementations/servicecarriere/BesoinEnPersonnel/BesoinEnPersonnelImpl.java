
package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.BesoinEnPersonnel;

import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;

import com.querydsl.core.BooleanBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.commons.exception.GenericApiException;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.QBesoinEnPersonnel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.StatutBEP;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelMapper;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;

import sn.gainde2000.backenmfpai.repositories.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.BesoinEnPersonnel.StatutBEPRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.BesoinEnPersonnel.IBesoinEnPersonnel;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelRDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.IndicateurMutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class BesoinEnPersonnelImpl implements IBesoinEnPersonnel {
    private final BusinessNotificationService businessNotifications;

    private final BesoinEnPersonnelRepository besoinEnPersonnelRepository;
    private final BesoinEnPersonnelMapper besoinEnPersonnelMapper;
    private final StatutBEPRepository statutBEPRepository;
    private final IUtilisateurRepository iUtilisateurRepository;
    private final IUtilisateur iUtilisateur;
    @Override
    @Transactional
    public Response<Object> createBEP(BesoinEnPersonnelDTO besoinEnPersonnelDTO) {

        try {
            Long userId = besoinEnPersonnelDTO.userId();
            Utilisateur utilisateur = iUtilisateurRepository.findById(userId)
                    .orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));

            BesoinEnPersonnel besoinEnPersonnel = this.besoinEnPersonnelMapper.toEntity(besoinEnPersonnelDTO);
            besoinEnPersonnel.setUtilisateur(utilisateur);

//            StatutBEP statutBEP = statutBEPRepository.findById(1L)
//                    .orElseThrow(() -> new GenericApiException("Statut BEP inexistant"));
//            besoinEnPersonnel.setStatut(statutBEP);
            besoinEnPersonnel = this.besoinEnPersonnelRepository.save(besoinEnPersonnel);

            utilisateur.getBesoinsEnPersonnel().add(besoinEnPersonnel);
            iUtilisateurRepository.save(utilisateur);
            businessNotifications.notify(utilisateur, "Besoin en personnel",
                    "Votre besoin en personnel n° " + besoinEnPersonnel.getId() + " a été enregistré.");
            return Response.ok()
                    .setMessage("Besoin en personnel soumis. ")
                    .setPayload(this.besoinEnPersonnelMapper.toDto(besoinEnPersonnel));
       } catch (Exception e) {
            org.springframework.transaction.interceptor.TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Response.exception()
                    .setMessage("Une erreur s'est produite lors de la soumission du BEP.");
        }
    }

    @Override
    public Response<Object> listBEP(Long userId, int page, int size, String statut, String matricule, String etablissement,

                                    String region, String ia, String ief, String prenom, String nom, Long reference ) {

        try {
            Page<BesoinEnPersonnelRDTO> besoinEnPersonnels;
             QBesoinEnPersonnel besoinEnPersonnel = QBesoinEnPersonnel.besoinEnPersonnel;
            BooleanBuilder conditions = new BooleanBuilder();

            if (userId != 0) {
             conditions.and(besoinEnPersonnel.utilisateur.id.eq(userId));
             }
//            if (!Objects.equals(statut, "")) {
//             conditions.and(besoinEnPersonnel.statut.libelle.eq(statut));
//             }
            if (!Objects.equals(matricule, "")) {
             conditions.and(besoinEnPersonnel.utilisateur.matricule.eq(matricule));
             }
             if (!Objects.equals(etablissement, "")) {
               conditions.and(besoinEnPersonnel.etablissement.label.eq(etablissement));
             }
            if (!Objects.equals(region, "")) {
                conditions.and(besoinEnPersonnel.region.label.eq(region));
            }
            if (!Objects.equals(ia, "")) {
                conditions.and(besoinEnPersonnel.ia.label.eq(ia));
            }
            if (!Objects.equals(ief, "")) {
                conditions.and(besoinEnPersonnel.ief.label.eq(ief));
            }
            if (reference != 0) {
                conditions.and(besoinEnPersonnel.id.eq(reference));
            }
            if (!Objects.equals(prenom, "")) {
                conditions.and(besoinEnPersonnel.utilisateur.prenom.eq(prenom));
            }
            if (!Objects.equals(nom, "")) {
                conditions.and(besoinEnPersonnel.utilisateur.nom.eq(nom));
            }

            /*
             * JPAQuery<BesoinEnPersonnel> query =
             * queryFactory.selectFrom(besoinEnPersonnel)
             * .where(conditions);
             */

            /*
             * if (matricule != null) {
             * conditions.and(besoinEnPersonnel.utilTisateur.matricule.eq(matricule));
             * }
             */

            /*
             * if (motCle != null) {
             * conditions.and(besoinEnPersonnel.commentaire.containsIgnoreCase(motCle));
             * }
             */

            besoinEnPersonnels = Objects.nonNull(conditions.getValue()) ? besoinEnPersonnelRepository
                    .findAll(conditions.getValue(), PageRequest.of(page, size,
                            Sort.by(Sort.Direction.DESC, "id")))
                    .map(besoinEnPersonnelMapper::toDto)
                    : besoinEnPersonnelRepository.findAll(PageRequest.of(page, size,
                            Sort.by(Sort.Direction.DESC, "id")))
                            .map(besoinEnPersonnelMapper::toDto);

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(besoinEnPersonnels.getSize())
                    .totalPages(besoinEnPersonnels.getTotalPages())
                    .totalElements(besoinEnPersonnels.getTotalElements())
                    .number(besoinEnPersonnels.getNumber())
                    .build();

            log.info("============> GET LIST BESOIN EN PERSONNEL SUCCEFULLY");
            return Response.ok()
                    .setPayload(besoinEnPersonnels.getContent())
                    .setMetadata(pageMetadata).setMessage("Liste des besoins en personnels");
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur est servenue lors de la récupération des BEP :" + e.getMessage());

        }

    }

    @Override
    public Response<Object> getOneBEP(Long idBEP) {
        try {

            BesoinEnPersonnel besoinEnPersonnel = besoinEnPersonnelRepository.findById(idBEP)
                    .orElseThrow(() -> new GenericApiException("Un besoin en personnel avec un tel Id n'existe pas."));

            return Response.ok()
                    .setMessage("Besoin en personnel recupérer")
                    .setPayload(besoinEnPersonnelMapper.toDto(besoinEnPersonnel));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de la récupération du BEP : " + idBEP);
        }
    }

    @Override
    public Response<Object> indicateurBEP(String codeProfile) {
        long all = 0;
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        CentralLevel centralLevel = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (utilisateurConnected.getTypeUser().equals("CEN")) {
            centralLevel = (CentralLevel) utilisateurConnected;
            List<Profile> profiles = new ArrayList<>(centralLevel.getProfils());
            Profile profile =profiles.get(0);
            String pro = profile.getCode();

            switch (pro){
                case "ADMIN-DRH":
                case "Chef-division-dgpeec":
                case  "Directeur-DRH" :
                    all = besoinEnPersonnelRepository.countAllBep();
                    break;
                default:
                    break;
            }

        } else {
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            switch (codeProfile) {

                case "Représentant-IEF":
                    all = besoinEnPersonnelRepository.countAllBepIef(deconcentratedLevel.getIef().getCode());
                    break;
                case "Representant-IA":
                    all = besoinEnPersonnelRepository.countAllBepIa(deconcentratedLevel.getIa().getCode());
                    break;

                default:
                    break;
            }
        }

        return Response.ok().setMessage("Indicateurs besoin en personnels").setPayload(all);
    }
}
