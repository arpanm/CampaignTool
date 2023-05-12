package com.reliance.jpl.service;

import com.reliance.jpl.service.dto.TelecallerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reliance.jpl.domain.Telecaller}.
 */
public interface TelecallerService {
    /**
     * Save a telecaller.
     *
     * @param telecallerDTO the entity to save.
     * @return the persisted entity.
     */
    TelecallerDTO save(TelecallerDTO telecallerDTO);

    /**
     * Updates a telecaller.
     *
     * @param telecallerDTO the entity to update.
     * @return the persisted entity.
     */
    TelecallerDTO update(TelecallerDTO telecallerDTO);

    /**
     * Partially updates a telecaller.
     *
     * @param telecallerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TelecallerDTO> partialUpdate(TelecallerDTO telecallerDTO);

    /**
     * Get all the telecallers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TelecallerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" telecaller.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelecallerDTO> findOne(Long id);

    /**
     * Delete the "id" telecaller.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
