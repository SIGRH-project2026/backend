package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.entities.actualite.Actualite;
import sn.gainde2000.backenmfpai.entities.actualite.CategorieActualite;
import sn.gainde2000.backenmfpai.entities.actualite.QActualite;
import sn.gainde2000.backenmfpai.entities.actualite.TypeArticle;
import sn.gainde2000.backenmfpai.entities.file.File;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite.IActualiteRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite.ICategorieActualite;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actualite.ITypeArticle;
import sn.gainde2000.backenmfpai.services.interfaces.files.IFile;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IActualiteService;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.responses.FileDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActualiteServiceImpl implements IActualiteService {

    private final IActualiteRepository iActualiteRepository;
    private final IUtilisateur iUtilisateur;
    private final ITypeArticle iTypeArticle;
    private final ICategorieActualite iCategorieActualite;
    private final IFile iFile;


    @Override
    public Actualite create(Actualite actu, MultipartFile file) throws IOException {
        Utilisateur currentUser  = iUtilisateur.getCurrentUser();
        if(currentUser.getProfils().stream().findAny().get().getCode().equals("ADMIN-DRH")
                || currentUser.getProfils().stream().findAny().get().getCode().equals("Directeur-DRH") ) {
            actu.setDatePublication(LocalDate.now());
            File fileSaved = iFile.uploadImage(file);
            actu.setImage(fileSaved);
            return iActualiteRepository.save(actu);
        }else{
            throw new EntityNotFoundException("Vous n'avez pas le droit d'ajouter une actualité");
        }
    }

    @Override
    public Actualite getOne(long id) {
        Optional<Actualite> actualiteOptional = iActualiteRepository.findById(id);
        if(actualiteOptional.isPresent()) {
            Actualite actualite = actualiteOptional.get();
            java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
            byte[] fileContent = null;
            try {
                fileContent = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            actualite.getImage().setBase64(encodedString);
            return actualite;

        }
        return null;
    }
/*
    @Override
    public Response<Object> getAll(int page, int size, String statut) {

        BooleanBuilder builder = new BooleanBuilder();
        QActualite qActualite = QActualite.actualite;
        builder.and(
                qActualite.isDeleted.isFalse()
        );
        if (!Objects.equals(statut, "")) {
            if(statut.equals("true")) {
                builder.and(
                        qActualite.isActivated.isTrue() //
                );
            }else{
                builder.and(
                        qActualite.isActivated.isFalse() //
                );
            }
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return  Response.ok().setPayload(iActualiteRepository.findAll(builder.getValue(),pageRequest).stream().map(actualite -> {

            java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
            byte[] fileContent = null;
            try {
                fileContent = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            actualite.getImage().setBase64(encodedString);
            return actualite;
        }).collect(Collectors.toList()));
    }
*/
public Response<Object> getAll(int page, int size, String statut) {

    BooleanBuilder builder = new BooleanBuilder();
    QActualite qActualite = QActualite.actualite;
    builder.and(
            qActualite.isDeleted.isFalse()
    );
    if (!Objects.equals(statut, "")) {
        if(statut.equals("true")) {
            builder.and(
                    qActualite.isActivated.isTrue() //
            );
        }else{
            builder.and(
                    qActualite.isActivated.isFalse() //
            );
        }
    }
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    return  Response.ok().setPayload(iActualiteRepository.findAll(builder.getValue(),pageRequest).stream().map(actualite -> {

        java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
        byte[] fileContent = null;
        try {
            fileContent = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        actualite.getImage().setBase64(encodedString);
        return actualite;
    }).collect(Collectors.toList()));
}
    @Override
    public Response<Object> getAllActivate() {

        BooleanBuilder builder = new BooleanBuilder();
        QActualite qActualite = QActualite.actualite;
        builder.and(
                qActualite.isDeleted.isFalse()
        );
        builder.and(
                qActualite.isActivated.isTrue()
        );
        List<Actualite> actualites = (List<Actualite>) iActualiteRepository.findAll(builder.getValue(), Sort.by(Sort.Direction.DESC, "id"));

        List<Actualite> actualitesWithEncodedImages = actualites.stream().map(actualite -> {
            java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
            byte[] fileContent = null;
            try {
                fileContent = FileUtils.readFileToByteArray(file);
                // Encoder le fichier en base64
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                actualite.getImage().setBase64(encodedString);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier image: " + e.getMessage(), e);
            }
            return actualite;
        }).collect(Collectors.toList());
        return Response.ok().setPayload(actualitesWithEncodedImages);

    }

    @Override
    public Response<Object> getAllActuOrRecrutementActivate(String code) {

        BooleanBuilder builder = new BooleanBuilder();
        QActualite qActualite = QActualite.actualite;
        builder.and(
                qActualite.isDeleted.isFalse()
        );
        builder.and(
                qActualite.isActivated.isTrue()
        );
        builder.and(
                qActualite.typeArticle.code.likeIgnoreCase(code)
        );

        List<Actualite>  actualites = (List<Actualite>) iActualiteRepository.findAll(builder.getValue(), Sort.by(Sort.Direction.DESC, "id"));
        List<Actualite> actualitesWithEncodedImages = actualites.stream().map(actualite -> {
            java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
            byte[] fileContent = null;
            try {
                fileContent = FileUtils.readFileToByteArray(file);
                // Encoder le fichier en base64
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                actualite.getImage().setBase64(encodedString);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier image: " + e.getMessage(), e);
            }
            return actualite;
        }).collect(Collectors.toList());
        return Response.ok().setPayload(actualitesWithEncodedImages);
    }

    @Override
    public Response<Object> getLastActu(){
        List<Actualite> actualites = iActualiteRepository.findLastActu();

        List<Actualite> actualitesWithEncodedImages = actualites.stream().map(actualite -> {
            java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
            byte[] fileContent = null;
            try {
                fileContent = FileUtils.readFileToByteArray(file);
                // Encoder le fichier en base64
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                actualite.getImage().setBase64(encodedString);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier image: " + e.getMessage(), e);
            }
            return actualite;
        }).collect(Collectors.toList());
        return Response.ok().setPayload(actualitesWithEncodedImages).setMessage("les dernieres actualités");
    }

    @Override
    public Response<Object> getLastRecrutement(){
        List<Actualite> recrutement = iActualiteRepository.findByTypeArticle("RECRUTEMENT");
        List<Actualite> actualitesWithEncodedImages = recrutement.stream().map(actualite -> {
            java.io.File file = new java.io.File(actualite.getImage().getDownloadUrl());
            byte[] fileContent = null;
            try {
                fileContent = FileUtils.readFileToByteArray(file);
                // Encoder le fichier en base64
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                actualite.getImage().setBase64(encodedString);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de la lecture du fichier image: " + e.getMessage(), e);
            }
            return actualite;
        }).collect(Collectors.toList());
        return Response.ok().setPayload(actualitesWithEncodedImages).setMessage("les dernieres actualités");
    }

    @Override
    public Actualite delete(long id) {
        Optional<Actualite> actualiteOptional = iActualiteRepository.findById(id);
        if (actualiteOptional.isPresent()){
            Actualite actu = actualiteOptional.get();
            actu.setDeleted(true);
            return iActualiteRepository.save(actu);
        }else
            throw new EntityNotFoundException("actualité inexistante");
    }
    @Override
    public List<TypeArticle> getAllTypeArticle(){
        return iTypeArticle.findAll();
    }

    @Override
    public List<CategorieActualite> getAllCategorieActualite(){
        return iCategorieActualite.findAll();
    }
    @Override
    public Actualite changeStatus(long id){
        Optional<Actualite>  actualiteOptional = iActualiteRepository.findById(id);
        if(actualiteOptional.isPresent()){
            Actualite actualite = actualiteOptional.get();
            actualite.setActivated(!actualite.isActivated());
            return iActualiteRepository.save(actualite);
        }
        else
            throw new EntityNotFoundException("actualité inexistante");
    }
}
