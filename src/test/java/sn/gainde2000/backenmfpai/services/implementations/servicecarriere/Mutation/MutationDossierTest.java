package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Mutation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.mappers.servicecarriere.Mutation.MutationMapper;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.*;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;
import sn.gainde2000.backenmfpai.services.implementations.files.FileImpl;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.Status;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutationDossierTest {
    @Mock ImutationRepository mutations;
    @Mock IUtilisateurRepository utilisateurs;
    @Mock IStatutMutationRepository statuts;
    @Mock ITraitementMutationRepository traitements;
    @Mock FileImpl files;
    @Mock MutationMapper mapper;
    @Mock sn.gainde2000.backenmfpai.commons.Notification.BusinessNotificationService businessNotifications;
    @Spy @InjectMocks MutationImpl service;
    Mutation mutation;
    final String request = "{\"motif\":\"Signé\",\"idTraiteur\":2,\"codeStatutMutation\":\"REC-DR-CFP\"}";
    final MockMultipartFile signe = new MockMultipartFile("dossierSigne", "signe.pdf", "application/pdf", new byte[]{1});

    @BeforeEach void setup() {
        mutation = new Mutation();
        mutation.setId(1L);
        mutation.setProfilDevantTraiter("Chef-cfp");
        mutation.setDossierSigne("precedent.pdf");
        when(mutations.findById(1L)).thenReturn(Optional.of(mutation));
        when(utilisateurs.findById(2L)).thenReturn(Optional.of(mock(Utilisateur.class)));
        StatutMutation statut = new StatutMutation();
        statut.setCode("REC-DR-CFP");
        when(statuts.findByCode("REC-DR-CFP")).thenReturn(statut);
    }

    @Test void refuseTransmissionSansDossierSigne() {
        assertNotEquals(Status.OK, service.traitementMutation(1L, null, request, null).getStatus());
        verifyNoInteractions(files, traitements);
        assertEquals("precedent.pdf", mutation.getDossierSigne());
    }

    @Test void echecUploadNeChangePasLEtape() {
        when(files.uploadSingleFile(signe, 1L, "mutation")).thenReturn(Response.exception());
        try (MockedStatic<TransactionAspectSupport> transactions = mockStatic(TransactionAspectSupport.class)) {
            TransactionStatus transaction = mock(TransactionStatus.class);
            transactions.when(TransactionAspectSupport::currentTransactionStatus).thenReturn(transaction);
            assertNotEquals(Status.OK, service.traitementMutation(1L, null, request, signe).getStatus());
            verify(transaction).setRollbackOnly();
        }
        verifyNoInteractions(traitements);
        assertEquals("precedent.pdf", mutation.getDossierSigne());
        assertEquals("Chef-cfp", mutation.getProfilDevantTraiter());
    }

    @Test void transmetLaVersionSigneeEtConserveSaTrace() {
        Utilisateur demandeur = mock(Utilisateur.class);
        when(demandeur.getTypeUser()).thenReturn("DEC");
        mutation.setDemandeur(demandeur);
        when(files.uploadSingleFile(signe, 1L, "mutation")).thenReturn(Response.ok().setPayload("signe.pdf"));
        when(traitements.saveAndFlush(any())).thenAnswer(i -> i.getArgument(0));
        when(mutations.saveAndFlush(any())).thenAnswer(i -> i.getArgument(0));
        doNothing().when(service).sendNotify(any(), anyString(), anyString(), anyString());
        assertEquals(Status.OK, service.traitementMutation(1L, null, request, signe).getStatus());
        assertEquals("signe.pdf", mutation.getDossierSigne());
        assertEquals("signe.pdf", mutation.getTraitementMutation().getDossierSigne());
        assertEquals("Représentant-IEF", mutation.getProfilDevantTraiter());
    }
}
