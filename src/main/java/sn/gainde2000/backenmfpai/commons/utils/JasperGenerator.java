package sn.gainde2000.backenmfpai.commons.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.Acte;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.Mutation;
import sn.gainde2000.backenmfpai.entities.servicecarriere.MutationPermutation.Permutation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.ActeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.Mutation.MutationReportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeReportDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Actes.ActeResponseDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationOrBulletinReportDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.Imputation.ImputationResponseDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.PermutationReportDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.serviceutilisateur.utilisateur.UtilisateurResponseDTO;

import java.io.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JasperGenerator {


    private final CentralLevelRepository centralLevelRepository;

    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final ActeRepository acteRepository;

    @Value("classpath:models/bordereauBis.jrxml")
    private String bordereauModel;



    public static byte[] getBordereauPDF(ActeResponseDTO acte) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:models/bordereau.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRDataSource dataSource = new JRBeanArrayDataSource(new ActeResponseDTO[]{acte});
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Acte");
        //Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        // Transforme To Base64
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    public  byte[] getBordereauPDF1(ActeResponseDTO acte) throws FileNotFoundException, JRException {
        //File file = ResourceUtils.getFile("classpath:models/bordereauBis.jrxml");
        File file = ResourceUtils.getFile("classpath:models/BordereauBis.jrxml");
        Optional<Acte> optionalActe=acteRepository.findActeByReferenceActe(acte.getReferenceActe());
        System.out.println(optionalActe.toString());
        //acte.setTypeActeAA(optionalActe.get().getTypeAA());
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        ActeReportDTO acteReportDTO=new ActeReportDTO();
        UtilisateurResponseDTO agent=acte.getAgent();
       // acteReportDTO.setBordereau(acte.getBordereau());
        acteReportDTO.setNom(acte.getAgent().getNom());
        acteReportDTO.setReferenceActe(acte.getReferenceActe());
        acteReportDTO.setDateDemandeActe(acte.getDateDemandeActe());
        acteReportDTO.setPrenom(acte.getAgent().getPrenom());
        acteReportDTO.setDirection(acte.getDirection().getLabel());
        acteReportDTO.setMatricule(acte.getAgent().getMatricule());
        acteReportDTO.setTypeActeAA(optionalActe.get().getTypeAA().getLibelle());
        JRDataSource dataSource = new JRBeanArrayDataSource(new ActeReportDTO[]{acteReportDTO});
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Acte");
        //Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        // Transforme To Base64
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }



    public  byte[] getImputationOrBulletinPDF(ImputationOuBulletin imputation) throws FileNotFoundException, JRException {
        //File file = ResourceUtils.getFile("classpath:models/bordereauBis.jrxml");

        ImputationOrBulletinReportDto imputationOrBulletinReportDto = new ImputationOrBulletinReportDto();


        if(imputation.getUtilisateur().getTypeUser().equals("CEN")){
            CentralLevel centralLevel = centralLevelRepository.findByEmail(imputation.getUtilisateur().getEmail()).get();
            imputationOrBulletinReportDto.setDirection(centralLevel.getDirection().getLabel());
            imputationOrBulletinReportDto.setQualite(resolveQualite(imputation.getUtilisateur().getProfils(), "d'agent"));
        }else{
            DeconcentratedLevel deconcentratedLevel = deconcentratedLevelRepository.findByMatricule(imputation.getUtilisateur().getMatricule()).get();
            if(deconcentratedLevel.getEtablissement()!=null){
                // Agent formateur rattaché à un établissement : on affiche l'établissement exact,
                // plus précis que l'IA/IEF de rattachement.
                imputationOrBulletinReportDto.setIaIef(deconcentratedLevel.getEtablissement().getLabel());
            }else{
                String ief = "";
                if(deconcentratedLevel.getIef()!=null)
                    ief = " /"+deconcentratedLevel.getIef().getLabel();
                imputationOrBulletinReportDto.setIaIef(deconcentratedLevel.getIa().getLabel()+ief);
            }
            imputationOrBulletinReportDto.setQualite(resolveQualite(imputation.getUtilisateur().getProfils(), "de formateur"));
        }
        imputationOrBulletinReportDto.setNumeroDemande(imputation.getId());
        imputationOrBulletinReportDto.setDateImputation(imputation.getDateImputation());
        imputationOrBulletinReportDto.setCorps(imputation.getUtilisateur().getCorpsGrade() == null
                ? "Non renseigné" : imputation.getUtilisateur().getCorpsGrade().getLabel());
        imputationOrBulletinReportDto.setGrade(imputation.getUtilisateur().getGrade() == null
                ? "Non renseigné" : imputation.getUtilisateur().getGrade().getLabel());
        imputationOrBulletinReportDto.setId(imputation.getId());
        imputationOrBulletinReportDto.setMatricule(imputation.getUtilisateur().getMatricule());
        imputationOrBulletinReportDto.setNomBeneficiere(imputation.getNomBeneficiere());
        imputationOrBulletinReportDto.setPrenomBeneficiere(imputation.getPrenomBeneficiere());
        imputationOrBulletinReportDto.setTypeDemande(imputation.getTypeDemande());
        imputationOrBulletinReportDto.setStatusBeneficiere(imputation.getStatusBeneficiere());
        imputationOrBulletinReportDto.setAdresse(imputation.getUtilisateur().getAdresse());
        if(imputation.getStatusBeneficiere().equals("Soi")){
            imputationOrBulletinReportDto.setNomAgent("");
            imputationOrBulletinReportDto.setPrenomAgent("");
        }else{
            imputationOrBulletinReportDto.setNomAgent(imputation.getUtilisateur().getNom());
            imputationOrBulletinReportDto.setPrenomAgent(imputation.getUtilisateur().getPrenom());
        }

        if (imputation.getTypeDemande().equals("Imputation budgétaire")){
            if(imputation.getStatusBeneficiere().equals("Soi")){
                File file = ResourceUtils.getFile("classpath:static/Imputation.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRDataSource dataSource = new JRBeanArrayDataSource(new ImputationOrBulletinReportDto[]{imputationOrBulletinReportDto});
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("createdBy", "imputationOrBulletin");
                //Fill Jasper report
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                // Transforme To Base64
                return JasperExportManager.exportReportToPdf(jasperPrint);
            }else{
                File file = ResourceUtils.getFile("classpath:static/ImputationSoi.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRDataSource dataSource = new JRBeanArrayDataSource(new ImputationOrBulletinReportDto[]{imputationOrBulletinReportDto});
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("createdBy", "imputationOrBulletin");
                //Fill Jasper report
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                // Transforme To Base64
                return JasperExportManager.exportReportToPdf(jasperPrint);
            }
        }else{
            if(imputation.getStatusBeneficiere().equals("Soi")){
                File file = ResourceUtils.getFile("classpath:static/BulletinSoi.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

                JRDataSource dataSource = new JRBeanArrayDataSource(new ImputationOrBulletinReportDto[]{imputationOrBulletinReportDto});
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("createdBy", "imputationOrBulletin");
                //Fill Jasper report
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                // Transforme To Base64
                return JasperExportManager.exportReportToPdf(jasperPrint);
            }else{
                File file = ResourceUtils.getFile("classpath:static/Bulletin.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

                JRDataSource dataSource = new JRBeanArrayDataSource(new ImputationOrBulletinReportDto[]{imputationOrBulletinReportDto});
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("createdBy", "imputationOrBulletin");
                //Fill Jasper report
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                // Transforme To Base64
                return JasperExportManager.exportReportToPdf(jasperPrint);
            }

        }


    }

    static String resolveQualite(Set<Profile> profils, String fallback) {
        if (profils == null) {
            return fallback;
        }
        return profils.stream()
                .filter(Objects::nonNull)
                .map(Profile::getLabel)
                .filter(label -> label != null && !label.isBlank())
                .map(String::trim)
                .findFirst()
                .orElse(fallback);
    }

    public  byte[] getPermutationOS(Permutation permutation) throws FileNotFoundException, JRException {
        DeconcentratedLevel demandeur = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur1().getMatricule()).get();
        DeconcentratedLevel receveur = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur2().getMatricule()).get();

        PermutationReportDto ligneDemandeur = new PermutationReportDto();
        ligneDemandeur.setMatricule(demandeur.getMatricule());
        ligneDemandeur.setPrenoms(demandeur.getPrenom());
        ligneDemandeur.setNom(demandeur.getNom());
        ligneDemandeur.setSpecialite(demandeur.getSpeciality() != null ? demandeur.getSpeciality().getLabel() : "");
        ligneDemandeur.setOrigine(permutation.getEtablissementDemandeur().getLabel());
        ligneDemandeur.setDestination(permutation.getEtablissementReceveur().getLabel());
        ligneDemandeur.setAcademie(permutation.getIaReceveur().getLabel());

        PermutationReportDto ligneReceveur = new PermutationReportDto();
        ligneReceveur.setMatricule(receveur.getMatricule());
        ligneReceveur.setPrenoms(receveur.getPrenom());
        ligneReceveur.setNom(receveur.getNom());
        ligneReceveur.setSpecialite(receveur.getSpeciality() != null ? receveur.getSpeciality().getLabel() : "");
        ligneReceveur.setOrigine(permutation.getEtablissementReceveur().getLabel());
        ligneReceveur.setDestination(permutation.getEtablissementDemandeur().getLabel());
        ligneReceveur.setAcademie(permutation.getIaDemandeur().getLabel());

        File file = ResourceUtils.getFile("classpath:static/PermutationOS.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CollectionBeanParam", new JRBeanCollectionDataSource(List.of(ligneDemandeur, ligneReceveur)));
        parameters.put("dateCreation", new Date());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

    public  byte[] getPermutationAllOS(List<Permutation> permutations) throws FileNotFoundException, JRException {
        System.out.println("\n###entrer dans la partie generation fichier");

        int i = 0,j=0;
        List<PermutationReportDto> permutationReportDtos =  new ArrayList<>();
        //PermutationReportDto permutationReportDto = new PermutationReportDto();

        while(i <permutations.size()){
            PermutationReportDto permutationReportDtoDemandeur = new PermutationReportDto();
            PermutationReportDto permutationReportDtoReceveur = new PermutationReportDto();

            System.out.println("\n permu report == "+permutations.get(i).getId());
            Permutation permutation = permutations.get(i);
            DeconcentratedLevel demandeur = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur1().getMatricule()).get();
            DeconcentratedLevel receveur = deconcentratedLevelRepository.findByMatricule(permutation.getUtilisateur2().getMatricule()).get();

            permutationReportDtoDemandeur.setNom(demandeur.getNom());
            permutationReportDtoReceveur.setNom(receveur.getNom());
            permutationReportDtoDemandeur.setPrenoms(demandeur.getPrenom());
            permutationReportDtoReceveur.setPrenoms(receveur.getPrenom());
            permutationReportDtoDemandeur.setMatricule(demandeur.getMatricule());
            permutationReportDtoReceveur.setMatricule(receveur.getMatricule());
            permutationReportDtoDemandeur.setSpecialite(demandeur.getSpeciality().getLabel());
            permutationReportDtoReceveur.setSpecialite(receveur.getSpeciality().getLabel());
            // On lit l'origine / la destination / l'académie depuis les champs figés de la
            // permutation (capturés à la création) et NON depuis le compte utilisateur en direct :
            // à la validation de l'OS signé, les établissements/IA des deux agents sont échangés,
            // ce qui inverserait les colonnes si on relisait le compte utilisateur.
            permutationReportDtoDemandeur.setOrigine(permutation.getEtablissementDemandeur().getLabel());
            permutationReportDtoReceveur.setOrigine(permutation.getEtablissementReceveur().getLabel());
            permutationReportDtoDemandeur.setDestination(permutation.getEtablissementReceveur().getLabel());
            permutationReportDtoReceveur.setDestination(permutation.getEtablissementDemandeur().getLabel());
            permutationReportDtoDemandeur.setAcademie(permutation.getIaReceveur().getLabel());
            permutationReportDtoReceveur.setAcademie(permutation.getIaDemandeur().getLabel());
            permutationReportDtos.add(permutationReportDtoDemandeur);
            permutationReportDtos.add(permutationReportDtoReceveur);

            i++;
        }

        File file = ResourceUtils.getFile("classpath:static/Blank_A4_Landscape.jrxml");
        JRBeanCollectionDataSource itemsDataBean = new JRBeanCollectionDataSource(permutationReportDtos);
        Map<String , Object> parameters = new HashMap<String , Object>();
        parameters.put("CollectionBeanParam", itemsDataBean);
        parameters.put("dateCreation", new Date());
        JasperDesign jasperDesign = JRXmlLoader.load(file);
        JasperReport jasperReport1 = JasperCompileManager.compileReport((jasperDesign));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport1, parameters, new JREmptyDataSource());

        //parameters.put("createdBy", "permutationReports");

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }
    public  byte[] getMutationOS(List<Mutation> mutations) throws FileNotFoundException, JRException {

        int i = 0;

        List<MutationReportDTO> mutationReportDTOs = new ArrayList<>();

        for(Mutation mutation : mutations){
            MutationReportDTO mutationReportDTO1 = new MutationReportDTO();
            //  mutationReportDTO1.setId(mutation.getId());
            mutationReportDTO1.setCorpsEtGrade(mutation.getDemandeur().getCorpsGrade() != null
                    ? mutation.getDemandeur().getCorpsGrade().getLabel() : "");
            mutationReportDTO1.setNom(mutation.getDemandeur().getNom());
            mutationReportDTO1.setPrenom(mutation.getDemandeur().getPrenom());
            mutationReportDTO1.setMatricule(mutation.getDemandeur().getMatricule());
            if(Objects.equals(mutation.getOrigineDemandeurLog().getOrigineUserType(), "DEC"))
                mutationReportDTO1.setOrigine(mutation.getOrigineDemandeurLog().getEtablissement() != null
                        ? mutation.getOrigineDemandeurLog().getEtablissement().getLabel() : "");
            else{
                mutationReportDTO1.setOrigine(mutation.getOrigineDemandeurLog().getDirection() != null
                        ? mutation.getOrigineDemandeurLog().getDirection().getLabel() : "");
            }
            if(Objects.equals(mutation.getDestinataireType(), "DEC")) {
                mutationReportDTO1.setDestination(mutation.getEtablissementSouhaitee() != null
                        ? mutation.getEtablissementSouhaitee().getLabel() : "");
                mutationReportDTO1.setEnQualiteDe(mutation.getIaSouhaitee() != null
                        ? mutation.getIaSouhaitee().getLabel() : "");
            }
            else{
                mutationReportDTO1.setDestination(mutation.getDirectionSouhaitee() != null
                        ? mutation.getDirectionSouhaitee().getLabel() : "");
                mutationReportDTO1.setEnQualiteDe("Niveau central");
            }

            //  List<Profile> profiles = (List<Profile>) mutation.getDemandeur().getProfils();

            mutationReportDTO1.setSpecialite(mutation.getDemandeur().getSpeciality() != null
                    ? mutation.getDemandeur().getSpeciality().getLabel() : "");
            mutationReportDTOs.add(mutationReportDTO1);
        }

        File file = ResourceUtils.getFile("classpath:static/mutationOS.jrxml");
        JRBeanCollectionDataSource itemsDataBean = new JRBeanCollectionDataSource(mutationReportDTOs);
        Map<String , Object> parameters = new HashMap<String , Object>();

        parameters.put("CollectionBeanParam", itemsDataBean);
        JasperDesign jasperDesign = JRXmlLoader.load(file);
        JasperReport jasperReport1 = JasperCompileManager.compileReport((jasperDesign));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport1, parameters, new JREmptyDataSource());

        parameters.put("createdBy", "mutationReports");

        return JasperExportManager.exportReportToPdf(jasperPrint);

    }

}

