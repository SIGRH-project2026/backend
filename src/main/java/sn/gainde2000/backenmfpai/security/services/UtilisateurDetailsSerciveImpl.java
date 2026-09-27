
package sn.gainde2000.backenmfpai.security.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.commons.utils.i18n.I18nTranslate;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.repositories.serviceutilisateur.IUtilisateurRepository;


import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.CONNEXION_LOGIN_TENTATIVE;
import static sn.gainde2000.backenmfpai.commons.utils.i18n.I18nKeys.UTILISATEUR_ABSENT;

/**
 * @author G2k R&D
 */

@Service
@RequiredArgsConstructor
public class UtilisateurDetailsSerciveImpl implements UserDetailsService {
    private final IUtilisateurRepository utilisateurRepository;
    private final LoginAttemptService loginAttemptService;
    private final I18nTranslate i18nTranslat;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (loginAttemptService.isBlocked(username)) {
            throw new InternalAuthenticationServiceException(i18nTranslat.toTranslate(CONNEXION_LOGIN_TENTATIVE));
        }
        String identifier = username == null ? "" : username.trim();
        Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmailIgnoreCase(identifier)
                .or(() -> utilisateurRepository.findUtilisateurByNormalizedMatricule(identifier))
                .orElseThrow(() -> new UsernameNotFoundException(
                        i18nTranslat.toTranslate(UTILISATEUR_ABSENT) + " : " + identifier));
        return UtilisateurPrinciple.build(utilisateur);
    }
}
