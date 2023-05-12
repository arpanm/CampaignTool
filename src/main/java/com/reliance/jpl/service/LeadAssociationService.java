package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.LeadAssociationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.LeadAssociation}.
 */
public interface LeadAssociationService {
    /**
     * Save a leadAssociation.
     *
     * @param leadAssociationDTO the entity to save.
     * @return the persisted entity.
     */
    LeadAssociationDTO save(LeadAssociationDTO leadAssociationDTO);

    /**
     * Updates a leadAssociation.
     *
     * @param leadAssociationDTO the entity to update.
     * @return the persisted entity.
     */
    LeadAssociationDTO update(LeadAssociationDTO leadAssociationDTO);

    /**
     * Partially updates a leadAssociation.
     *
     * @param leadAssociationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LeadAssociationDTO> partialUpdate(LeadAssociationDTO leadAssociationDTO);

    /**
     * Get all the leadAssociations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeadAssociationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" leadAssociation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeadAssociationDTO> findOne(Long id);

    /**
     * Delete the "id" leadAssociation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
