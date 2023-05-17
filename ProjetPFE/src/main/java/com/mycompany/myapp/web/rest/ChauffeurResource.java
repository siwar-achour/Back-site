package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Chauffeur;
import com.mycompany.myapp.repository.ChauffeurRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Chauffeur}.
 */
@RestController
@RequestMapping("/api")
public class ChauffeurResource {

    private final Logger log = LoggerFactory.getLogger(ChauffeurResource.class);

    private static final String ENTITY_NAME = "chauffeur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChauffeurRepository chauffeurRepository;

    public ChauffeurResource(ChauffeurRepository chauffeurRepository) {
        this.chauffeurRepository = chauffeurRepository;
    }

    /**
     * {@code POST  /chauffeurs} : Create a new chauffeur.
     *
     * @param chauffeur the chauffeur to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chauffeur, or with status {@code 400 (Bad Request)} if the chauffeur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chauffeurs")
    public ResponseEntity<Chauffeur> createChauffeur(@RequestBody Chauffeur chauffeur) throws URISyntaxException {
        log.debug("REST request to save Chauffeur : {}", chauffeur);
        if (chauffeur.getId() != null) {
            throw new BadRequestAlertException("A new chauffeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chauffeur result = chauffeurRepository.save(chauffeur);
        return ResponseEntity
            .created(new URI("/api/chauffeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /chauffeurs/:id} : Updates an existing chauffeur.
     *
     * @param id the id of the chauffeur to save.
     * @param chauffeur the chauffeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chauffeur,
     * or with status {@code 400 (Bad Request)} if the chauffeur is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chauffeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chauffeurs/{id}")
    public ResponseEntity<Chauffeur> updateChauffeur(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Chauffeur chauffeur
    ) throws URISyntaxException {
        log.debug("REST request to update Chauffeur : {}, {}", id, chauffeur);
        if (chauffeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chauffeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chauffeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Chauffeur result = chauffeurRepository.save(chauffeur);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chauffeur.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /chauffeurs/:id} : Partial updates given fields of an existing chauffeur, field will ignore if it is null
     *
     * @param id the id of the chauffeur to save.
     * @param chauffeur the chauffeur to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chauffeur,
     * or with status {@code 400 (Bad Request)} if the chauffeur is not valid,
     * or with status {@code 404 (Not Found)} if the chauffeur is not found,
     * or with status {@code 500 (Internal Server Error)} if the chauffeur couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chauffeurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Chauffeur> partialUpdateChauffeur(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Chauffeur chauffeur
    ) throws URISyntaxException {
        log.debug("REST request to partial update Chauffeur partially : {}, {}", id, chauffeur);
        if (chauffeur.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chauffeur.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chauffeurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Chauffeur> result = chauffeurRepository
            .findById(chauffeur.getId())
            .map(existingChauffeur -> {
                if (chauffeur.getFirstName() != null) {
                    existingChauffeur.setFirstName(chauffeur.getFirstName());
                }
                if (chauffeur.getLastName() != null) {
                    existingChauffeur.setLastName(chauffeur.getLastName());
                }
                if (chauffeur.getEmail() != null) {
                    existingChauffeur.setEmail(chauffeur.getEmail());
                }
                if (chauffeur.getPhoneNumber() != null) {
                    existingChauffeur.setPhoneNumber(chauffeur.getPhoneNumber());
                }

                return existingChauffeur;
            })
            .map(chauffeurRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chauffeur.getId())
        );
    }

    /**
     * {@code GET  /chauffeurs} : get all the chauffeurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chauffeurs in body.
     */
    @GetMapping("/chauffeurs")
    public List<Chauffeur> getAllChauffeurs() {
        log.debug("REST request to get all Chauffeurs");
        return chauffeurRepository.findAll();
    }

    /**
     * {@code GET  /chauffeurs/:id} : get the "id" chauffeur.
     *
     * @param id the id of the chauffeur to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chauffeur, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chauffeurs/{id}")
    public ResponseEntity<Chauffeur> getChauffeur(@PathVariable String id) {
        log.debug("REST request to get Chauffeur : {}", id);
        Optional<Chauffeur> chauffeur = chauffeurRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chauffeur);
    }

    /**
     * {@code DELETE  /chauffeurs/:id} : delete the "id" chauffeur.
     *
     * @param id the id of the chauffeur to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chauffeurs/{id}")
    public ResponseEntity<Void> deleteChauffeur(@PathVariable String id) {
        log.debug("REST request to delete Chauffeur : {}", id);
        chauffeurRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
