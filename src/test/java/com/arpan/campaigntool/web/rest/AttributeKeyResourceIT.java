package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.AttributeKey;
import com.arpan.campaigntool.repository.AttributeKeyRepository;
import com.arpan.campaigntool.service.dto.AttributeKeyDTO;
import com.arpan.campaigntool.service.mapper.AttributeKeyMapper;
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
 * Integration tests for the {@link AttributeKeyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeKeyResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/attribute-keys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeKeyRepository attributeKeyRepository;

    @Autowired
    private AttributeKeyMapper attributeKeyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeKeyMockMvc;

    private AttributeKey attributeKey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeKey createEntity(EntityManager em) {
        AttributeKey attributeKey = new AttributeKey()
            .key(DEFAULT_KEY)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return attributeKey;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeKey createUpdatedEntity(EntityManager em) {
        AttributeKey attributeKey = new AttributeKey()
            .key(UPDATED_KEY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return attributeKey;
    }

    @BeforeEach
    public void initTest() {
        attributeKey = createEntity(em);
    }

    @Test
    @Transactional
    void createAttributeKey() throws Exception {
        int databaseSizeBeforeCreate = attributeKeyRepository.findAll().size();
        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);
        restAttributeKeyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeKey testAttributeKey = attributeKeyList.get(attributeKeyList.size() - 1);
        assertThat(testAttributeKey.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testAttributeKey.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAttributeKey.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttributeKey.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttributeKey.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAttributeKey.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createAttributeKeyWithExistingId() throws Exception {
        // Create the AttributeKey with an existing ID
        attributeKey.setId(1L);
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        int databaseSizeBeforeCreate = attributeKeyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeKeyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributeKeys() throws Exception {
        // Initialize the database
        attributeKeyRepository.saveAndFlush(attributeKey);

        // Get all the attributeKeyList
        restAttributeKeyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getAttributeKey() throws Exception {
        // Initialize the database
        attributeKeyRepository.saveAndFlush(attributeKey);

        // Get the attributeKey
        restAttributeKeyMockMvc
            .perform(get(ENTITY_API_URL_ID, attributeKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeKey.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAttributeKey() throws Exception {
        // Get the attributeKey
        restAttributeKeyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAttributeKey() throws Exception {
        // Initialize the database
        attributeKeyRepository.saveAndFlush(attributeKey);

        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();

        // Update the attributeKey
        AttributeKey updatedAttributeKey = attributeKeyRepository.findById(attributeKey.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeKey are not directly saved in db
        em.detach(updatedAttributeKey);
        updatedAttributeKey
            .key(UPDATED_KEY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(updatedAttributeKey);

        restAttributeKeyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeKeyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isOk());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
        AttributeKey testAttributeKey = attributeKeyList.get(attributeKeyList.size() - 1);
        assertThat(testAttributeKey.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAttributeKey.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttributeKey.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttributeKey.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributeKey.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttributeKey.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingAttributeKey() throws Exception {
        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();
        attributeKey.setId(count.incrementAndGet());

        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeKeyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeKeyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttributeKey() throws Exception {
        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();
        attributeKey.setId(count.incrementAndGet());

        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeKeyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttributeKey() throws Exception {
        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();
        attributeKey.setId(count.incrementAndGet());

        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeKeyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeKeyWithPatch() throws Exception {
        // Initialize the database
        attributeKeyRepository.saveAndFlush(attributeKey);

        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();

        // Update the attributeKey using partial update
        AttributeKey partialUpdatedAttributeKey = new AttributeKey();
        partialUpdatedAttributeKey.setId(attributeKey.getId());

        partialUpdatedAttributeKey
            .key(UPDATED_KEY)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restAttributeKeyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeKey.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeKey))
            )
            .andExpect(status().isOk());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
        AttributeKey testAttributeKey = attributeKeyList.get(attributeKeyList.size() - 1);
        assertThat(testAttributeKey.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAttributeKey.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAttributeKey.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttributeKey.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributeKey.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttributeKey.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateAttributeKeyWithPatch() throws Exception {
        // Initialize the database
        attributeKeyRepository.saveAndFlush(attributeKey);

        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();

        // Update the attributeKey using partial update
        AttributeKey partialUpdatedAttributeKey = new AttributeKey();
        partialUpdatedAttributeKey.setId(attributeKey.getId());

        partialUpdatedAttributeKey
            .key(UPDATED_KEY)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restAttributeKeyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttributeKey.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttributeKey))
            )
            .andExpect(status().isOk());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
        AttributeKey testAttributeKey = attributeKeyList.get(attributeKeyList.size() - 1);
        assertThat(testAttributeKey.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testAttributeKey.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttributeKey.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttributeKey.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttributeKey.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttributeKey.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingAttributeKey() throws Exception {
        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();
        attributeKey.setId(count.incrementAndGet());

        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeKeyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeKeyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttributeKey() throws Exception {
        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();
        attributeKey.setId(count.incrementAndGet());

        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeKeyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttributeKey() throws Exception {
        int databaseSizeBeforeUpdate = attributeKeyRepository.findAll().size();
        attributeKey.setId(count.incrementAndGet());

        // Create the AttributeKey
        AttributeKeyDTO attributeKeyDTO = attributeKeyMapper.toDto(attributeKey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeKeyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeKeyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AttributeKey in the database
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttributeKey() throws Exception {
        // Initialize the database
        attributeKeyRepository.saveAndFlush(attributeKey);

        int databaseSizeBeforeDelete = attributeKeyRepository.findAll().size();

        // Delete the attributeKey
        restAttributeKeyMockMvc
            .perform(delete(ENTITY_API_URL_ID, attributeKey.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeKey> attributeKeyList = attributeKeyRepository.findAll();
        assertThat(attributeKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
