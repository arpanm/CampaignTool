package com.reliance.jpl.web.rest;

import com.reliance.jpl.repository.TelecallerInOutRepository;
import com.reliance.jpl.service.TelecallerInOutService;
import com.reliance.jpl.service.dto.TelecallerInOutDTO;
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
 * REST controller for managing {@link com.reliance.jpl.domain.TelecallerInOut}.
 */
@RestController
@RequestMapping("/api")
public class TelecallerInOutResource {

    private final Logger log = LoggerFactory.getLogger(TelecallerInOutResource.class);

    private static final String ENTITY_NAME = "telecallerInOut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelecallerInOutService telecallerInOutService;

    private final TelecallerInOutRepository telecallerInOutRepository;

    public TelecallerInOutResource(TelecallerInOutService telecallerInOutService, TelecallerInOutRepository telecallerInOutRepository) {
        this.telecallerInOutService = telecallerInOutService;
        this.telecallerInOutRepository = telecallerInOutRepository;
    }

    /**
     * {@code POST  /telecaller-in-outs} : Create a new telecallerInOut.
     *
     * @param telecallerInOutDTO the telecallerInOutDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telecallerInOutDTO, or with status {@code 400 (Bad Request)} if the telecallerInOut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telecaller-in-outs")
    public ResponseEntity<TelecallerInOutDTO> createTelecallerInOut(@RequestBody TelecallerInOutDTO telecallerInOutDTO)
        throws URISyntaxException {
        log.debug("REST request to save TelecallerInOut : {}", telecallerInOutDTO);
        if (telecallerInOutDTO.getId() != null) {
            throw new BadRequestAlertException("A new telecallerInOut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelecallerInOutDTO result = telecallerInOutService.save(telecallerInOutDTO);
        return ResponseEntity
            .created(new URI("/api/telecaller-in-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telecaller-in-outs/:id} : Updates an existing telecallerInOut.
     *
     * @param id the id of the telecallerInOutDTO to save.
     * @param telecallerInOutDTO the telecallerInOutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telecallerInOutDTO,
     * or with status {@code 400 (Bad Request)} if the telecallerInOutDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telecallerInOutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telecaller-in-outs/{id}")
    public ResponseEntity<TelecallerInOutDTO> updateTelecallerInOut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelecallerInOutDTO telecallerInOutDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TelecallerInOut : {}, {}", id, telecallerInOutDTO);
        if (telecallerInOutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telecallerInOutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telecallerInOutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelecallerInOutDTO result = telecallerInOutService.update(telecallerInOutDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telecallerInOutDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telecaller-in-outs/:id} : Partial updates given fields of an existing telecallerInOut, field will ignore if it is null
     *
     * @param id the id of the telecallerInOutDTO to save.
     * @param telecallerInOutDTO the telecallerInOutDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telecallerInOutDTO,
     * or with status {@code 400 (Bad Request)} if the telecallerInOutDTO is not valid,
     * or with status {@code 404 (Not Found)} if the telecallerInOutDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the telecallerInOutDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telecaller-in-outs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TelecallerInOutDTO> partialUpdateTelecallerInOut(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelecallerInOutDTO telecallerInOutDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TelecallerInOut partially : {}, {}", id, telecallerInOutDTO);
        if (telecallerInOutDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telecallerInOutDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telecallerInOutRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelecallerInOutDTO> result = telecallerInOutService.partialUpdate(telecallerInOutDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telecallerInOutDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /telecaller-in-outs} : get all the telecallerInOuts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telecallerInOuts in body.
     */
    @GetMapping("/telecaller-in-outs")
    public ResponseEntity<List<TelecallerInOutDTO>> getAllTelecallerInOuts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of TelecallerInOuts");
        Page<TelecallerInOutDTO> page = telecallerInOutService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /telecaller-in-outs/:id} : get the "id" telecallerInOut.
     *
     * @param id the id of the telecallerInOutDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telecallerInOutDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telecaller-in-outs/{id}")
    public ResponseEntity<TelecallerInOutDTO> getTelecallerInOut(@PathVariable Long id) {
        log.debug("REST request to get TelecallerInOut : {}", id);
        Optional<TelecallerInOutDTO> telecallerInOutDTO = telecallerInOutService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telecallerInOutDTO);
    }

    /**
     * {@code DELETE  /telecaller-in-outs/:id} : delete the "id" telecallerInOut.
     *
     * @param id the id of the telecallerInOutDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telecaller-in-outs/{id}")
    public ResponseEntity<Void> deleteTelecallerInOut(@PathVariable Long id) {
        log.debug("REST request to delete TelecallerInOut : {}", id);
        telecallerInOutService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
