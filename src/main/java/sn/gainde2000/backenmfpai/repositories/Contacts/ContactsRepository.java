package sn.gainde2000.backenmfpai.repositories.Contacts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import sn.gainde2000.backenmfpai.entities.Contacts.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, Long>, QuerydslPredicateExecutor<Contacts> {
}
