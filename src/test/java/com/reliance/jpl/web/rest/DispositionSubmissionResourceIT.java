package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.DispositionSubmission;
import com.reliance.jpl.repository.DispositionSubmissionRepository;
import com.reliance.jpl.service.dto.DispositionSubmissionDTO;
import com.reliance.jpl.service.mapper.DispositionSubmissionMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DispositionSubmissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositionSubmissionResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/disposition-submissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DispositionSubmissionRepository dispositionSubmissionRepository;

    @Autowired
    private DispositionSubmissionMapper dispositionSubmissionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositionSubmissionMockMvc;

    private DispositionSubmission dispositionSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositionSubmission createEntity(EntityManager em) {
        DispositionSubmission dispositionSubmission = new DispositionSubmission()
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return dispositionSubmission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositionSubmission createUpdatedEntity(EntityManager em) {
        DispositionSubmission dispositionSubmission = new DispositionSubmission()
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return dispositionSubmission;
    }

    @BeforeEach
    public void initTest() {
        dispositionSubmission = createEntity(em);
    }

    @Test
    @Transactional
    void createDispositionSubmission() throws Exception {
        int databaseSizeBeforeCreate = dispositionSubmissionRepository.findAll().size();
        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);
        restDispositionSubmissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        DispositionSubmission testDispositionSubmission = dispositionSubmissionList.get(dispositionSubmissionList.size() - 1);
        assertThat(testDispositionSubmission.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDispositionSubmission.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDispositionSubmission.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDispositionSubmission.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createDispositionSubmissionWithExistingId() throws Exception {
        // Create the DispositionSubmission with an existing ID
        dispositionSubmission.setId(1L);
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        int databaseSizeBeforeCreate = dispositionSubmissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositionSubmissionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDispositionSubmissions() throws Exception {
        // Initialize the database
        dispositionSubmissionRepository.saveAndFlush(dispositionSubmission);

        // Get all the dispositionSubmissionList
        restDispositionSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositionSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getDispositionSubmission() throws Exception {
        // Initialize the database
        dispositionSubmissionRepository.saveAndFlush(dispositionSubmission);

        // Get the dispositionSubmission
        restDispositionSubmissionMockMvc
            .perform(get(ENTITY_API_URL_ID, dispositionSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispositionSubmission.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDispositionSubmission() throws Exception {
        // Get the dispositionSubmission
        restDispositionSubmissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDispositionSubmission() throws Exception {
        // Initialize the database
        dispositionSubmissionRepository.saveAndFlush(dispositionSubmission);

        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();

        // Update the dispositionSubmission
        DispositionSubmission updatedDispositionSubmission = dispositionSubmissionRepository.findById(dispositionSubmission.getId()).get();
        // Disconnect from session so that the updates on updatedDispositionSubmission are not directly saved in db
        em.detach(updatedDispositionSubmission);
        updatedDispositionSubmission
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(updatedDispositionSubmission);

        restDispositionSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositionSubmissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isOk());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
        DispositionSubmission testDispositionSubmission = dispositionSubmissionList.get(dispositionSubmissionList.size() - 1);
        assertThat(testDispositionSubmission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDispositionSubmission.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDispositionSubmission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDispositionSubmission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingDispositionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();
        dispositionSubmission.setId(count.incrementAndGet());

        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositionSubmissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDispositionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();
        dispositionSubmission.setId(count.incrementAndGet());

        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDispositionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();
        dispositionSubmission.setId(count.incrementAndGet());

        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositionSubmissionWithPatch() throws Exception {
        // Initialize the database
        dispositionSubmissionRepository.saveAndFlush(dispositionSubmission);

        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();

        // Update the dispositionSubmission using partial update
        DispositionSubmission partialUpdatedDispositionSubmission = new DispositionSubmission();
        partialUpdatedDispositionSubmission.setId(dispositionSubmission.getId());

        partialUpdatedDispositionSubmission.updatedBy(UPDATED_UPDATED_BY).updatedAt(UPDATED_UPDATED_AT);

        restDispositionSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositionSubmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDispositionSubmission))
            )
            .andExpect(status().isOk());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
        DispositionSubmission testDispositionSubmission = dispositionSubmissionList.get(dispositionSubmissionList.size() - 1);
        assertThat(testDispositionSubmission.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDispositionSubmission.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDispositionSubmission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDispositionSubmission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateDispositionSubmissionWithPatch() throws Exception {
        // Initialize the database
        dispositionSubmissionRepository.saveAndFlush(dispositionSubmission);

        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();

        // Update the dispositionSubmission using partial update
        DispositionSubmission partialUpdatedDispositionSubmission = new DispositionSubmission();
        partialUpdatedDispositionSubmission.setId(dispositionSubmission.getId());

        partialUpdatedDispositionSubmission
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restDispositionSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositionSubmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDispositionSubmission))
            )
            .andExpect(status().isOk());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
        DispositionSubmission testDispositionSubmission = dispositionSubmissionList.get(dispositionSubmissionList.size() - 1);
        assertThat(testDispositionSubmission.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDispositionSubmission.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDispositionSubmission.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDispositionSubmission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingDispositionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();
        dispositionSubmission.setId(count.incrementAndGet());

        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositionSubmissionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDispositionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();
        dispositionSubmission.setId(count.incrementAndGet());

        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDispositionSubmission() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionRepository.findAll().size();
        dispositionSubmission.setId(count.incrementAndGet());

        // Create the DispositionSubmission
        DispositionSubmissionDTO dispositionSubmissionDTO = dispositionSubmissionMapper.toDto(dispositionSubmission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositionSubmission in the database
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDispositionSubmission() throws Exception {
        // Initialize the database
        dispositionSubmissionRepository.saveAndFlush(dispositionSubmission);

        int databaseSizeBeforeDelete = dispositionSubmissionRepository.findAll().size();

        // Delete the dispositionSubmission
        restDispositionSubmissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, dispositionSubmission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DispositionSubmission> dispositionSubmissionList = dispositionSubmissionRepository.findAll();
        assertThat(dispositionSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
