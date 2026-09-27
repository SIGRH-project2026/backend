package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.backenmfpai.entities.other.DisposableEmail;

import java.util.Optional;

/**
 * @author G2k R&D
 */

@Repository
public interface IDisposableEmailRepository extends JpaRepository<DisposableEmail,Long> {
    Optional<DisposableEmail> findByDomain(String domain);
}
