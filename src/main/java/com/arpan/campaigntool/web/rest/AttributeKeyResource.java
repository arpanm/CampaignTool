package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.AttributeKeyRepository;
import com.arpan.campaigntool.service.AttributeKeyService;
import com.arpan.campaigntool.service.dto.AttributeKeyDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.AttributeKey}.
 */
@RestController
@RequestMapping("/api")
public class AttributeKeyResource {

    private final Logger log = LoggerFactory.getLogger(AttributeKeyResource.class);

    private static final String ENTITY_NAME = "attributeKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeKeyService attributeKeyService;

    private final AttributeKeyRepository attributeKeyRepository;

    public AttributeKeyResource(AttributeKeyService attributeKeyService, AttributeKeyRepository attributeKeyRepository) {
        this.attributeKeyService = attributeKeyService;
        this.attributeKeyRepository = attributeKeyRepository;
    }

    /**
     * {@code POST  /attribute-keys} : Create a new attributeKey.
     *
     * @param attributeKeyDTO the attributeKeyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeKeyDTO, or with status {@code 400 (Bad Request)} if the attributeKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-keys")
    public ResponseEntity<AttributeKeyDTO> createAttributeKey(@RequestBody AttributeKeyDTO attributeKeyDTO) throws URISyntaxException {
        log.debug("REST request to save AttributeKey : {}", attributeKeyDTO);
        if (attributeKeyDTO.getId() != null) {
            throw new BadRequestAlertException("A new attributeKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeKeyDTO result = attributeKeyService.save(attributeKeyDTO);
        return ResponseEntity
            .created(new URI("/api/attribute-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-keys/:id} : Updates an existing attributeKey.
     *
     * @param id the id of the attributeKeyDTO to save.
     * @param attributeKeyDTO the attributeKeyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeKeyDTO,
     * or with status {@code 400 (Bad Request)} if the attributeKeyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeKeyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-keys/{id}")
    public ResponseEntity<AttributeKeyDTO> updateAttributeKey(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeKeyDTO attributeKeyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AttributeKey : {}, {}", id, attributeKeyDTO);
        if (attributeKeyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeKeyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeKeyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AttributeKeyDTO result = attributeKeyService.save(attributeKeyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeKeyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /attribute-keys/:id} : Partial updates given fields of an existing attributeKey, field will ignore if it is null
     *
     * @param id the id of the attributeKeyDTO to save.
     * @param attributeKeyDTO the attributeKeyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeKeyDTO,
     * or with status {@code 400 (Bad Request)} if the attributeKeyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attributeKeyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attributeKeyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/attribute-keys/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AttributeKeyDTO> partialUpdateAttributeKey(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AttributeKeyDTO attributeKeyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AttributeKey partially : {}, {}", id, attributeKeyDTO);
        if (attributeKeyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attributeKeyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attributeKeyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttributeKeyDTO> result = attributeKeyService.partialUpdate(attributeKeyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attributeKeyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attribute-keys} : get all the attributeKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeKeys in body.
     */
    @GetMapping("/attribute-keys")
    public ResponseEntity<List<AttributeKeyDTO>> getAllAttributeKeys(Pageable pageable) {
        log.debug("REST request to get a page of AttributeKeys");
        Page<AttributeKeyDTO> page = attributeKeyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attribute-keys/:id} : get the "id" attributeKey.
     *
     * @param id the id of the attributeKeyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeKeyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-keys/{id}")
    public ResponseEntity<AttributeKeyDTO> getAttributeKey(@PathVariable Long id) {
        log.debug("REST request to get AttributeKey : {}", id);
        Optional<AttributeKeyDTO> attributeKeyDTO = attributeKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeKeyDTO);
    }

    /**
     * {@code DELETE  /attribute-keys/:id} : delete the "id" attributeKey.
     *
     * @param id the id of the attributeKeyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-keys/{id}")
    public ResponseEntity<Void> deleteAttributeKey(@PathVariable Long id) {
        log.debug("REST request to delete AttributeKey : {}", id);
        attributeKeyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
