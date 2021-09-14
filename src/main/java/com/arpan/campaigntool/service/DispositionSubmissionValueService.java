package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.DispositionSubmissionValueDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.DispositionSubmissionValue}.
 */
public interface DispositionSubmissionValueService {
    /**
     * Save a dispositionSubmissionValue.
     *
     * @param dispositionSubmissionValueDTO the entity to save.
     * @return the persisted entity.
     */
    DispositionSubmissionValueDTO save(DispositionSubmissionValueDTO dispositionSubmissionValueDTO);

    /**
     * Partially updates a dispositionSubmissionValue.
     *
     * @param dispositionSubmissionValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispositionSubmissionValueDTO> partialUpdate(DispositionSubmissionValueDTO dispositionSubmissionValueDTO);

    /**
     * Get all the dispositionSubmissionValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispositionSubmissionValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dispositionSubmissionValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispositionSubmissionValueDTO> findOne(Long id);

    /**
     * Delete the "id" dispositionSubmissionValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
