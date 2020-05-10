package com.test.rest;

import com.test.rest.data.controller.ContactsController;
import com.test.rest.data.entity.Contacts;
import com.test.rest.data.service.ContactsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ContactsControllerTest {
    @InjectMocks
    ContactsController contactsController;
    
    @Mock
    ContactsService contactsService;
    
    @Test
    public void testFindAllWithDefaultNameFilter() {
        Contacts employee1 = new Contacts(1, "Lokesh");
        Contacts employee2 = new Contacts(2, "Alex");
        List<Contacts> contacts = new ArrayList<>();
        contacts.add(employee1);
        contacts.add(employee2);
        when(contactsService.findAll(PageRequest.of(0, 2))).thenReturn(contacts);
        List<Contacts> result = contactsController.allContacts("^.", 0, 2);
        assertThat(result, hasSize(2));
    }
    
    @Test
    public void testFindAllWithSpecificNameFilter() throws Exception {
        List<Contacts> allContacts = Arrays.asList(
                new Contacts(1, "Dehjf"),
                new Contacts(2, "ARere"),
                new Contacts(3, "APiidgjdere"),
                new Contacts(4, "Top"),
                new Contacts(5, "Mikel"),
                new Contacts(6, "AQesfa"),
                new Contacts(7, "ASinkfk"),
                new Contacts(8, "ALimhdfj"),
                new Contacts(9, "Snity"));
        when(contactsService.findAll(PageRequest.of(0, 9))).thenReturn(allContacts);
        List<String> result = new ArrayList<>();
        contactsController.allContacts("^A.*$", 0, 9).forEach(e -> result.add(e.getName()));
        assertThat(result, everyItem(is(not(startsWith("A")))));
        assertThat(result, hasSize(4));
        
    }
    
    @Test
    public void testFindAllWithCombinationNameFilter() throws Exception {
        List<Contacts> allContacts = Arrays.asList(
                new Contacts(1, "Dehjf"),
                new Contacts(2, "ARere"),
                new Contacts(3, "APiidgjdere"),
                new Contacts(4, "Top"),
                new Contacts(5, "Mikel"),
                new Contacts(6, "AQesfa"),
                new Contacts(7, "ASinkfk"),
                new Contacts(8, "ALimhdfj"),
                new Contacts(9, "Snity"));
        when(contactsService.findAll(PageRequest.of(0, 9))).thenReturn(allContacts);
        List<String> result = new ArrayList<>();
        contactsController.allContacts("^.*[aei].*$", 0, 9).forEach(e -> result.add(e.getName()));
        assertThat(result, hasSize(1));
        assertThat(result, both(everyItem(not(containsString("i"))))
                .and(everyItem(not(containsString("a"))))
                .and(everyItem(not(containsString("e")))));
        
    }
}
