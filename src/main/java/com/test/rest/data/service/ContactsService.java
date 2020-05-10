package com.test.rest.data.service;

import com.test.rest.data.entity.Contacts;
import com.test.rest.data.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsService {
    @Autowired
    private ContactsRepository contactsRepository;
    
    public List<Contacts> findAll(Pageable pageable) {
        
        Page<Contacts> pagedResult = contactsRepository.findAll(pageable);
        return pagedResult.toList();
    }
    
}
