package com.reliance.jpl.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reliance.jpl.IntegrationTest;
import com.reliance.jpl.domain.LeadUploadFile;
import com.reliance.jpl.domain.enumeration.UploadStatus;
import com.reliance.jpl.repository.LeadUploadFileRepository;
import com.reliance.jpl.service.dto.LeadUploadFileDTO;
import com.reliance.jpl.service.mapper.LeadUploadFileMapper;
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
 * Integration tests for the {@link LeadUploadFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LeadUploadFileResourceIT {

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    private static final UploadStatus DEFAULT_UPLOAD_STATUS = UploadStatus.Pending;
    private static final UploadStatus UPDATED_UPLOAD_STATUS = UploadStatus.InProgress;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/lead-upload-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LeadUploadFileRepository leadUploadFileRepository;

    @Autowired
    private LeadUploadFileMapper leadUploadFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLeadUploadFileMockMvc;

    private LeadUploadFile leadUploadFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadUploadFile createEntity(EntityManager em) {
        LeadUploadFile leadUploadFile = new LeadUploadFile()
            .fileUrl(DEFAULT_FILE_URL)
            .uploadStatus(DEFAULT_UPLOAD_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedAt(DEFAULT_UPDATED_AT);
        return leadUploadFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LeadUploadFile createUpdatedEntity(EntityManager em) {
        LeadUploadFile leadUploadFile = new LeadUploadFile()
            .fileUrl(UPDATED_FILE_URL)
            .uploadStatus(UPDATED_UPLOAD_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        return leadUploadFile;
    }

    @BeforeEach
    public void initTest() {
        leadUploadFile = createEntity(em);
    }

    @Test
    @Transactional
    void createLeadUploadFile() throws Exception {
        int databaseSizeBeforeCreate = leadUploadFileRepository.findAll().size();
        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);
        restLeadUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeCreate + 1);
        LeadUploadFile testLeadUploadFile = leadUploadFileList.get(leadUploadFileList.size() - 1);
        assertThat(testLeadUploadFile.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testLeadUploadFile.getUploadStatus()).isEqualTo(DEFAULT_UPLOAD_STATUS);
        assertThat(testLeadUploadFile.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLeadUploadFile.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testLeadUploadFile.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLeadUploadFile.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createLeadUploadFileWithExistingId() throws Exception {
        // Create the LeadUploadFile with an existing ID
        leadUploadFile.setId(1L);
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        int databaseSizeBeforeCreate = leadUploadFileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLeadUploadFileMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLeadUploadFiles() throws Exception {
        // Initialize the database
        leadUploadFileRepository.saveAndFlush(leadUploadFile);

        // Get all the leadUploadFileList
        restLeadUploadFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(leadUploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].uploadStatus").value(hasItem(DEFAULT_UPLOAD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @Test
    @Transactional
    void getLeadUploadFile() throws Exception {
        // Initialize the database
        leadUploadFileRepository.saveAndFlush(leadUploadFile);

        // Get the leadUploadFile
        restLeadUploadFileMockMvc
            .perform(get(ENTITY_API_URL_ID, leadUploadFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(leadUploadFile.getId().intValue()))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL))
            .andExpect(jsonPath("$.uploadStatus").value(DEFAULT_UPLOAD_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLeadUploadFile() throws Exception {
        // Get the leadUploadFile
        restLeadUploadFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLeadUploadFile() throws Exception {
        // Initialize the database
        leadUploadFileRepository.saveAndFlush(leadUploadFile);

        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();

        // Update the leadUploadFile
        LeadUploadFile updatedLeadUploadFile = leadUploadFileRepository.findById(leadUploadFile.getId()).get();
        // Disconnect from session so that the updates on updatedLeadUploadFile are not directly saved in db
        em.detach(updatedLeadUploadFile);
        updatedLeadUploadFile
            .fileUrl(UPDATED_FILE_URL)
            .uploadStatus(UPDATED_UPLOAD_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(updatedLeadUploadFile);

        restLeadUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leadUploadFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
        LeadUploadFile testLeadUploadFile = leadUploadFileList.get(leadUploadFileList.size() - 1);
        assertThat(testLeadUploadFile.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testLeadUploadFile.getUploadStatus()).isEqualTo(UPDATED_UPLOAD_STATUS);
        assertThat(testLeadUploadFile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadUploadFile.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadUploadFile.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadUploadFile.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingLeadUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();
        leadUploadFile.setId(count.incrementAndGet());

        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, leadUploadFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLeadUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();
        leadUploadFile.setId(count.incrementAndGet());

        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLeadUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();
        leadUploadFile.setId(count.incrementAndGet());

        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadUploadFileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLeadUploadFileWithPatch() throws Exception {
        // Initialize the database
        leadUploadFileRepository.saveAndFlush(leadUploadFile);

        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();

        // Update the leadUploadFile using partial update
        LeadUploadFile partialUpdatedLeadUploadFile = new LeadUploadFile();
        partialUpdatedLeadUploadFile.setId(leadUploadFile.getId());

        partialUpdatedLeadUploadFile.uploadStatus(UPDATED_UPLOAD_STATUS).createdBy(UPDATED_CREATED_BY).createdAt(UPDATED_CREATED_AT);

        restLeadUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeadUploadFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeadUploadFile))
            )
            .andExpect(status().isOk());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
        LeadUploadFile testLeadUploadFile = leadUploadFileList.get(leadUploadFileList.size() - 1);
        assertThat(testLeadUploadFile.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testLeadUploadFile.getUploadStatus()).isEqualTo(UPDATED_UPLOAD_STATUS);
        assertThat(testLeadUploadFile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadUploadFile.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadUploadFile.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testLeadUploadFile.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateLeadUploadFileWithPatch() throws Exception {
        // Initialize the database
        leadUploadFileRepository.saveAndFlush(leadUploadFile);

        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();

        // Update the leadUploadFile using partial update
        LeadUploadFile partialUpdatedLeadUploadFile = new LeadUploadFile();
        partialUpdatedLeadUploadFile.setId(leadUploadFile.getId());

        partialUpdatedLeadUploadFile
            .fileUrl(UPDATED_FILE_URL)
            .uploadStatus(UPDATED_UPLOAD_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedAt(UPDATED_UPDATED_AT);

        restLeadUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLeadUploadFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLeadUploadFile))
            )
            .andExpect(status().isOk());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
        LeadUploadFile testLeadUploadFile = leadUploadFileList.get(leadUploadFileList.size() - 1);
        assertThat(testLeadUploadFile.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testLeadUploadFile.getUploadStatus()).isEqualTo(UPDATED_UPLOAD_STATUS);
        assertThat(testLeadUploadFile.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLeadUploadFile.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testLeadUploadFile.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testLeadUploadFile.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingLeadUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();
        leadUploadFile.setId(count.incrementAndGet());

        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLeadUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, leadUploadFileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLeadUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();
        leadUploadFile.setId(count.incrementAndGet());

        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLeadUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = leadUploadFileRepository.findAll().size();
        leadUploadFile.setId(count.incrementAndGet());

        // Create the LeadUploadFile
        LeadUploadFileDTO leadUploadFileDTO = leadUploadFileMapper.toDto(leadUploadFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLeadUploadFileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(leadUploadFileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LeadUploadFile in the database
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLeadUploadFile() throws Exception {
        // Initialize the database
        leadUploadFileRepository.saveAndFlush(leadUploadFile);

        int databaseSizeBeforeDelete = leadUploadFileRepository.findAll().size();

        // Delete the leadUploadFile
        restLeadUploadFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, leadUploadFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LeadUploadFile> leadUploadFileList = leadUploadFileRepository.findAll();
        assertThat(leadUploadFileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
