package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.TelecallerAssignmentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.TelecallerAssignment}.
 */
public interface TelecallerAssignmentService {
    /**
     * Save a telecallerAssignment.
     *
     * @param telecallerAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    TelecallerAssignmentDTO save(TelecallerAssignmentDTO telecallerAssignmentDTO);

    /**
     * Partially updates a telecallerAssignment.
     *
     * @param telecallerAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TelecallerAssignmentDTO> partialUpdate(TelecallerAssignmentDTO telecallerAssignmentDTO);

    /**
     * Get all the telecallerAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TelecallerAssignmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" telecallerAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelecallerAssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" telecallerAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
