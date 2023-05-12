package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.LeadAssignmentDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.LeadAssignment}.
 */
public interface LeadAssignmentService {
    /**
     * Save a leadAssignment.
     *
     * @param leadAssignmentDTO the entity to save.
     * @return the persisted entity.
     */
    LeadAssignmentDTO save(LeadAssignmentDTO leadAssignmentDTO);

    /**
     * Updates a leadAssignment.
     *
     * @param leadAssignmentDTO the entity to update.
     * @return the persisted entity.
     */
    LeadAssignmentDTO update(LeadAssignmentDTO leadAssignmentDTO);

    /**
     * Partially updates a leadAssignment.
     *
     * @param leadAssignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LeadAssignmentDTO> partialUpdate(LeadAssignmentDTO leadAssignmentDTO);

    /**
     * Get all the leadAssignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeadAssignmentDTO> findAll(Pageable pageable);
    /**
     * Get all the LeadAssignmentDTO where Call is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<LeadAssignmentDTO> findAllWhereCallIsNull();

    /**
     * Get the "id" leadAssignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeadAssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" leadAssignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
