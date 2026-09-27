package sn.gainde2000.backenmfpai.services.implementations.Contacts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.gainde2000.backenmfpai.entities.Contacts.Contacts;
import sn.gainde2000.backenmfpai.repositories.Contacts.ContactsRepository;
import sn.gainde2000.backenmfpai.services.interfaces.Contacts.ContactsService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactsServiceImpl implements ContactsService {

    private final ContactsRepository contactsRepository;

    @Override
    public Contacts createContacts(Contacts contacts) {
        return contactsRepository.save(contacts);
    }

    @Override
    public List<Contacts> getAllContacts() {
        return contactsRepository.findAll();
    }
}
