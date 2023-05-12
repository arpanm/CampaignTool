package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.DispositionSubmissionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.DispositionSubmission}.
 */
public interface DispositionSubmissionService {
    /**
     * Save a dispositionSubmission.
     *
     * @param dispositionSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    DispositionSubmissionDTO save(DispositionSubmissionDTO dispositionSubmissionDTO);

    /**
     * Updates a dispositionSubmission.
     *
     * @param dispositionSubmissionDTO the entity to update.
     * @return the persisted entity.
     */
    DispositionSubmissionDTO update(DispositionSubmissionDTO dispositionSubmissionDTO);

    /**
     * Partially updates a dispositionSubmission.
     *
     * @param dispositionSubmissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispositionSubmissionDTO> partialUpdate(DispositionSubmissionDTO dispositionSubmissionDTO);

    /**
     * Get all the dispositionSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispositionSubmissionDTO> findAll(Pageable pageable);
    /**
     * Get all the DispositionSubmissionDTO where Call is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DispositionSubmissionDTO> findAllWhereCallIsNull();

    /**
     * Get the "id" dispositionSubmission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispositionSubmissionDTO> findOne(Long id);

    /**
     * Delete the "id" dispositionSubmission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
