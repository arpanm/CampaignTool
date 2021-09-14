package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.FieldDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.Field}.
 */
public interface FieldService {
    /**
     * Save a field.
     *
     * @param fieldDTO the entity to save.
     * @return the persisted entity.
     */
    FieldDTO save(FieldDTO fieldDTO);

    /**
     * Partially updates a field.
     *
     * @param fieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldDTO> partialUpdate(FieldDTO fieldDTO);

    /**
     * Get all the fields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldDTO> findAll(Pageable pageable);

    /**
     * Get the "id" field.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldDTO> findOne(Long id);

    /**
     * Delete the "id" field.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
