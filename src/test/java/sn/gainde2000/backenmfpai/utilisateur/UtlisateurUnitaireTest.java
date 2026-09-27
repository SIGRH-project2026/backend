
package sn.gainde2000.backenmfpai.utilisateur;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author G2k R&D
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration
class UtlisateurUnitaireTest {

  /*
   * @Autowired
   * private IUtilisateurRepository utilisateurRepository;
   * 
   * // @Autowired
   * // private AuthentificationImpl authentificationService;
   * 
   * // private final IUtilisateurMapper utilisateurMapper =
   * Mappers.getMapper(IUtilisateurMapper.class);
   * // private LoginFormDTO loginFormDTO;
   * // private Utilisateur utilisateur;
   * // @Value("${projet.securite.jwtSecret}")
   * // private String jwtSecret;
   * 
   * // JwtProvider jwtProvider;
   * 
   * // @BeforeEach
   * // void setUp() {
   * // jwtProvider = mock(JwtProvider.class);
   * // loginFormDTO = new LoginFormDTO("sow@yopmail.com", "Ncg8y-");
   * // }
   * 
   * 
   * // @Test
   * // @DisplayName("je suis le test unitaire pour chercher un utilisateur par son email"
   * )
   * // void findAllUtilisateursTest() {
   * // Optional<Utilisateur> optionnelUtilisateur =
   * utilisateurRepository.findUtilisateurByEmail(loginFormDTO.login());
   * // optionnelUtilisateur.ifPresent(value -> utilisateur = value);
   * // assertNotNull(utilisateur);
   * // assertEquals(utilisateur.getEmail(), loginFormDTO.login());
   * // }
   * 
   * // @Test
   * // void siginServiceTest() {
   * // Optional<Utilisateur> optionnelUtilisateur =
   * utilisateurRepository.findUtilisateurByEmail(loginFormDTO.login());
   * // optionnelUtilisateur.ifPresent(value -> utilisateur = value);
   * // UtilisateurInfo utilisateurInfo = new UtilisateurInfo(utilisateur.getId(),
   * utilisateur.getEmail(),
   * utilisateurMapper.profilToProfilDTO(utilisateur.getProfil()), true);
   * 
   * // Authentication authentication = mock(Authentication.class);
   * // UtilisateurPrinciple utilisateurPrinciple =
   * mock(UtilisateurPrinciple.class);
   * // when(authentication.getPrincipal()).thenReturn(utilisateurPrinciple);
   * // when(utilisateurPrinciple.getUsername()).thenReturn(loginFormDTO.login());
   * // when(utilisateurPrinciple.getMenus()).thenReturn(utilisateurInfo.profil().
   * menu());
   * //
   * when(utilisateurPrinciple.getUtilisateurInfo()).thenReturn(utilisateurInfo);
   * 
   * // when(jwtProvider.getSignatureKey()).thenReturn(Keys.hmacShaKeyFor(
   * // jwtSecret.getBytes(StandardCharsets.UTF_8)));
   * // when(jwtProvider.generateToken(authentication)).thenCallRealMethod();
   * 
   * // Response<Object> response = authentificationService.singIn(loginFormDTO);
   * // assertNotNull(response);
   * // String generatedToken = jwtProvider.generateToken(authentication);
   * // assertNotNull(generatedToken);
   * 
   * }
   */
}
