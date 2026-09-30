package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Permutation;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.commons.Notification.Notification;
import sn.gainde2000.backenmfpai.commons.Notification.INotification;
import sn.gainde2000.backenmfpai.commons.utils.JasperGenerator;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.QPermutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.StatusPermutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.TraitementPermutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Permutation.PermutationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.IPermutationRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.IStatusPermutation;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.TraitementPermutationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IProfilRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.RegionRepository;
import sn.gainde2000.backenmfpai.services.implementations.files.FileImpl;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IPermutationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.INotificationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Permutation.PermutationRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.IndicateurMutation;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.IndicateursPermutations;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PermutationResponseDto;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

import static sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.QPermutation.permutation;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PermutationServiceImpl implements IPermutationService {
    private final BusinessNotificationService businessNotifications;


    private final IUtilisateurRepository iUtilisateurRepository;
    private final IPermutationRepository iPermutationRepository;
    private final PermutationMapper permutationMapper;
    private final IUtilisateur iUtilisateur;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final JasperGenerator jasperGenerator;
    private final FileImpl fileImpl;
    private final RegionRepository regionRepository;
    private final IStatusPermutation iStatusPermutationRepository;
    private final TraitementPermutationRepository traitementPermutationRepository;
    private final INotificationService notificationService;
    private final INotification iNotification;
    private final IProfilRepository iProfilRepository;
    private final CentralLevelRepository centralLevelRepository;

    @Override
    @Transactional
    public Permutation addPermutation(PermutationRequestDto permutationRequestDto) {

        Optional<DeconcentratedLevel> user1Optional = deconcentratedLevelRepository.findByMatricule(permutationRequestDto.getMatriculeUtilisateur1());
        Optional<DeconcentratedLevel> user2Optional = deconcentratedLevelRepository.findByMatricule(permutationRequestDto.getMatriculeUtilisateur2());
        if(user1Optional.isPresent() && user2Optional.isPresent()){
            DeconcentratedLevel user1 = user1Optional.get();
            DeconcentratedLevel user2 = user2Optional.get();
            boolean sameSpeciality = user1.getSpeciality() != null && user2.getSpeciality() != null
                    && Objects.equals(user1.getSpeciality().getCode(), user2.getSpeciality().getCode());
            boolean sameCorps = user1.getCorpsGrade() != null && user2.getCorpsGrade() != null
                    && Objects.equals(user1.getCorpsGrade().getCode(), user2.getCorpsGrade().getCode());
            boolean differentEstablishment = user1.getEtablissement() != null && user2.getEtablissement() != null
                    && !Objects.equals(user1.getEtablissement().getCode(), user2.getEtablissement().getCode());
            if (sameSpeciality && sameCorps && differentEstablishment){
                TraitementPermutation traitementPermutation = new TraitementPermutation();

                StatusPermutation statut = iStatusPermutationRepository.findByCode("SOUMISE");
                traitementPermutation.setStatut(statut);
                traitementPermutation.setDateTraitementMutation(LocalDate.now());
                traitementPermutation.setTraiteur(user1Optional.get());
                TraitementPermutation traitementPermutationSaved = traitementPermutationRepository.save(traitementPermutation);
                Permutation permutation = new Permutation();
                permutation = permutationMapper.toEntity(permutationRequestDto);
                permutation.setUtilisateur1(user1Optional.get());
                permutation.setUtilisateur2(user2Optional.get());
                permutation.setDatePermutation(LocalDate.now());
                permutation.setIaDemandeur(user1Optional.get().getIa());
                permutation.setIaReceveur(user2Optional.get().getIa());
                permutation.setTraitementPermutation(traitementPermutationSaved);
                permutation.setEtablissementDemandeur(user1Optional.get().getEtablissement());
                permutation.setEtablissementReceveur(user2Optional.get().getEtablissement());
                permutation.setIefDemandeur(user1Optional.get().getIef());
                permutation.setIefReceveur(user2Optional.get().getIef());
                List<String> emailsTraitant = new ArrayList<>();
                emailsTraitant.add(user1Optional.get().getEmail());
                permutation.setEmailTraitant(emailsTraitant);
                Permutation permutationSaved = iPermutationRepository.save(permutation);
                businessNotifications.notify(permutationSaved.getUtilisateur1(), "Création de votre permutation",
                        "Votre demande de permutation n° " + permutationSaved.getId() + " a été enregistrée.");
                businessNotifications.notify(permutationSaved.getUtilisateur2(), "Demande de permutation à accepter",
                        "La demande de permutation n° " + permutationSaved.getId() + " vous a été transmise pour accord.");
                return permutationSaved;
            }else
                throw new IllegalArgumentException("Les agents doivent avoir la même spécialité, le même corps et des établissements différents");
        }else
            throw new EntityNotFoundException("utilisateur introuvable");
    }

    @Override
    public Response<Object> getAllPermutation(int page, int size, String matricule, String date, String statut, String nom, String prenom, String type, String ia, String ief, String etablissement){
        Utilisateur currentUtilisateur = iUtilisateur.getCurrentUser();
        BooleanBuilder builder = new BooleanBuilder();
        QPermutation qPermutation = permutation;
        System.out.println("\n matricule user "+currentUtilisateur.getMatricule());

        if(type.equals("emise")){
            System.out.println("!!!!!dans la partie emise !!!!!!!!");
            builder.and(
                    permutation.isDeleted.isFalse()
            );
            if (!Objects.equals(nom, "")){
                builder.and(
                        permutation.utilisateur1.nom.likeIgnoreCase("%" + nom + "%")
                );
            }
            if (!Objects.equals(prenom, "")){
                builder.and(
                        permutation.utilisateur1.prenom.likeIgnoreCase("%" + prenom + "%")
                );}
            if (!Objects.equals(matricule, "")){
                builder.and(
                        permutation.utilisateur1.matricule.likeIgnoreCase("%" + matricule + "%")
                );
            }
            if (!Objects.equals(statut, "")){
                builder.and(
                        permutation.traitementPermutation.statut.code.likeIgnoreCase("%" + statut + "%")
                );}
            if(!Objects.equals(date, "")){
                builder.and(
                        permutation.datePermutation.stringValue().likeIgnoreCase("%" + date + "%")
                );}
            builder.and(
                    permutation.utilisateur1.matricule.likeIgnoreCase("%" + currentUtilisateur.getMatricule() + "%").
                            or(permutation.utilisateur2.matricule.likeIgnoreCase("%" + currentUtilisateur.getMatricule()+ "%"))
            );
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            assert builder.getValue() != null;
            return Response.ok().setPayload( iPermutationRepository.findAll(builder.getValue(), pageRequest));
        }

        if (currentUtilisateur.getTypeUser().equals("DEC")) {
            DeconcentratedLevel currentUser = deconcentratedLevelRepository.findByMatricule(currentUtilisateur.getMatricule()).get();
            if (currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IA") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Représentant-IEF") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-etablissement") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-cfp") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-EFF")){

                    System.out.println("!!!!!dans la partie recue IA !!!!!!!!");
                    if (currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-etablissement") ||
                        currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-cfp") ||
                        currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-EFF")){
                        builder.and(
                                permutation.niveau.gt(0)
                        );
                        builder.and(
                                permutation.etablissementReceveur.code.likeIgnoreCase("%" + currentUser.getEtablissement().getCode() + "%").
                                        or(permutation.etablissementDemandeur.code.likeIgnoreCase("%" + currentUser.getEtablissement().getCode() + "%"))
                        );
                    }else if(currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IA")){
                        builder.and(
                                permutation.niveau.gt(2)
                        );
                        builder.and(
                                permutation.iaReceveur.code.likeIgnoreCase("%" + currentUser.getIa().getCode() + "%").
                                        or(permutation.iaDemandeur.code.likeIgnoreCase("%" + currentUser.getIa().getCode() + "%"))
                        );
                    } else if ( currentUser.getProfils().stream().findAny().get().getCode().equals("Représentant-IEF")) {
                        builder.and(
                                permutation.niveau.gt(1)
                        );
                        builder.and(
                                permutation.iefReceveur.code.likeIgnoreCase("%" + currentUser.getIef().getCode() + "%").
                                        or( permutation.iefDemandeur.code.likeIgnoreCase("%" + currentUser.getIef().getCode() + "%"))
                        );
                    }
                    builder.and(
                            permutation.isDeleted.isFalse()
                    );


                    if (!Objects.equals(nom, "")){
                        builder.and(
                                permutation.utilisateur1.nom.likeIgnoreCase("%" + nom + "%")
                        );
                    }
                    if (!Objects.equals(prenom, "")){
                        builder.and(
                                permutation.utilisateur1.prenom.likeIgnoreCase("%" + prenom + "%")
                        );}
                    if (!Objects.equals(matricule, "")){
                        builder.and(
                                permutation.utilisateur1.matricule.likeIgnoreCase("%" + matricule + "%")
                        );
                    }
                    if (!Objects.equals(statut, "")){
                        builder.and(
                                permutation.traitementPermutation.statut.code.likeIgnoreCase("%" + statut + "%")
                        );}
                    if(!Objects.equals(date, "")){
                        builder.and(
                                permutation.datePermutation.stringValue().likeIgnoreCase("%" + date + "%")
                        );}

                    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
                    return Response.ok().setPayload( iPermutationRepository.findAll(builder.getValue(), pageRequest));

            }
            else if(currentUser.getProfils().stream().findAny().get().getCode().equals("Professeur") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Formateur-CFP") ||
                    currentUser.getProfils().stream().findAny().get().getCode().equals("Formateur-EFF")){
                if(type.equals("traitée")){
                    System.out.println("!!!!!dans la partie recue !!!!!!!!");
                    builder.and(
                            permutation.isDeleted.isFalse()
                    );

                    if (!Objects.equals(nom, "")){
                        builder.and(
                                permutation.utilisateur1.nom.likeIgnoreCase("%" + nom + "%")
                        );
                    }
                    if (!Objects.equals(prenom, "")){
                        builder.and(
                                permutation.utilisateur1.prenom.likeIgnoreCase("%" + prenom + "%")
                        );}
                    if (!Objects.equals(matricule, "")){
                        builder.and(
                                permutation.utilisateur1.matricule.likeIgnoreCase("%" + matricule + "%")
                        );
                    }
                    if (!Objects.equals(statut, "")){
                        builder.and(
                                permutation.traitementPermutation.statut.code.likeIgnoreCase("%" + statut + "%")
                        );}
                    if(!Objects.equals(date, "")){
                        builder.and(
                                permutation.datePermutation.stringValue().likeIgnoreCase("%" + date + "%")
                        );}
                    builder.and(
                            permutation.utilisateur2.matricule.likeIgnoreCase("%" + currentUser.getMatricule() + "%")
                    );
                    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
                    return Response.ok().setPayload( iPermutationRepository.findAll(builder.getValue(), pageRequest));
                }
            }
        }else{
            if(currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("Chef-division-dgpeec")||
                    currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("bureau-mo-rec") ||
                    currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("Chef-service") ||
                    currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("ADMIN-DRH") ||
                    currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("Directeur-DRH") ||
                    // debut modification aicha
                    currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("Assistant-DRH")
                     // fin modification aicha
                ){
                System.out.println("\n type user "+currentUtilisateur.getProfils().stream().findAny().get().getCode());
                builder.and(
                        permutation.isDeleted.isFalse()
                );
                if(currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("bureau-mo-rec") ||
                        currentUtilisateur.getProfils().stream().findAny().get().getCode().equals("Chef-division-dgpeec")){
                    builder.and(
                            permutation.niveau.gt(4)
                    );
                }

                if (!Objects.equals(nom, "")){
                    builder.and(
                            permutation.utilisateur1.nom.likeIgnoreCase("%" + nom + "%")
                    );
                }
                if (StringUtils.isNotBlank(prenom)){
                    builder.and(
                            permutation.utilisateur1.prenom.likeIgnoreCase("%" + prenom + "%")
                    );}
                if (StringUtils.isNotBlank(matricule)){
                    builder.and(
                            permutation.utilisateur1.matricule.likeIgnoreCase("%" + matricule + "%")
                    );
                }
                if (StringUtils.isNotBlank(statut)){
                    builder.and(
                            permutation.traitementPermutation.statut.code.likeIgnoreCase("%" + statut + "%")
                    );}
                if(StringUtils.isNotBlank(date)){
                    builder.and(
                            permutation.datePermutation.stringValue().likeIgnoreCase("%" + date + "%")
                    );}
                PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
                return Response.ok().setPayload(iPermutationRepository.findAll(builder.getValue(), pageRequest));
            }
        }

        return null;
    }

    @Override
    public PermutationResponseDto getOnePermutation(long id) {
        Optional<Permutation>  permutationOptional = iPermutationRepository.findById(id);
        return permutationOptional.map(permutationMapper::toDto).orElse(null);
    }

    @Override
    public DeconcentratedLevel getUserByMatricule(String matricule) {

        return null;
    }

    public TraitementPermutation saveTraitement(String motif, long id, Utilisateur currentUser, StatusPermutation status){
        TraitementPermutation traitementPermutation = new TraitementPermutation();
        traitementPermutation.setTraiteur(currentUser);
        traitementPermutation.setIdPermutation(id);
        traitementPermutation.setMotif(motif);
        traitementPermutation.setDateTraitementMutation(LocalDate.now());
        traitementPermutation.setStatut(status);

        return traitementPermutationRepository.save(traitementPermutation);
    }


    @Override
    public PermutationResponseDto traiterPermutation(long id, String action, String motif, String type) {
        PermutationResponseDto result = traiterPermutationInternal(id, action, motif, type);
        if (result != null) {
            Permutation permutation = iPermutationRepository.findById(id).orElseThrow();
            String message = "Votre demande de permutation n° " + id + " : "
                    + permutation.getTraitementPermutation().getStatut().getLibelle() + ".";
            businessNotifications.notify(permutation.getUtilisateur1(), "Suivi de votre permutation", message);
            if (!Objects.equals(permutation.getUtilisateur1().getId(), permutation.getUtilisateur2().getId())) {
                businessNotifications.notify(permutation.getUtilisateur2(), "Suivi de votre permutation", message);
            }
        }
        return result;
    }

    private PermutationResponseDto traiterPermutationInternal(long id, String action, String motif, String type) {
        Utilisateur currentUser = iUtilisateur.getCurrentUser();
        StatusPermutation status = new StatusPermutation();
        if(currentUser.getProfils().stream().findAny().get().getCode().equals("Professeur") ||
                currentUser.getProfils().stream().findAny().get().getCode().equals("Formateur-CFP") ||
                currentUser.getProfils().stream().findAny().get().getCode().equals("Formateur-EFF")){
            Optional<Permutation> optionalPermutation = iPermutationRepository.findById(id);
            if (optionalPermutation.isPresent()) {
                Permutation permutation = optionalPermutation.get();
                if (action.equals("ACCEPTER")) {
                    System.out.println("\n ##### Accepter \n");
                    status = iStatusPermutationRepository.findByCode("TRANSMISE_CE");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement("demande de permutation Acceptée", id, currentUser, status);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    permutation.setNiveau(1);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    String profilTraitant = "";
                    if (currentUser.getProfils().stream().findAny().get().getCode().equals("Professeur"))
                        profilTraitant = "Chef-etablissement";
                    else if(currentUser.getProfils().stream().findAny().get().getCode().equals("Formateurs-CFP"))
                        profilTraitant = "Chef-cfp";
                    else
                        profilTraitant = "Chef-EFF";
                    sendPlateformeNotification(permutation,profilTraitant,"DEC");
                    return permutationMapper.toDto(permutationSaved);
                } else if (action.equals("REJETER")) {
                    System.out.println("\n ##### refuser \n");
                    status = iStatusPermutationRepository.findByCode("REJETER");
                    this.saveTraitement(motif, id, currentUser, status);
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, status);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                }else{
                    System.out.println("\n ##### soumettre a modifier \n");
                    status = iStatusPermutationRepository.findByCode("TRANSMISE_CE");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, status);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                }
            }
        }else if(currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-division-dgpeec") ||
                currentUser.getProfils().stream().findAny().get().getCode().equals("bureau-mo-rec")){
            Optional<Permutation> optionalPermutation = iPermutationRepository.findById(id);
            if (optionalPermutation.isPresent()){
                Permutation permutation = optionalPermutation.get();
                 if (action.equals("AMODIFIER")) {
                    System.out.println("\n ##### A modifier \n");
                    status = iStatusPermutationRepository.findByCode("AMODIFIER");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, status);
                    permutation.setValidateEtabDemandeur(false);
                    permutation.setValidateEtabReceveur(false);
                    permutation.setValidateIaDemandeur(false);
                    permutation.setValidateIaReceveur(false);
                    if (permutation.getIefReceveur()!=null && permutation.getIefDemandeur() != null){
                        permutation.setValidateIefDemandeur(false);
                        permutation.setValidateIefReceveur(false);
                    }
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    permutation.setNiveau(0);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);

                    return permutationMapper.toDto(permutationSaved);
                } else if (action.equals("REJETER")) {
                    System.out.println("\n ##### rejeter \n");
                    status = iStatusPermutationRepository.findByCode("REJETER");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, status);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                } else if (action.equals("TRAITEE")) {
                    status = iStatusPermutationRepository.findByCode("TRAITEE");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, status);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                }
            }
        }
        else if (currentUser.getProfils().stream().findAny().get().getCode().equals("Representant-IA")) {
            System.out.println("\n #### Representant IA ####");
            Optional<Permutation> optionalPermutation = iPermutationRepository.findById(id);
            if (optionalPermutation.isPresent()){
                System.out.println("\n #### permutation existante ####");
                Permutation permutation = optionalPermutation.get();
                if (action.equals("VALIDER")){
                    System.out.println("\n Valider");
                        System.out.println("\n VALIDER type recu");
                        StatusPermutation statusIaReceveur = iStatusPermutationRepository.findByCode("TRANSMISE_IA");
                        TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusIaReceveur);
                        permutation.setTraitementPermutation(traitementPermutationSaved);
                        permutation.getEmailTraitant().add(currentUser.getEmail());
                    if (permutation.getIaReceveur().getCode().equals(deconcentratedLevelRepository.findByMatricule(currentUser.getMatricule()).get().getIa().getCode())){
                        permutation.setValidateIaReceveur(true);
                        if (permutation.getIaReceveur().getCode().equals(permutation.getIaDemandeur().getCode())) {
                            permutation.setValidateIaDemandeur(true);
                        }
                        iPermutationRepository.save(permutation);

                    } else if (permutation.getIaDemandeur().getCode().equals(deconcentratedLevelRepository.findByMatricule(currentUser.getMatricule()).get().getIa().getCode())) {
                        permutation.setValidateIaDemandeur(true);
                        if (permutation.getIaReceveur().getCode().equals(permutation.getIaDemandeur().getCode())) {
                            permutation.setValidateIaReceveur(true);
                        }
                        iPermutationRepository.save(permutation);

                    }
                    if (permutation.isValidateIaDemandeur() && permutation.isValidateIaReceveur()) {
                            String  profilTraitant = "Directeur-DRH";
                            status = iStatusPermutationRepository.findByCode("TRANSMISE_DRH");
                            TraitementPermutation traitementPermutationSaved2 = this.saveTraitement(motif, id, currentUser, status);
                            permutation.setTraitementPermutation(traitementPermutationSaved2);
                            permutation.setNiveau(4);
                            //iPermutationRepository.save(permutation);
                        sendPlateformeNotification(permutation, profilTraitant, "CEN");
                        }
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                } else if (action.equals("REJETER")) {
                        StatusPermutation statusIa = iStatusPermutationRepository.findByCode("REJETER");
                        TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusIa);
                        permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                }
            }else{
                throw new EntityNotFoundException("demande de permutation introuvable");
            }
        } else if (currentUser.getProfils().stream().findAny().get().getCode().equals("Représentant-IEF")) {
            System.out.println("\n #### Representant IEF ####");
            Optional<Permutation> optionalPermutation = iPermutationRepository.findById(id);
            if (optionalPermutation.isPresent()){
                Permutation permutation = optionalPermutation.get();
                if (action.equals("VALIDER")){
                    System.out.println("\n #### validation ####");
                    StatusPermutation statusIef = iStatusPermutationRepository.findByCode("TRANSMISE_IEF");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusIef);
                    permutation.getEmailTraitant().add(currentUser.getEmail());
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    if (permutation.getIefDemandeur().getCode().equals(deconcentratedLevelRepository.findByMatricule(currentUser.getMatricule()).get().getIef().getCode())){
                        permutation.setValidateIefDemandeur(true);
                        if (permutation.getIefReceveur().getCode().equals(permutation.getIefDemandeur().getCode())) {
                            permutation.setValidateIefReceveur(true);
                        }
                        iPermutationRepository.save(permutation);

                    }else if(permutation.getIefReceveur().getCode().equals(deconcentratedLevelRepository.findByMatricule(currentUser.getMatricule()).get().getIef().getCode())){
                        permutation.setValidateIefReceveur(true);
                        if (permutation.getIefReceveur().getCode().equals(permutation.getIefDemandeur().getCode())) {
                            permutation.setValidateIefDemandeur(true);
                        }
                        iPermutationRepository.save(permutation);

                    }
                    if(permutation.isValidateIefDemandeur() && permutation.isValidateIefReceveur()){
                        System.out.println("ici recue");
                        String profilTraitant = "Representant-IA";
                        status = iStatusPermutationRepository.findByCode("TRANSMISE_IA");
                        permutation.setNiveau(3);
                        TraitementPermutation traitementPermutationSaved1 = this.saveTraitement(motif, id, currentUser, status);
                        permutation.setTraitementPermutation(traitementPermutationSaved1);
                        sendPlateformeNotification(permutation, profilTraitant, "DEC");
                        iPermutationRepository.save(permutation);
                    }
                    return permutationMapper.toDto(permutation);
                } else if (action.equals("REJETER")) {
                    StatusPermutation statusRejet = iStatusPermutationRepository.findByCode("REJETER");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusRejet);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                }
            }else{
                throw new EntityNotFoundException("demande de permutation introuvable");
            }
        } else if (currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-etablissement") ||
                currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-cfp") ||
                currentUser.getProfils().stream().findAny().get().getCode().equals("Chef-EFF"))
        {
            System.out.println("\n #### chef Etablissement ####");
            Optional<Permutation> optionalPermutation = iPermutationRepository.findById(id);
            if (optionalPermutation.isPresent()){
                Permutation permutation = optionalPermutation.get();
                if (action.equals("VALIDER")){
                    System.out.println("\n #### validation ####");
                    StatusPermutation statusChef = iStatusPermutationRepository.findByCode("TRANSMISE_CE");
                        TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusChef);
                        permutation.setTraitementPermutation(traitementPermutationSaved);
                        permutation.getEmailTraitant().add(currentUser.getEmail());
                        if (permutation.getEtablissementDemandeur().getCode().equals(deconcentratedLevelRepository.findByMatricule(currentUser.getMatricule()).get().getEtablissement().getCode())){
                            permutation.setValidateEtabDemandeur(true);
                            iPermutationRepository.save(permutation);

                        }else if(permutation.getEtablissementReceveur().getCode().equals(deconcentratedLevelRepository.findByMatricule(currentUser.getMatricule()).get().getEtablissement().getCode())){
                            permutation.setValidateEtabReceveur(true);
                            iPermutationRepository.save(permutation);

                        }
                        if(permutation.isValidateEtabDemandeur() && permutation.isValidateEtabReceveur()){
                            if(permutation.getIefReceveur()!=null && permutation.getIefDemandeur()!=null){
                                status = iStatusPermutationRepository.findByCode("TRANSMISE_IEF");
                                permutation.setNiveau(2);
                                sendPlateformeNotification(permutation, "Représentant-IEF", "DEC");
                            }else{
                                status = iStatusPermutationRepository.findByCode("TRANSMISE_IA");
                                permutation.setNiveau(3);
                                sendPlateformeNotification(permutation, "Representant-IA", "DEC");
                            }
                            TraitementPermutation traitementPermutationSaved1 = this.saveTraitement(motif, id, currentUser, status);
                            permutation.setTraitementPermutation(traitementPermutationSaved1);
                            iPermutationRepository.save(permutation);
                        }
                        return permutationMapper.toDto(permutation);
                } else if (action.equals("REJETER")) {
                    StatusPermutation statusRejet = iStatusPermutationRepository.findByCode("REJETER");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusRejet);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    Permutation permutationSaved = iPermutationRepository.save(permutation);
                    return permutationMapper.toDto(permutationSaved);
                }
            }else{
                throw new EntityNotFoundException("demande de permutation introuvable");
            }
        }else if(currentUser.getProfils().stream().findAny().get().getCode().equals("Directeur-DRH")){
            Optional<Permutation> optionalPermutation = iPermutationRepository.findById(id);
            if (optionalPermutation.isPresent()){
                Permutation permutation = optionalPermutation.get();
                if (action.equals("VALIDER")){
                    System.out.println("\n #### validation DRH ####");
                    StatusPermutation statusDRH = iStatusPermutationRepository.findByCode("TRAITEMENT");
                    TraitementPermutation traitementPermutationSaved = this.saveTraitement(motif, id, currentUser, statusDRH);
                    permutation.setTraitementPermutation(traitementPermutationSaved);
                    permutation.setNiveau(5);
                    permutation.getEmailTraitant().add(currentUser.getEmail());
                    PermutationResponseDto permutationResponseDto = permutationMapper.toDto(iPermutationRepository.save(permutation));

                    sendPlateformeNotification(permutation, "Chef-division-dgpeec", "CEN");
                    return permutationResponseDto;
                }
            }else{
                throw new EntityNotFoundException("demande de permutation introuvable");
            }
        }
        return null;
    }

    @Override
    public Permutation genererPermutationOS(long id)throws JRException, FileNotFoundException {

        //https://www.youtube.com/watch?v=fZtnoQpPzaw
        Permutation permutation = iPermutationRepository.findById(id).get();
        byte[] permutationPDF = jasperGenerator.getPermutationOS(permutation);
        MultipartFile multipartFile = new MockMultipartFile(permutation.getUtilisateur1().getMatricule()+"_"+permutation.getUtilisateur2().getMatricule()+"_"+permutation.getDatePermutation()+"_"+permutation.getId(),permutationPDF);
        fileImpl.uploadSingleFile(multipartFile, permutation.getId(),"permutation");
        return permutation;
    }

    @Override
    public Response<Object> genererPermutationAllOS()throws JRException, FileNotFoundException {
        System.out.println("\n###entrer dans la partie implement");
        //https://www.youtube.com/watch?v=fZtnoQpPzaw
        BooleanBuilder builder = new BooleanBuilder();
        QPermutation qPermutation = permutation;
        String statut = "VALIDER";
        int i=0;
        LocalDate date = LocalDate.now();
        builder.and(
                permutation.isDeleted.isFalse()
        );
        builder.and(
                permutation.traitementPermutation.statut.code.likeIgnoreCase("%" + statut + "%")
        );
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

        List<Permutation>  permutations =  iPermutationRepository.findAll();
        List<Permutation>  permutationsList = new ArrayList<>();
        while(i<permutations.size()){
            String codeStatut = permutations.get(i).getTraitementPermutation().getStatut().getCode();
            // Permutations finalisées : traitées par la DGPEEC (TRAITEE, avant signature)
            // ou déjà validées après téléversement de l'OS signé (VALIDER).
            if (codeStatut.equals("TRAITEE") || codeStatut.equals("VALIDER")){
                System.out.println("\n id permu ===="+permutations.get(i).getId());
                permutationsList.add(permutations.get(i));
            }
            i++;
        }
        byte[] permutationPDF = jasperGenerator.getPermutationAllOS(permutationsList);
        MultipartFile multipartFile = new MockMultipartFile("Ordre_de_service"+date,permutationPDF);
        return fileImpl.uploadSingleFile(multipartFile, 0,"permutation");
        //return permutations;
    }

    //uplaod OS de validation
    @Override
    public Response<Object> UploadPermutation(long idPermutation, long idTraitant, MultipartFile file){
        Optional<Permutation> permutationOptional = iPermutationRepository.findById(idPermutation);
        if (permutationOptional.isPresent()){
            Permutation permutation = permutationOptional.get();
            Optional<Utilisateur> utilisateurOptional = iUtilisateurRepository.findById(idTraitant);
            if (utilisateurOptional.isPresent()){
                Utilisateur utilisateur = utilisateurOptional.get();
                if (file.isEmpty()) {
                    System.out.println("+++++ FICHIER VIDE=====");
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                Object result = fileImpl.uploadPermutationOs(file,permutation,utilisateur);

                return Response.ok().setPayload(result);
            }
        }
        return null;
    }

    /*
    * les indicateurs
    * */
    @Override
    public Response<Object> indicateurPermutation(String codeProfile) {
        long valid = 0;
        long reject = 0;
        long all = 0;
        Utilisateur utilisateurConnected = iUtilisateur.getCurrentUser();
        CentralLevel centralLevel = new CentralLevel();
        DeconcentratedLevel deconcentratedLevel = new DeconcentratedLevel();
        if (utilisateurConnected.getTypeUser().equals("CEN")) {
            centralLevel = (CentralLevel) utilisateurConnected;
            List<Profile> profiles = new ArrayList<>(centralLevel.getProfils());
            Profile profile =profiles.get(0);
            String pro = profile.getCode();
            if(profile.getCode().equals("Chef-division-dgpeec") ||
                    profile.getCode().equals("Chef-bureau-dgpeec") ||
                    profile.getCode().equals("Agent-bureau-dgpeec")
                    ){
                all = iPermutationRepository.countPermutationForChefDivision(4);
                valid = iPermutationRepository.countAllPermutationsByStatut("VALIDER");
                reject = iPermutationRepository.countAllPermutationsByStatut("REJETER");

            }else{
                all = iPermutationRepository.countAllPermutations();
                valid = iPermutationRepository.countAllPermutationsByStatut("VALIDER");
                reject = iPermutationRepository.countAllPermutationsByStatut("REJETER");
            }

        } else {
            deconcentratedLevel = (DeconcentratedLevel) utilisateurConnected;
            switch (codeProfile) {
                case "Chef-etablissement":
                case "Chef-EFF":
                case "Chef-cfp":
                {
                    valid = iPermutationRepository.countPermutationByStatutDemandeurEtab(deconcentratedLevel.getEtablissement().getCode(),"VALIDER") +
                            iPermutationRepository.countPermutationByStatutReceveurEtab(deconcentratedLevel.getEtablissement().getCode(),"VALIDER");
                    reject = iPermutationRepository.countPermutationByStatutDemandeurEtab(deconcentratedLevel.getEtablissement().getCode(),"REJETER") +
                            iPermutationRepository.countPermutationByStatutReceveurEtab(deconcentratedLevel.getEtablissement().getCode(),"REJETER");
                    ;
                    all = iPermutationRepository.countPermutationEtabDemandeur(deconcentratedLevel.getEtablissement().getCode()) +
                            iPermutationRepository.countPermutationEtabReceveur(deconcentratedLevel.getEtablissement().getCode());
                    System.out.println("receveur = "+iPermutationRepository.countPermutationIefReceveur(deconcentratedLevel.getIef().getCode()));
                    break;
                }
                case "Représentant-IEF":
                    valid = iPermutationRepository.countPermutationByStatutDemandeurIef(deconcentratedLevel.getIef().getCode(),"VALIDER") +
                            iPermutationRepository.countPermutationByStatutReceveurIef(deconcentratedLevel.getIef().getCode(),"VALIDER");
                    reject =  iPermutationRepository.countPermutationByStatutDemandeurIef(deconcentratedLevel.getIef().getCode(),"REJETER") +
                            iPermutationRepository.countPermutationByStatutReceveurIef(deconcentratedLevel.getIef().getCode(),"REJETER");
                    all = iPermutationRepository.countPermutationIefReceveur(deconcentratedLevel.getIef().getCode()) +
                            iPermutationRepository.countPermutationIefDemandeur(deconcentratedLevel.getIef().getCode());

                    break;
                case "Representant-IA":
                    valid = iPermutationRepository.countPermutationByStatutDemandeurIa(deconcentratedLevel.getIa().getCode(),"VALIDER") +
                            iPermutationRepository.countPermutationByStatutReceveurIa(deconcentratedLevel.getIa().getCode(),"VALIDER");
                    reject = iPermutationRepository.countPermutationByStatutDemandeurIa(deconcentratedLevel.getIa().getCode(),"REJETER") +
                            iPermutationRepository.countPermutationByStatutReceveurIa(deconcentratedLevel.getIa().getCode(),"REJETER");
                    all = iPermutationRepository.countPermutationIaReceveur(deconcentratedLevel.getIa().getCode()) +
                            iPermutationRepository.countPermutationIaDemandeur(deconcentratedLevel.getIa().getCode());
                    break;

                default:
//                    valid = imutationRepository.countValidRejectedMutations("VALIDER");
//                    reject = imutationRepository.countValidRejectedMutations("REJETER");
//                    all = imutationRepository.countAllMutations();

                    break;
            }
        }
        IndicateursPermutations indicateursPermutations = new IndicateursPermutations();
        indicateursPermutations.setValidated(valid);
        indicateursPermutations.setRejected(reject);
        indicateursPermutations.setAll(all);
        return Response.ok().setMessage("Indicateurs permutations").setPayload(indicateursPermutations);
    }

    // gestion des notification dans la plateforme

    void sendPlateformeNotification(Permutation permutation, String profileTraitant, String userType){
        Profile profile = iProfilRepository.findProfileByCode(profileTraitant);
        Set<Profile> profileSet = new HashSet<>();
        profileSet.add(profile);
        List<DeconcentratedLevel>  deconcentratedLevel = new ArrayList<>();
        List<CentralLevel>  centralLevels = new ArrayList<>();

        if(userType.equals("CEN")){
            centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
        }else {

            switch (profileTraitant) {
                case "Chef-etablissement":
                case "Chef-EFF":
                case "Chef-cfp":{
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByEtablissementAndProfilsContains(permutation.getEtablissementDemandeur(), profile);
                    deconcentratedLevel.addAll(deconcentratedLevelRepository.findDeconcentratedLevelByEtablissementAndProfilsContains(permutation.getEtablissementReceveur(), profile));
                    break;
                }

                case "Représentant-IEF":
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByIefAndProfilsContains(permutation.getIefDemandeur(), profile);
                    deconcentratedLevel.addAll(deconcentratedLevelRepository.findDeconcentratedLevelByIefAndProfilsContains(permutation.getIefReceveur(), profile));

                    break;
                case "Representant-IA":
                    deconcentratedLevel = deconcentratedLevelRepository.findDeconcentratedLevelByIaAndProfilsContains(permutation.getIaDemandeur(), profile);
                    deconcentratedLevel.addAll(deconcentratedLevelRepository.findDeconcentratedLevelByIaAndProfilsContains(permutation.getIaReceveur(), profile));

                    break;

                case "Professeur":
                case "Formateurs-cfp":
                case "Formateurs-EFF":{
                    DeconcentratedLevel dec = new DeconcentratedLevel();
                    dec = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur2().getMatricule()).get();
                    deconcentratedLevel.add(dec);
                }
//               case "Chef-division-dgpeec":
//                   centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                //                       break;

                default:
                    centralLevels = centralLevelRepository.findCentralLevelByProfilsContains(profile);
                    //   mutation.setBtDGPEEC(bordereauName);
                    break;
            }

        }
        if(!deconcentratedLevel.isEmpty()){
        for (DeconcentratedLevel deconcentratedLevel1 : deconcentratedLevel){
            System.out.println("#### Avant save notif deconcentre"+ deconcentratedLevel1.getNom());
            Notification notification = new Notification();
            notification.setObjet("Traitement permutation");
            notification.setMessage("Bonjour, \n une nouvelle demande de permutation n° " + permutation.getId() + " vous a été transmise. \n Merci de procéder au traitement.");
            notification.setIdUser(deconcentratedLevel1.getId());
            businessNotifications.notifyUser(notification);
            System.out.println("#### Apres save notif deconcentre");
        }
        }
        if(!centralLevels.isEmpty()){
        for (CentralLevel centralLevel : centralLevels){
            Notification notification = new Notification();
            notification.setObjet("Traitement permutation");
            notification.setMessage("Bonjour, \n une nouvelle demande de permutation n° " + permutation.getId() + " vous a été transmise. \n Merci de procéder au traitement.");
            System.out.println("#### Avant save notif central");
            notification.setIdUser(centralLevel.getId());
            businessNotifications.notifyUser(notification);
            System.out.println("#### Apres save notif central");
        }
        }
       }

}
