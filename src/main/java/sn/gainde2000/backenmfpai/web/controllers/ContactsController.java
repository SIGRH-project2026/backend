package sn.gainde2000.backenmfpai.web.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.gainde2000.backenmfpai.entities.Contacts.Contacts;
import sn.gainde2000.backenmfpai.entities.servicecarriere.Imputation.ImputationOuBulletin;
import sn.gainde2000.backenmfpai.services.interfaces.Contacts.ContactsService;
import sn.gainde2000.backenmfpai.web.dtos.responses.MFPAIResponse;

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
@RequestMapping("/contacts/")
@Tag(name = "gestion des contacts (retours) de la plateforme", description = "Permet de gérer les les retours")

public class ContactsController {

    private final ContactsService contactsService;

    @PostMapping("/add")
    public ResponseEntity<MFPAIResponse> enregistrer(@RequestBody Contacts contact){
        Contacts cont = contactsService.createContacts(contact);
        MFPAIResponse response = MFPAIResponse.success(cont);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list")
    public ResponseEntity<MFPAIResponse> getAllImputation(){
        List<Contacts> contacts = contactsService.getAllContacts();
        MFPAIResponse response = MFPAIResponse.success(contacts);
        return ResponseEntity.ok().body(response);
    }
}
