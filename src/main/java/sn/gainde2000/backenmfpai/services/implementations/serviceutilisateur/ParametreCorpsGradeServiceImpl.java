package sn.gainde2000.backenmfpai.services.implementations.serviceutilisateur;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.Speciality;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.serviceutilisateur.ParametreCorpsGradeMapper;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.SpecialityRepository;
import sn.gainde2000.backenmfpai.services.interfaces.serviceutilisateur.ParametreCorpsGradeService;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.utilisateur.ParametreCorpsGradeDTO;

import sn.gainde2000.backenmfpai.web.dtos.responses.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage.CORPS_ALREADY_EXISTS;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/07/2024-16:17
 * @project backend_mfpai
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ParametreCorpsGradeServiceImpl implements ParametreCorpsGradeService {
    private final ParametreCorpsGradeRepository parametreCorpsGradeRepository;
    private final ParametreCorpsGradePkRepository parametreCorpsGradePkRepository;
    private final ParametreCorpsGradeMapper parametreCorpsGradeMapper;

    private final CorpsGradeRepository corpsGradeRepository;
    private final GradeRepository gradeRepository;
    private final SpecialityRepository specialityRepository;
    private final TDSequenceRefRepository tdSequenceRefRepository;
    private final TypeMatriculeRepository typeMatriculeRepository;

    @Override
    public ParametreCorpsGrade addParam(ParametreCorpsGradeDTO dto) {

        ParametreCorpsGrade parametreCorpsGradeDTO = parametreCorpsGradeMapper.toEntity(dto);


        CorpsGrade corpsGradeDto = parametreCorpsGradeDTO.getCorpsGrade();
        Set<Grade> gradeDto = dto.getGrades();
        TypeMatricule typeMatriculeDto = dto.getTypeMatricules();
     //   Speciality speciality = parametreCorpsGradeDTO.getSpeciality();


        TDSequenceRef sequence = updateSequence();
        parametreCorpsGradeDTO.setNoRef(createSquence("ref", sequence.getNumero() + ""));


        ParametreCorpsGradePk parametreCorpsGradePk1 = parametreCorpsGradePkRepository.getParamFromMatriculeCode(typeMatriculeDto.getCode(), corpsGradeDto.getLabel()).orElse(null);

       // CorpsGrade corpsGrade = corpsGradeRepository.findByLabel(corpsGradeDto.getLabel()).orElse(null);


        if (Objects.nonNull(parametreCorpsGradePk1)) {
            throw new MFPAIException(CORPS_ALREADY_EXISTS, "Le corps  existe déjà");

        }


        ParametreCorpsGradePk parametreCorpsGradePk = new ParametreCorpsGradePk();

        CorpsGrade corpsSaving = null;

        CorpsGrade corpsBuilder = new CorpsGrade();

        corpsBuilder.setId(corpsGradeRepository.findTopByOrderByIdDesc().getId() + 1);
        corpsBuilder.setCode(createSquence(corpsGradeDto.getLabel().substring(0, 3) , sequence.getNumero() + ""));
        corpsBuilder.setLabel(corpsGradeDto.getLabel());
        corpsBuilder.setStatut(true);


            TypeMatricule typeMatriculeDb = typeMatriculeRepository.findByCode(typeMatriculeDto.getCode()).orElse(null);

            if (Objects.nonNull(typeMatriculeDb)) {
               // typeMatricules.add(typeMatriculeDb);

                if (typeMatriculeDb.getCode().equals("MATFONC") || typeMatriculeDb.getCode().equals("MATDEE")) {

                    corpsBuilder.setTypeMatricule("FD");

                     corpsSaving = corpsGradeRepository.save(corpsBuilder);
                }else if(typeMatriculeDb.getCode().equals("MATCON")) {

                    corpsBuilder.setTypeMatricule("VCC");

                    corpsSaving = corpsGradeRepository.save(corpsBuilder);
                }else {

                    corpsBuilder.setTypeMatricule("VCV");


                    corpsSaving = corpsGradeRepository.save(corpsBuilder);
                }


            }




       // Speciality specialityDB = specialityRepository.findByCode(speciality.getCode()).orElse(null);
        parametreCorpsGradeDTO.setCorpsGrade(corpsSaving);
      //  parametreCorpsGradeDTO.setSpeciality(specialityDB);
        parametreCorpsGradeDTO.setDateParam(LocalDate.now());
        parametreCorpsGradeDTO.setStatut(true);


        ParametreCorpsGrade param = parametreCorpsGradeRepository.save(parametreCorpsGradeDTO);

        Grade gradeBuild = new Grade();

        Grade gradeSaving = new Grade();

        for (Grade grade : gradeDto) {
            Grade corpsGradeDb = getGrade(grade);
            /*  if (Objects.nonNull(corpsGradeDb)) {

                System.out.println("ici");
                corpsGradeDb.setCorpsGrade(corpsSaving);


                parametreCorpsGradePk.setGrade(corpsGradeDb);
            } else { */



                gradeBuild.setId(gradeRepository.findTopByOrderByIdDesc().getId() + 1);
                gradeBuild.setCode(createSquence(grade.getCode().substring(0, 2) , sequence.getNumero() + ""));
                gradeBuild.setLabel(grade.getCode());
                gradeBuild.setCorpsGrade(corpsSaving);

                gradeSaving  = gradeRepository.save(gradeBuild);
               // grades.add(gradeSaving);

                parametreCorpsGradePk.setGrade(gradeSaving);

                parametreCorpsGradePk.setParametreCorpsGrade(param);
                parametreCorpsGradePk.setTypeMatricule(typeMatriculeDb);
                parametreCorpsGradePkRepository.save(parametreCorpsGradePk);

                gradeBuild = new Grade();
                gradeSaving = new Grade();

                parametreCorpsGradePk = new ParametreCorpsGradePk();
         //   }
        }


        return param;
    }

    private Grade getGrade(Grade grade) {
        Grade corpsGradeDb = gradeRepository.findByCode(grade.getCode()).orElse(null);
        return corpsGradeDb;
    }

    @Override
    public ParametreCorpsGrade getParam(Long id) {
        ParametreCorpsGrade parametreCorpsGrade = parametreCorpsGradeRepository.findById(id).orElse(null);


        Set<Grade> grades = parametreCorpsGradePkRepository
                .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                .stream().map(ParametreCorpsGradePk::getGrade)
                .collect(Collectors.toSet());


        TypeMatricule typeMatricules = parametreCorpsGradePkRepository
                .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                .stream().map(ParametreCorpsGradePk::getTypeMatricule).findFirst().get();

        parametreCorpsGrade.setGrades(grades);
        parametreCorpsGrade.setTypeMatricules(typeMatricules);


        return parametreCorpsGrade;
    }

    @Override
    public ParametreCorpsGrade updateParam(Long id, ParametreCorpsGradeDTO dto) {
        ParametreCorpsGrade parametreCorpsGrade = parametreCorpsGradeRepository.findById(id).orElse(null);




        if (Objects.nonNull(parametreCorpsGrade)) {
            CorpsGrade corpsGradeDto = dto.getCorpsGrade();
            Set<Grade> gradeDto = dto.getGrades();
            TypeMatricule typeMatriculeDto = dto.getTypeMatricules();
          //  Speciality speciality = dto.getSpeciality();


            TDSequenceRef sequence = updateSequence();
           // parametreCorpsGrade.setNoRef(createSquence("ref", sequence.getNumero() + ""));



            ParametreCorpsGradePk parametreCorpsGradePk = new ParametreCorpsGradePk();

            CorpsGrade corpsSaving = null;

            CorpsGrade corpsGrade = corpsGradeRepository.findByCode(parametreCorpsGrade.getCorpsGrade().getCode()).orElse(null);
           // CorpsGrade newCorpsGrade = corpsGradeRepository.findByLabel(corpsGradeDto.getLabel()).orElse(null);

            CorpsGrade corpsBuilder = new CorpsGrade();
            TypeMatricule typeMatriculeDb = typeMatriculeRepository.findByCode(typeMatriculeDto.getCode()).orElse(null);


           /*  if (Objects.nonNull(corpsGrade)) {

                 System.out.println("@1");

                corpsSaving = corpsGradeRepository.findByCode(parametreCorpsGrade.getCorpsGrade().getCode()).orElse(null);
                parametreCorpsGrade.setCorpsGrade(corpsSaving);
            }

            else {*/


                corpsBuilder.setId(corpsGradeRepository.findTopByOrderByIdDesc().getId() + 1);
                corpsBuilder.setCode(createSquence(corpsGradeDto.getLabel().substring(0, 3) , sequence.getNumero() + ""));
                corpsBuilder.setLabel(corpsGradeDto.getLabel());
                corpsBuilder.setStatut(true);



                if (Objects.nonNull(typeMatriculeDb)) {

                    if (typeMatriculeDb.getCode().equals("MATFONC") || typeMatriculeDb.getCode().equals("MATDEE")) {

                        corpsBuilder.setTypeMatricule("FD");

                        corpsSaving = corpsGradeRepository.save(corpsBuilder);
                    }else if(typeMatriculeDb.getCode().equals("MATCON")) {

                        corpsBuilder.setTypeMatricule("VCC");

                        corpsSaving = corpsGradeRepository.save(corpsBuilder);
                    }else {

                        corpsBuilder.setTypeMatricule("VCV");


                        corpsSaving = corpsGradeRepository.save(corpsBuilder);
                    }


                }

                parametreCorpsGrade.setCorpsGrade(corpsSaving);

           // }



      //      Speciality specialityDB = specialityRepository.findByCode(speciality.getCode()).orElse(null);

       //     parametreCorpsGrade.setSpeciality(specialityDB);
            parametreCorpsGrade.setDateParam(LocalDate.now());
            parametreCorpsGrade.setStatut(true);


            ParametreCorpsGrade param = parametreCorpsGradeRepository.save(parametreCorpsGrade);

            Grade gradeBuild = new Grade();

            Grade gradeSaving = new Grade();





           // System.out.println(" g " + gradeDto);
            Set<String> gradeDTO = gradeDto.stream().map(Grade::getCode).collect(Collectors.toSet());
            for (Grade grade : gradeDto) {

                ParametreCorpsGradePk byParametreCorpsGradeIdAndGradeLabel = parametreCorpsGradePkRepository.getParamFromGradeLabel(parametreCorpsGrade.getId(), grade.getCode());


                /*
                 Ajout d'un nouveau element qui ne fessait pas partie de la liste initiale
                 */
               if(!Objects.nonNull(byParametreCorpsGradeIdAndGradeLabel)){


                   Grade chechGrade = gradeRepository.findByCode(grade.getCode()).orElse(null);


                   if(!Objects.nonNull(chechGrade)) {
                       gradeBuild.setId(gradeRepository.findTopByOrderByIdDesc().getId() + 1);
                       gradeBuild.setCode(createSquence(grade.getCode().substring(0, 2) , sequence.getNumero() + ""));
                       gradeBuild.setLabel(grade.getCode());
                       gradeBuild.setCorpsGrade(corpsSaving);

                       gradeSaving  = gradeRepository.save(gradeBuild);

                       parametreCorpsGradePk.setGrade(gradeSaving);

                       parametreCorpsGradePk.setParametreCorpsGrade(param);
                       parametreCorpsGradePk.setTypeMatricule(typeMatriculeDb);

                       parametreCorpsGradePkRepository.save(parametreCorpsGradePk);

                       gradeBuild = new Grade();
                       gradeSaving = new Grade();

                       parametreCorpsGradePk = new ParametreCorpsGradePk();
                   }else {
                       parametreCorpsGradePk.setGrade(chechGrade);
                       parametreCorpsGradePk.setParametreCorpsGrade(param);
                       parametreCorpsGradePk.setTypeMatricule(typeMatriculeDb);
                   }


                }


               else {

                   List<String> grades = parametreCorpsGradePkRepository
                           .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                           .stream().map(ParametreCorpsGradePk::getGrade).map(Grade::getLabel)
                           .toList();



                   for (String codeGradeNotPresentAfterUpdate : grades) {

                      // System.out.println(" == " + gradeDTO);
                       if(!gradeDTO.contains(codeGradeNotPresentAfterUpdate) ){

                           ParametreCorpsGradePk paramForDeleted = parametreCorpsGradePkRepository.getParamFromGradeLabel(parametreCorpsGrade.getId(), codeGradeNotPresentAfterUpdate);

                           ParametreCorpsGradePk deletedPK = parametreCorpsGradePkRepository.findById(paramForDeleted.getId()).get();
                           parametreCorpsGradePkRepository.delete(deletedPK);


                       }
                   }

               }



            }


            return param;
        }



        return null;


    }


    private List<String> getGradeLabelsForParametreCorpsGrade(ParametreCorpsGrade parametreCorpsGrade) {
        var value =  parametreCorpsGradePkRepository
                .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                .stream()
                .map(ParametreCorpsGradePk::getGrade)
                .map(Grade::getLabel)
                .collect(Collectors.toList());

        System.out.println(" va " + value);
        return value;
    }

    private boolean isLabelInGradeList(ParametreCorpsGrade parametreCorpsGrade, String labelToCheck) {

        List<String> gradeLabels = getGradeLabelsForParametreCorpsGrade(parametreCorpsGrade);

        return gradeLabels.contains(labelToCheck);
    }

    @Override
    public ParametreCorpsGrade activate(Long id) {
        ParametreCorpsGrade parametreCorpsGrade = parametreCorpsGradeRepository.findById(id).orElseThrow(null);

        if (Objects.nonNull(parametreCorpsGrade)) {
            parametreCorpsGrade.setStatut(!parametreCorpsGrade.getStatut());

            parametreCorpsGradeRepository.save(parametreCorpsGrade);

            CorpsGrade corpsGrade = parametreCorpsGrade.getCorpsGrade();

            CorpsGrade corps = corpsGradeRepository.findByCode(corpsGrade.getCode()).orElse(null);

             if (Objects.nonNull(corps)) {
                 corps.setStatut(!corpsGrade.getStatut());
                 corpsGradeRepository.save(corps);
             }



        }

        return null;
    }

    @Override
    public Response<Object> getPageParamAdvanced(int page, int size, String filter, String libelleCorps, String libelleGrade, String libelleSpecialite) {


        Page<ParametreCorpsGrade> paramPage;
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.isNotBlank(filter)) {
            builder.andAnyOf(
              QParametreCorpsGrade.parametreCorpsGrade.corpsGrade.label.equalsIgnoreCase(filter),
              QParametreCorpsGrade.parametreCorpsGrade.noRef.equalsIgnoreCase(filter),
              QParametreCorpsGrade.parametreCorpsGrade.speciality.label.equalsIgnoreCase(filter)
            );
        }

        Pageable pageRequest = createPageRequestUsing(page, size);
        List<ParametreCorpsGrade> allParametreCorpsGrades = new ArrayList<>();
        List<ParametreCorpsGrade> pageContent = List.of();

        if (StringUtils.isNotBlank(filter)) {


            builder.andAnyOf(
                    QParametreCorpsGrade.parametreCorpsGrade.speciality.label.containsIgnoreCase(filter),
                    QParametreCorpsGrade.parametreCorpsGrade.noRef.containsIgnoreCase(filter),
                    QParametreCorpsGrade.parametreCorpsGrade.corpsGrade.label.containsIgnoreCase(filter));

        }


        if(Objects.nonNull(builder.getValue())) {

            allParametreCorpsGrades  = parametreCorpsGradeRepository
                    .findAll(builder.getValue(), PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).toList();

            for (ParametreCorpsGrade parametreCorpsGrade : allParametreCorpsGrades) {


                Set<Grade> grades = parametreCorpsGradePkRepository
                        .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                        .stream().map(ParametreCorpsGradePk::getGrade)
                        .collect(Collectors.toSet());


                TypeMatricule typeMatricules = parametreCorpsGradePkRepository
                        .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                        .stream().map(ParametreCorpsGradePk::getTypeMatricule).findFirst().get();

                parametreCorpsGrade.setGrades(grades);
                parametreCorpsGrade.setTypeMatricules(typeMatricules);



            }

            if(!allParametreCorpsGrades.isEmpty()) {
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), allParametreCorpsGrades.size());


                pageContent = allParametreCorpsGrades.subList(start, end);


            }


            paramPage = new PageImpl<>(pageContent, pageRequest, allParametreCorpsGrades.size());

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(paramPage.getSize())
                    .number(paramPage.getNumber())
                    .totalElements(paramPage.getTotalElements())
                    .totalPages(paramPage.getTotalPages())
                    .build();

            return Response.ok().setPayload(paramPage.getContent()).setMetadata(pageMetadata).setMessage("Liste des  demandes de plaintes");

        } else {

            allParametreCorpsGrades  = parametreCorpsGradeRepository
                    .findAll( PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))).toList();



            for (ParametreCorpsGrade parametreCorpsGrade : allParametreCorpsGrades) {


               // System.out.println(parametreCorpsGrade.getCorpsGrade().getId());
                Set<Grade> grades = parametreCorpsGradePkRepository
                        .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                        .stream().map(ParametreCorpsGradePk::getGrade)
                        .collect(Collectors.toSet());


                TypeMatricule typeMatricules = parametreCorpsGradePkRepository
                        .findByParametreCorpsGrade_Id(parametreCorpsGrade.getId())
                        .stream().map(ParametreCorpsGradePk::getTypeMatricule).findFirst().get();

                parametreCorpsGrade.setGrades(grades);
                parametreCorpsGrade.setTypeMatricules(typeMatricules);


            }


            if(!allParametreCorpsGrades.isEmpty()) {
                int start = (int) pageRequest.getOffset();
                int end = Math.min((start + pageRequest.getPageSize()), allParametreCorpsGrades.size());


                pageContent = allParametreCorpsGrades.subList(start, end);


            }


            paramPage = new PageImpl<>(pageContent, pageRequest, allParametreCorpsGrades.size());

            Response.PageMetadata pageMetadata = Response.PageMetadata.builder()
                    .size(paramPage.getSize())
                    .number(paramPage.getNumber())
                    .totalElements(paramPage.getTotalElements())
                    .totalPages(paramPage.getTotalPages())
                    .build();

            return Response.ok().setPayload(paramPage.getContent()).setMetadata(pageMetadata).setMessage("Liste des  params");



        }


    }

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    private TDSequenceRef updateSequence() {
        TDSequenceRef lastSequence = tdSequenceRefRepository.findTopByOrderByIdDesc();
        lastSequence.setNumero(lastSequence.getNumero() + 1);
        return lastSequence;

    }


    private String createSquence(String sequence, String numero) {
        return sequence + "0" + "" + numero;
    }
}
