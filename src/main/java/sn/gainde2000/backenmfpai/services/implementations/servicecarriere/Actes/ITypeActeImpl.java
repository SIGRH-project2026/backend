package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeActe;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeActeRepository;
import sn.gainde2000.backenmfpai.services.interfaces.servicecarriere.Actes.ITypeActe;
@Slf4j
@Component
@RequiredArgsConstructor
public class ITypeActeImpl implements ITypeActe {
    private TypeActeRepository typeActeRepository;
    @Override
    public TypeActe createTypeActe(String statut) {
        return null;
    }

    @Override
    public TypeActe getTypeActeByCode(String type) {
         TypeActe typeActe=typeActeRepository.findTypeActeByCodeActe(type).orElseThrow();
        return typeActe;
    }
}
