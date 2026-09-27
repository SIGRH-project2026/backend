package sn.gainde2000.backenmfpai.services.implementations.servicepta.parametre;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.Parametre;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.QParametre;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.TraitementParametre;
import sn.gainde2000.backenmfpai.entities.servicepta.parametre.enums.ResponsableActivite;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Bureau;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.Division;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.servicepta.parametre.ParametreMapper;
import sn.gainde2000.backenmfpai.repositories.servicepta.parametre.ParametreRepository;
import sn.gainde2000.backenmfpai.repositories.servicepta.parametre.StatutParametreRepository;
import sn.gainde2000.backenmfpai.repositories.servicepta.parametre.TraitementParametreRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.BureauRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.DivisionRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicepta.parametre.IParametre;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.IUtilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicepta.parametre.ParametreRequest;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicepta.parametre.ParametreResponse;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParametreService implements IParametre {
  private final ParametreRepository parametreRepository;
  private final DivisionRepository divisionRepository;
  private final BureauRepository bureauRepository;
  private final StatutParametreRepository statutParametreRepository;
  private final IUtilisateur iUtilisateur;
  private final TraitementParametreRepository traitementParametreRepository;
  private final CentralLevelRepository centralLevelRepository;
  private final ParametreMapper parametreMapper;

  public String formatNumber(long number) {
    return String.format("%0" + 5 + "d", number);
  }


  /**
   * enregistrement parametre
   * @param parametreRequest
   * @return
   */
  @Override
  @Transactional
  public Response<Object> saveParametre(ParametreRequest parametreRequest) {

    List<String> divisionCodes = parametreRequest.getDivisionCodes();
    List<String> bureauCodes = parametreRequest.getBureauCodes();

    List<Division> divisions = new ArrayList<>();
    List<Bureau> bureaux = new ArrayList<>();


    if (divisionCodes.isEmpty() )
      return Response.exception().setMessage("La liste des divisions est vide.");
    // verif divison
    for (String code: divisionCodes) {
      Optional<Division> optionalDivision = divisionRepository.findByCode(code);
      if (optionalDivision.isEmpty())
        return Response.exception().setMessage("Choix de divison invalide !");
      divisions.add(optionalDivision.get());
    }




    if (bureauCodes.isEmpty())
      return Response.exception().setMessage("Liste bureaux est vide.");


    // verif divison
    for (String code: bureauCodes
    ) {
      Optional<Bureau> optionalBureau = bureauRepository.findByCode(code);
      if (optionalBureau.isEmpty())
        return Response.exception().setMessage("Choix de bureau invalide !");
      bureaux.add(optionalBureau.get());
    }


    Parametre parametre = new Parametre();
    parametre.setBureaux(bureaux);
    parametre.setDivisions(divisions);
    parametre.setResponsableActivite(ResponsableActivite.DRH);
    parametre.setLibelle(parametreRequest.getLibelle());
    parametre.setStatut("ACTIF");

    Parametre savedParametre = parametreRepository.save(parametre);
    savedParametre.setNumero(formatNumber(savedParametre.getId()));
    Parametre save = parametreRepository.save(savedParametre);


    TraitementParametre traitementParametre = new TraitementParametre();
    traitementParametre.setParametre(savedParametre);
    traitementParametre.setStatutParametre(statutParametreRepository.findByCode("ACTIF").get());
    traitementParametre.setActivated(true);
    traitementParametre.setUtilisateur(iUtilisateur.getCurrentUser());
    traitementParametreRepository.save(traitementParametre);

    return Response.ok().setMessage("Indicateur enregistre avec succes.");
  }

  /**
   * modifier parametre de stage
   * @param parametreRequest
   * @param id
   * @return
   */
  @Transactional
  public Response<Object> editParametre(ParametreRequest parametreRequest, Long id) {
    Optional<Parametre> optionalParametre = parametreRepository.findById(id);
    if (optionalParametre.isEmpty())
      return Response.exception().setMessage("Le parametre n'existe pas !");


    List<String> divisionCodes = parametreRequest.getDivisionCodes();
    List<String> bureauCodes = parametreRequest.getBureauCodes();

    List<Division> divisions = new ArrayList<>();
    List<Bureau> bureaux = new ArrayList<>();


    if (divisionCodes.isEmpty())
      return Response.exception().setMessage("La liste des divisions est vide.");
    // verif divison
    for (String code: divisionCodes
    ) {
      Optional<Division> optionalDivision = divisionRepository.findByCode(code);
      if (optionalDivision.isEmpty())
        return Response.exception().setMessage("Choix de divison invalide !");
      divisions.add(optionalDivision.get());
    }




    if (bureauCodes.isEmpty())
      return Response.exception().setMessage("Liste bureaux est vide.");


    // verif divison
    for (String code: bureauCodes
    ) {
      Optional<Bureau> optionalBureau = bureauRepository.findByCode(code);
      if (optionalBureau.isEmpty())
        return Response.exception().setMessage("Choix de bureau invalide !");
      bureaux.add(optionalBureau.get());
    }


    Parametre parametre = optionalParametre.get();
    parametre.setBureaux(bureaux);
    parametre.setDivisions(divisions);
    parametre.setResponsableActivite(ResponsableActivite.DRH);
    parametre.setLibelle(parametreRequest.getLibelle());

    Parametre savedParametre = parametreRepository.save(parametre);
    savedParametre.setNumero(formatNumber(savedParametre.getId()));
    parametreRepository.save(savedParametre);


    return Response.ok().setMessage("Indicateur modifie avec succes.");
  }

  /**
   * recuperation parametre
   * @param id
   * @return
   */
  @Override
  @Transactional
  public Response<Object> findParametre(long id) {
    Optional<Parametre> optionalParametre = parametreRepository.findById(id);
    if (optionalParametre.isEmpty())
      return Response.exception().setMessage("Indicateur n'existe pas !");

    return Response.ok().setMessage("Recuperation indicateur").setPayload(parametreMapper.mapToParametreResponse(optionalParametre.get()));
  }

  /**
   * recuperation parametre
   * @param id
   * @return
   */
  @Override
  @Transactional
  public Parametre getParametre(Long id) {
    Optional<Parametre> optionalParametre = parametreRepository.findById(id);
    if (optionalParametre.isEmpty())
        throw new MFPAIException("Indicateur n'existe pas !");

    String bureaux = optionalParametre.get().getBureaux().stream().map(Bureau::getLabel).collect(Collectors.joining(","));
    String divisions = optionalParametre.get().getDivisions().stream().map(Division::getLabel).collect(Collectors.joining(","));

    optionalParametre.get().setListBureaux(bureaux);
    optionalParametre.get().setListDivisions(divisions);
    return optionalParametre.get();

  }

  /**
   * activer ou desacter parametre
   * @param id
   * @return
   */
  @Override
  @Transactional
  public Response<Object> enableOrDisableParametre(long id) {
    Optional<Parametre> optionalParametre = parametreRepository.findById(id);
    if (optionalParametre.isEmpty())
      return Response.exception().setMessage("Indicateur n'existe pas !");


    TraitementParametre traitementParametre = new TraitementParametre();
    traitementParametre.setParametre(optionalParametre.get());

    if (optionalParametre.get().getStatut().equals("ACTIF")){
      optionalParametre.get().setStatut("INACTIF");
      traitementParametre.setStatutParametre(statutParametreRepository.findByCode("INACTIF").get());

    }else{
      optionalParametre.get().setStatut("ACTIF");
      traitementParametre.setStatutParametre(statutParametreRepository.findByCode("ACTIF").get());
    }
    parametreRepository.save(optionalParametre.get());
    traitementParametre.setActivated(true);
    traitementParametre.setUtilisateur(iUtilisateur.getCurrentUser());
    traitementParametreRepository.save(traitementParametre);
    return Response.ok().setMessage("Statut mise a jour avec succes.");
  }

  /**
   * recuperation des parametres
   * @param page
   * @param size
   * @param filter
   * @param numero
   * @param libelle
   * @param date
   * @param responsableActivite
   * @param statut
   * @param divisions
   * @return
   */
  @Override
  @Transactional
  public Response<Object> getAllParametre(int page, int size, String filter, String numero, String libelle, String date, String responsableActivite, String statut, String divisions) {

    Page<ParametreResponse> parametreResponses;
    BooleanBuilder builder = new BooleanBuilder();



    if(StringUtils.isNotBlank(filter)){
      builder.andAnyOf(
              QParametre.parametre.libelle.likeIgnoreCase("%" + filter + "%"),
              QParametre.parametre.date.stringValue().likeIgnoreCase("%" + filter + "%"),
              QParametre.parametre.responsableActivite.stringValue().likeIgnoreCase("%" + filter + "%"),
              QParametre.parametre.statut.likeIgnoreCase("%" + filter + "%"),
              QParametre.parametre.numero.likeIgnoreCase("%" + filter + "%")

      );
    }

    if(StringUtils.isNotBlank(divisions)){
      List<String> listDivisions = Arrays.stream(divisions.split(",")).toList();
      for (String division : listDivisions) {
        Optional<Division> optionalDivision = divisionRepository.findByCode(division);
        if (optionalDivision.isPresent()){
          Division divisionEntity = optionalDivision.get();
          builder.and(
                  QParametre.parametre.divisions.contains(divisionEntity)
          );
        }

      }
    }





    if(StringUtils.isNotBlank(numero)){
      builder.and(
              QParametre.parametre.numero.likeIgnoreCase("%" + numero + "%")
      );
    }
    if(StringUtils.isNotBlank(statut)){
      builder.and(
              QParametre.parametre.statut.like(statut)
      );
    }
    if(StringUtils.isNotBlank(libelle)){
      builder.and(
              QParametre.parametre.libelle.likeIgnoreCase("%" + libelle + "%")
      );
    }
    if(StringUtils.isNotBlank(date)){
      builder.and(
              QParametre.parametre.date.stringValue().likeIgnoreCase("%" + date + "%")
      );
    }

    if(StringUtils.isNotBlank(responsableActivite)){
      builder.and(
              QParametre.parametre.responsableActivite.stringValue().likeIgnoreCase("%" + responsableActivite + "%")
      );
    }

    if(StringUtils.isNotBlank(statut)){
      builder.and(
              QParametre.parametre.statut.likeIgnoreCase("%" + statut + "%")
      );
    }

    parametreResponses = Objects.nonNull(builder.getValue()) ?
            parametreRepository.findAll(builder.getValue(), PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "id"))).map(parametreMapper::mapToParametreResponse)
            :
            parametreRepository.findAll(PageRequest.of(page,size, Sort.by(Sort.Direction.DESC, "id"))).map(parametreMapper::mapToParametreResponse);
    Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
            .size(parametreResponses.getSize())
            .number(parametreResponses.getNumber())
            .totalElements(parametreResponses.getTotalElements())
            .totalPages(parametreResponses.getTotalPages())
            .build();
    return Response.ok().setPayload(parametreResponses.getContent()).setMetadata(pageMetadata).setMessage("Liste des Indicateurs");
  }

  /**
   * liste des parametre
   * @return
   */
  @Override
  @Transactional
  public List<ParametreResponse> getListParametre() {
    return parametreRepository.findAll(Sort.by("id").descending()).stream().filter(parametre -> parametre.getStatut().equals("ACTIF")).map(parametreMapper::mapToParametreResponse).toList();
  }
}
