package com.test.rest.data.controller;

import com.test.rest.data.entity.Contacts;
import com.test.rest.data.service.ContactsService;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContactsController {
    
    @Autowired
    private ContactsService contactsService;
    
    @GetMapping(value = "/contacts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contacts> allContacts(
            @RequestParam(value = "nameFilter") String nameFilter,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "28") int size) {
        
        List<Contacts> resultContacts = new ArrayList<>();
        List<Contacts> list = new ArrayList<>(contactsService.findAll(PageRequest.of(page, size)));
        list.stream().filter(e -> !e.getName().matches(nameFilter)).forEach(resultContacts::add);
        return resultContacts;
    }
    
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]^");
            }
        });
        return factory;
    }
}
