package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.Disposition;
import com.arpan.campaigntool.repository.DispositionRepository;
import com.arpan.campaigntool.service.dto.DispositionDTO;
import com.arpan.campaigntool.service.mapper.DispositionMapper;
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
 * Integration tests for the {@link DispositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DispositionResourceIT {

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

    private static final String ENTITY_API_URL = "/api/dispositions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DispositionRepository dispositionRepository;

    @Autowired
    private DispositionMapper dispositionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDispositionMockMvc;

    private Disposition disposition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disposition createEntity(EntityManager em) {
        Disposition disposition = new Disposition()
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return disposition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disposition createUpdatedEntity(EntityManager em) {
        Disposition disposition = new Disposition()
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return disposition;
    }

    @BeforeEach
    public void initTest() {
        disposition = createEntity(em);
    }

    @Test
    @Transactional
    void createDisposition() throws Exception {
        int databaseSizeBeforeCreate = dispositionRepository.findAll().size();
        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);
        restDispositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeCreate + 1);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testDisposition.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDisposition.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createDispositionWithExistingId() throws Exception {
        // Create the Disposition with an existing ID
        disposition.setId(1L);
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        int databaseSizeBeforeCreate = dispositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDispositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDispositions() throws Exception {
        // Initialize the database
        dispositionRepository.saveAndFlush(disposition);

        // Get all the dispositionList
        restDispositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disposition.getId().intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getDisposition() throws Exception {
        // Initialize the database
        dispositionRepository.saveAndFlush(disposition);

        // Get the disposition
        restDispositionMockMvc
            .perform(get(ENTITY_API_URL_ID, disposition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disposition.getId().intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDisposition() throws Exception {
        // Get the disposition
        restDispositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDisposition() throws Exception {
        // Initialize the database
        dispositionRepository.saveAndFlush(disposition);

        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();

        // Update the disposition
        Disposition updatedDisposition = dispositionRepository.findById(disposition.getId()).get();
        // Disconnect from session so that the updates on updatedDisposition are not directly saved in db
        em.detach(updatedDisposition);
        updatedDisposition
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        DispositionDTO dispositionDTO = dispositionMapper.toDto(updatedDisposition);

        restDispositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testDisposition.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDisposition.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(count.incrementAndGet());

        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dispositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(count.incrementAndGet());

        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(count.incrementAndGet());

        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dispositionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDispositionWithPatch() throws Exception {
        // Initialize the database
        dispositionRepository.saveAndFlush(disposition);

        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();

        // Update the disposition using partial update
        Disposition partialUpdatedDisposition = new Disposition();
        partialUpdatedDisposition.setId(disposition.getId());

        partialUpdatedDisposition.createdBy(UPDATED_CREATED_BY);

        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisposition))
            )
            .andExpect(status().isOk());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testDisposition.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDisposition.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateDispositionWithPatch() throws Exception {
        // Initialize the database
        dispositionRepository.saveAndFlush(disposition);

        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();

        // Update the disposition using partial update
        Disposition partialUpdatedDisposition = new Disposition();
        partialUpdatedDisposition.setId(disposition.getId());

        partialUpdatedDisposition
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDisposition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDisposition))
            )
            .andExpect(status().isOk());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
        Disposition testDisposition = dispositionList.get(dispositionList.size() - 1);
        assertThat(testDisposition.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testDisposition.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDisposition.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDisposition.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testDisposition.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(count.incrementAndGet());

        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dispositionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(count.incrementAndGet());

        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDisposition() throws Exception {
        int databaseSizeBeforeUpdate = dispositionRepository.findAll().size();
        disposition.setId(count.incrementAndGet());

        // Create the Disposition
        DispositionDTO dispositionDTO = dispositionMapper.toDto(disposition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDispositionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dispositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Disposition in the database
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDisposition() throws Exception {
        // Initialize the database
        dispositionRepository.saveAndFlush(disposition);

        int databaseSizeBeforeDelete = dispositionRepository.findAll().size();

        // Delete the disposition
        restDispositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, disposition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disposition> dispositionList = dispositionRepository.findAll();
        assertThat(dispositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
