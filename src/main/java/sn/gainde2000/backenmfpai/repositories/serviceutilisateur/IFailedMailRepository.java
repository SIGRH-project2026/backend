package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.gainde2000.backenmfpai.entities.other.FailedMail;

/**
 * @author G2k R&D
 */

@Repository
public interface IFailedMailRepository extends JpaRepository<FailedMail,Long> {
}

