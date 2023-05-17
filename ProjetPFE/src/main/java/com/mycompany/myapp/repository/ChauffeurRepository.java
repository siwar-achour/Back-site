package com.mycompany.myapp.repository;
import com.mycompany.myapp.service.EmailAlreadyUsedException;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.domain.Chauffeur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Chauffeur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChauffeurRepository extends MongoRepository<Chauffeur, String> {
    
}


