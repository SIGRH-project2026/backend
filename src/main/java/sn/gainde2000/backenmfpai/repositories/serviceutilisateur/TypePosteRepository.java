package sn.gainde2000.backenmfpai.repositories.serviceutilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import sn.gainde2000.backenmfpai.entities.serviceutilisateur.TypePoste;

import java.util.Optional;

/**
 * @author Abdou Karim CISSOKHO
 * @created 29/04/2024-11:58
 * @project backend_mfpai
 */
public interface TypePosteRepository   extends JpaRepository<TypePoste, Long>, QuerydslPredicateExecutor<TypePoste> {
    Optional<TypePoste> findByCode(String code);
}


