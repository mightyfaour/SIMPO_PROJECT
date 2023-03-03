package com.example.diary.controller;

import com.example.diary.dto.request.CreateContactRequest;
import com.example.diary.dto.request.UpdateContactRequest;
import com.example.diary.dto.response.CreateContactResponse;
import com.example.diary.exception.ContactException;
import com.example.diary.services.ContactService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1/diary/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/createContact")
    public ResponseEntity<?> createContact(@RequestBody CreateContactRequest createContactRequest) throws ContactException, IOException {
        CreateContactResponse createContactResponse = contactService.createContact(createContactRequest);
        return new ResponseEntity<>(createContactResponse, HttpStatus.CREATED);
    }
    @GetMapping("/listContact")
    public ResponseEntity<?> ListContact() throws ContactException{
        return new ResponseEntity<>(contactService.getAllContact(), HttpStatus.OK);
    }

    @GetMapping("/listContact/{username}")
    public ResponseEntity<?> findContactByUsername(@PathVariable String username) throws ContactException{
        return new ResponseEntity<>(contactService.findContactByUsername(username), HttpStatus.OK);
    }

    @DeleteMapping("/deleteContact/{username}")
    public ResponseEntity<?> deleteContactByUsername(@PathVariable String username) throws ContactException{
        return new ResponseEntity<>(contactService.deleteContactByUsername(username), HttpStatus.OK);
    }

    @PatchMapping("/updateContactDetails")
    public ResponseEntity<?> updateContactByUsername(@RequestBody UpdateContactRequest updateContactRequest) throws ContactException, InvocationTargetException, IllegalAccessException {
        return new ResponseEntity<>(contactService.updateContactDetails(updateContactRequest), HttpStatus.OK);
    }


}
