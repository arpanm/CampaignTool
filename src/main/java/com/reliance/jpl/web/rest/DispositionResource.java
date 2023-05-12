package com.reliance.jpl.web.rest;

import com.reliance.jpl.repository.DispositionRepository;
import com.reliance.jpl.service.DispositionService;
import com.reliance.jpl.service.dto.DispositionDTO;
import com.reliance.jpl.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.reliance.jpl.domain.Disposition}.
 */
@RestController
@RequestMapping("/api")
public class DispositionResource {

    private final Logger log = LoggerFactory.getLogger(DispositionResource.class);

    private static final String ENTITY_NAME = "disposition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositionService dispositionService;

    private final DispositionRepository dispositionRepository;

    public DispositionResource(DispositionService dispositionService, DispositionRepository dispositionRepository) {
        this.dispositionService = dispositionService;
        this.dispositionRepository = dispositionRepository;
    }

    /**
     * {@code POST  /dispositions} : Create a new disposition.
     *
     * @param dispositionDTO the dispositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositionDTO, or with status {@code 400 (Bad Request)} if the disposition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dispositions")
    public ResponseEntity<DispositionDTO> createDisposition(@RequestBody DispositionDTO dispositionDTO) throws URISyntaxException {
        log.debug("REST request to save Disposition : {}", dispositionDTO);
        if (dispositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new disposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispositionDTO result = dispositionService.save(dispositionDTO);
        return ResponseEntity
            .created(new URI("/api/dispositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dispositions/:id} : Updates an existing disposition.
     *
     * @param id the id of the dispositionDTO to save.
     * @param dispositionDTO the dispositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositionDTO,
     * or with status {@code 400 (Bad Request)} if the dispositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dispositions/{id}")
    public ResponseEntity<DispositionDTO> updateDisposition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositionDTO dispositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Disposition : {}, {}", id, dispositionDTO);
        if (dispositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DispositionDTO result = dispositionService.update(dispositionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dispositions/:id} : Partial updates given fields of an existing disposition, field will ignore if it is null
     *
     * @param id the id of the dispositionDTO to save.
     * @param dispositionDTO the dispositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositionDTO,
     * or with status {@code 400 (Bad Request)} if the dispositionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispositionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dispositions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DispositionDTO> partialUpdateDisposition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositionDTO dispositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Disposition partially : {}, {}", id, dispositionDTO);
        if (dispositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispositionDTO> result = dispositionService.partialUpdate(dispositionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dispositions} : get all the dispositions.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositions in body.
     */
    @GetMapping("/dispositions")
    public ResponseEntity<List<DispositionDTO>> getAllDispositions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false) String filter
    ) {
        if ("campaign-is-null".equals(filter)) {
            log.debug("REST request to get all Dispositions where campaign is null");
            return new ResponseEntity<>(dispositionService.findAllWhereCampaignIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of Dispositions");
        Page<DispositionDTO> page = dispositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispositions/:id} : get the "id" disposition.
     *
     * @param id the id of the dispositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dispositions/{id}")
    public ResponseEntity<DispositionDTO> getDisposition(@PathVariable Long id) {
        log.debug("REST request to get Disposition : {}", id);
        Optional<DispositionDTO> dispositionDTO = dispositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositionDTO);
    }

    /**
     * {@code DELETE  /dispositions/:id} : delete the "id" disposition.
     *
     * @param id the id of the dispositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dispositions/{id}")
    public ResponseEntity<Void> deleteDisposition(@PathVariable Long id) {
        log.debug("REST request to delete Disposition : {}", id);
        dispositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
