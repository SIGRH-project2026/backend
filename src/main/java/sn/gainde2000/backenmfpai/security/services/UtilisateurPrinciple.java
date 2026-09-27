
package sn.gainde2000.backenmfpai.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sn.gainde2000.backenmfpai.commons.utils.AuthUtils;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Menu;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Profile;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.Utilisateur;
import sn.gainde2000.backenmfpai.web.dtos.requests.serviceutilisateur.MenuDTO;
import sn.gainde2000.backenmfpai.web.dtos.responses.autentification.UtilisateurInfo;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author G2k R&D
 */

@Getter
@Builder
@RequiredArgsConstructor
public class UtilisateurPrinciple implements UserDetails {
    private  transient UtilisateurInfo utilisateurInfo;
  //  private static final IUtilisateurMapper utilisateurMapper = Mappers.getMapper(IUtilisateurMapper.class);
    @JsonIgnore
    private  String password;
    private final  Collection<? extends  GrantedAuthority> authorities;
    private  transient  Set<MenuDTO> menus;

    public UtilisateurPrinciple(UtilisateurInfo utilisateurInfo, String password, Collection<? extends GrantedAuthority> authorities, Set<MenuDTO> menus) {
        this.password = password;
        this.authorities = authorities;
        this.utilisateurInfo = utilisateurInfo;
        this.menus = menus;
    }


    public static UtilisateurPrinciple build(Utilisateur user) {
    /*    List<GrantedAuthority> authorities = Stream.of(user.getProfils())
        .map(profils -> {
                  for (Profile profile: profils) {
                      new SimpleGrantedAuthority(profile.getCode());
                  }

                  return authorities;
                }

        ).collect(Collectors.toList());*/

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

      //  Set<MenuDTO> menus = new HashSet<>();
        if (Objects.nonNull(user.
                getProfils()) && !user.getProfils().isEmpty()) {

            authorities = user.getProfils().stream()


                    .map(permission ->
                                new SimpleGrantedAuthority(permission.getCode())

                          )
                    .collect(Collectors.toList());


          // var menus =  AuthUtils.getMenusOfUtilisateur()
        }



       // Optional<Set<MenuDTO> menuOptional = user.getProfils().stream().map(Profile::getMenus).findAny();


        //Set<MenuDTO> menus = AuthUtils.getMenusOfUtilisateur(menuOptional.get());


        UtilisateurInfo utilisateurInfo = new UtilisateurInfo(user.getId(), user.getEmail(), user.getMatricule(),
                user.getPrenom(), user.getNom(), user.getProfils(), user.getStatus());
        return UtilisateurPrinciple.builder()
                .utilisateurInfo(utilisateurInfo)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }


  /*  public static UtilisateurPrinciple build(Utilisateur user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if (Objects.nonNull(user.
                getProfils()) && !user.getProfils().isEmpty()) {

            authorities = user.getProfils().stream()


                    .map(permission ->
                            new SimpleGrantedAuthority(permission.getCode()))
                    .collect(Collectors.toList());

        }

        AuthenticatedUserInfosDTO details = SecurityUtils.formatUser(account);

        return new UserDetailsImpl(
                account.getLogin(),
                account.getUser().getEmail(),
                account.getPassword(),
                authorities, details);
    }

 */


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.utilisateurInfo.email() != null && !this.utilisateurInfo.email().isBlank()
                ? this.utilisateurInfo.email()
                : this.utilisateurInfo.matricule();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.utilisateurInfo.status();
    }
}
