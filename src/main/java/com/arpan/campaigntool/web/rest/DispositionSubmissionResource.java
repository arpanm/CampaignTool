package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.DispositionSubmissionRepository;
import com.arpan.campaigntool.service.DispositionSubmissionService;
import com.arpan.campaigntool.service.dto.DispositionSubmissionDTO;
import com.arpan.campaigntool.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.DispositionSubmission}.
 */
@RestController
@RequestMapping("/api")
public class DispositionSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(DispositionSubmissionResource.class);

    private static final String ENTITY_NAME = "dispositionSubmission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositionSubmissionService dispositionSubmissionService;

    private final DispositionSubmissionRepository dispositionSubmissionRepository;

    public DispositionSubmissionResource(
        DispositionSubmissionService dispositionSubmissionService,
        DispositionSubmissionRepository dispositionSubmissionRepository
    ) {
        this.dispositionSubmissionService = dispositionSubmissionService;
        this.dispositionSubmissionRepository = dispositionSubmissionRepository;
    }

    /**
     * {@code POST  /disposition-submissions} : Create a new dispositionSubmission.
     *
     * @param dispositionSubmissionDTO the dispositionSubmissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositionSubmissionDTO, or with status {@code 400 (Bad Request)} if the dispositionSubmission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disposition-submissions")
    public ResponseEntity<DispositionSubmissionDTO> createDispositionSubmission(
        @RequestBody DispositionSubmissionDTO dispositionSubmissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DispositionSubmission : {}", dispositionSubmissionDTO);
        if (dispositionSubmissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispositionSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispositionSubmissionDTO result = dispositionSubmissionService.save(dispositionSubmissionDTO);
        return ResponseEntity
            .created(new URI("/api/disposition-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disposition-submissions/:id} : Updates an existing dispositionSubmission.
     *
     * @param id the id of the dispositionSubmissionDTO to save.
     * @param dispositionSubmissionDTO the dispositionSubmissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositionSubmissionDTO,
     * or with status {@code 400 (Bad Request)} if the dispositionSubmissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositionSubmissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disposition-submissions/{id}")
    public ResponseEntity<DispositionSubmissionDTO> updateDispositionSubmission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositionSubmissionDTO dispositionSubmissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DispositionSubmission : {}, {}", id, dispositionSubmissionDTO);
        if (dispositionSubmissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositionSubmissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositionSubmissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DispositionSubmissionDTO result = dispositionSubmissionService.save(dispositionSubmissionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositionSubmissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /disposition-submissions/:id} : Partial updates given fields of an existing dispositionSubmission, field will ignore if it is null
     *
     * @param id the id of the dispositionSubmissionDTO to save.
     * @param dispositionSubmissionDTO the dispositionSubmissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositionSubmissionDTO,
     * or with status {@code 400 (Bad Request)} if the dispositionSubmissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispositionSubmissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispositionSubmissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/disposition-submissions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DispositionSubmissionDTO> partialUpdateDispositionSubmission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositionSubmissionDTO dispositionSubmissionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DispositionSubmission partially : {}, {}", id, dispositionSubmissionDTO);
        if (dispositionSubmissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositionSubmissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositionSubmissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispositionSubmissionDTO> result = dispositionSubmissionService.partialUpdate(dispositionSubmissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositionSubmissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /disposition-submissions} : get all the dispositionSubmissions.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositionSubmissions in body.
     */
    @GetMapping("/disposition-submissions")
    public ResponseEntity<List<DispositionSubmissionDTO>> getAllDispositionSubmissions(
        Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("call-is-null".equals(filter)) {
            log.debug("REST request to get all DispositionSubmissions where call is null");
            return new ResponseEntity<>(dispositionSubmissionService.findAllWhereCallIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of DispositionSubmissions");
        Page<DispositionSubmissionDTO> page = dispositionSubmissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disposition-submissions/:id} : get the "id" dispositionSubmission.
     *
     * @param id the id of the dispositionSubmissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositionSubmissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disposition-submissions/{id}")
    public ResponseEntity<DispositionSubmissionDTO> getDispositionSubmission(@PathVariable Long id) {
        log.debug("REST request to get DispositionSubmission : {}", id);
        Optional<DispositionSubmissionDTO> dispositionSubmissionDTO = dispositionSubmissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositionSubmissionDTO);
    }

    /**
     * {@code DELETE  /disposition-submissions/:id} : delete the "id" dispositionSubmission.
     *
     * @param id the id of the dispositionSubmissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disposition-submissions/{id}")
    public ResponseEntity<Void> deleteDispositionSubmission(@PathVariable Long id) {
        log.debug("REST request to delete DispositionSubmission : {}", id);
        dispositionSubmissionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
