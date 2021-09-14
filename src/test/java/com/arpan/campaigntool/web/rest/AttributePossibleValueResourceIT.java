package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.AttributePossibleValue;
import com.arpan.campaigntool.repository.AttributePossibleValueRepository;
import com.arpan.campaigntool.service.dto.AttributePossibleValueDTO;
import com.arpan.campaigntool.service.mapper.AttributePossibleValueMapper;
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
 * Integration tests for the {@link AttributePossibleValueResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributePossibleValueResourceIT {

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

    private static final String ENTITY_API_URL = "/api/attribute-possible-values";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributePossibleValueRepository attributePossibleValueRepository;

    @Autowired
    private AttributePossibleValueMapper attributePossibleValueMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributePossibleValueMockMvc;

    private AttributePossibleValue attributePossibleValue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributePossibleValue createEntity(EntityManager em) {
        AttributePossibleValue attributePossibleValue = new AttributePossibleValue()
            .value(DEFAULT_VALUE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return attributePossibleValue;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributePossibleValue createUpdatedEntity(EntityManager em) {
        AttributePossibleValue attributePossibleValue = new AttributePossibleValue()
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return attributePossibleValue;
    }

    @BeforeEach
    public void initTest() {
        attributePossibleValue = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributePossibleValue() throws Exception {
        int databaseSizeBeforeCreate = attributePossibleValueRepository.findAll().size();
        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);
        restAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeCreate + 1);
        AttributePossibleValue testAttributePossibleValue = attributePossibleValueList.get(attributePossibleValueList.size() - 1);
        assertThat(testAttributePossibleValue.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAttributePossibleValue.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAttributePossibleValue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttributePossibleValue.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttributePossibleValue.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAttributePossibleValue.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createAttributePossibleValueWithExistingId() throws Exception {
        // Create the AttributePossibleValue with an existing ID
        attributePossibleValue.setId(1L);
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        int databaseSizeBeforeCreate = attributePossibleValueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributePossibleValueMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributePossibleValues() throws Exception {
        // Initialize the database
        attributePossibleValueRepository.saveAndFlush(attributePossibleValue);

        // Get all the attributePossibleValueList
        restAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributePossibleValue.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getAttributePossibleValue() throws Exception {
        // Initialize the database
        attributePossibleValueRepository.saveAndFlush(attributePossibleValue);

        // Get the attributePossibleValue
        restAttributePossibleValueMockMvc
            .perform(get(ENTITY_API_URL_ID, attributePossibleValue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributePossibleValue.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAttributePossibleValue() throws Exception {
        // Get the attributePossibleValue
        restAttributePossibleValueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributePossibleValue() throws Exception {
        // Initialize the database
        attributePossibleValueRepository.saveAndFlush(attributePossibleValue);

        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();

        // Update the attributePossibleValue
        AttributePossibleValue updatedAttributePossibleValue = attributePossibleValueRepository
            .findById(attributePossibleValue.getId())
            .get();
        // Disconnect from session so that the updates on updatedAttributePossibleValue are not directly saved in db
        em.detach(updatedAttributePossibleValue);
        updatedAttributePossibleValue
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(updatedAttributePossibleValue);

        restAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributePossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
        AttributePossibleValue testAttributePossibleValue = attributePossibleValueList.get(attributePossibleValueList.size() - 1);
        assertThat(testAttributePossibleValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAttributePossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttributePossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttributePossibleValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributePossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttributePossibleValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingAttributePossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();
        attributePossibleValue.setId(count.incrementAndGet());

        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributePossibleValueDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributePossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();
        attributePossibleValue.setId(count.incrementAndGet());

        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributePossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();
        attributePossibleValue.setId(count.incrementAndGet());

        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePossibleValueMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributePossibleValueWithPatch() throws Exception {
        // Initialize the database
        attributePossibleValueRepository.saveAndFlush(attributePossibleValue);

        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();

        // Update the attributePossibleValue using partial update
        AttributePossibleValue partialUpdatedAttributePossibleValue = new AttributePossibleValue();
        partialUpdatedAttributePossibleValue.setId(attributePossibleValue.getId());

        partialUpdatedAttributePossibleValue
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY);

        restAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
        AttributePossibleValue testAttributePossibleValue = attributePossibleValueList.get(attributePossibleValueList.size() - 1);
        assertThat(testAttributePossibleValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAttributePossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttributePossibleValue.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttributePossibleValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributePossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttributePossibleValue.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateAttributePossibleValueWithPatch() throws Exception {
        // Initialize the database
        attributePossibleValueRepository.saveAndFlush(attributePossibleValue);

        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();

        // Update the attributePossibleValue using partial update
        AttributePossibleValue partialUpdatedAttributePossibleValue = new AttributePossibleValue();
        partialUpdatedAttributePossibleValue.setId(attributePossibleValue.getId());

        partialUpdatedAttributePossibleValue
            .value(UPDATED_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributePossibleValue.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributePossibleValue))
            )
            .andExpect(status().isOk());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
        AttributePossibleValue testAttributePossibleValue = attributePossibleValueList.get(attributePossibleValueList.size() - 1);
        assertThat(testAttributePossibleValue.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAttributePossibleValue.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttributePossibleValue.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttributePossibleValue.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributePossibleValue.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttributePossibleValue.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingAttributePossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();
        attributePossibleValue.setId(count.incrementAndGet());

        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributePossibleValueDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributePossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();
        attributePossibleValue.setId(count.incrementAndGet());

        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributePossibleValue() throws Exception {
        int databaseSizeBeforeUpdate = attributePossibleValueRepository.findAll().size();
        attributePossibleValue.setId(count.incrementAndGet());

        // Create the AttributePossibleValue
        AttributePossibleValueDTO attributePossibleValueDTO = attributePossibleValueMapper.toDto(attributePossibleValue);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributePossibleValueMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributePossibleValueDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributePossibleValue in the database
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributePossibleValue() throws Exception {
        // Initialize the database
        attributePossibleValueRepository.saveAndFlush(attributePossibleValue);

        int databaseSizeBeforeDelete = attributePossibleValueRepository.findAll().size();

        // Delete the attributePossibleValue
        restAttributePossibleValueMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributePossibleValue.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributePossibleValue> attributePossibleValueList = attributePossibleValueRepository.findAll();
        assertThat(attributePossibleValueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
