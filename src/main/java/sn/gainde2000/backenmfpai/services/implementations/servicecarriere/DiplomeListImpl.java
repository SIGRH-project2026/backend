package sn.gainde2000.backenmfpai.services.implementations.servicecarriere;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.servicecarriere.dossieragent.DiplomeList;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.IDiplomeListRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.IDiplomeList;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class DiplomeListImpl implements IDiplomeList {

    private final IDiplomeListRepository iDiplomeListRepository;

    @Override
    public DiplomeList getOne(long id) {
        return iDiplomeListRepository.findById(id).get();
    }

    @Override
    public List<DiplomeList> getAll() {
        return iDiplomeListRepository.findAll();
    }
}
