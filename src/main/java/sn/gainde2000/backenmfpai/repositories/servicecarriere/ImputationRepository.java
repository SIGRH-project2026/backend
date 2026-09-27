package sn.gainde2000.backenmfpai.repositories.servicecarriere;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;

public interface ImputationRepository extends JpaRepository<ImputationOuBulletin, Long>,
        QuerydslPredicateExecutor<ImputationOuBulletin> {

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp WHERE imp.typeDemande = :typeDemande")
    long countImputationOrBulletinByType(String typeDemande);

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp")
    long countAllImputationOrBulletin();

    @Query("SELECT imp FROM ImputationOuBulletin imp WHERE imp.typeDemande = :typeDemande")
    Page<ImputationOuBulletin> imputationOrBulletinByType(String typeDemande, PageRequest pageRequest);

    @Query("SELECT imp FROM ImputationOuBulletin imp")
    Page<ImputationOuBulletin> allImputationOrBulletin(PageRequest pageRequest);

    // ===================== REPRESENTANT IA =====================

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "WHERE imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById")
    Page<ImputationOuBulletin> AllImputationOrBulletinByIaOrCreatedBy(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById,
            PageRequest pageRequest
    );

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "WHERE (imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById) " +
           "AND imp.typeDemande = :type")
    Page<ImputationOuBulletin> AllImputationOrBulletinByIaOrCreatedByAndType(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById,
            @Param("type") String type,
            PageRequest pageRequest
    );

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "WHERE imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById")
    long countAllImputationOrBulletinByIaOrCreatedBy(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById
    );

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "WHERE (imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById) " +
           "AND imp.typeDemande = :type")
    long countAllImputationOrBulletinByIaOrCreatedByAndType(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById,
            @Param("type") String type
    );

    // ===================== REPRESENTANT IEF =====================

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "WHERE imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ief.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById")
    Page<ImputationOuBulletin> AllImputationOrBulletinByIefIaOrCreatedBy(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById,
            PageRequest pageRequest
    );

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "WHERE (imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ief.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById) " +
           "AND imp.typeDemande = :type")
    Page<ImputationOuBulletin> AllImputationOrBulletinByIefIaOrCreatedByAndType(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById,
            @Param("type") String type,
            PageRequest pageRequest
    );

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "WHERE imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ief.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById")
    long countAllImputationOrBulletinByIefIaOrCreatedBy(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById
    );

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "WHERE (imp.id IN (" +
           "  SELECT imp2.id FROM ImputationOuBulletin imp2 " +
           "  LEFT JOIN DeconcentratedLevel dec ON imp2.utilisateur.id = dec.id " +
           "  WHERE dec.ief.ia.code = :iaCode" +
           ") OR imp.createdBy.id = :createdById) " +
           "AND imp.typeDemande = :type")
    long countAllImputationOrBulletinByIefIaOrCreatedByAndType(
            @Param("iaCode") String iaCode,
            @Param("createdById") Long createdById,
            @Param("type") String type
    );


    // À ajouter dans ImputationRepository.java
@Query("SELECT imp FROM ImputationOuBulletin imp " +
       "WHERE imp.createdBy.id = :createdById " +
       "AND imp.isDeleted = false")
Page<ImputationOuBulletin> findMesImputations(
        @Param("createdById") Long createdById,
        PageRequest pageRequest);

@Query("SELECT imp FROM ImputationOuBulletin imp " +
       "WHERE imp.createdBy.id = :createdById " +
       "AND imp.typeDemande = :type " +
       "AND imp.isDeleted = false")
Page<ImputationOuBulletin> findMesImputationsByType(
        @Param("createdById") Long createdById,
        @Param("type") String type,
        PageRequest pageRequest);

    // ===================== CHEF ETABLISSEMENT =====================

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "INNER JOIN DeconcentratedLevel dec ON imp.utilisateur.id = dec.id " +
           "WHERE dec.etablissement.code = :EtabCode")
    Page<ImputationOuBulletin> AllImputationOrBulletinByEtablissement(
            @Param("EtabCode") String EtabCode, PageRequest pageRequest);

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "INNER JOIN DeconcentratedLevel dec ON imp.utilisateur.id = dec.id " +
           "WHERE dec.etablissement.code = :EtabCode " +
           "AND imp.typeDemande = :Type")
    Page<ImputationOuBulletin> AllImputationOrBulletinByEtabAndType(
            @Param("EtabCode") String EtabCode,
            @Param("Type") String Type,
            PageRequest pageRequest);

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "INNER JOIN DeconcentratedLevel dec ON imp.utilisateur.id = dec.id " +
           "WHERE dec.etablissement.code = :EtabCode")
    long countAllImputationOrBulletinByEtablissement(@Param("EtabCode") String EtabCode);

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "INNER JOIN DeconcentratedLevel dec ON imp.utilisateur.id = dec.id " +
           "WHERE dec.etablissement.code = :EtabCode " +
           "AND imp.typeDemande = :Type")
    long countAllImputationOrBulletinByEtabAndType(
            @Param("EtabCode") String EtabCode,
            @Param("Type") String Type);

    // ===================== CHEF SERVICE DFC =====================

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.service.code = :codeService " +
           "AND imp.typeDemande = :Type")
    long countImputationOrbulletinForServiceDfc(
            @Param("codeService") String codeService,
            @Param("Type") String Type);

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.service.code = :codeService")
    long countAllImputaionOrBulletinServiceDfc(@Param("codeService") String codeService);

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.service.code = :codeService " +
           "AND imp.typeDemande = :Type")
    Page<ImputationOuBulletin> ImputationOrbulletinForServiceType(
            @Param("codeService") String codeService,
            @Param("Type") String Type,
            PageRequest pageRequest);

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.service.code = :codeService")
    Page<ImputationOuBulletin> AllImputaionOrBulletinService(
            @Param("codeService") String codeService,
            PageRequest pageRequest);

    // ===================== CHEF DIVISION DFC =====================

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.division.code = :codeDivision " +
           "AND imp.typeDemande = :Type")
    long countImputationOrbulletinForDivisionDfc(
            @Param("codeDivision") String codeDivision,
            @Param("Type") String Type);

    @Query("SELECT COUNT(imp) FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.division.code = :codeDivision")
    long countAllImputaionOrBulletinDivisionDfc(@Param("codeDivision") String codeDivision);

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.division.code = :codeDivision " +
           "AND imp.typeDemande = :Type")
    Page<ImputationOuBulletin> ImputationOrbulletinForDivisionDfc(
            @Param("codeDivision") String codeDivision,
            @Param("Type") String Type,
            PageRequest pageRequest);

    @Query("SELECT imp FROM ImputationOuBulletin imp " +
           "INNER JOIN CentralLevel cen ON imp.utilisateur.id = cen.id " +
           "WHERE cen.division.code = :codeDivision")
    Page<ImputationOuBulletin> AllImputaionOrBulletinDivisionDfc(
            @Param("codeDivision") String codeDivision,
            PageRequest pageRequest);
}