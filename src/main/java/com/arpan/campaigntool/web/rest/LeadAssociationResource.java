package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.LeadAssociationRepository;
import com.arpan.campaigntool.service.LeadAssociationService;
import com.arpan.campaigntool.service.dto.LeadAssociationDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.LeadAssociation}.
 */
@RestController
@RequestMapping("/api")
public class LeadAssociationResource {

    private final Logger log = LoggerFactory.getLogger(LeadAssociationResource.class);

    private static final String ENTITY_NAME = "leadAssociation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeadAssociationService leadAssociationService;

    private final LeadAssociationRepository leadAssociationRepository;

    public LeadAssociationResource(LeadAssociationService leadAssociationService, LeadAssociationRepository leadAssociationRepository) {
        this.leadAssociationService = leadAssociationService;
        this.leadAssociationRepository = leadAssociationRepository;
    }

    /**
     * {@code POST  /lead-associations} : Create a new leadAssociation.
     *
     * @param leadAssociationDTO the leadAssociationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leadAssociationDTO, or with status {@code 400 (Bad Request)} if the leadAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lead-associations")
    public ResponseEntity<LeadAssociationDTO> createLeadAssociation(@RequestBody LeadAssociationDTO leadAssociationDTO)
        throws URISyntaxException {
        log.debug("REST request to save LeadAssociation : {}", leadAssociationDTO);
        if (leadAssociationDTO.getId() != null) {
            throw new BadRequestAlertException("A new leadAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeadAssociationDTO result = leadAssociationService.save(leadAssociationDTO);
        return ResponseEntity
            .created(new URI("/api/lead-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lead-associations/:id} : Updates an existing leadAssociation.
     *
     * @param id the id of the leadAssociationDTO to save.
     * @param leadAssociationDTO the leadAssociationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadAssociationDTO,
     * or with status {@code 400 (Bad Request)} if the leadAssociationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the leadAssociationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lead-associations/{id}")
    public ResponseEntity<LeadAssociationDTO> updateLeadAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeadAssociationDTO leadAssociationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LeadAssociation : {}, {}", id, leadAssociationDTO);
        if (leadAssociationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leadAssociationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leadAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LeadAssociationDTO result = leadAssociationService.save(leadAssociationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadAssociationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /lead-associations/:id} : Partial updates given fields of an existing leadAssociation, field will ignore if it is null
     *
     * @param id the id of the leadAssociationDTO to save.
     * @param leadAssociationDTO the leadAssociationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leadAssociationDTO,
     * or with status {@code 400 (Bad Request)} if the leadAssociationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leadAssociationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the leadAssociationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lead-associations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LeadAssociationDTO> partialUpdateLeadAssociation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LeadAssociationDTO leadAssociationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LeadAssociation partially : {}, {}", id, leadAssociationDTO);
        if (leadAssociationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, leadAssociationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!leadAssociationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LeadAssociationDTO> result = leadAssociationService.partialUpdate(leadAssociationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, leadAssociationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /lead-associations} : get all the leadAssociations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leadAssociations in body.
     */
    @GetMapping("/lead-associations")
    public ResponseEntity<List<LeadAssociationDTO>> getAllLeadAssociations(Pageable pageable) {
        log.debug("REST request to get a page of LeadAssociations");
        Page<LeadAssociationDTO> page = leadAssociationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lead-associations/:id} : get the "id" leadAssociation.
     *
     * @param id the id of the leadAssociationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leadAssociationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lead-associations/{id}")
    public ResponseEntity<LeadAssociationDTO> getLeadAssociation(@PathVariable Long id) {
        log.debug("REST request to get LeadAssociation : {}", id);
        Optional<LeadAssociationDTO> leadAssociationDTO = leadAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leadAssociationDTO);
    }

    /**
     * {@code DELETE  /lead-associations/:id} : delete the "id" leadAssociation.
     *
     * @param id the id of the leadAssociationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lead-associations/{id}")
    public ResponseEntity<Void> deleteLeadAssociation(@PathVariable Long id) {
        log.debug("REST request to delete LeadAssociation : {}", id);
        leadAssociationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
