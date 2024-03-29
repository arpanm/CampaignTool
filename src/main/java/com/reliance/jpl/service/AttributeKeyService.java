package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.AttributeKeyDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.AttributeKey}.
 */
public interface AttributeKeyService {
    /**
     * Save a attributeKey.
     *
     * @param attributeKeyDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeKeyDTO save(AttributeKeyDTO attributeKeyDTO);

    /**
     * Updates a attributeKey.
     *
     * @param attributeKeyDTO the entity to update.
     * @return the persisted entity.
     */
    AttributeKeyDTO update(AttributeKeyDTO attributeKeyDTO);

    /**
     * Partially updates a attributeKey.
     *
     * @param attributeKeyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeKeyDTO> partialUpdate(AttributeKeyDTO attributeKeyDTO);

    /**
     * Get all the attributeKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttributeKeyDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attributeKey.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeKeyDTO> findOne(Long id);

    /**
     * Delete the "id" attributeKey.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
