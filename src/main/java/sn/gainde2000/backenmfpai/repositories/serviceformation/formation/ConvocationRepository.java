package sn.gainde2000.backenmfpai.repositories.serviceformation.formation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sn.gainde2000.backenmfpai.entities.serviceformation.formation.Convocation;

@Repository
public interface ConvocationRepository extends JpaRepository<Convocation, Long> {

    List<Convocation> findByFormationId(Long formationId);
}