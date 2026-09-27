package sn.gainde2000.backenmfpai.repositories.serviceformation.planformation;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sn.gainde2000.backenmfpai.entities.serviceformation.statutplanformation.StatutPlanFormation;

public interface StatutPlanFormationRepository extends JpaRepository<StatutPlanFormation, Long> {

    public StatutPlanFormation findByCode(String code);
}