package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.TelecallerInOutDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.TelecallerInOut}.
 */
public interface TelecallerInOutService {
    /**
     * Save a telecallerInOut.
     *
     * @param telecallerInOutDTO the entity to save.
     * @return the persisted entity.
     */
    TelecallerInOutDTO save(TelecallerInOutDTO telecallerInOutDTO);

    /**
     * Partially updates a telecallerInOut.
     *
     * @param telecallerInOutDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TelecallerInOutDTO> partialUpdate(TelecallerInOutDTO telecallerInOutDTO);

    /**
     * Get all the telecallerInOuts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TelecallerInOutDTO> findAll(Pageable pageable);

    /**
     * Get the "id" telecallerInOut.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TelecallerInOutDTO> findOne(Long id);

    /**
     * Delete the "id" telecallerInOut.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
