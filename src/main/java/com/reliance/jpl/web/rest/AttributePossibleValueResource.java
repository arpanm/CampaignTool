package com.reliance.jpl.web.rest;

import com.reliance.jpl.repository.AttributePossibleValueRepository;
import com.reliance.jpl.service.AttributePossibleValueService;
import com.reliance.jpl.service.dto.AttributePossibleValueDTO;
import com.reliance.jpl.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.reliance.jpl.domain.AttributePossibleValue}.
 */
@RestController
@RequestMapping("/api")
public class AttributePossibleValueResource {

    private final Logger log = LoggerFactory.getLogger(AttributePossibleValueResource.class);

    private static final String ENTITY_NAME = "attributePossibleValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributePossibleValueService attributePossibleValueService;

    private final AttributePossibleValueRepository attributePossibleValueRepository;

    public AttributePossibleValueResource(
        AttributePossibleValueService attributePossibleValueService,
        AttributePossibleValueRepository attributePossibleValueRepository
    ) {
        this.attributePossibleValueService = attributePossibleValueService;
        this.attributePossibleValueRepository = attributePossibleValueRepository;
    }

    /**
     * {@code POST  /attribute-possible-values} : Create a new attributePossibleValue.
     *
     * @param attributePossibleValueDTO the attributePossibleValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributePossibleValueDTO, or with status {@code 400 (Bad Request)} if the attributePossibleValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-possible-values")
    public ResponseEntity<AttributePossibleValueDTO> createAttributePossibleValue(
        @RequestBody AttributePossibleValueDTO attributePossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AttributePossibleValue : {}", attributePossibleValueDTO);
        if (attributePossibleValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributePossibleValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributePossibleValueDTO result = attributePossibleValueService.save(attributePossibleValueDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-possible-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-possible-values/:id} : Updates an existing attributePossibleValue.
     *
     * @param id the id of the attributePossibleValueDTO to save.
     * @param attributePossibleValueDTO the attributePossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributePossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the attributePossibleValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributePossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-possible-values/{id}")
    public ResponseEntity<AttributePossibleValueDTO> updateAttributePossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributePossibleValueDTO attributePossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributePossibleValue : {}, {}", id, attributePossibleValueDTO);
        if (attributePossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributePossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributePossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributePossibleValueDTO result = attributePossibleValueService.update(attributePossibleValueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributePossibleValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-possible-values/:id} : Partial updates given fields of an existing attributePossibleValue, field will ignore if it is null
     *
     * @param id the id of the attributePossibleValueDTO to save.
     * @param attributePossibleValueDTO the attributePossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributePossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the attributePossibleValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributePossibleValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributePossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-possible-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttributePossibleValueDTO> partialUpdateAttributePossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributePossibleValueDTO attributePossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributePossibleValue partially : {}, {}", id, attributePossibleValueDTO);
        if (attributePossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributePossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributePossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributePossibleValueDTO> result = attributePossibleValueService.partialUpdate(attributePossibleValueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributePossibleValueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-possible-values} : get all the attributePossibleValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributePossibleValues in body.
     */
    @GetMapping("/attribute-possible-values")
    public ResponseEntity<List<AttributePossibleValueDTO>> getAllAttributePossibleValues(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of AttributePossibleValues");
        Page<AttributePossibleValueDTO> page = attributePossibleValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attribute-possible-values/:id} : get the "id" attributePossibleValue.
     *
     * @param id the id of the attributePossibleValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributePossibleValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-possible-values/{id}")
    public ResponseEntity<AttributePossibleValueDTO> getAttributePossibleValue(@PathVariable Long id) {
        log.debug("REST request to get AttributePossibleValue : {}", id);
        Optional<AttributePossibleValueDTO> attributePossibleValueDTO = attributePossibleValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributePossibleValueDTO);
    }

    /**
     * {@code DELETE  /attribute-possible-values/:id} : delete the "id" attributePossibleValue.
     *
     * @param id the id of the attributePossibleValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-possible-values/{id}")
    public ResponseEntity<Void> deleteAttributePossibleValue(@PathVariable Long id) {
        log.debug("REST request to delete AttributePossibleValue : {}", id);
        attributePossibleValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
