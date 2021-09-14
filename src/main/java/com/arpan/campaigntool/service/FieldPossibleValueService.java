package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.FieldPossibleValueDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.FieldPossibleValue}.
 */
public interface FieldPossibleValueService {
    /**
     * Save a fieldPossibleValue.
     *
     * @param fieldPossibleValueDTO the entity to save.
     * @return the persisted entity.
     */
    FieldPossibleValueDTO save(FieldPossibleValueDTO fieldPossibleValueDTO);

    /**
     * Partially updates a fieldPossibleValue.
     *
     * @param fieldPossibleValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldPossibleValueDTO> partialUpdate(FieldPossibleValueDTO fieldPossibleValueDTO);

    /**
     * Get all the fieldPossibleValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldPossibleValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fieldPossibleValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldPossibleValueDTO> findOne(Long id);

    /**
     * Delete the "id" fieldPossibleValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
