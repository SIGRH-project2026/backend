package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.TypeFormation;

public interface TypeFormationRepository extends JpaRepository<TypeFormation, Long> {
    TypeFormation findByCode(String code);
}