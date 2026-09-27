package sn.gainde2000.backenmfpai.commons.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.central.CentralLevel;
import sn.gainde2000.backenmfpai.repositories.servicecarriere.Actes.ActeRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.central.CentralLevelRepository;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred.DeconcentratedLevelRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class JasperGeneratorTest {
    @ParameterizedTest
    @CsvSource({"Professeur,de formateur", "Chef de division,d'agent"})
    void usesPersonalProfileAsQualite(String label, String fallback) {
        Profile profile = new Profile();
        profile.setLabel(label);
        assertEquals(label, JasperGenerator.resolveQualite(Set.of(profile), fallback));
    }

    @ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings = {"d'agent", "de formateur"})
    void retainsDefaultWhenProfileIsMissing(String fallback) {
        assertEquals(fallback, JasperGenerator.resolveQualite(null, fallback));
        assertEquals(fallback, JasperGenerator.resolveQualite(Set.of(), fallback));
        Profile profile = new Profile();
        assertEquals(fallback, JasperGenerator.resolveQualite(Set.of(profile), fallback));
        profile.setLabel("  ");
        assertEquals(fallback, JasperGenerator.resolveQualite(Set.of(profile), fallback));
    }

    @ParameterizedTest
    @CsvSource({
            "Bulletin de visite,Soi", "Bulletin de visite,Époux",
            "Bulletin de visite,Épouse", "Bulletin de visite,Enfant",
            "Imputation budgétaire,Soi", "Imputation budgétaire,Époux",
            "Imputation budgétaire,Épouse", "Imputation budgétaire,Enfant"
    })
    void generatesPdfWithoutCorpsOrGrade(String type, String beneficiary) throws Exception {
        CentralLevel agent = mock(CentralLevel.class, RETURNS_DEEP_STUBS);
        when(agent.getTypeUser()).thenReturn("CEN");
        when(agent.getEmail()).thenReturn("agent@example.test");
        when(agent.getDirection().getLabel()).thenReturn("DRH");
        when(agent.getCorpsGrade()).thenReturn(null);
        when(agent.getGrade()).thenReturn(null);
        when(agent.getNom()).thenReturn("Exemple");
        when(agent.getPrenom()).thenReturn("Agent");
        when(agent.getMatricule()).thenReturn("123456/A");
        when(agent.getAdresse()).thenReturn("Dakar");
        CentralLevelRepository repository = mock(CentralLevelRepository.class);
        when(repository.findByEmail("agent@example.test")).thenReturn(Optional.of(agent));
        JasperGenerator generator = new JasperGenerator(repository,
                mock(DeconcentratedLevelRepository.class), mock(ActeRepository.class));
        ImputationOuBulletin request = new ImputationOuBulletin();
        request.setId(351L);
        request.setUtilisateur(agent);
        request.setTypeDemande(type);
        request.setStatusBeneficiere(beneficiary);
        request.setDateImputation(LocalDate.of(2026, 9, 10));
        request.setNomBeneficiere("Exemple");
        request.setPrenomBeneficiere("Bénéficiaire");

        byte[] pdf = generator.getImputationOrBulletinPDF(request);

        assertTrue(pdf.length > 1000);
        assertTrue(new String(pdf, 0, 5, StandardCharsets.US_ASCII).equals("%PDF-"));
    }
}
