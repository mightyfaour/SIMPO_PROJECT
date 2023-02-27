package com.example.diary.services;

import com.cloudinary.utils.ObjectUtils;
import com.example.diary.dto.request.CreateContactRequest;
import com.example.diary.dto.request.UpdateContactRequest;
import com.example.diary.dto.response.CreateContactResponse;
import com.example.diary.dto.response.DeleteContactResponse;
import com.example.diary.dto.response.UpdateContactResponse;
import com.example.diary.exception.ContactException;
import com.example.diary.model.data.Contact;
import com.example.diary.repositories.ContactRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService{

    private ContactRepository contactRepository;
    private final CloudService cloudService;

    @Override
    public CreateContactResponse createContact(CreateContactRequest createContactRequest) throws ContactException, IOException {
        Optional<Contact> optionalContact = contactRepository.findContactByUsername(createContactRequest.getUsername());
        if (optionalContact.isPresent()){
            throw new ContactException("Contact with Username "+createContactRequest.getUsername()+" already exist", 400);
        }
        Contact contact = buildContact(createContactRequest);
        if(createContactRequest.getImage() != null){
            Map<?,?> uploadResult = cloudService.upload(createContactRequest.getImage().getBytes(),
                    ObjectUtils.asMap("public_id", "profile_image/" + createContactRequest.getImage().getOriginalFilename(),
                            "overwrite", true
                    ));
            contact.setProfilePicture_url(uploadResult.get("url").toString());
        }
        Contact saveContact = contactRepository.save(contact);
        return CreateContactResponse.builder()
                .successful(true)
                .id(saveContact.getUsername())
                .build();
    }

    private Contact buildContact(CreateContactRequest createContactRequest) {
        return Contact.builder()
                .username(createContactRequest.getUsername())
                .first_name(createContactRequest.getFirst_name())
                .middle_name(createContactRequest.getMiddle_name())
                .last_name(createContactRequest.getLast_name())
                .email(createContactRequest.getEmail())
                .phone_number(createContactRequest.getPhone_number())
                .gender(createContactRequest.getGender())
                .password(createContactRequest.getPassword())
                .build();
    }

//    public List<Contact> getAllContact(){
//        return contactRepository.findAll();
//    }

    @Override
    public Contact findContactByUsername(String username) throws ContactException {
        Optional<Contact> foundContact = contactRepository.findContactByUsername(String.valueOf(username));
        if (foundContact.isEmpty()) {
            throw new ContactException("Contact with Username " + username + " does not exist", 404);
        }
        return foundContact.get();
    }

    @Override
    public List<Contact> getAllUsername() throws ContactException {
        return null;
    }

    @Override
    public DeleteContactResponse deleteContactByUsername(String username) throws ContactException {
        Optional<Contact> foundContact = contactRepository.findContactByUsername(String.valueOf(username));

        if (foundContact.isEmpty()) {
            log.info("found contact is after repo search ====> null o");
            throw new ContactException("Contact with Username " + username + " does not exist", 404);
        }
//        contactRepository.deleteContactByUsername(String.valueOf(username));
        log.info("found contact is ====> "+foundContact.get());
        contactRepository.delete(foundContact.get());
        return new DeleteContactResponse(true);
    }

    @Override
    public List<Contact> getAllContact(){
        return contactRepository.findAll();
    }

    @Override
    public UpdateContactResponse updateContactDetails(UpdateContactRequest updateContactRequest) throws ContactException, InvocationTargetException, IllegalAccessException {
        Optional<Contact> foundContact = contactRepository.findContactByUsername(String.valueOf(updateContactRequest.getUsername()));
        if (foundContact.isEmpty()){
            throw new ContactException("Contact with Username: "+updateContactRequest.getUsername()+" does not exist", 404);
        }
        Contact contact = foundContact.get();
        contact.setUsername(updateContactRequest.getUsername());
        contact.setFirst_name(updateContactRequest.getFirst_name());
        contact.setMiddle_name(updateContactRequest.getMiddle_name());
        contact.setLast_name(updateContactRequest.getLast_name());
        contact.setEmail(updateContactRequest.getEmail());
        contact.setPassword(updateContactRequest.getPassword());
        contact.setGender(updateContactRequest.getGender());
        contact.setPhone_number(updateContactRequest.getPhone_number());
        contact.setGender(updateContactRequest.getGender());
        contactRepository.save(contact);
        UpdateContactResponse updateContactResponse = new UpdateContactResponse();
        BeanUtils.copyProperties(updateContactResponse, contact);
        return updateContactResponse;
    }


}
