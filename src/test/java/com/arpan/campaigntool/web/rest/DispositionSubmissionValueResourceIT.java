package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.DispositionSubmissionValue;
import com.arpan.campaigntool.repository.DispositionSubmissionValueRepository;
import com.arpan.campaigntool.service.dto.DispositionSubmissionValueDTO;
import com.arpan.campaigntool.service.mapper.DispositionSubmissionValueMapper;
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
 * Integration tests for the {@link DispositionSubmissionValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositionSubmissionValueResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/disposition-submission-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DispositionSubmissionValueRepository dispositionSubmissionValueRepository;

    @Autowired
    private DispositionSubmissionValueMapper dispositionSubmissionValueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositionSubmissionValueMockMvc;

    private DispositionSubmissionValue dispositionSubmissionValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositionSubmissionValue createEntity(EntityManager em) {
        DispositionSubmissionValue dispositionSubmissionValue = new DispositionSubmissionValue()
            .value(DEFAULT_VALUE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return dispositionSubmissionValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DispositionSubmissionValue createUpdatedEntity(EntityManager em) {
        DispositionSubmissionValue dispositionSubmissionValue = new DispositionSubmissionValue()
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return dispositionSubmissionValue;
    }

    @BeforeEach
    public void initTest() {
        dispositionSubmissionValue = createEntity(em);
    }

    @Test
    @Transactional
    void createDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeCreate = dispositionSubmissionValueRepository.findAll().size();
        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);
        restDispositionSubmissionValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeCreate + 1);
        DispositionSubmissionValue testDispositionSubmissionValue = dispositionSubmissionValueList.get(
            dispositionSubmissionValueList.size() - 1
        );
        assertThat(testDispositionSubmissionValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDispositionSubmissionValue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDispositionSubmissionValue.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDispositionSubmissionValue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDispositionSubmissionValue.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createDispositionSubmissionValueWithExistingId() throws Exception {
        // Create the DispositionSubmissionValue with an existing ID
        dispositionSubmissionValue.setId(1L);
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        int databaseSizeBeforeCreate = dispositionSubmissionValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositionSubmissionValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDispositionSubmissionValues() throws Exception {
        // Initialize the database
        dispositionSubmissionValueRepository.saveAndFlush(dispositionSubmissionValue);

        // Get all the dispositionSubmissionValueList
        restDispositionSubmissionValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dispositionSubmissionValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getDispositionSubmissionValue() throws Exception {
        // Initialize the database
        dispositionSubmissionValueRepository.saveAndFlush(dispositionSubmissionValue);

        // Get the dispositionSubmissionValue
        restDispositionSubmissionValueMockMvc
            .perform(get(ENTITY_API_URL_ID, dispositionSubmissionValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dispositionSubmissionValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDispositionSubmissionValue() throws Exception {
        // Get the dispositionSubmissionValue
        restDispositionSubmissionValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDispositionSubmissionValue() throws Exception {
        // Initialize the database
        dispositionSubmissionValueRepository.saveAndFlush(dispositionSubmissionValue);

        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();

        // Update the dispositionSubmissionValue
        DispositionSubmissionValue updatedDispositionSubmissionValue = dispositionSubmissionValueRepository
            .findById(dispositionSubmissionValue.getId())
            .get();
        // Disconnect from session so that the updates on updatedDispositionSubmissionValue are not directly saved in db
        em.detach(updatedDispositionSubmissionValue);
        updatedDispositionSubmissionValue
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(
            updatedDispositionSubmissionValue
        );

        restDispositionSubmissionValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositionSubmissionValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
        DispositionSubmissionValue testDispositionSubmissionValue = dispositionSubmissionValueList.get(
            dispositionSubmissionValueList.size() - 1
        );
        assertThat(testDispositionSubmissionValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDispositionSubmissionValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDispositionSubmissionValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDispositionSubmissionValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDispositionSubmissionValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();
        dispositionSubmissionValue.setId(count.incrementAndGet());

        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionSubmissionValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositionSubmissionValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();
        dispositionSubmissionValue.setId(count.incrementAndGet());

        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();
        dispositionSubmissionValue.setId(count.incrementAndGet());

        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionValueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositionSubmissionValueWithPatch() throws Exception {
        // Initialize the database
        dispositionSubmissionValueRepository.saveAndFlush(dispositionSubmissionValue);

        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();

        // Update the dispositionSubmissionValue using partial update
        DispositionSubmissionValue partialUpdatedDispositionSubmissionValue = new DispositionSubmissionValue();
        partialUpdatedDispositionSubmissionValue.setId(dispositionSubmissionValue.getId());

        partialUpdatedDispositionSubmissionValue.createdBy(UPDATED_CREATED_BY).createdAt(UPDATED_CREATED_AT).updatedBy(UPDATED_UPDATED_BY);

        restDispositionSubmissionValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositionSubmissionValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDispositionSubmissionValue))
            )
            .andExpect(status().isOk());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
        DispositionSubmissionValue testDispositionSubmissionValue = dispositionSubmissionValueList.get(
            dispositionSubmissionValueList.size() - 1
        );
        assertThat(testDispositionSubmissionValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDispositionSubmissionValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDispositionSubmissionValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDispositionSubmissionValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDispositionSubmissionValue.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateDispositionSubmissionValueWithPatch() throws Exception {
        // Initialize the database
        dispositionSubmissionValueRepository.saveAndFlush(dispositionSubmissionValue);

        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();

        // Update the dispositionSubmissionValue using partial update
        DispositionSubmissionValue partialUpdatedDispositionSubmissionValue = new DispositionSubmissionValue();
        partialUpdatedDispositionSubmissionValue.setId(dispositionSubmissionValue.getId());

        partialUpdatedDispositionSubmissionValue
            .value(UPDATED_VALUE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restDispositionSubmissionValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDispositionSubmissionValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDispositionSubmissionValue))
            )
            .andExpect(status().isOk());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
        DispositionSubmissionValue testDispositionSubmissionValue = dispositionSubmissionValueList.get(
            dispositionSubmissionValueList.size() - 1
        );
        assertThat(testDispositionSubmissionValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDispositionSubmissionValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDispositionSubmissionValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDispositionSubmissionValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDispositionSubmissionValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();
        dispositionSubmissionValue.setId(count.incrementAndGet());

        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionSubmissionValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositionSubmissionValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();
        dispositionSubmissionValue.setId(count.incrementAndGet());

        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDispositionSubmissionValue() throws Exception {
        int databaseSizeBeforeUpdate = dispositionSubmissionValueRepository.findAll().size();
        dispositionSubmissionValue.setId(count.incrementAndGet());

        // Create the DispositionSubmissionValue
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO = dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionSubmissionValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionSubmissionValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DispositionSubmissionValue in the database
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDispositionSubmissionValue() throws Exception {
        // Initialize the database
        dispositionSubmissionValueRepository.saveAndFlush(dispositionSubmissionValue);

        int databaseSizeBeforeDelete = dispositionSubmissionValueRepository.findAll().size();

        // Delete the dispositionSubmissionValue
        restDispositionSubmissionValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, dispositionSubmissionValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DispositionSubmissionValue> dispositionSubmissionValueList = dispositionSubmissionValueRepository.findAll();
        assertThat(dispositionSubmissionValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
