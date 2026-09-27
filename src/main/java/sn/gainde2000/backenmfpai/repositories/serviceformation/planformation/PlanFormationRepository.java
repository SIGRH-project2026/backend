package sn.gainde2000.backenmfpai.repositories.serviceformation.planformation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import sn.gainde2000.backenmfpai.entities.serviceformation.planformation.PlanFormation;
import sn.gainde2000.backenmfpai.entities.serviceutilisateur.deconcentred.DeconcentratedLevel;

import java.util.Date;
import java.util.List;

public interface PlanFormationRepository
        extends JpaRepository<PlanFormation, Long>, QuerydslPredicateExecutor<DeconcentratedLevel> {

    @Query("SELECT DISTINCT pf FROM PlanFormation pf LEFT JOIN FETCH pf.themesFormation")
    Page<PlanFormation> findAllWithThemes(Pageable pageable);

    List<PlanFormation> findByDateDebutBetweenOrDateFinBetween(Date start1, Date end1, Date start2, Date end2);

    List<PlanFormation> findByDateDebutBetween(Date startDate, Date endDate);

}
