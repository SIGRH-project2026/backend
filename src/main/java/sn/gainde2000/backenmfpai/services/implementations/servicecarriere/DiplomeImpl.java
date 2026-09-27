package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.Diplome;

import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.QDiplome;
import sn.gainde2000.backenmfpai.exceptions.MFPAIException;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.DiplomeMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.IDiplomeRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IDiplome;
import sn.gainde2000.backenmfpai.web.dtos.requests.servicecarriere.DiplomeRequestDto;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIMessage;
import sn.gainde2000.backenmfpai.web.dtos.responses.servicecarriere.DiplomeResponseDto;

import java.util.Locale;
import java.util.Optional;

/**
 * @author bsdieme
 */
@Service
public class DiplomeImpl implements IDiplome {

    @Autowired
    private IDiplomeRepository diplomeRepository;

    @Autowired
    private DiplomeMapper diplomeMapper;


    @Override
    public DiplomeResponseDto getOneDiplome(Long id) {

        try{
            Optional<Diplome> diplomeOptional = diplomeRepository.findById(id);
            if(diplomeOptional.isPresent()){
                Diplome diplome = diplomeOptional.get();
                DiplomeResponseDto diplomeResponseDto = diplomeMapper.toDto(diplome);
                return diplomeResponseDto;
            }
        }catch (Exception e){
            System.out.println("erreur "+e);
            return null;
        }
        return null;
    }

    @Override
    public Page<Diplome> getAllDiplomes(int page, int size, String filter, boolean sortByDescending) {

        try{
            BooleanBuilder builder = new BooleanBuilder();
            QDiplome qDiplome = QDiplome.diplome;
            builder.and(
                    qDiplome.isDeleted.isFalse()
            );
            Sort sort = sortByDescending ? Sort.by(filter).descending() : Sort.by(filter).ascending();
            PageRequest pageRequest = PageRequest.of(page, size, sort);

            return diplomeRepository.findAll(builder,pageRequest);
        }catch (Exception e){
            System.out.println("#### ici catch ######"+e);
            return null;
        }
    }

    @Override
    public DiplomeResponseDto deleteDiplome(Long id) {

        Diplome diplome = diplomeRepository.findById(id).orElseThrow(
                () -> new MFPAIException(MFPAIMessage.NOT_FOUND, "with id = " + id)
        );
        diplome.setIsDeleted(true);
        diplomeRepository.save(diplome);
        return diplomeMapper.toDto(diplome);
    }

    @Override
    public Diplome createDiplome(DiplomeRequestDto dto) {

        try{
            Diplome diplome = diplomeMapper.toEntity(dto);
            return diplomeRepository.save(diplome);
        }catch (Exception e){
            System.out.println("Erreur "+e);
            return null;
        }

    }

    @Override
    public Diplome updateDiplome(DiplomeRequestDto dto) {

        try {
            Optional<Diplome> diplomeOptional = diplomeRepository.findById(dto.getId());
            Diplome diplome = diplomeOptional.get();
            diplome = diplomeMapper.toEntity(dto);
            return diplome;
        }catch (Exception e){
            System.out.println("Erreur "+e);
            return null;
        }

    }
}
