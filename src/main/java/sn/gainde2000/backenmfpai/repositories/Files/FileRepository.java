package sn.gainde2000.backenmfpai.repositories.Files;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.file.File;

import java.util.List;

public interface FileRepository extends JpaRepository<File,Long>, QuerydslPredicateExecutor<File> {

    List<File> getAllByIdAppartenance(long id);
    File findByGeneratedName(String generatedName);
}
