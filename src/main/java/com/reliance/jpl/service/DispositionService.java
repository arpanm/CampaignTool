package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.DispositionDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.Disposition}.
 */
public interface DispositionService {
    /**
     * Save a disposition.
     *
     * @param dispositionDTO the entity to save.
     * @return the persisted entity.
     */
    DispositionDTO save(DispositionDTO dispositionDTO);

    /**
     * Updates a disposition.
     *
     * @param dispositionDTO the entity to update.
     * @return the persisted entity.
     */
    DispositionDTO update(DispositionDTO dispositionDTO);

    /**
     * Partially updates a disposition.
     *
     * @param dispositionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DispositionDTO> partialUpdate(DispositionDTO dispositionDTO);

    /**
     * Get all the dispositions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DispositionDTO> findAll(Pageable pageable);
    /**
     * Get all the DispositionDTO where Campaign is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DispositionDTO> findAllWhereCampaignIsNull();

    /**
     * Get the "id" disposition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DispositionDTO> findOne(Long id);

    /**
     * Delete the "id" disposition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
