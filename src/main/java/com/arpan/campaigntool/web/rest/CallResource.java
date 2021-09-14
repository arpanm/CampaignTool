package com.arpan.campaigntool.web.rest;

import com.arpan.campaigntool.repository.CallRepository;
import com.arpan.campaigntool.service.CallService;
import com.arpan.campaigntool.service.dto.CallDTO;
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
 * REST controller for managing {@link com.arpan.campaigntool.domain.Call}.
 */
@RestController
@RequestMapping("/api")
public class CallResource {

    private final Logger log = LoggerFactory.getLogger(CallResource.class);

    private static final String ENTITY_NAME = "call";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CallService callService;

    private final CallRepository callRepository;

    public CallResource(CallService callService, CallRepository callRepository) {
        this.callService = callService;
        this.callRepository = callRepository;
    }

    /**
     * {@code POST  /calls} : Create a new call.
     *
     * @param callDTO the callDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new callDTO, or with status {@code 400 (Bad Request)} if the call has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calls")
    public ResponseEntity<CallDTO> createCall(@RequestBody CallDTO callDTO) throws URISyntaxException {
        log.debug("REST request to save Call : {}", callDTO);
        if (callDTO.getId() != null) {
            throw new BadRequestAlertException("A new call cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CallDTO result = callService.save(callDTO);
        return ResponseEntity
            .created(new URI("/api/calls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calls/:id} : Updates an existing call.
     *
     * @param id the id of the callDTO to save.
     * @param callDTO the callDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated callDTO,
     * or with status {@code 400 (Bad Request)} if the callDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the callDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calls/{id}")
    public ResponseEntity<CallDTO> updateCall(@PathVariable(value = "id", required = false) final Long id, @RequestBody CallDTO callDTO)
        throws URISyntaxException {
        log.debug("REST request to update Call : {}, {}", id, callDTO);
        if (callDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, callDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!callRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CallDTO result = callService.save(callDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, callDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /calls/:id} : Partial updates given fields of an existing call, field will ignore if it is null
     *
     * @param id the id of the callDTO to save.
     * @param callDTO the callDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated callDTO,
     * or with status {@code 400 (Bad Request)} if the callDTO is not valid,
     * or with status {@code 404 (Not Found)} if the callDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the callDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calls/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CallDTO> partialUpdateCall(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CallDTO callDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Call partially : {}, {}", id, callDTO);
        if (callDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, callDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!callRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CallDTO> result = callService.partialUpdate(callDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, callDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /calls} : get all the calls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calls in body.
     */
    @GetMapping("/calls")
    public ResponseEntity<List<CallDTO>> getAllCalls(Pageable pageable) {
        log.debug("REST request to get a page of Calls");
        Page<CallDTO> page = callService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calls/:id} : get the "id" call.
     *
     * @param id the id of the callDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the callDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calls/{id}")
    public ResponseEntity<CallDTO> getCall(@PathVariable Long id) {
        log.debug("REST request to get Call : {}", id);
        Optional<CallDTO> callDTO = callService.findOne(id);
        return ResponseUtil.wrapOrNotFound(callDTO);
    }

    /**
     * {@code DELETE  /calls/:id} : delete the "id" call.
     *
     * @param id the id of the callDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calls/{id}")
    public ResponseEntity<Void> deleteCall(@PathVariable Long id) {
        log.debug("REST request to delete Call : {}", id);
        callService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
