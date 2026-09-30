package sn.gainde2000.backenmfpai.services.implementations.files;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.commons.utils.mail.MailService;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Borderau;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.StatusPermutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.TraitementPermutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Avancement;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.EtatCivil;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.SituationAdministrative;
import sn.gainde2000.backenmfpai.entities.serviceformation.expressionbession.Campagne;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.DemandeStage;
import sn.gainde2000.backenmfpai.entities.servicesociale.PriseEnCharge;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.AttestationStage;
import sn.gainde2000.backenmfpai.entities.serviceformation.stage.RapportStage;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Region;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IA;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;
import sn.gainde2000.backenmfpai.repositories.Files.FileRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.*;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.ActeRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.BordereauRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.ITraitementMutationRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.ImutationRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.IPermutationRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.IStatusPermutation;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Permutation.TraitementPermutationRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.expressionbesoin.CampagneRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.DemandeStageRepository;
import sn.gainde2000.backenmfpai.repositories.servicesociale.PriseEnChargeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.AttestationStageRepository;
import sn.gainde2000.backenmfpai.repositories.serviceformation.stage.RapportStageRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.RegionRepository;
import sn.gainde2000.backenmfpai.services.interfaces.files.IFile;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.INotificationService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Permutation.PermutationSignatures;
import sn.gainde2000.backenmfpai.web.dtos.requests.authentification.LoginFormDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.mails.MailInfosDTO;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class FileImpl implements IFile {
    private final CampagneRepository campagneRepository;
    private final BordereauRepository bordereauRepository;
    private final MailService mailService;
    @Value("${upload.path}")
    private String uploadDirectory;

    @Value("${upload.image}")
    private String uploadImagesDirectory;

    @Value("${host.url}")
    private String downloadUrl;

    private final ActeRepository acteRepository;
    private final FileRepository fileRepository;
    private final IDiplomeRepository diplomeRepository;

    private final IAvancementRepository avancementRepository;
    private final ImputationRepository imputationRepository;
    private final IEtatCivilRepository iEtatCivilRepository;

    private final DemandeStageRepository demandeStageRepository;
    private final PriseEnChargeRepository priseEnChargeRepository;
    private final RapportStageRepository rapportStageRepository;
    private final AttestationStageRepository attestationStageRepository;
    private final IPermutationRepository iPermutationRepository;
    private final ImutationRepository imutationRepository;
    private final IStatusPermutation iStatusPermutationRepository;
    private final TraitementPermutationRepository traitementPermutationRepository;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final RegionRepository regionRepository;
    private final INotificationService notificationService;
    private final ISituationAdministrativeRepository iSituationAdministrativeRepository;

    private final IUtilisateur iUtilisateur;


    @Override
    public File uploadImage(MultipartFile image) throws IOException {
        if (image.isEmpty()){
            throw new EntityNotFoundException("Veuillez selectionner une image");
        }
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        try {
            byte[] bytes = image.getBytes();
            Path path = Paths.get(uploadImagesDirectory+newFileName);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(image.getSize());
            file1.setFileType(image.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl+newFileName);
            Files.write(path, bytes);

            return fileRepository.save(file1);
        } catch (IOException e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Une erreur s'est produit lors de l'enregistrement du fichier");
        }

    }
    @Override
    public Response<Object> uploadSingleFile(MultipartFile file, long id, String type) {
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }
        if (type.equalsIgnoreCase("diplome")) {
            Optional<Diplome> diplome = diplomeRepository.findById(id);
            // System.out.println("i'm here");

            if (diplome.isEmpty()) {
                return Response.exception().setMessage("diplome inexistant");
            }

            // Generer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFileName = UUID.randomUUID().toString() + extension;
            //  System.out.println(newFileName);
            // enregistrement fichier
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                fileRepository.save(file1);
               // System.out.println(file1);
               // System.out.println(file1.getFileSize());
                diplome.get().setPieceJointes(file1);
                diplomeRepository.save(diplome.get());
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }
            return Response.ok().setMessage("Fichiers enregistree avec success");

        } else if  (type.equalsIgnoreCase("acte"))
        {
            Optional<Acte> acte=acteRepository.findById(id);
            System.out.println("i'm here");

            if (acte.isEmpty()) {
                return Response.exception().setMessage("acte inexistant");
            }
            System.out.println(acte.toString());

            // Generer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFileName = UUID.randomUUID().toString() + extension;

            // enregistrement fichier
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                fileRepository.save(file1);
                System.out.println(file1);
                System.out.println(file1.getFileSize());
               // acte.get().setBordereau(file1);
                acteRepository.save(acte.get());
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }
            return Response.ok().setMessage("Fichiers enregistree avec success");
        }  else if (type.equalsIgnoreCase("imputationOuBulletin")) {
            Optional<ImputationOuBulletin> imputationOuBulletin = imputationRepository.findById(id);
            // System.out.println("i'm here");

            if (imputationOuBulletin.isEmpty()) {
                return Response.exception().setMessage("imputation inexistant");
            }

            // Generer un nom de fichier unique
            String originalFilename = file.getName();
            //String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFileName = UUID.randomUUID().toString() + ".pdf";
            System.out.println(newFileName);
            // enregistrement fichier
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                fileRepository.save(file1);
               // System.out.println(file1);
              //  System.out.println(file1.getFileSize());
                //imputationOuBulletin.get().setBordereau(file1.getGeneratedName());
                imputationOuBulletin.get().setImputationGeneree(file1.getGeneratedName());
                imputationRepository.save(imputationOuBulletin.get());
                Files.write(path, bytes);
                imputationOuBulletin.get().setImputationGeneree(file1.getGeneratedName());


            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }
            return Response.ok().setMessage("Fichiers enregistree avec success");
        } else if (type.equalsIgnoreCase("permutation")) {
            String generatedName = "";
            if (id == 0) {
                String originalFilename = file.getName();
                //String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

                String newFileName = UUID.randomUUID().toString()+".pdf";
                //System.out.println(newFileName);
                try{
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadDirectory + newFileName);
                    File file1 = new File();
                    file1.setOriginalName(originalFilename);
                    file1.setFileSize(file.getSize());
                    file1.setFileType(file.getContentType());
                    file1.setGeneratedName(newFileName);
                    file1.setDownloadUrl(downloadUrl + newFileName);
                    file1.setIdAppartenance(id);
                    fileRepository.save(file1);
                   // System.out.println(file1);
                    generatedName = newFileName;
                    Files.write(path, bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                }

            } else {
                Optional<Permutation> permutation = iPermutationRepository.findById(id);
                // System.out.println("i'm here");

                if (permutation.isEmpty()) {
                    return Response.exception().setMessage("permutation inexistant");
                }
                String originalFilename = file.getName();
                //String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));

                String newFileName = "Ordre_de_Service_"+UUID.randomUUID().toString()+".pdf";
                System.out.println(newFileName);
                // enregistrement fichier
                try {
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadDirectory + newFileName);
                    File file1 = new File();
                    file1.setOriginalName(originalFilename);
                    file1.setFileSize(file.getSize());
                    file1.setFileType(file.getContentType());
                    file1.setGeneratedName(newFileName);
                    file1.setDownloadUrl(downloadUrl + newFileName);
                    file1.setIdAppartenance(id);
                    fileRepository.save(file1);
                  //  System.out.println(file1);
                  //  System.out.println(file1.getFileSize());
                    //imputationOuBulletin.get().setBordereau(file1.getGeneratedName());
                    permutation.get().setOrdreService(file1.getGeneratedName());
                    iPermutationRepository.save(permutation.get());
                    Files.write(path, bytes);
                    //permutation.get().setImputationGeneree(file1.getGeneratedName());


                } catch (IOException e) {
                    e.printStackTrace();
                    return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                }
            }

            return Response.ok().setMessage("Fichiers enregistree avec success")
                    .setPayload(generatedName);
        } else if (type.equalsIgnoreCase("mutation")) {
            String generatedName = "";


            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.lastIndexOf('.') >= 0
                    ? originalFilename.substring(originalFilename.lastIndexOf('.')) : "";
            String newFileName = UUID.randomUUID().toString() + extension;

            // enregistrement fichier
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);

                fileRepository.save(file1);

                //imputationOuBulletin.get().setBordereau(file1.getGeneratedName());
                generatedName = newFileName;
                Files.write(path, bytes);
                //permutation.get().setImputationGeneree(file1.getGeneratedName());

                return Response.ok().setMessage("Fichiers enregistree avec success")
                        .setPayload(generatedName);
            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }


        } else {
            System.out.println("on est dans le else");
            return null;
        }

    }

    @Override
    public Response<Object> uploadSingleFile(MultipartFile file, long id) {
        return null;
    }

    @Override
    public Response<Object> uploadSingleDiplomeFile(MultipartFile file, long id) {
        System.out.println("+++++ UPLOAD DIPLOME=====");
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            System.out.println("+++++ UPLOAD DIPLOME CASE FILE IS EMPTY=====");
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }

        Optional<Diplome> diplome=diplomeRepository.findById(id);
        //System.out.println("i'm here");

        if (diplome.isEmpty()) {
            return Response.exception().setMessage("diplome inexistant");
        }

        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
      //  System.out.println(newFileName);
        // enregistrement fichier
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            System.out.println("path ==== " + path);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            System.out.println("le download URL RRRRRRRRRR" + downloadUrl);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            fileRepository.save(file1);
           // System.out.println(file1);
          //  System.out.println(file1.getFileSize());
            diplome.get().setPieceJointes(file1);
            diplomeRepository.save(diplome.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            System.out.println("+++++ UPLOAD DIPLOME CASE ERROR=====");

            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");

    }

    @Override
    public Response<Object> uploadFileForCampagne(List<MultipartFile> files, long id) {
        {
            Optional<Campagne> campagneOptional = campagneRepository.findByIdAndDeletedFalse(id);
            if (campagneOptional.isEmpty())
                return Response.exception().setMessage("campagne inexistant");
            if (files.isEmpty()) {

                return Response.exception().setMessage("Veuillez selectionner un fichier");
            }

            Campagne campagne = campagneOptional.get();
//            campagne.getPieceJoint().clear();
//            campagneRepository.save(campagne);



            // enregistrement fichier

            files.forEach(file -> {
                // Generer un nom de fichier unique
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                String newFileName = UUID.randomUUID().toString() + extension;
                System.out.println(newFileName);
                byte[] bytes = null;
                try {
                    bytes = file.getBytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Path path = Paths.get(uploadDirectory + newFileName);
                System.out.println("path ==== " + path);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                System.out.println("le download URL RRRRRRRRRR" + downloadUrl);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                fileRepository.save(file1);
                System.out.println(file1);
                System.out.println(file1.getFileSize());
                campagne.getPieceJoint().add(file1);
                campagneRepository.save(campagne);
                try {
                    Files.write(path, bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return Response.ok().setMessage("Fichiers enregistree avec success");

        }
    }

    @Override
    public Response<Object> uploadSingleImputationFile(MultipartFile file, long id) {
        System.out.println("+++++ UPLOAD IMPUTATION=====");
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            System.out.println("+++++ UPLOAD IMPUTATION CASE FILE IS EMPTY=====");

            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }
        Optional<ImputationOuBulletin> imputationOuBulletin = imputationRepository.findById(id);
        System.out.println("i'm here");

        if (imputationOuBulletin.isEmpty()) {
            return Response.exception().setMessage("imputation inexistant");
        }

        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            System.out.println("path ==== " + path);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            System.out.println("le download URL RRRRRRRRRR" + downloadUrl);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            fileRepository.save(file1);
           // System.out.println(file1);
           // System.out.println(file1.getFileSize());
            imputationOuBulletin.get().getJustificatifs().add(file1);
            imputationRepository.save(imputationOuBulletin.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            System.out.println("+++++ UPLOAD IMPUTATION CASE ERROR=====");

            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");

    }

    @Override
    public Response<Object> uploadSinglePermutationBordereauFile(MultipartFile file, long id) {
        System.out.println("+++++ UPLOAD PERMUTATION BORDEREAU=====");
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            System.out.println("+++++ UPLOAD PERMUTATION CASE FILE IS EMPTY=====");

            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }
        Optional<TraitementPermutation> traitement=traitementPermutationRepository.findById(id);
        System.out.println("i'm here");

        if(!traitement.isPresent()){
            System.out.println("empty traitement");
            return Response.exception().setMessage("traitement inexistant");
        }

        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            System.out.println("path ==== "+path);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            System.out.println("le download URL RRRRRRRRRR"+downloadUrl);
            file1.setDownloadUrl(downloadUrl+newFileName);
            file1.setIdAppartenance(id);
            fileRepository.save(file1);
            System.out.println(file1);
            System.out.println(file1.getFileSize());
            traitement.get().setBordereauValidation(file1);
            traitementPermutationRepository.save(traitement.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            System.out.println("+++++ UPLOAD IMPUTATION CASE ERROR=====");

            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");

    }

    @Override
    public Response<Object> uploadSingleAvancementFile(MultipartFile file, long id) {

        System.out.println("+++++ UPLOAD AVANCEMENT=====");

        // verifier si le fichier est vide
        if (file.isEmpty()) {
            System.out.println("+++++ UPLOAD AVANCEMENT CASE FILE EMPTY=====");

            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }
        Optional<Avancement> avancement = avancementRepository.findById(id);
        System.out.println("i'm here");

        if (avancement.isEmpty()) {
            return Response.exception().setMessage("diplome inexistant");
        }

        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            System.out.println("path ==== " + path);

            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            fileRepository.save(file1);
            System.out.println(file1);
            System.out.println(file1.getFileSize());
            avancement.get().setPieceJointes(file1);
            avancementRepository.save(avancement.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            System.out.println("+++++ UPLOAD AVANCEMENT CASE  ERROR =====");

            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");

    }

    @Override
    public Response<Object> uploadSingleEtatCivilFile(MultipartFile file, long id) {

        System.out.println("+++++ UPLOAD etat civil===== id ===== " + id);

        // verifier si le fichier est vide
        if (file.isEmpty()) {
            System.out.println("+++++ UPLOAD etat civil CASE FILE EMPTY=====");

            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }
        Optional<EtatCivil> etatCivil = iEtatCivilRepository.findById(id);
        System.out.println("i'm here");

        if (etatCivil.isEmpty()) {
            return Response.exception().setMessage("etat civil inexistant");
        }

        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            System.out.println("path ==== " + path);

            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            fileRepository.save(file1);
            System.out.println(file1);
            System.out.println(file1.getFileSize());
            etatCivil.get().setPiecejointes(file1);
            iEtatCivilRepository.save(etatCivil.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            System.out.println("+++++ UPLOAD ETAT CIVIL CASE  ERROR =====");

            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");

    }


    @Override
    public Response<Object> uploadSingleActeFile(MultipartFile file, long id) {
        if (file.isEmpty()) {
            System.out.println("+++++ UPLOAD Acte CASE FILE EMPTY=====");
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }
        Optional<SituationAdministrative> sitAd = iSituationAdministrativeRepository.findById(id);
        System.out.println("i'm here");

        if (sitAd.isEmpty()) {
            return Response.exception().setMessage("Acte inexistant");
        }

        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            System.out.println("path ==== " + path);

            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            fileRepository.save(file1);
            System.out.println(file1);
            System.out.println(file1.getFileSize());
            sitAd.get().setPieceJointes(file1);
            iSituationAdministrativeRepository.save(sitAd.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            System.out.println("+++++ UPLOAD Acte CASE  ERROR =====");

            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");

    }


    @Override
    @Transactional
    public Response<Object> uploadFiles(List<MultipartFile> files, long id) {
        Optional<Acte> acte = acteRepository.findActeById(id);
        //System.out.println("i'm here");

        if (acte.isEmpty()) {
            return Response.exception().setMessage("acte inexistant");
        }
        // verifier si le fichier est vide
        if (files.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }

        for (MultipartFile file : files) {
            // Generer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFileName = UUID.randomUUID().toString() + extension;
            System.out.println(newFileName);
            // enregistrement fichier
            try {

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                fileRepository.save(file1);
                // System.out.println(file1);
                //   System.out.println(file1.getFileSize());
                acte.get().getPieceJointes().add(file1);
                acteRepository.save(acte.get());
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");
    }

    public Response<Object> uploadFiles(List<MultipartFile> files, long id, String type) {
        switch (type) {
            case "acte":
                Optional<Acte> acte = acteRepository.findActeById(id);
                if (acte.isEmpty()) {
                    return Response.exception().setMessage("acte inexistant");
                }

                // verifier si le fichier est vide
                if (files.isEmpty()) {
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                for (MultipartFile file : files) {
                    // Generer un nom de fichier unique
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    System.out.println(newFileName);
                    // enregistrement fichier
                    try {

                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + newFileName);
                        File file1 = new File();
                        file1.setOriginalName(originalFilename);
                        file1.setFileSize(file.getSize());
                        file1.setFileType(file.getContentType());
                        file1.setGeneratedName(newFileName);
                        file1.setDownloadUrl(downloadUrl + newFileName);
                        file1.setIdAppartenance(id);
                        fileRepository.save(file1);
                        // System.out.println(file1);
                        //   System.out.println(file1.getFileSize());
                        acte.get().getPieceJointes().add(file1);
                        acteRepository.save(acte.get());
                        Files.write(path, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                    }
                }

                break;
            case "pec":
                Optional<PriseEnCharge> priseEnCharge = priseEnChargeRepository.findPriseEnChargeById(id);
                if (priseEnCharge.isEmpty()) {
                    return Response.exception().setMessage("prise en charge inexistant");
                }
                // verifier si le fichier est vide
                if (files.isEmpty()) {
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                for (MultipartFile file : files) {
                    // Generer un nom de fichier unique
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    System.out.println(newFileName);
                    // enregistrement fichier
                    try {

                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + newFileName);
                        File file1 = new File();
                        file1.setOriginalName(originalFilename);
                        file1.setFileSize(file.getSize());
                        file1.setFileType(file.getContentType());
                        file1.setGeneratedName(newFileName);
                        file1.setDownloadUrl(downloadUrl + newFileName);
                        file1.setIdAppartenance(id);
                        fileRepository.save(file1);
                        // System.out.println(file1);
                        //   System.out.println(file1.getFileSize());
                        priseEnCharge.get().getPieceJointes().add(file1);
                        priseEnChargeRepository.save(priseEnCharge.get());
                        Files.write(path, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                    }
                }
                break;
            case "demandeStage":
                Optional<DemandeStage> demandeStage = demandeStageRepository.findById(id);
                if (demandeStage.isEmpty()) {
                    return Response.exception().setMessage("Demande n'existe pas.");
                }
                // verifier si le fichier est vide
                if (files.isEmpty()) {
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                for (MultipartFile file : files) {
                    // Generer un nom de fichier unique
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    System.out.println(newFileName);
                    // enregistrement fichier
                    try {

                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + newFileName);
                        File file1 = new File();
                        file1.setOriginalName(originalFilename);
                        file1.setFileSize(file.getSize());
                        file1.setFileType(file.getContentType());
                        file1.setGeneratedName(newFileName);
                        file1.setDownloadUrl(downloadUrl + newFileName);
                        file1.setIdAppartenance(id);
                        fileRepository.save(file1);
                        // System.out.println(file1);
                        //   System.out.println(file1.getFileSize());
                        demandeStage.get().getJustificatfsAuthorisationStage().add(file1);
                        demandeStage.get().setHaveAuthorisationStage(true);
                        demandeStageRepository.save(demandeStage.get());
                        Files.write(path, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                    }
                }
                break;
            case "mutationDemande":
                Optional<Mutation> mutation = imutationRepository.findById(id);
                if (mutation.isEmpty()) {
                    return Response.exception().setMessage("Demande de mutation inexistante");
                }
                // verifier si le fichier est vide
                if (files.isEmpty()) {
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                for (MultipartFile file : files) {
                    // Generer un nom de fichier unique
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    // enregistrement fichier
                    try {
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + newFileName);
                        File file1 = new File();
                        file1.setOriginalName(originalFilename);
                        file1.setFileSize(file.getSize());
                        file1.setFileType(file.getContentType());
                        file1.setGeneratedName(newFileName);
                        file1.setDownloadUrl(downloadUrl + newFileName);
                        file1.setIdAppartenance(id);
                        fileRepository.save(file1);
                        mutation.get().getPieceJointes().add(file1);
                        imutationRepository.save(mutation.get());
                        Files.write(path, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                    }
                }
                break;
            case "permutationDemande":
                Optional<Permutation> permutationDemande = iPermutationRepository.findById(id);
                if (permutationDemande.isEmpty()) {
                    return Response.exception().setMessage("Demande de permutation inexistante");
                }
                if (files.isEmpty()) {
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                // Seuls les deux agents concernés peuvent joindre leur dossier
                String matriculeConnecte = iUtilisateur.getCurrentUser().getMatricule();
                String partie;
                if (matriculeConnecte.equals(permutationDemande.get().getUtilisateur1().getMatricule())) {
                    partie = "DEMANDEUR";
                } else if (matriculeConnecte.equals(permutationDemande.get().getUtilisateur2().getMatricule())) {
                    partie = "RECEVEUR";
                } else {
                    return Response.exception().setMessage("Seuls les agents concernés par la permutation peuvent joindre un dossier");
                }
                if (permutationDemande.get().getPieceJointes() == null) {
                    permutationDemande.get().setPieceJointes(new java.util.ArrayList<>());
                }
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    try {
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + newFileName);
                        File file1 = new File();
                        file1.setOriginalName(originalFilename);
                        file1.setFileSize(file.getSize());
                        file1.setFileType(file.getContentType());
                        file1.setGeneratedName(newFileName);
                        file1.setDownloadUrl(downloadUrl + newFileName);
                        file1.setIdAppartenance(id);
                        file1.setFileCode(partie);
                        fileRepository.save(file1);
                        permutationDemande.get().getPieceJointes().add(file1);
                        iPermutationRepository.save(permutationDemande.get());
                        Files.write(path, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                    }
                }
                break;
            case "permutationSigne_DEMANDEUR":
            case "permutationSigne_RECEVEUR":
            case "permutationBordereau":
                // Circuit de signature : chef d'établissement, IEF puis IA (voir PermutationSignatures)
                Optional<Permutation> permutationSignee = iPermutationRepository.findById(id);
                if (permutationSignee.isEmpty()) {
                    return Response.exception().setMessage("Demande de permutation inexistante");
                }
                if (files.isEmpty()) {
                    return Response.exception().setMessage("Veuillez selectionner un fichier");
                }
                // type : permutationSigne_DEMANDEUR / permutationSigne_RECEVEUR (demande signée de l'agent)
                //        permutationBordereau (bordereau de transmission IEF / IA)
                Utilisateur acteurConnecte = iUtilisateur.getCurrentUser();
                String niveauActeur = PermutationSignatures.niveau(acteurConnecte.getProfils().stream().findAny().get().getCode());
                List<String> agentsCouverts = PermutationSignatures.agentsCouverts(permutationSignee.get(),
                        deconcentratedLevelRepository.findByMatricule(acteurConnecte.getMatricule()).orElse(null), niveauActeur);
                boolean estBordereau = type.equals("permutationBordereau");
                String agentSigne = estBordereau ? null : type.substring("permutationSigne_".length());
                if (agentsCouverts.isEmpty() || (estBordereau && niveauActeur.equals("CE"))
                        || (!estBordereau && !agentsCouverts.contains(agentSigne))) {
                    return Response.exception().setMessage("Vous n'êtes pas habilité à signer la demande de cet agent");
                }
                String codeSigne = estBordereau
                        ? PermutationSignatures.codeBordereau(niveauActeur, agentsCouverts)
                        : PermutationSignatures.codeSigne(agentSigne, niveauActeur);
                if (permutationSignee.get().getPieceJointes() == null) {
                    permutationSignee.get().setPieceJointes(new java.util.ArrayList<>());
                }
                // la demande signée remplace la demande courante de l'agent (soumise ou signée au niveau précédent) ;
                // un nouveau bordereau remplace celui du même acteur
                List<File> versionsRemplacees = permutationSignee.get().getPieceJointes().stream()
                        .filter(doc -> doc.getFileCode() != null
                                && (estBordereau ? codeSigne.equals(doc.getFileCode()) : doc.getFileCode().startsWith(agentSigne)))
                        .toList();
                if (!versionsRemplacees.isEmpty()) {
                    permutationSignee.get().getPieceJointes().removeAll(versionsRemplacees);
                    iPermutationRepository.save(permutationSignee.get());
                    fileRepository.deleteAll(versionsRemplacees);
                }
                for (MultipartFile file : files) {
                    String originalFilename = file.getOriginalFilename();
                    String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
                    String newFileName = UUID.randomUUID().toString() + extension;
                    try {
                        byte[] bytes = file.getBytes();
                        Path path = Paths.get(uploadDirectory + newFileName);
                        File file1 = new File();
                        file1.setOriginalName(originalFilename);
                        file1.setFileSize(file.getSize());
                        file1.setFileType(file.getContentType());
                        file1.setGeneratedName(newFileName);
                        file1.setDownloadUrl(downloadUrl + newFileName);
                        file1.setIdAppartenance(id);
                        file1.setFileCode(codeSigne);
                        fileRepository.save(file1);
                        permutationSignee.get().getPieceJointes().add(file1);
                        iPermutationRepository.save(permutationSignee.get());
                        Files.write(path, bytes);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
                    }
                }
                break;
        }
        Optional<Acte> acte = acteRepository.findActeById(id);
        //System.out.println("i'm here");


        // verifier si le fichier est vide
        if (files.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }


        return Response.ok().setMessage("Fichiers enregistree avec success");
    }

    @Override
    @Transactional
    public Response<Object> uploadFiles_(List<MultipartFile> files, long id) {
        Optional<DemandeStage> demandeStage = demandeStageRepository.findById(id);

        if (demandeStage.isEmpty()) {
            return Response.exception().setMessage("Demande stage inexistante.");
        }
        // verifier si le fichier est vide
        if (files.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }

        for (MultipartFile file : files) {
            // Generer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFileName = "DemandeDeStage" + UUID.randomUUID().toString() + extension;
            // enregistrement fichier
            try {

                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                File savedFile = fileRepository.save(file1);
                demandeStage.get().getJustificatfs().add(savedFile);
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }
        }
        return Response.ok().setMessage("Fichiers enregistree avec success");
    }

    @Override
    public Response<Object> uploadFilesForRapport(MultipartFile file, long id) {
        Optional<RapportStage> rapportStage = rapportStageRepository.findById(id);

        if (rapportStage.isEmpty()) {
            return Response.exception().setMessage("Rapport stage inéxistante.");
        }
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }

//        for (MultipartFile file : files) {
        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = "RapportDeStage_" + UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            File savedFile = fileRepository.save(file1);
            rapportStage.get().setPiecesJoint(savedFile);
            rapportStageRepository.save(rapportStage.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
//        }
        return Response.ok().setMessage("Fichiers enregistree avec success");
    }

    @Override
    public Response<Object> uploadFilesForAttestation(MultipartFile file, long id) {
        Optional<AttestationStage> attestationStage = attestationStageRepository.findById(id);

        if (attestationStage.isEmpty()) {
            return Response.exception().setMessage("Attestation stage inéxistante.");
        }
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }

//        for (MultipartFile file : files) {
        // Generer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = "AttestationDeSTage_" + UUID.randomUUID().toString() + extension;
        System.out.println(newFileName);
        // enregistrement fichier
        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl + newFileName);
            file1.setIdAppartenance(id);
            File savedFile = fileRepository.save(file1);
            attestationStage.get().setPiecesJoint(savedFile);
            attestationStageRepository.save(attestationStage.get());
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }
//        }
        return Response.ok().setMessage("Fichiers enregistree avec success");
    }

    @Override
    @Transactional
    public ResponseEntity<Resource> downloadFIle(String fileName) {
        // Load file as a Resource
        Path filePath = Paths.get(uploadDirectory).resolve(fileName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Check if the file exists
        if (!resource.exists()) {
        }

        // Set content disposition as attachment to force download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        // Set the content type based on file extension or default to application/octet-stream
        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            contentType = MediaType.parseMediaType(Files.probeContentType(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(contentType)
                .body(resource);
    }

    @Override
    public Response<Object> uploadFilesImputation(List<MultipartFile> files, long id) {
        return null;
    }

    @Override
    public Response<Object> uploadActesBordereaux(MultipartFile file, long id) {
        System.out.println("Bordereau");
        Optional<Acte> acte = acteRepository.findById(id);
        Utilisateur user= acte.get().getAgent();
        Utilisateur userConnceted= iUtilisateur.getCurrentUser();
      /*  if(user.getTypeUser().equalsIgnoreCase("CEN")){
            CentralLevel centralLevel=(CentralLevel) user;
        }
        else
            DeconcentratedLevel deconcentratedLevel=*/
        if (acte.isEmpty()) {
            return Response.exception().setMessage("Acte non existant.");
        }
        // verifier si le fichier est vide
        if (file.isEmpty()) {
            return Response.exception().setMessage("Veuillez selectionner un fichier");
        }

       // for (MultipartFile file : files) {
            // Generer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String newFileName = "Bordereau" + UUID.randomUUID().toString() + extension;
            // enregistrement fichier
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(uploadDirectory + newFileName);
                File file1 = new File();
                file1.setOriginalName(originalFilename);
                file1.setFileSize(file.getSize());
                file1.setFileType(file.getContentType());
                file1.setGeneratedName(newFileName);
                file1.setDownloadUrl(downloadUrl + newFileName);
                file1.setIdAppartenance(id);
                File savedFile = fileRepository.save(file1);
                System.out.println(savedFile.getGeneratedName());
                System.out.println(savedFile.getFileSize());
                Borderau borderau=new Borderau();
                borderau.setFileName(savedFile.getGeneratedName());
                borderau.setActeId(id);
                Borderau savedBordereau= bordereauRepository.save(borderau);
                System.out.println(
                        savedBordereau.toString()
                );
                if (acte.get().getStatutActe().getCode().equalsIgnoreCase("SOUMIS") ||
                        acte.get().getCurrentBordereau()==null){
                    acte.get().setCurrentBordereau(savedBordereau);
                    acte.get().setPredBordereau(savedBordereau);
                }
                if(user.getId()==userConnceted.getId()){
                    acte.get().setCurrentBordereau(null);
                }
                else {
                    acte.get().setPredBordereau(acte.get().getCurrentBordereau());
                    acte.get().setCurrentBordereau(savedBordereau);
                }
                //A continuer ici

                acteRepository.save(acte.get());
                System.out.println(acte.get().getCurrentBordereau().toString());
                Files.write(path, bytes);

            } catch (IOException e) {
                e.printStackTrace();
                return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
            }
       // }
        return Response.ok().setMessage("Fichiers enregistree avec success");
    }

    @Override
    public File getFileByGeneratedName(String generatedName) {
        return fileRepository.findByGeneratedName(generatedName);

    }


    @Override
    public Response<Object> uploadPermutationOs(MultipartFile file, Permutation permutation, Utilisateur utilisateur) {

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String newFileName = "Ordre_de_Service_signe_"+UUID.randomUUID().toString() + extension;
        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + newFileName);
            File file1 = new File();
            file1.setOriginalName(originalFilename);
            file1.setFileSize(file.getSize());
            file1.setFileType(file.getContentType());
            file1.setGeneratedName(newFileName);
            file1.setDownloadUrl(downloadUrl+newFileName);
            File savedFile = fileRepository.save(file1);
            Files.write(path, bytes);
            StatusPermutation statusPermutation = iStatusPermutationRepository.findByCode("VALIDER");
            TraitementPermutation traitementPermutation = new TraitementPermutation();
            traitementPermutation.setDateTraitementMutation(LocalDate.now());
            traitementPermutation.setStatut(statusPermutation);
            traitementPermutation.setTraiteur(utilisateur);
            traitementPermutation.setIdPermutation(permutation.getId());
            traitementPermutation.setMotif("valider");
            TraitementPermutation traitementSaved = traitementPermutationRepository.save(traitementPermutation);
            permutation.setTraitementPermutation(traitementSaved);

            /*
            * permutation des profils lors du chargement de l'OS signé
            * */
            permutation.setNiveau(6);
            DeconcentratedLevel deconcentratedLevel1 = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur1().getMatricule()).get();
            DeconcentratedLevel deconcentratedLevel2 = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur2().getMatricule()).get();
            IA ia = deconcentratedLevel1.getIa();
            IEF ief = deconcentratedLevel1.getIef();
            Etablissement etablissement = deconcentratedLevel1.getEtablissement();
            Region region = regionRepository.findById(permutation.getUtilisateur1().getRegion().getId()).orElseThrow(null);

            deconcentratedLevel1.setIa(deconcentratedLevel2.getIa());
            deconcentratedLevel1.setIef(deconcentratedLevel2.getIef());
            deconcentratedLevel1.setEtablissement(deconcentratedLevel2.getEtablissement());
            Region region1 = regionRepository.findById(permutation.getUtilisateur2().getRegion().getId()).orElseThrow(null);


            // permutation.getUtilisateur1().getRegion().setId(region1.getId());
            permutation.getUtilisateur1().setRegion(region1);

            deconcentratedLevel2.setIa(ia);
            deconcentratedLevel2.setIef(ief);
            deconcentratedLevel2.setEtablissement(etablissement);
            permutation.getUtilisateur2().setRegion(region);

            permutation.setDateValidation(LocalDate.now());
            deconcentratedLevelRepository.save(deconcentratedLevel1);
            deconcentratedLevelRepository.save(deconcentratedLevel2);

            permutation.setOrdreService(file1.getGeneratedName());
            Permutation permutationSaved = iPermutationRepository.save(permutation);

            System.out.println("####send notif #####");
            notificationService.sendNotificationDemandeurPermutation(
                    new LoginFormDTO(permutation.getUtilisateur1().getEmail(),""),
                    "le service DGPEEC",
                    permutation.getId(),
                    "validée"

            );
            System.out.println("####send mail #####");
            for(String email : permutation.getEmailTraitant())
                //System.out.println("email : " + email);
                mailService.sendMailWithPJ(
                        new MailInfosDTO(null, "Bonjour, \n Veuillez recevoir en piéce en jointe la liste des permutations validées.", "Permutation(s) validée(s)", null, email),
                        newFileName
                );

            return Response.ok().setMessage("Fichiers enregistrement avec success")
                    .setPayload(permutationSaved);
        } catch (IOException e) {
            e.printStackTrace();
            return Response.exception().setMessage("Une erreur s'est produit lors de l'enregistrement du fichier");
        }

    }

    @Override
    public Response<Object> deleteFileForCampagne(long idCampagne, long idFile) {
        Optional<Campagne> campagneOptional = campagneRepository.findById(idCampagne);
        if(campagneOptional.isPresent()){
            campagneOptional.get().getPieceJoint().stream().filter(file -> file.getId() != idFile);
            fileRepository.deleteById(idFile);
        }
        return Response.ok().setMessage("Pièce supprimer avec succès.");
    }
}

