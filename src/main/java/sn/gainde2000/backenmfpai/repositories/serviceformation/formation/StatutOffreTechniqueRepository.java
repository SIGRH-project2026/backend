package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.StatutOffreTechnique;

public interface StatutOffreTechniqueRepository extends JpaRepository<StatutOffreTechnique, Long> {
    StatutOffreTechnique findByCode(String code);
}