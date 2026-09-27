package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Actes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.gainde2000.backenmfpai.commons.exception.GenericApiException;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAA;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Actes.TypeAG;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeAARepository;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.TypeAGRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IActeImplTest {
    @Mock TypeAARepository typeAARepository;
    @Mock TypeAGRepository typeAGRepository;
    @InjectMocks IActeImpl service;

    @Test
    void createsAdministrativeTypeWithTrimmedLabel() {
        when(typeAARepository.findFirstByLibelleIgnoreCaseOrderByIdAsc("Nouveau type"))
                .thenReturn(Optional.empty());
        when(typeAARepository.save(any(TypeAA.class))).thenAnswer(call -> call.getArgument(0));
        TypeAA type = service.resolveTypeAA("AUTRE", "  Nouveau type  ");
        assertThat(type.getLibelle()).isEqualTo("Nouveau type");
        assertThat(type.getCode()).startsWith("CUSTOM_AA_");
        verifyNoInteractions(typeAGRepository);
    }

    @Test
    void createsManagementType() {
        when(typeAGRepository.findFirstByLibelleIgnoreCaseOrderByIdAsc("Attestation spéciale"))
                .thenReturn(Optional.empty());
        when(typeAGRepository.save(any(TypeAG.class))).thenAnswer(call -> call.getArgument(0));
        TypeAG type = service.resolveTypeAG("AUTRE", "Attestation spéciale");
        assertThat(type.getLibelle()).isEqualTo("Attestation spéciale");
        assertThat(type.getCode()).startsWith("CUSTOM_AG_");
        verifyNoInteractions(typeAARepository);
    }

    @Test
    void reusesExistingType() {
        TypeAG existing = TypeAG.builder().code("EXISTANT").libelle("Certificat").build();
        when(typeAGRepository.findFirstByLibelleIgnoreCaseOrderByIdAsc("certificat"))
                .thenReturn(Optional.of(existing));
        assertThat(service.resolveTypeAG("AUTRE", " certificat ")).isSameAs(existing);
        verify(typeAGRepository, never()).save(any());
    }

    @Test
    void rejectsMissingBlankAndLongLabels() {
        for (String label : new String[] { null, "", "   ", "x".repeat(101) }) {
            assertThatThrownBy(() -> service.resolveTypeAA("AUTRE", label))
                    .isInstanceOf(GenericApiException.class);
            assertThatThrownBy(() -> service.resolveTypeAG("AUTRE", label))
                    .isInstanceOf(GenericApiException.class);
        }
        verifyNoInteractions(typeAARepository, typeAGRepository);
    }

    @Test
    void keepsExistingSelection() {
        TypeAA existing = TypeAA.builder().code("AA01").build();
        when(typeAARepository.findTypeAAByCode("AA01")).thenReturn(Optional.of(existing));
        assertThat(service.resolveTypeAA("AA01", null)).isSameAs(existing);
        verify(typeAARepository, never()).save(any());
    }
}
