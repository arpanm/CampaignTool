package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.AttributeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.Attribute}.
 */
public interface AttributeService {
    /**
     * Save a attribute.
     *
     * @param attributeDTO the entity to save.
     * @return the persisted entity.
     */
    AttributeDTO save(AttributeDTO attributeDTO);

    /**
     * Partially updates a attribute.
     *
     * @param attributeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributeDTO> partialUpdate(AttributeDTO attributeDTO);

    /**
     * Get all the attributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttributeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributeDTO> findOne(Long id);

    /**
     * Delete the "id" attribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
