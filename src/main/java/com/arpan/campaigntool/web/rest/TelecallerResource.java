package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.TelecallerRepository;
import com.arpan.campaigntool.service.TelecallerService;
import com.arpan.campaigntool.service.dto.TelecallerDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.Telecaller}.
 */
@RestController
@RequestMapping("/api")
public class TelecallerResource {

    private final Logger log = LoggerFactory.getLogger(TelecallerResource.class);

    private static final String ENTITY_NAME = "telecaller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelecallerService telecallerService;

    private final TelecallerRepository telecallerRepository;

    public TelecallerResource(TelecallerService telecallerService, TelecallerRepository telecallerRepository) {
        this.telecallerService = telecallerService;
        this.telecallerRepository = telecallerRepository;
    }

    /**
     * {@code POST  /telecallers} : Create a new telecaller.
     *
     * @param telecallerDTO the telecallerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telecallerDTO, or with status {@code 400 (Bad Request)} if the telecaller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/telecallers")
    public ResponseEntity<TelecallerDTO> createTelecaller(@RequestBody TelecallerDTO telecallerDTO) throws URISyntaxException {
        log.debug("REST request to save Telecaller : {}", telecallerDTO);
        if (telecallerDTO.getId() != null) {
            throw new BadRequestAlertException("A new telecaller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TelecallerDTO result = telecallerService.save(telecallerDTO);
        return ResponseEntity
            .created(new URI("/api/telecallers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /telecallers/:id} : Updates an existing telecaller.
     *
     * @param id the id of the telecallerDTO to save.
     * @param telecallerDTO the telecallerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telecallerDTO,
     * or with status {@code 400 (Bad Request)} if the telecallerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telecallerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/telecallers/{id}")
    public ResponseEntity<TelecallerDTO> updateTelecaller(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelecallerDTO telecallerDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Telecaller : {}, {}", id, telecallerDTO);
        if (telecallerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telecallerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telecallerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TelecallerDTO result = telecallerService.save(telecallerDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telecallerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /telecallers/:id} : Partial updates given fields of an existing telecaller, field will ignore if it is null
     *
     * @param id the id of the telecallerDTO to save.
     * @param telecallerDTO the telecallerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telecallerDTO,
     * or with status {@code 400 (Bad Request)} if the telecallerDTO is not valid,
     * or with status {@code 404 (Not Found)} if the telecallerDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the telecallerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/telecallers/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TelecallerDTO> partialUpdateTelecaller(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TelecallerDTO telecallerDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Telecaller partially : {}, {}", id, telecallerDTO);
        if (telecallerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telecallerDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telecallerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelecallerDTO> result = telecallerService.partialUpdate(telecallerDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telecallerDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /telecallers} : get all the telecallers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telecallers in body.
     */
    @GetMapping("/telecallers")
    public ResponseEntity<List<TelecallerDTO>> getAllTelecallers(Pageable pageable) {
        log.debug("REST request to get a page of Telecallers");
        Page<TelecallerDTO> page = telecallerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /telecallers/:id} : get the "id" telecaller.
     *
     * @param id the id of the telecallerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telecallerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/telecallers/{id}")
    public ResponseEntity<TelecallerDTO> getTelecaller(@PathVariable Long id) {
        log.debug("REST request to get Telecaller : {}", id);
        Optional<TelecallerDTO> telecallerDTO = telecallerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telecallerDTO);
    }

    /**
     * {@code DELETE  /telecallers/:id} : delete the "id" telecaller.
     *
     * @param id the id of the telecallerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/telecallers/{id}")
    public ResponseEntity<Void> deleteTelecaller(@PathVariable Long id) {
        log.debug("REST request to delete Telecaller : {}", id);
        telecallerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
