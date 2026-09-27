package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.StatutActe;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.StatutActeRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.IStatutActe;
@Slf4j
@Component
@RequiredArgsConstructor
public class IStatuActeImpl implements IStatutActe {

    private final StatutActeRepository statutActeRepository;

    private final ObjectMapper objectMapper;
    @Override
    public StatutActe createStatutActe(String statut) {
        return StatutActe.builder()
                .code(statut)
                .libelle(statut.toUpperCase())
                .build();
    }

    @Override
    public StatutActe updateStatutActe(long id, String statut) {
        StatutActe statutActe=statutActeRepository.findStatutActeById(id).orElseThrow();
        statutActe.setCode(statut);
        statutActe.setLibelle(statut.toUpperCase());
        return statutActeRepository.save(statutActe);
    }

    @Override
    public StatutActe getStatutActeByCode(String code) {
        StatutActe statutActe=statutActeRepository.findStatutActeByCode(code).orElseThrow();
        return statutActe;
    }
}
