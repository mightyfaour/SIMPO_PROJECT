package com.example.diary.services;

import com.example.diary.dto.request.CreateContactRequest;
import com.example.diary.dto.request.UpdateContactRequest;
import com.example.diary.dto.response.CreateContactResponse;
import com.example.diary.dto.response.DeleteContactResponse;
import com.example.diary.dto.response.UpdateContactResponse;
import com.example.diary.exception.ContactException;
import com.example.diary.model.data.Contact;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ContactService {
    CreateContactResponse createContact(CreateContactRequest createContactRequest) throws ContactException, IOException;

    Contact findContactByUsername(String username) throws ContactException;

    List<Contact> getAllUsername() throws ContactException;

    List<Contact> getAllContact() throws ContactException;

    UpdateContactResponse updateContactDetails(UpdateContactRequest request) throws ContactException, InvocationTargetException, IllegalAccessException;

    DeleteContactResponse deleteContactByUsername(String username) throws ContactException ;
}
