package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.FieldPossibleValueRepository;
import com.arpan.campaigntool.service.FieldPossibleValueService;
import com.arpan.campaigntool.service.dto.FieldPossibleValueDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.FieldPossibleValue}.
 */
@RestController
@RequestMapping("/api")
public class FieldPossibleValueResource {

    private final Logger log = LoggerFactory.getLogger(FieldPossibleValueResource.class);

    private static final String ENTITY_NAME = "fieldPossibleValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldPossibleValueService fieldPossibleValueService;

    private final FieldPossibleValueRepository fieldPossibleValueRepository;

    public FieldPossibleValueResource(
        FieldPossibleValueService fieldPossibleValueService,
        FieldPossibleValueRepository fieldPossibleValueRepository
    ) {
        this.fieldPossibleValueService = fieldPossibleValueService;
        this.fieldPossibleValueRepository = fieldPossibleValueRepository;
    }

    /**
     * {@code POST  /field-possible-values} : Create a new fieldPossibleValue.
     *
     * @param fieldPossibleValueDTO the fieldPossibleValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldPossibleValueDTO, or with status {@code 400 (Bad Request)} if the fieldPossibleValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-possible-values")
    public ResponseEntity<FieldPossibleValueDTO> createFieldPossibleValue(@RequestBody FieldPossibleValueDTO fieldPossibleValueDTO)
        throws URISyntaxException {
        log.debug("REST request to save FieldPossibleValue : {}", fieldPossibleValueDTO);
        if (fieldPossibleValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldPossibleValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldPossibleValueDTO result = fieldPossibleValueService.save(fieldPossibleValueDTO);
        return ResponseEntity
            .created(new URI("/api/field-possible-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-possible-values/:id} : Updates an existing fieldPossibleValue.
     *
     * @param id the id of the fieldPossibleValueDTO to save.
     * @param fieldPossibleValueDTO the fieldPossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldPossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the fieldPossibleValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldPossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-possible-values/{id}")
    public ResponseEntity<FieldPossibleValueDTO> updateFieldPossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldPossibleValueDTO fieldPossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FieldPossibleValue : {}, {}", id, fieldPossibleValueDTO);
        if (fieldPossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldPossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldPossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldPossibleValueDTO result = fieldPossibleValueService.save(fieldPossibleValueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldPossibleValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /field-possible-values/:id} : Partial updates given fields of an existing fieldPossibleValue, field will ignore if it is null
     *
     * @param id the id of the fieldPossibleValueDTO to save.
     * @param fieldPossibleValueDTO the fieldPossibleValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldPossibleValueDTO,
     * or with status {@code 400 (Bad Request)} if the fieldPossibleValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldPossibleValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldPossibleValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/field-possible-values/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FieldPossibleValueDTO> partialUpdateFieldPossibleValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FieldPossibleValueDTO fieldPossibleValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FieldPossibleValue partially : {}, {}", id, fieldPossibleValueDTO);
        if (fieldPossibleValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldPossibleValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldPossibleValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldPossibleValueDTO> result = fieldPossibleValueService.partialUpdate(fieldPossibleValueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldPossibleValueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /field-possible-values} : get all the fieldPossibleValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldPossibleValues in body.
     */
    @GetMapping("/field-possible-values")
    public ResponseEntity<List<FieldPossibleValueDTO>> getAllFieldPossibleValues(Pageable pageable) {
        log.debug("REST request to get a page of FieldPossibleValues");
        Page<FieldPossibleValueDTO> page = fieldPossibleValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-possible-values/:id} : get the "id" fieldPossibleValue.
     *
     * @param id the id of the fieldPossibleValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldPossibleValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-possible-values/{id}")
    public ResponseEntity<FieldPossibleValueDTO> getFieldPossibleValue(@PathVariable Long id) {
        log.debug("REST request to get FieldPossibleValue : {}", id);
        Optional<FieldPossibleValueDTO> fieldPossibleValueDTO = fieldPossibleValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldPossibleValueDTO);
    }

    /**
     * {@code DELETE  /field-possible-values/:id} : delete the "id" fieldPossibleValue.
     *
     * @param id the id of the fieldPossibleValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-possible-values/{id}")
    public ResponseEntity<Void> deleteFieldPossibleValue(@PathVariable Long id) {
        log.debug("REST request to delete FieldPossibleValue : {}", id);
        fieldPossibleValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
