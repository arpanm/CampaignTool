package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.TelecallerAssignment;
import com.reliance.jpl.repository.TelecallerAssignmentRepository;
import com.reliance.jpl.service.dto.TelecallerAssignmentDTO;
import com.reliance.jpl.service.mapper.TelecallerAssignmentMapper;
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
 * Integration tests for the {@link TelecallerAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelecallerAssignmentResourceIT {

    private static final String DEFAULT_ASSIGNMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNMENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/telecaller-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelecallerAssignmentRepository telecallerAssignmentRepository;

    @Autowired
    private TelecallerAssignmentMapper telecallerAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelecallerAssignmentMockMvc;

    private TelecallerAssignment telecallerAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelecallerAssignment createEntity(EntityManager em) {
        TelecallerAssignment telecallerAssignment = new TelecallerAssignment()
            .assignmentDate(DEFAULT_ASSIGNMENT_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return telecallerAssignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelecallerAssignment createUpdatedEntity(EntityManager em) {
        TelecallerAssignment telecallerAssignment = new TelecallerAssignment()
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return telecallerAssignment;
    }

    @BeforeEach
    public void initTest() {
        telecallerAssignment = createEntity(em);
    }

    @Test
    @Transactional
    void createTelecallerAssignment() throws Exception {
        int databaseSizeBeforeCreate = telecallerAssignmentRepository.findAll().size();
        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);
        restTelecallerAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeCreate + 1);
        TelecallerAssignment testTelecallerAssignment = telecallerAssignmentList.get(telecallerAssignmentList.size() - 1);
        assertThat(testTelecallerAssignment.getAssignmentDate()).isEqualTo(DEFAULT_ASSIGNMENT_DATE);
        assertThat(testTelecallerAssignment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTelecallerAssignment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTelecallerAssignment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTelecallerAssignment.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createTelecallerAssignmentWithExistingId() throws Exception {
        // Create the TelecallerAssignment with an existing ID
        telecallerAssignment.setId(1L);
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        int databaseSizeBeforeCreate = telecallerAssignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelecallerAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelecallerAssignments() throws Exception {
        // Initialize the database
        telecallerAssignmentRepository.saveAndFlush(telecallerAssignment);

        // Get all the telecallerAssignmentList
        restTelecallerAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telecallerAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignmentDate").value(hasItem(DEFAULT_ASSIGNMENT_DATE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTelecallerAssignment() throws Exception {
        // Initialize the database
        telecallerAssignmentRepository.saveAndFlush(telecallerAssignment);

        // Get the telecallerAssignment
        restTelecallerAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, telecallerAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telecallerAssignment.getId().intValue()))
            .andExpect(jsonPath("$.assignmentDate").value(DEFAULT_ASSIGNMENT_DATE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTelecallerAssignment() throws Exception {
        // Get the telecallerAssignment
        restTelecallerAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelecallerAssignment() throws Exception {
        // Initialize the database
        telecallerAssignmentRepository.saveAndFlush(telecallerAssignment);

        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();

        // Update the telecallerAssignment
        TelecallerAssignment updatedTelecallerAssignment = telecallerAssignmentRepository.findById(telecallerAssignment.getId()).get();
        // Disconnect from session so that the updates on updatedTelecallerAssignment are not directly saved in db
        em.detach(updatedTelecallerAssignment);
        updatedTelecallerAssignment
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(updatedTelecallerAssignment);

        restTelecallerAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telecallerAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
        TelecallerAssignment testTelecallerAssignment = telecallerAssignmentList.get(telecallerAssignmentList.size() - 1);
        assertThat(testTelecallerAssignment.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testTelecallerAssignment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecallerAssignment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecallerAssignment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecallerAssignment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTelecallerAssignment() throws Exception {
        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();
        telecallerAssignment.setId(count.incrementAndGet());

        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelecallerAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telecallerAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelecallerAssignment() throws Exception {
        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();
        telecallerAssignment.setId(count.incrementAndGet());

        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelecallerAssignment() throws Exception {
        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();
        telecallerAssignment.setId(count.incrementAndGet());

        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelecallerAssignmentWithPatch() throws Exception {
        // Initialize the database
        telecallerAssignmentRepository.saveAndFlush(telecallerAssignment);

        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();

        // Update the telecallerAssignment using partial update
        TelecallerAssignment partialUpdatedTelecallerAssignment = new TelecallerAssignment();
        partialUpdatedTelecallerAssignment.setId(telecallerAssignment.getId());

        partialUpdatedTelecallerAssignment
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restTelecallerAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelecallerAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelecallerAssignment))
            )
            .andExpect(status().isOk());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
        TelecallerAssignment testTelecallerAssignment = telecallerAssignmentList.get(telecallerAssignmentList.size() - 1);
        assertThat(testTelecallerAssignment.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testTelecallerAssignment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecallerAssignment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecallerAssignment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecallerAssignment.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTelecallerAssignmentWithPatch() throws Exception {
        // Initialize the database
        telecallerAssignmentRepository.saveAndFlush(telecallerAssignment);

        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();

        // Update the telecallerAssignment using partial update
        TelecallerAssignment partialUpdatedTelecallerAssignment = new TelecallerAssignment();
        partialUpdatedTelecallerAssignment.setId(telecallerAssignment.getId());

        partialUpdatedTelecallerAssignment
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restTelecallerAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelecallerAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelecallerAssignment))
            )
            .andExpect(status().isOk());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
        TelecallerAssignment testTelecallerAssignment = telecallerAssignmentList.get(telecallerAssignmentList.size() - 1);
        assertThat(testTelecallerAssignment.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testTelecallerAssignment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecallerAssignment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecallerAssignment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecallerAssignment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTelecallerAssignment() throws Exception {
        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();
        telecallerAssignment.setId(count.incrementAndGet());

        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelecallerAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telecallerAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelecallerAssignment() throws Exception {
        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();
        telecallerAssignment.setId(count.incrementAndGet());

        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelecallerAssignment() throws Exception {
        int databaseSizeBeforeUpdate = telecallerAssignmentRepository.findAll().size();
        telecallerAssignment.setId(count.incrementAndGet());

        // Create the TelecallerAssignment
        TelecallerAssignmentDTO telecallerAssignmentDTO = telecallerAssignmentMapper.toDto(telecallerAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelecallerAssignment in the database
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelecallerAssignment() throws Exception {
        // Initialize the database
        telecallerAssignmentRepository.saveAndFlush(telecallerAssignment);

        int databaseSizeBeforeDelete = telecallerAssignmentRepository.findAll().size();

        // Delete the telecallerAssignment
        restTelecallerAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, telecallerAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TelecallerAssignment> telecallerAssignmentList = telecallerAssignmentRepository.findAll();
        assertThat(telecallerAssignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
