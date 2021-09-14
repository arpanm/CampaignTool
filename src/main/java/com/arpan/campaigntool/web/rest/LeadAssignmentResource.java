package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.LeadAssignmentRepository;
import com.arpan.campaigntool.service.LeadAssignmentService;
import com.arpan.campaigntool.service.dto.LeadAssignmentDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.LeadAssignment}.
 */
@RestController
@RequestMapping("/api")
public class LeadAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(LeadAssignmentResource.class);

    private static final String ENTITY_NAME = "leadAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeadAssignmentService leadAssignmentService;

    private final LeadAssignmentRepository leadAssignmentRepository;

    public LeadAssignmentResource(LeadAssignmentService leadAssignmentService, LeadAssignmentRepository leadAssignmentRepository) {
        this.leadAssignmentService = leadAssignmentService;
        this.leadAssignmentRepository = leadAssignmentRepository;
    }

    /**
     * {@code POST  /lead-assignments} : Create a new leadAssignment.
     *
     * @param leadAssignmentDTO the leadAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leadAssignmentDTO, or with status {@code 400 (Bad Request)} if the leadAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lead-assignments")
    public ResponseEntity<LeadAssignmentDTO> createLeadAssignment(@RequestBody LeadAssignmentDTO leadAssignmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save LeadAssignment : {}", leadAssignmentDTO);
        if (leadAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new leadAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeadAssignmentDTO result = leadAssignmentService.save(leadAssignmentDTO);
        return ResponseEntity
            .created(new URI("/api/lead-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lead-assignments/:id} : Updates an existing leadAssignment.
     *
     * @param id the id of the leadAssignmentDTO to save.
     * @param leadAssignmentDTO the leadAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the leadAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leadAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lead-assignments/{id}")
    public ResponseEntity<LeadAssignmentDTO> updateLeadAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeadAssignmentDTO leadAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LeadAssignment : {}, {}", id, leadAssignmentDTO);
        if (leadAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leadAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leadAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeadAssignmentDTO result = leadAssignmentService.save(leadAssignmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadAssignmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lead-assignments/:id} : Partial updates given fields of an existing leadAssignment, field will ignore if it is null
     *
     * @param id the id of the leadAssignmentDTO to save.
     * @param leadAssignmentDTO the leadAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the leadAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leadAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leadAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lead-assignments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LeadAssignmentDTO> partialUpdateLeadAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeadAssignmentDTO leadAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeadAssignment partially : {}, {}", id, leadAssignmentDTO);
        if (leadAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leadAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leadAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeadAssignmentDTO> result = leadAssignmentService.partialUpdate(leadAssignmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadAssignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lead-assignments} : get all the leadAssignments.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leadAssignments in body.
     */
    @GetMapping("/lead-assignments")
    public ResponseEntity<List<LeadAssignmentDTO>> getAllLeadAssignments(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("call-is-null".equals(filter)) {
            log.debug("REST request to get all LeadAssignments where call is null");
            return new ResponseEntity<>(leadAssignmentService.findAllWhereCallIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of LeadAssignments");
        Page<LeadAssignmentDTO> page = leadAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lead-assignments/:id} : get the "id" leadAssignment.
     *
     * @param id the id of the leadAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leadAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lead-assignments/{id}")
    public ResponseEntity<LeadAssignmentDTO> getLeadAssignment(@PathVariable Long id) {
        log.debug("REST request to get LeadAssignment : {}", id);
        Optional<LeadAssignmentDTO> leadAssignmentDTO = leadAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leadAssignmentDTO);
    }

    /**
     * {@code DELETE  /lead-assignments/:id} : delete the "id" leadAssignment.
     *
     * @param id the id of the leadAssignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lead-assignments/{id}")
    public ResponseEntity<Void> deleteLeadAssignment(@PathVariable Long id) {
        log.debug("REST request to delete LeadAssignment : {}", id);
        leadAssignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
