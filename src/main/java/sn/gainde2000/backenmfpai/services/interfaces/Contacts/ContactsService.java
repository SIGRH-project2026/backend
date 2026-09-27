package sn.gainde2000.backenmfpai.services.interfaces.Contacts;

import sn.gainde2000.backenmfpai.entities.Contacts.Contacts;

import java.util.List;

public interface ContactsService {

    Contacts createContacts(Contacts contacts);
    List<Contacts> getAllContacts();
}
