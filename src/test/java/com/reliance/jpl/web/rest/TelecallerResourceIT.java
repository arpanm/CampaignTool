package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.Telecaller;
import com.reliance.jpl.repository.TelecallerRepository;
import com.reliance.jpl.service.dto.TelecallerDTO;
import com.reliance.jpl.service.mapper.TelecallerMapper;
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
 * Integration tests for the {@link TelecallerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelecallerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/telecallers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelecallerRepository telecallerRepository;

    @Autowired
    private TelecallerMapper telecallerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelecallerMockMvc;

    private Telecaller telecaller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telecaller createEntity(EntityManager em) {
        Telecaller telecaller = new Telecaller()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .isActive(DEFAULT_IS_ACTIVE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return telecaller;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Telecaller createUpdatedEntity(EntityManager em) {
        Telecaller telecaller = new Telecaller()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return telecaller;
    }

    @BeforeEach
    public void initTest() {
        telecaller = createEntity(em);
    }

    @Test
    @Transactional
    void createTelecaller() throws Exception {
        int databaseSizeBeforeCreate = telecallerRepository.findAll().size();
        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);
        restTelecallerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telecallerDTO)))
            .andExpect(status().isCreated());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeCreate + 1);
        Telecaller testTelecaller = telecallerList.get(telecallerList.size() - 1);
        assertThat(testTelecaller.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTelecaller.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTelecaller.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTelecaller.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTelecaller.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTelecaller.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testTelecaller.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTelecaller.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTelecaller.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTelecaller.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createTelecallerWithExistingId() throws Exception {
        // Create the Telecaller with an existing ID
        telecaller.setId(1L);
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        int databaseSizeBeforeCreate = telecallerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelecallerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telecallerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelecallers() throws Exception {
        // Initialize the database
        telecallerRepository.saveAndFlush(telecaller);

        // Get all the telecallerList
        restTelecallerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telecaller.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTelecaller() throws Exception {
        // Initialize the database
        telecallerRepository.saveAndFlush(telecaller);

        // Get the telecaller
        restTelecallerMockMvc
            .perform(get(ENTITY_API_URL_ID, telecaller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telecaller.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTelecaller() throws Exception {
        // Get the telecaller
        restTelecallerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelecaller() throws Exception {
        // Initialize the database
        telecallerRepository.saveAndFlush(telecaller);

        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();

        // Update the telecaller
        Telecaller updatedTelecaller = telecallerRepository.findById(telecaller.getId()).get();
        // Disconnect from session so that the updates on updatedTelecaller are not directly saved in db
        em.detach(updatedTelecaller);
        updatedTelecaller
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(updatedTelecaller);

        restTelecallerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telecallerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
        Telecaller testTelecaller = telecallerList.get(telecallerList.size() - 1);
        assertThat(testTelecaller.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTelecaller.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTelecaller.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTelecaller.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTelecaller.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTelecaller.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTelecaller.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecaller.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecaller.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecaller.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTelecaller() throws Exception {
        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();
        telecaller.setId(count.incrementAndGet());

        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelecallerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telecallerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelecaller() throws Exception {
        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();
        telecaller.setId(count.incrementAndGet());

        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelecaller() throws Exception {
        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();
        telecaller.setId(count.incrementAndGet());

        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telecallerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelecallerWithPatch() throws Exception {
        // Initialize the database
        telecallerRepository.saveAndFlush(telecaller);

        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();

        // Update the telecaller using partial update
        Telecaller partialUpdatedTelecaller = new Telecaller();
        partialUpdatedTelecaller.setId(telecaller.getId());

        partialUpdatedTelecaller
            .name(UPDATED_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT);

        restTelecallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelecaller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelecaller))
            )
            .andExpect(status().isOk());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
        Telecaller testTelecaller = telecallerList.get(telecallerList.size() - 1);
        assertThat(testTelecaller.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTelecaller.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTelecaller.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTelecaller.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTelecaller.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTelecaller.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testTelecaller.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecaller.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecaller.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTelecaller.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTelecallerWithPatch() throws Exception {
        // Initialize the database
        telecallerRepository.saveAndFlush(telecaller);

        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();

        // Update the telecaller using partial update
        Telecaller partialUpdatedTelecaller = new Telecaller();
        partialUpdatedTelecaller.setId(telecaller.getId());

        partialUpdatedTelecaller
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restTelecallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelecaller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelecaller))
            )
            .andExpect(status().isOk());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
        Telecaller testTelecaller = telecallerList.get(telecallerList.size() - 1);
        assertThat(testTelecaller.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTelecaller.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTelecaller.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTelecaller.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTelecaller.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTelecaller.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testTelecaller.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecaller.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecaller.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecaller.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTelecaller() throws Exception {
        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();
        telecaller.setId(count.incrementAndGet());

        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelecallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telecallerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelecaller() throws Exception {
        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();
        telecaller.setId(count.incrementAndGet());

        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelecaller() throws Exception {
        int databaseSizeBeforeUpdate = telecallerRepository.findAll().size();
        telecaller.setId(count.incrementAndGet());

        // Create the Telecaller
        TelecallerDTO telecallerDTO = telecallerMapper.toDto(telecaller);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(telecallerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Telecaller in the database
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelecaller() throws Exception {
        // Initialize the database
        telecallerRepository.saveAndFlush(telecaller);

        int databaseSizeBeforeDelete = telecallerRepository.findAll().size();

        // Delete the telecaller
        restTelecallerMockMvc
            .perform(delete(ENTITY_API_URL_ID, telecaller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Telecaller> telecallerList = telecallerRepository.findAll();
        assertThat(telecallerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
