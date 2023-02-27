package com.example.diary.repositories;

import com.example.diary.model.data.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

    Optional<Contact> findContactByUsername(String username);

    void deleteContactByUsername(String valueOf);
}
