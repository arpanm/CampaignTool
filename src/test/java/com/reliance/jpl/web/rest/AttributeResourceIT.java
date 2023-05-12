package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.Attribute;
import com.reliance.jpl.repository.AttributeRepository;
import com.reliance.jpl.service.dto.AttributeDTO;
import com.reliance.jpl.service.mapper.AttributeMapper;
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
 * Integration tests for the {@link AttributeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttributeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/attributes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private AttributeMapper attributeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeMockMvc;

    private Attribute attribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attribute createEntity(EntityManager em) {
        Attribute attribute = new Attribute()
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return attribute;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attribute createUpdatedEntity(EntityManager em) {
        Attribute attribute = new Attribute()
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return attribute;
    }

    @BeforeEach
    public void initTest() {
        attribute = createEntity(em);
    }

    @Test
    @Transactional
    void createAttribute() throws Exception {
        int databaseSizeBeforeCreate = attributeRepository.findAll().size();
        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);
        restAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attributeDTO)))
            .andExpect(status().isCreated());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeCreate + 1);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testAttribute.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAttribute.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAttribute.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAttribute.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createAttributeWithExistingId() throws Exception {
        // Create the Attribute with an existing ID
        attribute.setId(1L);
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        int databaseSizeBeforeCreate = attributeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAttributes() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        // Get all the attributeList
        restAttributeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getAttribute() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        // Get the attribute
        restAttributeMockMvc
            .perform(get(ENTITY_API_URL_ID, attribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attribute.getId().intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAttribute() throws Exception {
        // Get the attribute
        restAttributeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttribute() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();

        // Update the attribute
        Attribute updatedAttribute = attributeRepository.findById(attribute.getId()).get();
        // Disconnect from session so that the updates on updatedAttribute are not directly saved in db
        em.detach(updatedAttribute);
        updatedAttribute
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        AttributeDTO attributeDTO = attributeMapper.toDto(updatedAttribute);

        restAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttribute.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttribute.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attributeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(attributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(attributeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttributeWithPatch() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();

        // Update the attribute using partial update
        Attribute partialUpdatedAttribute = new Attribute();
        partialUpdatedAttribute.setId(attribute.getId());

        partialUpdatedAttribute
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttribute))
            )
            .andExpect(status().isOk());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttribute.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttribute.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testAttribute.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateAttributeWithPatch() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();

        // Update the attribute using partial update
        Attribute partialUpdatedAttribute = new Attribute();
        partialUpdatedAttribute.setId(attribute.getId());

        partialUpdatedAttribute
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttribute.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAttribute))
            )
            .andExpect(status().isOk());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
        Attribute testAttribute = attributeList.get(attributeList.size() - 1);
        assertThat(testAttribute.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testAttribute.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAttribute.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAttribute.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testAttribute.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attributeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(attributeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttribute() throws Exception {
        int databaseSizeBeforeUpdate = attributeRepository.findAll().size();
        attribute.setId(count.incrementAndGet());

        // Create the Attribute
        AttributeDTO attributeDTO = attributeMapper.toDto(attribute);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttributeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(attributeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attribute in the database
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttribute() throws Exception {
        // Initialize the database
        attributeRepository.saveAndFlush(attribute);

        int databaseSizeBeforeDelete = attributeRepository.findAll().size();

        // Delete the attribute
        restAttributeMockMvc
            .perform(delete(ENTITY_API_URL_ID, attribute.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attribute> attributeList = attributeRepository.findAll();
        assertThat(attributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
