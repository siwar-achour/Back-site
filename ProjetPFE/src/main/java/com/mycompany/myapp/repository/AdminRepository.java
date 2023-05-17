package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Admin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {}
