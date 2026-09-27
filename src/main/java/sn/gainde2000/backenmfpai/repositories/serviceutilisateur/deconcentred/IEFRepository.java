
package sn.gainde2000.backenmfpai.repositories.serviceutilisateur.deconcentred;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.IEF;

import java.util.List;
import java.util.Optional;


/**
 * @author Abdou Karim CISSOKHO
 * @created 01/02/2024-11:26
 * @project backend_mfpai
 */
public interface IEFRepository extends JpaRepository<IEF, Long>, QuerydslPredicateExecutor<IEF> {
    List<IEF> findByIa_Code(String code);

    Optional<IEF> findByCode(String code);
    Optional<IEF> findByLabel(String code);
    //indicateurs IEF pour Representant IEF
    @Query("SELECT COUNT(ief) FROM IEF ief WHERE ief.ia.code = :codeIa ")
    long countAllIefIa(String codeIa);

    @Query("SELECT COUNT(ief) FROM IEF ief")
    long countAllIef();
}
