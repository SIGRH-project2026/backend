package sn.gainde2000.backenmfpai.repositories.serviceformation.planformation;

import java.util.List;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.ThemeFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

public interface ThemeFormationRepository
        extends JpaRepository<ThemeFormation, Long>, QuerydslPredicateExecutor<DeconcentratedLevel> {

    List<ThemeFormation> findByPlanFormationId(Long planFormationId);

}
