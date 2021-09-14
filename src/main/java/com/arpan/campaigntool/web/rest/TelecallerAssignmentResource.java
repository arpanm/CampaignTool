package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.TelecallerAssignmentRepository;
import com.arpan.campaigntool.service.TelecallerAssignmentService;
import com.arpan.campaigntool.service.dto.TelecallerAssignmentDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.TelecallerAssignment}.
 */
@RestController
@RequestMapping("/api")
public class TelecallerAssignmentResource {

    private final Logger log = LoggerFactory.getLogger(TelecallerAssignmentResource.class);

    private static final String ENTITY_NAME = "telecallerAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelecallerAssignmentService telecallerAssignmentService;

    private final TelecallerAssignmentRepository telecallerAssignmentRepository;

    public TelecallerAssignmentResource(
        TelecallerAssignmentService telecallerAssignmentService,
        TelecallerAssignmentRepository telecallerAssignmentRepository
    ) {
        this.telecallerAssignmentService = telecallerAssignmentService;
        this.telecallerAssignmentRepository = telecallerAssignmentRepository;
    }

    /**
     * {@code POST  /telecaller-assignments} : Create a new telecallerAssignment.
     *
     * @param telecallerAssignmentDTO the telecallerAssignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telecallerAssignmentDTO, or with status {@code 400 (Bad Request)} if the telecallerAssignment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telecaller-assignments")
    public ResponseEntity<TelecallerAssignmentDTO> createTelecallerAssignment(@RequestBody TelecallerAssignmentDTO telecallerAssignmentDTO)
        throws URISyntaxException {
        log.debug("REST request to save TelecallerAssignment : {}", telecallerAssignmentDTO);
        if (telecallerAssignmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new telecallerAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelecallerAssignmentDTO result = telecallerAssignmentService.save(telecallerAssignmentDTO);
        return ResponseEntity
            .created(new URI("/api/telecaller-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telecaller-assignments/:id} : Updates an existing telecallerAssignment.
     *
     * @param id the id of the telecallerAssignmentDTO to save.
     * @param telecallerAssignmentDTO the telecallerAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telecallerAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the telecallerAssignmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telecallerAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telecaller-assignments/{id}")
    public ResponseEntity<TelecallerAssignmentDTO> updateTelecallerAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelecallerAssignmentDTO telecallerAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TelecallerAssignment : {}, {}", id, telecallerAssignmentDTO);
        if (telecallerAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telecallerAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telecallerAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelecallerAssignmentDTO result = telecallerAssignmentService.save(telecallerAssignmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telecallerAssignmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telecaller-assignments/:id} : Partial updates given fields of an existing telecallerAssignment, field will ignore if it is null
     *
     * @param id the id of the telecallerAssignmentDTO to save.
     * @param telecallerAssignmentDTO the telecallerAssignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telecallerAssignmentDTO,
     * or with status {@code 400 (Bad Request)} if the telecallerAssignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the telecallerAssignmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the telecallerAssignmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telecaller-assignments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TelecallerAssignmentDTO> partialUpdateTelecallerAssignment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelecallerAssignmentDTO telecallerAssignmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TelecallerAssignment partially : {}, {}", id, telecallerAssignmentDTO);
        if (telecallerAssignmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telecallerAssignmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telecallerAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelecallerAssignmentDTO> result = telecallerAssignmentService.partialUpdate(telecallerAssignmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telecallerAssignmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /telecaller-assignments} : get all the telecallerAssignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telecallerAssignments in body.
     */
    @GetMapping("/telecaller-assignments")
    public ResponseEntity<List<TelecallerAssignmentDTO>> getAllTelecallerAssignments(Pageable pageable) {
        log.debug("REST request to get a page of TelecallerAssignments");
        Page<TelecallerAssignmentDTO> page = telecallerAssignmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /telecaller-assignments/:id} : get the "id" telecallerAssignment.
     *
     * @param id the id of the telecallerAssignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telecallerAssignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telecaller-assignments/{id}")
    public ResponseEntity<TelecallerAssignmentDTO> getTelecallerAssignment(@PathVariable Long id) {
        log.debug("REST request to get TelecallerAssignment : {}", id);
        Optional<TelecallerAssignmentDTO> telecallerAssignmentDTO = telecallerAssignmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telecallerAssignmentDTO);
    }

    /**
     * {@code DELETE  /telecaller-assignments/:id} : delete the "id" telecallerAssignment.
     *
     * @param id the id of the telecallerAssignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telecaller-assignments/{id}")
    public ResponseEntity<Void> deleteTelecallerAssignment(@PathVariable Long id) {
        log.debug("REST request to delete TelecallerAssignment : {}", id);
        telecallerAssignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
