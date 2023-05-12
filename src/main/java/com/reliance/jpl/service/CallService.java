package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.CallDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.Call}.
 */
public interface CallService {
    /**
     * Save a call.
     *
     * @param callDTO the entity to save.
     * @return the persisted entity.
     */
    CallDTO save(CallDTO callDTO);

    /**
     * Updates a call.
     *
     * @param callDTO the entity to update.
     * @return the persisted entity.
     */
    CallDTO update(CallDTO callDTO);

    /**
     * Partially updates a call.
     *
     * @param callDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CallDTO> partialUpdate(CallDTO callDTO);

    /**
     * Get all the calls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CallDTO> findAll(Pageable pageable);

    /**
     * Get the "id" call.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CallDTO> findOne(Long id);

    /**
     * Delete the "id" call.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
