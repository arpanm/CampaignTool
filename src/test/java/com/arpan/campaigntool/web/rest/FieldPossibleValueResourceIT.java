package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.FieldPossibleValue;
import com.arpan.campaigntool.repository.FieldPossibleValueRepository;
import com.arpan.campaigntool.service.dto.FieldPossibleValueDTO;
import com.arpan.campaigntool.service.mapper.FieldPossibleValueMapper;
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
 * Integration tests for the {@link FieldPossibleValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldPossibleValueResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/field-possible-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FieldPossibleValueRepository fieldPossibleValueRepository;

    @Autowired
    private FieldPossibleValueMapper fieldPossibleValueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldPossibleValueMockMvc;

    private FieldPossibleValue fieldPossibleValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldPossibleValue createEntity(EntityManager em) {
        FieldPossibleValue fieldPossibleValue = new FieldPossibleValue()
            .value(DEFAULT_VALUE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return fieldPossibleValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldPossibleValue createUpdatedEntity(EntityManager em) {
        FieldPossibleValue fieldPossibleValue = new FieldPossibleValue()
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return fieldPossibleValue;
    }

    @BeforeEach
    public void initTest() {
        fieldPossibleValue = createEntity(em);
    }

    @Test
    @Transactional
    void createFieldPossibleValue() throws Exception {
        int databaseSizeBeforeCreate = fieldPossibleValueRepository.findAll().size();
        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);
        restFieldPossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeCreate + 1);
        FieldPossibleValue testFieldPossibleValue = fieldPossibleValueList.get(fieldPossibleValueList.size() - 1);
        assertThat(testFieldPossibleValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFieldPossibleValue.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFieldPossibleValue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFieldPossibleValue.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFieldPossibleValue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFieldPossibleValue.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createFieldPossibleValueWithExistingId() throws Exception {
        // Create the FieldPossibleValue with an existing ID
        fieldPossibleValue.setId(1L);
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        int databaseSizeBeforeCreate = fieldPossibleValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldPossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFieldPossibleValues() throws Exception {
        // Initialize the database
        fieldPossibleValueRepository.saveAndFlush(fieldPossibleValue);

        // Get all the fieldPossibleValueList
        restFieldPossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldPossibleValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getFieldPossibleValue() throws Exception {
        // Initialize the database
        fieldPossibleValueRepository.saveAndFlush(fieldPossibleValue);

        // Get the fieldPossibleValue
        restFieldPossibleValueMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldPossibleValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldPossibleValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFieldPossibleValue() throws Exception {
        // Get the fieldPossibleValue
        restFieldPossibleValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFieldPossibleValue() throws Exception {
        // Initialize the database
        fieldPossibleValueRepository.saveAndFlush(fieldPossibleValue);

        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();

        // Update the fieldPossibleValue
        FieldPossibleValue updatedFieldPossibleValue = fieldPossibleValueRepository.findById(fieldPossibleValue.getId()).get();
        // Disconnect from session so that the updates on updatedFieldPossibleValue are not directly saved in db
        em.detach(updatedFieldPossibleValue);
        updatedFieldPossibleValue
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(updatedFieldPossibleValue);

        restFieldPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldPossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
        FieldPossibleValue testFieldPossibleValue = fieldPossibleValueList.get(fieldPossibleValueList.size() - 1);
        assertThat(testFieldPossibleValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFieldPossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFieldPossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFieldPossibleValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFieldPossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFieldPossibleValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingFieldPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();
        fieldPossibleValue.setId(count.incrementAndGet());

        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldPossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();
        fieldPossibleValue.setId(count.incrementAndGet());

        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();
        fieldPossibleValue.setId(count.incrementAndGet());

        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldPossibleValueWithPatch() throws Exception {
        // Initialize the database
        fieldPossibleValueRepository.saveAndFlush(fieldPossibleValue);

        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();

        // Update the fieldPossibleValue using partial update
        FieldPossibleValue partialUpdatedFieldPossibleValue = new FieldPossibleValue();
        partialUpdatedFieldPossibleValue.setId(fieldPossibleValue.getId());

        partialUpdatedFieldPossibleValue.createdBy(UPDATED_CREATED_BY).createdAt(UPDATED_CREATED_AT).updatedAt(UPDATED_UPDATED_AT);

        restFieldPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldPossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldPossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
        FieldPossibleValue testFieldPossibleValue = fieldPossibleValueList.get(fieldPossibleValueList.size() - 1);
        assertThat(testFieldPossibleValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testFieldPossibleValue.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testFieldPossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFieldPossibleValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFieldPossibleValue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFieldPossibleValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateFieldPossibleValueWithPatch() throws Exception {
        // Initialize the database
        fieldPossibleValueRepository.saveAndFlush(fieldPossibleValue);

        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();

        // Update the fieldPossibleValue using partial update
        FieldPossibleValue partialUpdatedFieldPossibleValue = new FieldPossibleValue();
        partialUpdatedFieldPossibleValue.setId(fieldPossibleValue.getId());

        partialUpdatedFieldPossibleValue
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restFieldPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldPossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFieldPossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
        FieldPossibleValue testFieldPossibleValue = fieldPossibleValueList.get(fieldPossibleValueList.size() - 1);
        assertThat(testFieldPossibleValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testFieldPossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testFieldPossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFieldPossibleValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFieldPossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFieldPossibleValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFieldPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();
        fieldPossibleValue.setId(count.incrementAndGet());

        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldPossibleValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();
        fieldPossibleValue.setId(count.incrementAndGet());

        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldPossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = fieldPossibleValueRepository.findAll().size();
        fieldPossibleValue.setId(count.incrementAndGet());

        // Create the FieldPossibleValue
        FieldPossibleValueDTO fieldPossibleValueDTO = fieldPossibleValueMapper.toDto(fieldPossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldPossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldPossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldPossibleValue in the database
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldPossibleValue() throws Exception {
        // Initialize the database
        fieldPossibleValueRepository.saveAndFlush(fieldPossibleValue);

        int databaseSizeBeforeDelete = fieldPossibleValueRepository.findAll().size();

        // Delete the fieldPossibleValue
        restFieldPossibleValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldPossibleValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FieldPossibleValue> fieldPossibleValueList = fieldPossibleValueRepository.findAll();
        assertThat(fieldPossibleValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
