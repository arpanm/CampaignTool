package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.LeadAssignment;
import com.arpan.campaigntool.repository.LeadAssignmentRepository;
import com.arpan.campaigntool.service.dto.LeadAssignmentDTO;
import com.arpan.campaigntool.service.mapper.LeadAssignmentMapper;
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
 * Integration tests for the {@link LeadAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeadAssignmentResourceIT {

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

    private static final String ENTITY_API_URL = "/api/lead-assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeadAssignmentRepository leadAssignmentRepository;

    @Autowired
    private LeadAssignmentMapper leadAssignmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeadAssignmentMockMvc;

    private LeadAssignment leadAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadAssignment createEntity(EntityManager em) {
        LeadAssignment leadAssignment = new LeadAssignment()
            .assignmentDate(DEFAULT_ASSIGNMENT_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return leadAssignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadAssignment createUpdatedEntity(EntityManager em) {
        LeadAssignment leadAssignment = new LeadAssignment()
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return leadAssignment;
    }

    @BeforeEach
    public void initTest() {
        leadAssignment = createEntity(em);
    }

    @Test
    @Transactional
    void createLeadAssignment() throws Exception {
        int databaseSizeBeforeCreate = leadAssignmentRepository.findAll().size();
        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);
        restLeadAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeCreate + 1);
        LeadAssignment testLeadAssignment = leadAssignmentList.get(leadAssignmentList.size() - 1);
        assertThat(testLeadAssignment.getAssignmentDate()).isEqualTo(DEFAULT_ASSIGNMENT_DATE);
        assertThat(testLeadAssignment.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeadAssignment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeadAssignment.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLeadAssignment.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createLeadAssignmentWithExistingId() throws Exception {
        // Create the LeadAssignment with an existing ID
        leadAssignment.setId(1L);
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        int databaseSizeBeforeCreate = leadAssignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeadAssignmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeadAssignments() throws Exception {
        // Initialize the database
        leadAssignmentRepository.saveAndFlush(leadAssignment);

        // Get all the leadAssignmentList
        restLeadAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leadAssignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignmentDate").value(hasItem(DEFAULT_ASSIGNMENT_DATE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getLeadAssignment() throws Exception {
        // Initialize the database
        leadAssignmentRepository.saveAndFlush(leadAssignment);

        // Get the leadAssignment
        restLeadAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, leadAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leadAssignment.getId().intValue()))
            .andExpect(jsonPath("$.assignmentDate").value(DEFAULT_ASSIGNMENT_DATE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLeadAssignment() throws Exception {
        // Get the leadAssignment
        restLeadAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLeadAssignment() throws Exception {
        // Initialize the database
        leadAssignmentRepository.saveAndFlush(leadAssignment);

        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();

        // Update the leadAssignment
        LeadAssignment updatedLeadAssignment = leadAssignmentRepository.findById(leadAssignment.getId()).get();
        // Disconnect from session so that the updates on updatedLeadAssignment are not directly saved in db
        em.detach(updatedLeadAssignment);
        updatedLeadAssignment
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(updatedLeadAssignment);

        restLeadAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leadAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
        LeadAssignment testLeadAssignment = leadAssignmentList.get(leadAssignmentList.size() - 1);
        assertThat(testLeadAssignment.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testLeadAssignment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadAssignment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadAssignment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadAssignment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingLeadAssignment() throws Exception {
        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();
        leadAssignment.setId(count.incrementAndGet());

        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leadAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeadAssignment() throws Exception {
        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();
        leadAssignment.setId(count.incrementAndGet());

        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeadAssignment() throws Exception {
        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();
        leadAssignment.setId(count.incrementAndGet());

        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeadAssignmentWithPatch() throws Exception {
        // Initialize the database
        leadAssignmentRepository.saveAndFlush(leadAssignment);

        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();

        // Update the leadAssignment using partial update
        LeadAssignment partialUpdatedLeadAssignment = new LeadAssignment();
        partialUpdatedLeadAssignment.setId(leadAssignment.getId());

        partialUpdatedLeadAssignment
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restLeadAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeadAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeadAssignment))
            )
            .andExpect(status().isOk());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
        LeadAssignment testLeadAssignment = leadAssignmentList.get(leadAssignmentList.size() - 1);
        assertThat(testLeadAssignment.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testLeadAssignment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadAssignment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadAssignment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadAssignment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateLeadAssignmentWithPatch() throws Exception {
        // Initialize the database
        leadAssignmentRepository.saveAndFlush(leadAssignment);

        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();

        // Update the leadAssignment using partial update
        LeadAssignment partialUpdatedLeadAssignment = new LeadAssignment();
        partialUpdatedLeadAssignment.setId(leadAssignment.getId());

        partialUpdatedLeadAssignment
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restLeadAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeadAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeadAssignment))
            )
            .andExpect(status().isOk());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
        LeadAssignment testLeadAssignment = leadAssignmentList.get(leadAssignmentList.size() - 1);
        assertThat(testLeadAssignment.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testLeadAssignment.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadAssignment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadAssignment.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadAssignment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingLeadAssignment() throws Exception {
        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();
        leadAssignment.setId(count.incrementAndGet());

        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leadAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeadAssignment() throws Exception {
        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();
        leadAssignment.setId(count.incrementAndGet());

        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeadAssignment() throws Exception {
        int databaseSizeBeforeUpdate = leadAssignmentRepository.findAll().size();
        leadAssignment.setId(count.incrementAndGet());

        // Create the LeadAssignment
        LeadAssignmentDTO leadAssignmentDTO = leadAssignmentMapper.toDto(leadAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeadAssignment in the database
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeadAssignment() throws Exception {
        // Initialize the database
        leadAssignmentRepository.saveAndFlush(leadAssignment);

        int databaseSizeBeforeDelete = leadAssignmentRepository.findAll().size();

        // Delete the leadAssignment
        restLeadAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, leadAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeadAssignment> leadAssignmentList = leadAssignmentRepository.findAll();
        assertThat(leadAssignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
