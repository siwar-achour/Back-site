package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Chauffeur;
import com.mycompany.myapp.repository.ChauffeurRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ChauffeurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChauffeurResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chauffeurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ChauffeurRepository chauffeurRepository;

    @Autowired
    private MockMvc restChauffeurMockMvc;

    private Chauffeur chauffeur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chauffeur createEntity() {
        Chauffeur chauffeur = new Chauffeur()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return chauffeur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chauffeur createUpdatedEntity() {
        Chauffeur chauffeur = new Chauffeur()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return chauffeur;
    }

    @BeforeEach
    public void initTest() {
        chauffeurRepository.deleteAll();
        chauffeur = createEntity();
    }

    @Test
    void createChauffeur() throws Exception {
        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();
        // Create the Chauffeur
        restChauffeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isCreated());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeCreate + 1);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testChauffeur.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testChauffeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testChauffeur.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    void createChauffeurWithExistingId() throws Exception {
        // Create the Chauffeur with an existing ID
        chauffeur.setId("existing_id");

        int databaseSizeBeforeCreate = chauffeurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChauffeurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllChauffeurs() throws Exception {
        // Initialize the database
        chauffeurRepository.save(chauffeur);

        // Get all the chauffeurList
        restChauffeurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chauffeur.getId())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }

    @Test
    void getChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.save(chauffeur);

        // Get the chauffeur
        restChauffeurMockMvc
            .perform(get(ENTITY_API_URL_ID, chauffeur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chauffeur.getId()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    void getNonExistingChauffeur() throws Exception {
        // Get the chauffeur
        restChauffeurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.save(chauffeur);

        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Update the chauffeur
        Chauffeur updatedChauffeur = chauffeurRepository.findById(chauffeur.getId()).get();
        updatedChauffeur.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL).phoneNumber(UPDATED_PHONE_NUMBER);

        restChauffeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChauffeur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChauffeur))
            )
            .andExpect(status().isOk());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testChauffeur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testChauffeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testChauffeur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void putNonExistingChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();
        chauffeur.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChauffeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chauffeur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chauffeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();
        chauffeur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChauffeurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chauffeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();
        chauffeur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChauffeurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chauffeur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChauffeurWithPatch() throws Exception {
        // Initialize the database
        chauffeurRepository.save(chauffeur);

        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Update the chauffeur using partial update
        Chauffeur partialUpdatedChauffeur = new Chauffeur();
        partialUpdatedChauffeur.setId(chauffeur.getId());

        partialUpdatedChauffeur.lastName(UPDATED_LAST_NAME).phoneNumber(UPDATED_PHONE_NUMBER);

        restChauffeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChauffeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChauffeur))
            )
            .andExpect(status().isOk());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testChauffeur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testChauffeur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testChauffeur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void fullUpdateChauffeurWithPatch() throws Exception {
        // Initialize the database
        chauffeurRepository.save(chauffeur);

        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();

        // Update the chauffeur using partial update
        Chauffeur partialUpdatedChauffeur = new Chauffeur();
        partialUpdatedChauffeur.setId(chauffeur.getId());

        partialUpdatedChauffeur
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restChauffeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChauffeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChauffeur))
            )
            .andExpect(status().isOk());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
        Chauffeur testChauffeur = chauffeurList.get(chauffeurList.size() - 1);
        assertThat(testChauffeur.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testChauffeur.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testChauffeur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testChauffeur.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    void patchNonExistingChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();
        chauffeur.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChauffeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chauffeur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chauffeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();
        chauffeur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChauffeurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chauffeur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChauffeur() throws Exception {
        int databaseSizeBeforeUpdate = chauffeurRepository.findAll().size();
        chauffeur.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChauffeurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chauffeur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Chauffeur in the database
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChauffeur() throws Exception {
        // Initialize the database
        chauffeurRepository.save(chauffeur);

        int databaseSizeBeforeDelete = chauffeurRepository.findAll().size();

        // Delete the chauffeur
        restChauffeurMockMvc
            .perform(delete(ENTITY_API_URL_ID, chauffeur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chauffeur> chauffeurList = chauffeurRepository.findAll();
        assertThat(chauffeurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
