package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.TelecallerInOut;
import com.reliance.jpl.domain.enumeration.InOutType;
import com.reliance.jpl.repository.TelecallerInOutRepository;
import com.reliance.jpl.service.dto.TelecallerInOutDTO;
import com.reliance.jpl.service.mapper.TelecallerInOutMapper;
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
 * Integration tests for the {@link TelecallerInOutResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TelecallerInOutResourceIT {

    private static final InOutType DEFAULT_EVENT_TYPE = InOutType.IN;
    private static final InOutType UPDATED_EVENT_TYPE = InOutType.OUT;

    private static final LocalDate DEFAULT_EVENT_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EVENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/telecaller-in-outs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TelecallerInOutRepository telecallerInOutRepository;

    @Autowired
    private TelecallerInOutMapper telecallerInOutMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTelecallerInOutMockMvc;

    private TelecallerInOut telecallerInOut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelecallerInOut createEntity(EntityManager em) {
        TelecallerInOut telecallerInOut = new TelecallerInOut()
            .eventType(DEFAULT_EVENT_TYPE)
            .eventTime(DEFAULT_EVENT_TIME)
            .eventDate(DEFAULT_EVENT_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return telecallerInOut;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TelecallerInOut createUpdatedEntity(EntityManager em) {
        TelecallerInOut telecallerInOut = new TelecallerInOut()
            .eventType(UPDATED_EVENT_TYPE)
            .eventTime(UPDATED_EVENT_TIME)
            .eventDate(UPDATED_EVENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return telecallerInOut;
    }

    @BeforeEach
    public void initTest() {
        telecallerInOut = createEntity(em);
    }

    @Test
    @Transactional
    void createTelecallerInOut() throws Exception {
        int databaseSizeBeforeCreate = telecallerInOutRepository.findAll().size();
        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);
        restTelecallerInOutMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeCreate + 1);
        TelecallerInOut testTelecallerInOut = telecallerInOutList.get(telecallerInOutList.size() - 1);
        assertThat(testTelecallerInOut.getEventType()).isEqualTo(DEFAULT_EVENT_TYPE);
        assertThat(testTelecallerInOut.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testTelecallerInOut.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testTelecallerInOut.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTelecallerInOut.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTelecallerInOut.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTelecallerInOut.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createTelecallerInOutWithExistingId() throws Exception {
        // Create the TelecallerInOut with an existing ID
        telecallerInOut.setId(1L);
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        int databaseSizeBeforeCreate = telecallerInOutRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTelecallerInOutMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTelecallerInOuts() throws Exception {
        // Initialize the database
        telecallerInOutRepository.saveAndFlush(telecallerInOut);

        // Get all the telecallerInOutList
        restTelecallerInOutMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(telecallerInOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventType").value(hasItem(DEFAULT_EVENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].eventTime").value(hasItem(DEFAULT_EVENT_TIME.toString())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getTelecallerInOut() throws Exception {
        // Initialize the database
        telecallerInOutRepository.saveAndFlush(telecallerInOut);

        // Get the telecallerInOut
        restTelecallerInOutMockMvc
            .perform(get(ENTITY_API_URL_ID, telecallerInOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(telecallerInOut.getId().intValue()))
            .andExpect(jsonPath("$.eventType").value(DEFAULT_EVENT_TYPE.toString()))
            .andExpect(jsonPath("$.eventTime").value(DEFAULT_EVENT_TIME.toString()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTelecallerInOut() throws Exception {
        // Get the telecallerInOut
        restTelecallerInOutMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTelecallerInOut() throws Exception {
        // Initialize the database
        telecallerInOutRepository.saveAndFlush(telecallerInOut);

        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();

        // Update the telecallerInOut
        TelecallerInOut updatedTelecallerInOut = telecallerInOutRepository.findById(telecallerInOut.getId()).get();
        // Disconnect from session so that the updates on updatedTelecallerInOut are not directly saved in db
        em.detach(updatedTelecallerInOut);
        updatedTelecallerInOut
            .eventType(UPDATED_EVENT_TYPE)
            .eventTime(UPDATED_EVENT_TIME)
            .eventDate(UPDATED_EVENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(updatedTelecallerInOut);

        restTelecallerInOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telecallerInOutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isOk());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
        TelecallerInOut testTelecallerInOut = telecallerInOutList.get(telecallerInOutList.size() - 1);
        assertThat(testTelecallerInOut.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTelecallerInOut.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testTelecallerInOut.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testTelecallerInOut.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecallerInOut.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecallerInOut.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecallerInOut.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingTelecallerInOut() throws Exception {
        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();
        telecallerInOut.setId(count.incrementAndGet());

        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelecallerInOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, telecallerInOutDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTelecallerInOut() throws Exception {
        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();
        telecallerInOut.setId(count.incrementAndGet());

        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerInOutMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTelecallerInOut() throws Exception {
        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();
        telecallerInOut.setId(count.incrementAndGet());

        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerInOutMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTelecallerInOutWithPatch() throws Exception {
        // Initialize the database
        telecallerInOutRepository.saveAndFlush(telecallerInOut);

        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();

        // Update the telecallerInOut using partial update
        TelecallerInOut partialUpdatedTelecallerInOut = new TelecallerInOut();
        partialUpdatedTelecallerInOut.setId(telecallerInOut.getId());

        partialUpdatedTelecallerInOut
            .eventType(UPDATED_EVENT_TYPE)
            .eventDate(UPDATED_EVENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restTelecallerInOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelecallerInOut.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelecallerInOut))
            )
            .andExpect(status().isOk());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
        TelecallerInOut testTelecallerInOut = telecallerInOutList.get(telecallerInOutList.size() - 1);
        assertThat(testTelecallerInOut.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTelecallerInOut.getEventTime()).isEqualTo(DEFAULT_EVENT_TIME);
        assertThat(testTelecallerInOut.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testTelecallerInOut.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecallerInOut.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecallerInOut.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testTelecallerInOut.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateTelecallerInOutWithPatch() throws Exception {
        // Initialize the database
        telecallerInOutRepository.saveAndFlush(telecallerInOut);

        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();

        // Update the telecallerInOut using partial update
        TelecallerInOut partialUpdatedTelecallerInOut = new TelecallerInOut();
        partialUpdatedTelecallerInOut.setId(telecallerInOut.getId());

        partialUpdatedTelecallerInOut
            .eventType(UPDATED_EVENT_TYPE)
            .eventTime(UPDATED_EVENT_TIME)
            .eventDate(UPDATED_EVENT_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restTelecallerInOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTelecallerInOut.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTelecallerInOut))
            )
            .andExpect(status().isOk());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
        TelecallerInOut testTelecallerInOut = telecallerInOutList.get(telecallerInOutList.size() - 1);
        assertThat(testTelecallerInOut.getEventType()).isEqualTo(UPDATED_EVENT_TYPE);
        assertThat(testTelecallerInOut.getEventTime()).isEqualTo(UPDATED_EVENT_TIME);
        assertThat(testTelecallerInOut.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testTelecallerInOut.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTelecallerInOut.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTelecallerInOut.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testTelecallerInOut.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingTelecallerInOut() throws Exception {
        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();
        telecallerInOut.setId(count.incrementAndGet());

        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTelecallerInOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, telecallerInOutDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTelecallerInOut() throws Exception {
        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();
        telecallerInOut.setId(count.incrementAndGet());

        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerInOutMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTelecallerInOut() throws Exception {
        int databaseSizeBeforeUpdate = telecallerInOutRepository.findAll().size();
        telecallerInOut.setId(count.incrementAndGet());

        // Create the TelecallerInOut
        TelecallerInOutDTO telecallerInOutDTO = telecallerInOutMapper.toDto(telecallerInOut);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTelecallerInOutMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(telecallerInOutDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TelecallerInOut in the database
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTelecallerInOut() throws Exception {
        // Initialize the database
        telecallerInOutRepository.saveAndFlush(telecallerInOut);

        int databaseSizeBeforeDelete = telecallerInOutRepository.findAll().size();

        // Delete the telecallerInOut
        restTelecallerInOutMockMvc
            .perform(delete(ENTITY_API_URL_ID, telecallerInOut.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TelecallerInOut> telecallerInOutList = telecallerInOutRepository.findAll();
        assertThat(telecallerInOutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
