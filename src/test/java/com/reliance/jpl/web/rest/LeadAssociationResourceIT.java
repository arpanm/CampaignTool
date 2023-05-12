package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.LeadAssociation;
import com.reliance.jpl.repository.LeadAssociationRepository;
import com.reliance.jpl.service.dto.LeadAssociationDTO;
import com.reliance.jpl.service.mapper.LeadAssociationMapper;
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
 * Integration tests for the {@link LeadAssociationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeadAssociationResourceIT {

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

    private static final String ENTITY_API_URL = "/api/lead-associations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeadAssociationRepository leadAssociationRepository;

    @Autowired
    private LeadAssociationMapper leadAssociationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeadAssociationMockMvc;

    private LeadAssociation leadAssociation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadAssociation createEntity(EntityManager em) {
        LeadAssociation leadAssociation = new LeadAssociation()
            .assignmentDate(DEFAULT_ASSIGNMENT_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return leadAssociation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadAssociation createUpdatedEntity(EntityManager em) {
        LeadAssociation leadAssociation = new LeadAssociation()
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return leadAssociation;
    }

    @BeforeEach
    public void initTest() {
        leadAssociation = createEntity(em);
    }

    @Test
    @Transactional
    void createLeadAssociation() throws Exception {
        int databaseSizeBeforeCreate = leadAssociationRepository.findAll().size();
        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);
        restLeadAssociationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeCreate + 1);
        LeadAssociation testLeadAssociation = leadAssociationList.get(leadAssociationList.size() - 1);
        assertThat(testLeadAssociation.getAssignmentDate()).isEqualTo(DEFAULT_ASSIGNMENT_DATE);
        assertThat(testLeadAssociation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeadAssociation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeadAssociation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLeadAssociation.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createLeadAssociationWithExistingId() throws Exception {
        // Create the LeadAssociation with an existing ID
        leadAssociation.setId(1L);
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        int databaseSizeBeforeCreate = leadAssociationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeadAssociationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeadAssociations() throws Exception {
        // Initialize the database
        leadAssociationRepository.saveAndFlush(leadAssociation);

        // Get all the leadAssociationList
        restLeadAssociationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leadAssociation.getId().intValue())))
            .andExpect(jsonPath("$.[*].assignmentDate").value(hasItem(DEFAULT_ASSIGNMENT_DATE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getLeadAssociation() throws Exception {
        // Initialize the database
        leadAssociationRepository.saveAndFlush(leadAssociation);

        // Get the leadAssociation
        restLeadAssociationMockMvc
            .perform(get(ENTITY_API_URL_ID, leadAssociation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leadAssociation.getId().intValue()))
            .andExpect(jsonPath("$.assignmentDate").value(DEFAULT_ASSIGNMENT_DATE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLeadAssociation() throws Exception {
        // Get the leadAssociation
        restLeadAssociationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeadAssociation() throws Exception {
        // Initialize the database
        leadAssociationRepository.saveAndFlush(leadAssociation);

        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();

        // Update the leadAssociation
        LeadAssociation updatedLeadAssociation = leadAssociationRepository.findById(leadAssociation.getId()).get();
        // Disconnect from session so that the updates on updatedLeadAssociation are not directly saved in db
        em.detach(updatedLeadAssociation);
        updatedLeadAssociation
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(updatedLeadAssociation);

        restLeadAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leadAssociationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
        LeadAssociation testLeadAssociation = leadAssociationList.get(leadAssociationList.size() - 1);
        assertThat(testLeadAssociation.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testLeadAssociation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadAssociation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadAssociation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadAssociation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingLeadAssociation() throws Exception {
        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();
        leadAssociation.setId(count.incrementAndGet());

        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leadAssociationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeadAssociation() throws Exception {
        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();
        leadAssociation.setId(count.incrementAndGet());

        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssociationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeadAssociation() throws Exception {
        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();
        leadAssociation.setId(count.incrementAndGet());

        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssociationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeadAssociationWithPatch() throws Exception {
        // Initialize the database
        leadAssociationRepository.saveAndFlush(leadAssociation);

        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();

        // Update the leadAssociation using partial update
        LeadAssociation partialUpdatedLeadAssociation = new LeadAssociation();
        partialUpdatedLeadAssociation.setId(leadAssociation.getId());

        partialUpdatedLeadAssociation.updatedAt(UPDATED_UPDATED_AT);

        restLeadAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeadAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeadAssociation))
            )
            .andExpect(status().isOk());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
        LeadAssociation testLeadAssociation = leadAssociationList.get(leadAssociationList.size() - 1);
        assertThat(testLeadAssociation.getAssignmentDate()).isEqualTo(DEFAULT_ASSIGNMENT_DATE);
        assertThat(testLeadAssociation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeadAssociation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeadAssociation.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLeadAssociation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateLeadAssociationWithPatch() throws Exception {
        // Initialize the database
        leadAssociationRepository.saveAndFlush(leadAssociation);

        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();

        // Update the leadAssociation using partial update
        LeadAssociation partialUpdatedLeadAssociation = new LeadAssociation();
        partialUpdatedLeadAssociation.setId(leadAssociation.getId());

        partialUpdatedLeadAssociation
            .assignmentDate(UPDATED_ASSIGNMENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restLeadAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeadAssociation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeadAssociation))
            )
            .andExpect(status().isOk());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
        LeadAssociation testLeadAssociation = leadAssociationList.get(leadAssociationList.size() - 1);
        assertThat(testLeadAssociation.getAssignmentDate()).isEqualTo(UPDATED_ASSIGNMENT_DATE);
        assertThat(testLeadAssociation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadAssociation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadAssociation.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadAssociation.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingLeadAssociation() throws Exception {
        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();
        leadAssociation.setId(count.incrementAndGet());

        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leadAssociationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeadAssociation() throws Exception {
        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();
        leadAssociation.setId(count.incrementAndGet());

        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeadAssociation() throws Exception {
        int databaseSizeBeforeUpdate = leadAssociationRepository.findAll().size();
        leadAssociation.setId(count.incrementAndGet());

        // Create the LeadAssociation
        LeadAssociationDTO leadAssociationDTO = leadAssociationMapper.toDto(leadAssociation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadAssociationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadAssociationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeadAssociation in the database
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeadAssociation() throws Exception {
        // Initialize the database
        leadAssociationRepository.saveAndFlush(leadAssociation);

        int databaseSizeBeforeDelete = leadAssociationRepository.findAll().size();

        // Delete the leadAssociation
        restLeadAssociationMockMvc
            .perform(delete(ENTITY_API_URL_ID, leadAssociation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeadAssociation> leadAssociationList = leadAssociationRepository.findAll();
        assertThat(leadAssociationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
