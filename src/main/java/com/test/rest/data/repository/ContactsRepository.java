package com.test.rest.data.repository;

import com.test.rest.data.entity.Contacts;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends PagingAndSortingRepository<Contacts, Long> {
}
