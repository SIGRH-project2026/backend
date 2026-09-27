package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.FicheEtablissement;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.commons.exception.GenericApiException;
import sn.gainde2000.backenmfpai.entities.servicecarriere.BesoinEnPersonnel.QBesoinEnPersonnel;
import sn.gainde2000.backenmfpai.entities.servicecarriere.FicheEtablissement.*;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Etablissement;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.FicheEtablissement.IFicheSynoptiqueMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.FicheEtablissement.IDisciplineRepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.FicheEtablissement.IFicheSynoptiqueRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.EtablissementRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.FicheEtablissement.IFicheSynoptique;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.FicheEtablissement.FicheSynoptiqueDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.BesoinEnPersonnel.BesoinEnPersonnelRDTO;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FicheSynoptiqueImpl implements IFicheSynoptique {
    private final IUtilisateurRepository iUtilisateurRepository;
    private final IFicheSynoptiqueRepository iFicheSynoptiqueRepository;
    private final IFicheSynoptiqueMapper iFicheSynoptiqueMapper;
    private final DeconcentratedLevelRepository deconcentratedLevelRepository;
    private final IDisciplineRepository iDisciplineRepository;
    private final EtablissementRepository typeDetablissementRepository;
    @Override
    @Transactional
    public Response<Object> createFicheSynoptique(FicheSynoptiqueDTO ficheSynoptiqueDTO) {
        try {
            Long userId = ficheSynoptiqueDTO.userId();

            DeconcentratedLevel chefEtablissement = (DeconcentratedLevel) iUtilisateurRepository.findById(userId)
                    .orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));


            FicheSynoptique ficheSynoptique = this.iFicheSynoptiqueMapper.toEntity(ficheSynoptiqueDTO);
            ficheSynoptique.setChefEtablissemnt(chefEtablissement);

            ficheSynoptique = this.iFicheSynoptiqueRepository.save(ficheSynoptique);

            return Response.ok()
                    .setMessage("Fiche Synoptique créée . ")
                    .setPayload(this.iFicheSynoptiqueMapper.toDto(ficheSynoptique));
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur s'est produite lors de la création de fiche synoptique.");
        }
    }

    @Override
    @Transactional
    public Response<Object> updateFicheSynoptique(FicheSynoptique ficheSynoptique) {
        try {

            ficheSynoptique = this.iFicheSynoptiqueRepository.save(ficheSynoptique);

            return Response.ok()
                    .setMessage("Fiche Synoptique modifiée. ")
                    .setPayload(this.iFicheSynoptiqueMapper.toDto(ficheSynoptique));
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur s'est produite lors de la modification de la fiche synoptique.");
        }
    }

    @Override
    public Response<Object> getMaFicheSynoptique(Long chefEtablissementUserId) {
        try {

            DeconcentratedLevel chefEtablissement = (DeconcentratedLevel) iUtilisateurRepository.findById(chefEtablissementUserId)
                    .orElseThrow(() -> new GenericApiException("Cet utilisateur n'existe pas"));


            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findFicheSynoptiqueByChefEtablissemnt(chefEtablissement)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            return Response.ok()
                    .setMessage("Fiche d'etablissement recupérée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de la récupération de votre Fiche Synoptique : " );
        }
    }
    @Override
    public Response<Object> getMaFicheSynoptiqueByCodeEtab(String codeEtablissement) {
        try {



            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findFicheSynoptiqueByChefEtablissemnt_EtablissementCode(codeEtablissement)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            return Response.ok()
                    .setMessage("Fiche d'etablissement recupérée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de la récupération de votre Fiche Synoptique : " );
        }
    }
    /*@Override
    public Response<Object> calculeDeficit(String codeEtablissement, Long idDiscipline ) {
        try {


            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findFicheSynoptiqueByChefEtablissemnt_EtablissementCode(codeEtablissement)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));
            List<FiliereDiscipline> filiereDisciplines = ficheSynoptique.getFiliereDisciplines();


            //nombre Total  d'heures que la que la discipline est dispensée dans ll'etablissement
            int totalHeureDispenses = 0;
            for (ClasseProfDiscipline classeProfDiscipline : ficheSynoptique.getClasseProfDisciplines()){
               for(ProfDiscipline profDiscipline : classeProfDiscipline.getProfDiscipline()){

               }
            }
            int THC = 0;
            for (FiliereDiscipline filiereDiscipline : filiereDisciplines){
                List<DisciplineQuantum> disciplineQuantums = filiereDiscipline.getDisciplineQuantums();
                for (DisciplineQuantum disciplineQuantum : disciplineQuantums){
                    if(disciplineQuantum.getDiscipline().getId() == idDiscipline)
                        THC += disciplineQuantum.getQuantum();
                }
            }
            return Response.ok()
                    .setMessage("Fiche d'etablissement recupérée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de la récupération de votre Fiche Synoptique : " );
        }
    }*/
    @Override
    public Response<Object>  addSerie(Long idFiche, List<SerieNiveauDiscipline> serieNiveauDisciplines) {
        try {

            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findById(idFiche)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            List<SerieNiveauDiscipline> serieNiveauDisciplines1 = ficheSynoptique.getSerieNiveauDisciplines();


            serieNiveauDisciplines1.addAll(serieNiveauDisciplines);

            ficheSynoptique.setSerieNiveauDisciplines(serieNiveauDisciplines1);
            iFicheSynoptiqueRepository.save(ficheSynoptique);

            return Response.ok()
                    .setMessage("Série ajoutée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

       } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de l'ajout de(s) série(s)' : " );
        }
    }
    @Override
    public Response<Object> addFiliere(Long idFiche, List<FiliereDiscipline> filiereDisciplineDTO) {
        try {

            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findById(idFiche)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            List<FiliereDiscipline> filiereDisciplines = ficheSynoptique.getFiliereDisciplines();
       /*     List<FiliereDiscipline> filiereDisciplinesValid = new ArrayList<>();
            for(FiliereDiscipline filiereDiscipline : filiereDisciplines){
                for (FiliereDiscipline filiereDiscipline1 : filiereDisciplineDTO){
                    //verifions si le code d'une ancienne filére est égale à un code d'une nouvelle
                    if(Objects.equals(filiereDiscipline.getFiliere().getCode(), filiereDiscipline1.getFiliere().getCode())){
                       List<DisciplineQuantum> disciplineQuantums = filiereDiscipline.getDisciplineQuantums();
                       disciplineQuantums.addAll(filiereDiscipline1.getDisciplineQuantums());
                       filiereDiscipline.setDisciplineQuantums(disciplineQuantums);
                        filiereDisciplinesValid.add(filiereDiscipline);
                    }else  filiereDisciplinesValid.add(filiereDiscipline1);
                }

            }*/
            filiereDisciplines.addAll(filiereDisciplineDTO);

            ficheSynoptique.setFiliereDisciplines(filiereDisciplines);
            iFicheSynoptiqueRepository.save(ficheSynoptique);

            return Response.ok()
                    .setMessage("Filiére ajoutée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de la récupération de votre Fiche Synoptique : " );
        }
    }
    @Override
    public Response<Object> addClasse(Long idFiche, List<ClasseProfDiscipline> classeProfDisciplineDTO) {
        try {

            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findById(idFiche)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            List<ClasseProfDiscipline> classeProfDisciplines = ficheSynoptique.getClasseProfDisciplines();
            classeProfDisciplines.addAll(classeProfDisciplineDTO);
            ficheSynoptique.setClasseProfDisciplines(classeProfDisciplines);
            iFicheSynoptiqueRepository.save(ficheSynoptique);

            return Response.ok()
                    .setMessage("Classe ajoutée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de l'ajout de la classe dans votre Fiche Synoptique : " );
        }
    }

    //add classe with Série
    @Override
    public Response<Object> addClasseSerie(Long idFiche, List<SerieClasseProfDiscipline> serieClasseProfDisciplines) {
        try {

            FicheSynoptique ficheSynoptique = iFicheSynoptiqueRepository.findById(idFiche)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            List<SerieClasseProfDiscipline> serieClasseProfDisciplines1 = ficheSynoptique.getSerieClasseProfDisciplines();
            serieClasseProfDisciplines1.addAll(serieClasseProfDisciplines);
            ficheSynoptique.setSerieClasseProfDisciplines(serieClasseProfDisciplines1);
            iFicheSynoptiqueRepository.save(ficheSynoptique);

            return Response.ok()
                    .setMessage("Classe ajoutée")
                    .setPayload(iFicheSynoptiqueMapper.toDto(ficheSynoptique));

        } catch (Exception e) {
            return Response.exception().setMessage("Une erreur est survenue lors de l'ajout de la classe avec série dans votre Fiche Synoptique : " );
        }
    }

    //Horaire Profs
    @Override
    public Response<Object> HorairesProfs(String codeEtab, int page, int size, String matricule, String nomComplet) {

        try {
            FicheSynoptique ficheSynoptique = this.iFicheSynoptiqueRepository.findFicheSynoptiqueByChefEtablissemnt_EtablissementCode(codeEtab)
                                                  .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));
            List<DeconcentratedLevel> professeurs =  deconcentratedLevelRepository.getDeconnectedByProfilEtablissement(codeEtab);

            List<HoraireProfesseurs> horaireProfesseurs = new ArrayList<>();
            Set<Discipline> disciplineSet = new HashSet<>();
            List<ClasseDisciplinesForProf> classeDisciplinesForProfs = new ArrayList<>();
            if(Objects.equals(matricule, "") && Objects.equals(nomComplet, ""))
            for (DeconcentratedLevel prof : professeurs) {
                HoraireProfesseurs horaireProfesseurs1 = new HoraireProfesseurs();
                int heurDisp = 0;
                int nbreClasse = 0;
                int nbrDisc = 0;
                if(ficheSynoptique.getClasseProfDisciplines() != null)
                    for (ClasseProfDiscipline classeProfDiscipline : ficheSynoptique.getClasseProfDisciplines()) {
                    ClasseDisciplinesForProf cpd = new ClasseDisciplinesForProf();
                    for (ProfDiscipline profDiscipline : classeProfDiscipline.getProfDiscipline()) {

                       if (Objects.equals(profDiscipline.getProfesseur().getId(), prof.getId())){
                           cpd.setDisciplineQuantum(profDiscipline.getDisciplineQuantums());
                           cpd.setNomClasse(classeProfDiscipline.getNomClasse());
                           horaireProfesseurs1.setProfesseur(prof);
                           for(DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums() ){

                               heurDisp += disciplineQuantum.getQuantum();
                               disciplineSet.add(disciplineQuantum.getDiscipline());

                           }
                           classeDisciplinesForProfs.add(cpd);
                           nbreClasse += 1;
                       }
                    }

                }
                if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                    for (SerieClasseProfDiscipline serieClasseProfDiscipline : ficheSynoptique.getSerieClasseProfDisciplines()) {
                        ClasseDisciplinesForProf cpd = new ClasseDisciplinesForProf();
                        for (ProfDiscipline profDiscipline : serieClasseProfDiscipline.getProfDiscipline()) {

                            if (Objects.equals(profDiscipline.getProfesseur().getId(), prof.getId())){
                                cpd.setDisciplineQuantum(profDiscipline.getDisciplineQuantums());
                                cpd.setNomClasse(serieClasseProfDiscipline.getNomClasse());
                                horaireProfesseurs1.setProfesseur(prof);
                                for(DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums() ){

                                    heurDisp += disciplineQuantum.getQuantum();
                                    disciplineSet.add(disciplineQuantum.getDiscipline());

                                }
                                classeDisciplinesForProfs.add(cpd);
                                nbreClasse += 1;
                            }
                        }

                    }
                if(nbreClasse > 0) {
                    nbrDisc = disciplineSet.size();
                    horaireProfesseurs1.setHeuresDispensees(heurDisp);
                    horaireProfesseurs1.setNbreClasse(nbreClasse);
                    horaireProfesseurs1.setNbrDiscipline(nbrDisc);
                    horaireProfesseurs1.setClasseDisciplinesForProfs(classeDisciplinesForProfs);
                    horaireProfesseurs.add(horaireProfesseurs1);
                }
            }else{
                HoraireProfesseurs horaireProfesseurs1 = new HoraireProfesseurs();
                int heurDisp = 0;
                int nbreClasse = 0;
                int nbrDisc = 0;
                if(ficheSynoptique.getClasseProfDisciplines() != null)
                    for (ClasseProfDiscipline classeProfDiscipline : ficheSynoptique.getClasseProfDisciplines()) {
                    ClasseDisciplinesForProf cpd = new ClasseDisciplinesForProf();
                    for (ProfDiscipline profDiscipline : classeProfDiscipline.getProfDiscipline()) {
                        if (Objects.equals(profDiscipline.getProfesseur().getMatricule(), matricule) ||
                                (profDiscipline.getProfesseur().getPrenom().toLowerCase() + profDiscipline.getProfesseur().getNom().toLowerCase()).replaceAll("\\s", "").equals(nomComplet.toLowerCase().replaceAll("\\s", ""))){

                            cpd.setDisciplineQuantum(profDiscipline.getDisciplineQuantums());
                            cpd.setNomClasse(classeProfDiscipline.getNomClasse());
                            DeconcentratedLevel prof = profDiscipline.getProfesseur();
                            horaireProfesseurs1.setProfesseur(prof);
                            for(DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums() ){

                                heurDisp += disciplineQuantum.getQuantum();
                                disciplineSet.add(disciplineQuantum.getDiscipline());

                            }
                            classeDisciplinesForProfs.add(cpd);
                            nbreClasse += 1;
                        }
                    }

                }
                if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                    for (SerieClasseProfDiscipline serieClasseProfDiscipline : ficheSynoptique.getSerieClasseProfDisciplines()) {
                        ClasseDisciplinesForProf cpd = new ClasseDisciplinesForProf();
                        for (ProfDiscipline profDiscipline : serieClasseProfDiscipline.getProfDiscipline()) {
                            if (Objects.equals(profDiscipline.getProfesseur().getMatricule(), matricule) ||
                                    (profDiscipline.getProfesseur().getPrenom().toLowerCase() + profDiscipline.getProfesseur().getNom().toLowerCase()).replaceAll("\\s", "").equals(nomComplet.toLowerCase().replaceAll("\\s", ""))){

                                cpd.setDisciplineQuantum(profDiscipline.getDisciplineQuantums());
                                cpd.setNomClasse(serieClasseProfDiscipline.getNomClasse());
                                DeconcentratedLevel prof = profDiscipline.getProfesseur();
                                horaireProfesseurs1.setProfesseur(prof);
                                for(DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums() ){

                                    heurDisp += disciplineQuantum.getQuantum();
                                    disciplineSet.add(disciplineQuantum.getDiscipline());

                                }
                                classeDisciplinesForProfs.add(cpd);
                                nbreClasse += 1;
                            }
                        }

                    }
                nbrDisc = disciplineSet.size();
                horaireProfesseurs1.setHeuresDispensees(heurDisp);
                horaireProfesseurs1.setNbreClasse(nbreClasse);
                horaireProfesseurs1.setNbrDiscipline(nbrDisc);
                horaireProfesseurs1.setClasseDisciplinesForProfs(classeDisciplinesForProfs);
                if(!horaireProfesseurs1.getClasseDisciplinesForProfs().isEmpty())
                    horaireProfesseurs.add(horaireProfesseurs1);
            }
         /*   Page<HoraireProfesseurs> horaireProfesseursPage = (Page<HoraireProfesseurs>) horaireProfesseurs;

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(horaireProfesseursPage.getSize())
                    .totalPages(horaireProfesseursPage.getTotalPages())
                    .totalElements(horaireProfesseursPage.getTotalElements())
                    .number(horaireProfesseursPage.getNumber())
                    .build();*/

            // Calculer l'index de début et de fin
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, horaireProfesseurs.size());

            // Extraire les résultats de la plage spécifiée
            List<HoraireProfesseurs> paginatedResult = horaireProfesseurs.subList(startIndex, endIndex);

            log.info("============> GET LIST HORAIRE PROFS SUCCEFULLY");
            return Response.ok()
                    .setPayload(paginatedResult)
                    .setCollectionSize(horaireProfesseurs.size());
                  //  .setMetadata(pageMetadata).setMessage("Liste des horaire profs");
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur est servenue lors de la liste de Horaire profs :" + e.getMessage());

        }

    }
    //Horaire Profs
    @Override
    public Response<Object> discplinesAyantDeficit(String codeEtab, int page, int size, String disciplineName) {

        try {
            FicheSynoptique ficheSynoptique = this.iFicheSynoptiqueRepository.findFicheSynoptiqueByChefEtablissemnt_EtablissementCode(codeEtab)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            List<DisciplineDeficitaire> disciplineDeficitaires = new ArrayList<>();
            Set<Discipline> disciplinesDeLEtablissement = new HashSet<>();


            //récupération des disciplines disoensées dan sl'etablissement
            if(ficheSynoptique.getFiliereDisciplines() != null)
                for (FiliereDiscipline  filiereDispline : ficheSynoptique.getFiliereDisciplines()){
                    for(DisciplineQuantum disciplineQuantum : filiereDispline.getDisciplineQuantums()){
                        disciplinesDeLEtablissement.add(disciplineQuantum.getDiscipline());
                    }
                }
            if(ficheSynoptique.getSerieNiveauDisciplines() != null)
                for (SerieNiveauDiscipline  serieNiveauDiscipline : ficheSynoptique.getSerieNiveauDisciplines()){
                    for(DisciplineQuantum disciplineQuantum : serieNiveauDiscipline.getDisciplineQuantums()){
                        disciplinesDeLEtablissement.add(disciplineQuantum.getDiscipline());
                    }
                }
        if(Objects.equals(disciplineName, ""))
            for (Discipline discipline : disciplinesDeLEtablissement) {
                DisciplineDeficitaire disciplineDeficitaire = new DisciplineDeficitaire();
                int heurDispensee = 0;
                int heureAttribuee = 0;

                //calcul des heures dispensées sur la matiére
                if(ficheSynoptique.getClasseProfDisciplines() != null)
                    heurDispensee += getHeureDispenseClasseProfDisp(ficheSynoptique.getClasseProfDisciplines(), discipline, disciplineName);

                if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                    heurDispensee += getHeureDispenseSerieClasseProfDisp(ficheSynoptique.getSerieClasseProfDisciplines(), discipline, disciplineName);

                    //calcul des heures attribuées à la matiére
                if(ficheSynoptique.getFiliereDisciplines() != null)
                    heureAttribuee += getHeureAttribueeFiliereDiscipline(ficheSynoptique.getFiliereDisciplines(), discipline, disciplineName);

                if(ficheSynoptique.getSerieNiveauDisciplines() != null)
                    heureAttribuee += getHeureAttribueeSerieNiveauDisp(ficheSynoptique.getSerieNiveauDisciplines(), discipline, disciplineName);


                if(heurDispensee > heureAttribuee){
                    disciplineDeficitaire.setDiscipline(discipline);
                    disciplineDeficitaire.setTotalHeuresDispensee(heurDispensee);
                    disciplineDeficitaire.setTotalHeuresAttribuees(heureAttribuee);
                    disciplineDeficitaires.add(disciplineDeficitaire);
                }
            }

        else{
            DisciplineDeficitaire disciplineDeficitaire = new DisciplineDeficitaire();
            Discipline discipline = new Discipline();
            int heurDispensee = 0;
            int heureAttribuee = 0;

            if(ficheSynoptique.getClasseProfDisciplines() != null)
                heurDispensee += getHeureDispenseClasseProfDisp(ficheSynoptique.getClasseProfDisciplines(), discipline, disciplineName
                );

            if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                heurDispensee += getHeureDispenseSerieClasseProfDisp(ficheSynoptique.getSerieClasseProfDisciplines(), discipline, disciplineName);

            //calcul des heures attribuées à la matiére
            if(ficheSynoptique.getFiliereDisciplines() != null)
                heureAttribuee += getHeureAttribueeFiliereDiscipline(ficheSynoptique.getFiliereDisciplines(), discipline, disciplineName);

            if(ficheSynoptique.getSerieNiveauDisciplines() != null)
                for(SerieNiveauDiscipline serieNiveauDiscipline : ficheSynoptique.getSerieNiveauDisciplines()){
                    for (DisciplineQuantum disciplineQuantum : serieNiveauDiscipline.getDisciplineQuantums()){
                        if(Objects.equals(disciplineName, disciplineQuantum.getDiscipline().getLibelle())) {
                            heureAttribuee += disciplineQuantum.getQuantum();
                        }
                    }
                }


            if(heurDispensee > heureAttribuee){
                disciplineDeficitaire.setDiscipline(discipline);
                disciplineDeficitaire.setTotalHeuresDispensee(heurDispensee);
                disciplineDeficitaire.setTotalHeuresAttribuees(heureAttribuee);
                disciplineDeficitaires.add(disciplineDeficitaire);
            }
        }

            // Calculer l'index de début et de fin
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, disciplineDeficitaires.size());

            // Extraire les résultats de la plage spécifiée
            List<DisciplineDeficitaire> paginatedResult = disciplineDeficitaires.subList(startIndex, endIndex);


            log.info("============> GET LIST HORAIRE PROFS SUCCEFULLY");
            return Response.ok()
                    .setPayload(paginatedResult)
                    .setCollectionSize(disciplineDeficitaires.size());

            //  .setMetadata(pageMetadata).setMessage("Liste des horaire profs");
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur est servenue lors de la liste de Horaire profs :" + e.getMessage());

        }

    }

    //deficit disciplines sur tous les etablissements
    @Override
    public Response<Object> discplinesAyantDeficitAllEtablisselent( int page, int size, String disciplineName) {

        try {
            //l'ensemble des discplines et etablissement
            List<Discipline> disciplines = iDisciplineRepository.findAll();
            List<FicheSynoptique> ficheSynoptiques = iFicheSynoptiqueRepository.findAll();
            List<DisciplineDeficitaireAllEtablisemment> disciplineDeficitaireAllEtablisemments = new ArrayList<>();

            if(Objects.equals(disciplineName, ""))
                for (Discipline discipline : disciplines) {
                int heurDispensee = 0;
                int heureAttribuee = 0;

                List<DisciplineDeficitaire> disciplineDeficitaires = new ArrayList<>();
                DisciplineDeficitaire disciplineDeficitaire = new DisciplineDeficitaire();
                DisciplineDeficitaireAllEtablisemment deficitaireAllEtablisemment = new DisciplineDeficitaireAllEtablisemment();
                for (FicheSynoptique ficheSynoptique : ficheSynoptiques) {


                  //  deficitaireAllEtablisemment.setEtablissement(ficheSynoptique.getChefEtablissemnt().getEtablissement());


                    //calcul des heures dispensées sur la matiére
                    if(ficheSynoptique.getClasseProfDisciplines() != null)
                        heurDispensee += getHeureDispenseClasseProfDisp(ficheSynoptique.getClasseProfDisciplines(), discipline, disciplineName);

                    if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                        heurDispensee += getHeureDispenseSerieClasseProfDisp(ficheSynoptique.getSerieClasseProfDisciplines(), discipline, disciplineName);

                    //calcul des heures attribuées à la matiére
                    if(ficheSynoptique.getFiliereDisciplines() != null)
                        heureAttribuee += getHeureAttribueeFiliereDiscipline(ficheSynoptique.getFiliereDisciplines(), discipline, disciplineName);

                    if(ficheSynoptique.getSerieNiveauDisciplines() != null)
                        heureAttribuee += getHeureAttribueeSerieNiveauDisp(ficheSynoptique.getSerieNiveauDisciplines(), discipline, disciplineName);


                }
                    if (heurDispensee > heureAttribuee) {
                        disciplineDeficitaire.setDiscipline(discipline);
                        disciplineDeficitaire.setTotalHeuresDispensee(heurDispensee);
                        disciplineDeficitaire.setTotalHeuresAttribuees(heureAttribuee);
                        disciplineDeficitaires.add(disciplineDeficitaire);
                        deficitaireAllEtablisemment.setDisciplineDeficitaires(disciplineDeficitaires);
                        disciplineDeficitaireAllEtablisemments.add(deficitaireAllEtablisemment);
                    }
            }else{

                int heurDispensee = 0;
                int heureAttribuee = 0;
                Discipline discipline = new Discipline();
                List<DisciplineDeficitaire> disciplineDeficitaires = new ArrayList<>();
                DisciplineDeficitaire disciplineDeficitaire = new DisciplineDeficitaire();
                DisciplineDeficitaireAllEtablisemment deficitaireAllEtablisemment = new DisciplineDeficitaireAllEtablisemment();
                for (FicheSynoptique ficheSynoptique : ficheSynoptiques) {

                    //calcul des heures dispensées sur la matiére
                    if(ficheSynoptique.getClasseProfDisciplines() != null)
                        heurDispensee += getHeureDispenseClasseProfDisp(ficheSynoptique.getClasseProfDisciplines(), discipline, disciplineName);

                    if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                        heurDispensee += getHeureDispenseSerieClasseProfDisp(ficheSynoptique.getSerieClasseProfDisciplines(), discipline, disciplineName);

                    //calcul des heures attribuées à la matiére
                    if(ficheSynoptique.getFiliereDisciplines() != null)
                        heureAttribuee += getHeureAttribueeFiliereDiscipline(ficheSynoptique.getFiliereDisciplines(), discipline, disciplineName);

                    if(ficheSynoptique.getSerieNiveauDisciplines() != null)
                        heureAttribuee += getHeureAttribueeSerieNiveauDisp(ficheSynoptique.getSerieNiveauDisciplines(), discipline, disciplineName);


                }
                if (heurDispensee > heureAttribuee) {
                    disciplineDeficitaire.setDiscipline(discipline);
                    disciplineDeficitaire.setTotalHeuresDispensee(heurDispensee);
                    disciplineDeficitaire.setTotalHeuresAttribuees(heureAttribuee);
                    disciplineDeficitaires.add(disciplineDeficitaire);
                    deficitaireAllEtablisemment.setDisciplineDeficitaires(disciplineDeficitaires);
                    disciplineDeficitaireAllEtablisemments.add(deficitaireAllEtablisemment);
                }
            }

            // Calculer l'index de début et de fin
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, disciplineDeficitaireAllEtablisemments.size());

            // Extraire les résultats de la plage spécifiée
            List<DisciplineDeficitaireAllEtablisemment> paginatedResult = disciplineDeficitaireAllEtablisemments.subList(startIndex, endIndex);


            log.info("============> GET LIST HORAIRE PROFS SUCCEFULLY");
            return Response.ok()
                    .setPayload(paginatedResult)
                    .setCollectionSize(disciplineDeficitaireAllEtablisemments.size());
            //  .setMetadata(pageMetadata).setMessage("Liste des horaire profs");
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur est servenue lors de la liste de Horaire profs :" + e.getMessage());

        }

    }

    @Override
    public Response<Object> ETablissementDeficit(String codeEtab) {

        try {
            FicheSynoptique ficheSynoptique = this.iFicheSynoptiqueRepository.findFicheSynoptiqueByChefEtablissemnt_EtablissementCode(codeEtab)
                    .orElseThrow(() -> new GenericApiException("Aucune fiche n'est enregistrée pour votre établisseme"));

            List<DisciplineDeficitaire> disciplineDeficitaires = new ArrayList<>();
            Set<Discipline> disciplinesDeLEtablissement = new HashSet<>();
            //récupération des disciplines disoensées dan sl'etablissement
            for (FiliereDiscipline  filiereDispline : ficheSynoptique.getFiliereDisciplines()){
                for(DisciplineQuantum disciplineQuantum : filiereDispline.getDisciplineQuantums()){
                    disciplinesDeLEtablissement.add(disciplineQuantum.getDiscipline());
                }
            }
            int heurDispensee = 0;
            int heureAttribuee = 0;
            for (Discipline discipline : disciplinesDeLEtablissement) {
                //calcul des heures dispensées sur la matiére
                if(ficheSynoptique.getClasseProfDisciplines() != null)
                    heurDispensee += getHeureDispenseClasseProfDisp(ficheSynoptique.getClasseProfDisciplines(), discipline, "");

                if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                    heurDispensee += getHeureDispenseSerieClasseProfDisp(ficheSynoptique.getSerieClasseProfDisciplines(), discipline, "");

                //calcul des heures attribuées à la matiére
                if(ficheSynoptique.getFiliereDisciplines() != null)
                    heureAttribuee += getHeureAttribueeFiliereDiscipline(ficheSynoptique.getFiliereDisciplines(), discipline, "");

                if(ficheSynoptique.getSerieNiveauDisciplines() != null)
                    heureAttribuee += getHeureAttribueeSerieNiveauDisp(ficheSynoptique.getSerieNiveauDisciplines(), discipline, "");



               // if(heurDispensee > heureAttribuee){

              //  }
                DisciplineDeficitaire disciplineDeficitaire = new DisciplineDeficitaire();

                disciplineDeficitaire.setTotalHeuresDispensee(heurDispensee);
                disciplineDeficitaire.setTotalHeuresAttribuees(heureAttribuee);
                disciplineDeficitaire.setEtablissement(ficheSynoptique.getChefEtablissemnt().getEtablissement());
                disciplineDeficitaires.add(disciplineDeficitaire);

            }


            log.info("============> GET LIST HORAIRE PROFS SUCCEFULLY");
            return Response.ok()
                    .setPayload(disciplineDeficitaires);
            //  .setMetadata(pageMetadata).setMessage("Liste des horaire profs");
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur est servenue lors de la liste de Horaire profs :" + e.getMessage());

        }

    }

    // les des etablissement ayant deficit sur une discipline
    @Override
    public Response<Object> listEtablissementAyantDeficitSurUneDiscipline(int page, int size, Long idDiscipline) {

        try {

            List<FicheSynoptique> ficheSynoptiques = iFicheSynoptiqueRepository.findAll();
                List<DisciplineDeficitaire> disciplineDeficitaires = new ArrayList<>();
                for (FicheSynoptique ficheSynoptique : ficheSynoptiques) {
                    int i =0;
                    int heurDispensee = 0;
                    int heureAttribuee = 0;
                    DisciplineDeficitaire disciplineDeficitaire = new DisciplineDeficitaire();

                    if(ficheSynoptique.getClasseProfDisciplines() != null)
                        for (ClasseProfDiscipline classeProfDiscipline : ficheSynoptique.getClasseProfDisciplines()) {
                            //calcul des heures dispensées sur la matiére
                            for (ProfDiscipline profDiscipline : classeProfDiscipline.getProfDiscipline()) {
                                for (DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums()) {
                                    if (Objects.equals(idDiscipline, disciplineQuantum.getDiscipline().getId())) {
                                        heurDispensee += disciplineQuantum.getQuantum();
                                        if(i == 0)
                                            disciplineDeficitaire.setDiscipline(disciplineQuantum.getDiscipline());
                                        i++;
                                    }
                                }
                            }
                        }
                    if(ficheSynoptique.getSerieClasseProfDisciplines() != null)
                        for (SerieClasseProfDiscipline serieClasseProfDiscipline : ficheSynoptique.getSerieClasseProfDisciplines()) {
                            //calcul des heures dispensées sur la matiére
                            for (ProfDiscipline profDiscipline : serieClasseProfDiscipline.getProfDiscipline()) {
                                for (DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums()) {
                                    if (Objects.equals(idDiscipline, disciplineQuantum.getDiscipline().getId())) {
                                        heurDispensee += disciplineQuantum.getQuantum();
                                        if(i == 0)
                                            disciplineDeficitaire.setDiscipline(disciplineQuantum.getDiscipline());
                                        i++;
                                    }
                                }
                            }
                        }
                    //calcul des heures attribuées à la matiére
                    if( ficheSynoptique.getFiliereDisciplines() != null)
                     for (FiliereDiscipline filiereDiscipline : ficheSynoptique.getFiliereDisciplines()) {
                        for (DisciplineQuantum disciplineQuantum : filiereDiscipline.getDisciplineQuantums()) {
                            if (Objects.equals(idDiscipline, disciplineQuantum.getDiscipline().getId())) {
                                heureAttribuee += disciplineQuantum.getQuantum();
                            }
                        }
                    }
                    if( ficheSynoptique.getSerieNiveauDisciplines() != null)
                        for (SerieNiveauDiscipline serieNiveauDiscipline : ficheSynoptique.getSerieNiveauDisciplines()) {
                            for (DisciplineQuantum disciplineQuantum : serieNiveauDiscipline.getDisciplineQuantums()) {
                                if (Objects.equals(idDiscipline, disciplineQuantum.getDiscipline().getId())) {
                                    heureAttribuee += disciplineQuantum.getQuantum();
                                }
                            }
                        }


                    if (heurDispensee > heureAttribuee) {
                        disciplineDeficitaire.setTotalHeuresDispensee(heurDispensee);
                        disciplineDeficitaire.setTotalHeuresAttribuees(heureAttribuee);
                        disciplineDeficitaire.setEtablissement(ficheSynoptique.getChefEtablissemnt().getEtablissement());
                        disciplineDeficitaires.add(disciplineDeficitaire);

                    }

                }

            // Calculer l'index de début et de fin
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, disciplineDeficitaires.size());

            // Extraire les résultats de la plage spécifiée
            List<DisciplineDeficitaire> paginatedResult = disciplineDeficitaires.subList(startIndex, endIndex);



            log.info("============> GET LIST HORAIRE PROFS SUCCEFULLY");
            return Response.ok()
                    .setPayload(paginatedResult)
                    .setCollectionSize(disciplineDeficitaires.size())
                    .setAllData(disciplineDeficitaires);
            //  .setMetadata(pageMetadata).setMessage("Liste des horaire profs");
        } catch (Exception e) {
            return Response.exception()
                    .setMessage("Une erreur est servenue lors de la liste de Horaire profs :" + e.getMessage());

        }

    }
int getHeureDispenseClasseProfDisp(List<ClasseProfDiscipline> classeProfDisciplines, Discipline discipline, String  disciplineName ){
        int heureDispensee = 0;
    for (ClasseProfDiscipline classeProfDiscipline : classeProfDisciplines) {
        //calcul des heures dispensées sur la matiére
        for (ProfDiscipline profDiscipline : classeProfDiscipline.getProfDiscipline()) {
            for (DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums()) {
                if(Objects.equals(disciplineName, "")) {
                    if (Objects.equals(discipline.getId(), disciplineQuantum.getDiscipline().getId())) {
                        heureDispensee += disciplineQuantum.getQuantum();
                    }
                }
                else {
                    if (Objects.equals(disciplineName, disciplineQuantum.getDiscipline().getLibelle())) {
                        discipline = disciplineQuantum.getDiscipline();
                        heureDispensee += disciplineQuantum.getQuantum();
                    }
                }
            }
        }
    }
    return heureDispensee;
}
    int getHeureDispenseSerieClasseProfDisp(List<SerieClasseProfDiscipline> serieClasseProfDisciplines, Discipline discipline, String disciplineName){
        int heureDispensee = 0;
        for (SerieClasseProfDiscipline serieClasseProfDiscipline : serieClasseProfDisciplines) {
            //calcul des heures dispensées sur la matiére
            for (ProfDiscipline profDiscipline : serieClasseProfDiscipline.getProfDiscipline()) {
                for (DisciplineQuantum disciplineQuantum : profDiscipline.getDisciplineQuantums()) {
                    if(Objects.equals(disciplineName, "")) {
                        if (Objects.equals(discipline.getId(), disciplineQuantum.getDiscipline().getId())) {
                            heureDispensee += disciplineQuantum.getQuantum();
                        }
                    }
                    else {
                        if (Objects.equals(disciplineName, disciplineQuantum.getDiscipline().getLibelle())) {
                            discipline = disciplineQuantum.getDiscipline();
                            heureDispensee += disciplineQuantum.getQuantum();
                        }
                    }
                }
            }
        }
        return heureDispensee;
    }
int getHeureAttribueeFiliereDiscipline(List<FiliereDiscipline> filiereDisciplines, Discipline discipline, String disciplineName) {
    int heureAttribuee = 0;
    for (FiliereDiscipline filiereDiscipline : filiereDisciplines) {
        for (DisciplineQuantum disciplineQuantum : filiereDiscipline.getDisciplineQuantums()) {
            if(Objects.equals(disciplineName, "")) {
                if (Objects.equals(discipline.getId(), disciplineQuantum.getDiscipline().getId())) {
                    heureAttribuee += disciplineQuantum.getQuantum();
                }
            }
            else {
                if (Objects.equals(disciplineName, disciplineQuantum.getDiscipline().getLibelle())) {
                    heureAttribuee += disciplineQuantum.getQuantum();
                }
            }
        }

    }return heureAttribuee;
}
    int getHeureAttribueeSerieNiveauDisp(List<SerieNiveauDiscipline> serieNiveauDisciplines, Discipline discipline, String disciplineName) {
        int heureAttribuee = 0;
        for (SerieNiveauDiscipline serieNiveauDiscipline : serieNiveauDisciplines) {
            for (DisciplineQuantum disciplineQuantum : serieNiveauDiscipline.getDisciplineQuantums()) {
                if(Objects.equals(disciplineName, "")) {
                    if (Objects.equals(discipline.getId(), disciplineQuantum.getDiscipline().getId())) {
                        heureAttribuee += disciplineQuantum.getQuantum();
                    }
                }else{
                    if(Objects.equals(disciplineName, disciplineQuantum.getDiscipline().getLibelle())) {
                        heureAttribuee += disciplineQuantum.getQuantum();
                    }
                }

            }

        }return heureAttribuee;
    }
}
