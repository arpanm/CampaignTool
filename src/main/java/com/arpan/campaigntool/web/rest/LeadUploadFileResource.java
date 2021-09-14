package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.LeadUploadFileRepository;
import com.arpan.campaigntool.service.LeadUploadFileService;
import com.arpan.campaigntool.service.dto.LeadUploadFileDTO;
import com.arpan.campaigntool.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.arpan.campaigntool.domain.LeadUploadFile}.
 */
@RestController
@RequestMapping("/api")
public class LeadUploadFileResource {

    private final Logger log = LoggerFactory.getLogger(LeadUploadFileResource.class);

    private static final String ENTITY_NAME = "leadUploadFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeadUploadFileService leadUploadFileService;

    private final LeadUploadFileRepository leadUploadFileRepository;

    public LeadUploadFileResource(LeadUploadFileService leadUploadFileService, LeadUploadFileRepository leadUploadFileRepository) {
        this.leadUploadFileService = leadUploadFileService;
        this.leadUploadFileRepository = leadUploadFileRepository;
    }

    /**
     * {@code POST  /lead-upload-files} : Create a new leadUploadFile.
     *
     * @param leadUploadFileDTO the leadUploadFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leadUploadFileDTO, or with status {@code 400 (Bad Request)} if the leadUploadFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lead-upload-files")
    public ResponseEntity<LeadUploadFileDTO> createLeadUploadFile(@RequestBody LeadUploadFileDTO leadUploadFileDTO)
        throws URISyntaxException {
        log.debug("REST request to save LeadUploadFile : {}", leadUploadFileDTO);
        if (leadUploadFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new leadUploadFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeadUploadFileDTO result = leadUploadFileService.save(leadUploadFileDTO);
        return ResponseEntity
            .created(new URI("/api/lead-upload-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lead-upload-files/:id} : Updates an existing leadUploadFile.
     *
     * @param id the id of the leadUploadFileDTO to save.
     * @param leadUploadFileDTO the leadUploadFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadUploadFileDTO,
     * or with status {@code 400 (Bad Request)} if the leadUploadFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leadUploadFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lead-upload-files/{id}")
    public ResponseEntity<LeadUploadFileDTO> updateLeadUploadFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeadUploadFileDTO leadUploadFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LeadUploadFile : {}, {}", id, leadUploadFileDTO);
        if (leadUploadFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leadUploadFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leadUploadFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeadUploadFileDTO result = leadUploadFileService.save(leadUploadFileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadUploadFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lead-upload-files/:id} : Partial updates given fields of an existing leadUploadFile, field will ignore if it is null
     *
     * @param id the id of the leadUploadFileDTO to save.
     * @param leadUploadFileDTO the leadUploadFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadUploadFileDTO,
     * or with status {@code 400 (Bad Request)} if the leadUploadFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leadUploadFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leadUploadFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lead-upload-files/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LeadUploadFileDTO> partialUpdateLeadUploadFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeadUploadFileDTO leadUploadFileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeadUploadFile partially : {}, {}", id, leadUploadFileDTO);
        if (leadUploadFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leadUploadFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leadUploadFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeadUploadFileDTO> result = leadUploadFileService.partialUpdate(leadUploadFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadUploadFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lead-upload-files} : get all the leadUploadFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leadUploadFiles in body.
     */
    @GetMapping("/lead-upload-files")
    public ResponseEntity<List<LeadUploadFileDTO>> getAllLeadUploadFiles(Pageable pageable) {
        log.debug("REST request to get a page of LeadUploadFiles");
        Page<LeadUploadFileDTO> page = leadUploadFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lead-upload-files/:id} : get the "id" leadUploadFile.
     *
     * @param id the id of the leadUploadFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leadUploadFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lead-upload-files/{id}")
    public ResponseEntity<LeadUploadFileDTO> getLeadUploadFile(@PathVariable Long id) {
        log.debug("REST request to get LeadUploadFile : {}", id);
        Optional<LeadUploadFileDTO> leadUploadFileDTO = leadUploadFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leadUploadFileDTO);
    }

    /**
     * {@code DELETE  /lead-upload-files/:id} : delete the "id" leadUploadFile.
     *
     * @param id the id of the leadUploadFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lead-upload-files/{id}")
    public ResponseEntity<Void> deleteLeadUploadFile(@PathVariable Long id) {
        log.debug("REST request to delete LeadUploadFile : {}", id);
        leadUploadFileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
