package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.AttributePossibleValueDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.AttributePossibleValue}.
 */
public interface AttributePossibleValueService {
    /**
     * Save a attributePossibleValue.
     *
     * @param attributePossibleValueDTO the entity to save.
     * @return the persisted entity.
     */
    AttributePossibleValueDTO save(AttributePossibleValueDTO attributePossibleValueDTO);

    /**
     * Partially updates a attributePossibleValue.
     *
     * @param attributePossibleValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttributePossibleValueDTO> partialUpdate(AttributePossibleValueDTO attributePossibleValueDTO);

    /**
     * Get all the attributePossibleValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttributePossibleValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attributePossibleValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttributePossibleValueDTO> findOne(Long id);

    /**
     * Delete the "id" attributePossibleValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
