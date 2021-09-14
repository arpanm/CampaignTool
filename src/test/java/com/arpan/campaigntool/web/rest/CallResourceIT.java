package com.arpan.campaigntool.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.arpan.campaigntool.IntegrationTest;
import com.arpan.campaigntool.domain.Call;
import com.arpan.campaigntool.domain.enumeration.CallStatus;
import com.arpan.campaigntool.repository.CallRepository;
import com.arpan.campaigntool.service.dto.CallDTO;
import com.arpan.campaigntool.service.mapper.CallMapper;
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
 * Integration tests for the {@link CallResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CallResourceIT {

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CALL_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CALL_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_FOLLOWUP_DATE = "AAAAAAAAAA";
    private static final String UPDATED_FOLLOWUP_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_RECORDING_URL = "AAAAAAAAAA";
    private static final String UPDATED_RECORDING_URL = "BBBBBBBBBB";

    private static final CallStatus DEFAULT_STATUS = CallStatus.Pending;
    private static final CallStatus UPDATED_STATUS = CallStatus.InProgress;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/calls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CallRepository callRepository;

    @Autowired
    private CallMapper callMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCallMockMvc;

    private Call call;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Call createEntity(EntityManager em) {
        Call call = new Call()
            .phone(DEFAULT_PHONE)
            .callDate(DEFAULT_CALL_DATE)
            .followupDate(DEFAULT_FOLLOWUP_DATE)
            .notes(DEFAULT_NOTES)
            .recordingUrl(DEFAULT_RECORDING_URL)
            .status(DEFAULT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return call;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Call createUpdatedEntity(EntityManager em) {
        Call call = new Call()
            .phone(UPDATED_PHONE)
            .callDate(UPDATED_CALL_DATE)
            .followupDate(UPDATED_FOLLOWUP_DATE)
            .notes(UPDATED_NOTES)
            .recordingUrl(UPDATED_RECORDING_URL)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return call;
    }

    @BeforeEach
    public void initTest() {
        call = createEntity(em);
    }

    @Test
    @Transactional
    void createCall() throws Exception {
        int databaseSizeBeforeCreate = callRepository.findAll().size();
        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);
        restCallMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callDTO)))
            .andExpect(status().isCreated());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeCreate + 1);
        Call testCall = callList.get(callList.size() - 1);
        assertThat(testCall.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCall.getCallDate()).isEqualTo(DEFAULT_CALL_DATE);
        assertThat(testCall.getFollowupDate()).isEqualTo(DEFAULT_FOLLOWUP_DATE);
        assertThat(testCall.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testCall.getRecordingUrl()).isEqualTo(DEFAULT_RECORDING_URL);
        assertThat(testCall.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCall.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCall.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCall.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testCall.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCallWithExistingId() throws Exception {
        // Create the Call with an existing ID
        call.setId(1L);
        CallDTO callDTO = callMapper.toDto(call);

        int databaseSizeBeforeCreate = callRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCallMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCalls() throws Exception {
        // Initialize the database
        callRepository.saveAndFlush(call);

        // Get all the callList
        restCallMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(call.getId().intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].callDate").value(hasItem(DEFAULT_CALL_DATE)))
            .andExpect(jsonPath("$.[*].followupDate").value(hasItem(DEFAULT_FOLLOWUP_DATE)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].recordingUrl").value(hasItem(DEFAULT_RECORDING_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCall() throws Exception {
        // Initialize the database
        callRepository.saveAndFlush(call);

        // Get the call
        restCallMockMvc
            .perform(get(ENTITY_API_URL_ID, call.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(call.getId().intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.callDate").value(DEFAULT_CALL_DATE))
            .andExpect(jsonPath("$.followupDate").value(DEFAULT_FOLLOWUP_DATE))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.recordingUrl").value(DEFAULT_RECORDING_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCall() throws Exception {
        // Get the call
        restCallMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCall() throws Exception {
        // Initialize the database
        callRepository.saveAndFlush(call);

        int databaseSizeBeforeUpdate = callRepository.findAll().size();

        // Update the call
        Call updatedCall = callRepository.findById(call.getId()).get();
        // Disconnect from session so that the updates on updatedCall are not directly saved in db
        em.detach(updatedCall);
        updatedCall
            .phone(UPDATED_PHONE)
            .callDate(UPDATED_CALL_DATE)
            .followupDate(UPDATED_FOLLOWUP_DATE)
            .notes(UPDATED_NOTES)
            .recordingUrl(UPDATED_RECORDING_URL)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        CallDTO callDTO = callMapper.toDto(updatedCall);

        restCallMockMvc
            .perform(
                put(ENTITY_API_URL_ID, callDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(callDTO))
            )
            .andExpect(status().isOk());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
        Call testCall = callList.get(callList.size() - 1);
        assertThat(testCall.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCall.getCallDate()).isEqualTo(UPDATED_CALL_DATE);
        assertThat(testCall.getFollowupDate()).isEqualTo(UPDATED_FOLLOWUP_DATE);
        assertThat(testCall.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCall.getRecordingUrl()).isEqualTo(UPDATED_RECORDING_URL);
        assertThat(testCall.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCall.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCall.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCall.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCall.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCall() throws Exception {
        int databaseSizeBeforeUpdate = callRepository.findAll().size();
        call.setId(count.incrementAndGet());

        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallMockMvc
            .perform(
                put(ENTITY_API_URL_ID, callDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(callDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCall() throws Exception {
        int databaseSizeBeforeUpdate = callRepository.findAll().size();
        call.setId(count.incrementAndGet());

        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(callDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCall() throws Exception {
        int databaseSizeBeforeUpdate = callRepository.findAll().size();
        call.setId(count.incrementAndGet());

        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(callDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCallWithPatch() throws Exception {
        // Initialize the database
        callRepository.saveAndFlush(call);

        int databaseSizeBeforeUpdate = callRepository.findAll().size();

        // Update the call using partial update
        Call partialUpdatedCall = new Call();
        partialUpdatedCall.setId(call.getId());

        partialUpdatedCall
            .phone(UPDATED_PHONE)
            .notes(UPDATED_NOTES)
            .recordingUrl(UPDATED_RECORDING_URL)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCall.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCall))
            )
            .andExpect(status().isOk());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
        Call testCall = callList.get(callList.size() - 1);
        assertThat(testCall.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCall.getCallDate()).isEqualTo(DEFAULT_CALL_DATE);
        assertThat(testCall.getFollowupDate()).isEqualTo(DEFAULT_FOLLOWUP_DATE);
        assertThat(testCall.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCall.getRecordingUrl()).isEqualTo(UPDATED_RECORDING_URL);
        assertThat(testCall.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCall.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCall.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCall.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCall.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCallWithPatch() throws Exception {
        // Initialize the database
        callRepository.saveAndFlush(call);

        int databaseSizeBeforeUpdate = callRepository.findAll().size();

        // Update the call using partial update
        Call partialUpdatedCall = new Call();
        partialUpdatedCall.setId(call.getId());

        partialUpdatedCall
            .phone(UPDATED_PHONE)
            .callDate(UPDATED_CALL_DATE)
            .followupDate(UPDATED_FOLLOWUP_DATE)
            .notes(UPDATED_NOTES)
            .recordingUrl(UPDATED_RECORDING_URL)
            .status(UPDATED_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCall.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCall))
            )
            .andExpect(status().isOk());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
        Call testCall = callList.get(callList.size() - 1);
        assertThat(testCall.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCall.getCallDate()).isEqualTo(UPDATED_CALL_DATE);
        assertThat(testCall.getFollowupDate()).isEqualTo(UPDATED_FOLLOWUP_DATE);
        assertThat(testCall.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testCall.getRecordingUrl()).isEqualTo(UPDATED_RECORDING_URL);
        assertThat(testCall.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCall.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCall.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCall.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testCall.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCall() throws Exception {
        int databaseSizeBeforeUpdate = callRepository.findAll().size();
        call.setId(count.incrementAndGet());

        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, callDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(callDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCall() throws Exception {
        int databaseSizeBeforeUpdate = callRepository.findAll().size();
        call.setId(count.incrementAndGet());

        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(callDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCall() throws Exception {
        int databaseSizeBeforeUpdate = callRepository.findAll().size();
        call.setId(count.incrementAndGet());

        // Create the Call
        CallDTO callDTO = callMapper.toDto(call);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCallMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(callDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Call in the database
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCall() throws Exception {
        // Initialize the database
        callRepository.saveAndFlush(call);

        int databaseSizeBeforeDelete = callRepository.findAll().size();

        // Delete the call
        restCallMockMvc
            .perform(delete(ENTITY_API_URL_ID, call.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Call> callList = callRepository.findAll();
        assertThat(callList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
