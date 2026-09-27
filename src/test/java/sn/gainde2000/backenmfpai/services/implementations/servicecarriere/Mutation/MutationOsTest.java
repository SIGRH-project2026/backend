package sn.gainde2000.backenmfpai.services.implementations.servicecarriere.Mutation;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import sn.gainde2000.backenmfpai.commons.utils.JasperGenerator;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Mutation.*;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Mutation.ImutationRepository;
import sn.gainde2000.backenmfpai.services.implementations.files.FileImpl;
import sn.gainde2000.backenmfpai.web.dtos.responses.Response;
import sn.gainde2000.backenmfpai.web.dtos.responses.Status;
import java.util.List;
import java.util.Optional;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutationOsTest {
    @Test void genereUnPdfEtLeTransmetAvecSonNomEtSonType() throws Exception {
        Mutation mutation = new Mutation();
        CentralLevel agent = new CentralLevel();
        agent.setNom("Exemple");
        agent.setPrenom("Agent");
        agent.setMatricule("TEST");
        mutation.setDemandeur(agent);
        mutation.setOrigineDemandeurLog(OrigineDemandeurLog.builder().origineUserType("CEN").build());
        mutation.setDestinataireType("CEN");
        JasperGenerator jasper = mock(JasperGenerator.class, CALLS_REAL_METHODS);
        ImutationRepository repository = mock(ImutationRepository.class);
        when(repository.findById(1L)).thenReturn(Optional.of(mutation));
        FileImpl files = mock(FileImpl.class);
        when(files.uploadSingleFile(any(), eq(0L), eq("mutation"))).thenAnswer(call -> {
            MultipartFile file = call.getArgument(0);
            assertEquals("ordre-de-service-mutation.pdf", file.getOriginalFilename());
            assertEquals("application/pdf", file.getContentType());
            assertEquals("%PDF-", new String(file.getBytes(), 0, 5, StandardCharsets.US_ASCII));
            return Response.ok().setPayload("os.pdf");
        });
        MutationImpl service = mock(MutationImpl.class, CALLS_REAL_METHODS);
        ReflectionTestUtils.setField(service, "imutationRepository", repository);
        ReflectionTestUtils.setField(service, "jasperGenerator", jasper);
        ReflectionTestUtils.setField(service, "fileImpl", files);
        assertEquals(Status.OK, service.genererMutation(false, 1L).getStatus());
        assertTrue(mutation.isOsgenerated());
        verify(repository).save(mutation);
    }

    @Test void neMarquePasLosGenereSiLeStockageNeRetournePasDeFichier() throws Exception {
        Mutation mutation = new Mutation();
        ImutationRepository repository = mock(ImutationRepository.class);
        when(repository.findById(1L)).thenReturn(Optional.of(mutation));
        JasperGenerator jasper = mock(JasperGenerator.class);
        when(jasper.getMutationOS(anyList())).thenReturn(new byte[]{1});
        FileImpl files = mock(FileImpl.class);
        when(files.uploadSingleFile(any(), eq(0L), eq("mutation"))).thenReturn(Response.ok());
        MutationImpl service = mock(MutationImpl.class, CALLS_REAL_METHODS);
        ReflectionTestUtils.setField(service, "imutationRepository", repository);
        ReflectionTestUtils.setField(service, "jasperGenerator", jasper);
        ReflectionTestUtils.setField(service, "fileImpl", files);
        assertNotEquals(Status.OK, service.genererMutation(false, 1L).getStatus());
        assertFalse(mutation.isOsgenerated());
        verify(repository, never()).save(any());
    }
}
