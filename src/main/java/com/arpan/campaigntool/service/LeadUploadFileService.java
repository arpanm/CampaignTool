package com.arpan.campaigntool.service;

import com.arpan.campaigntool.service.dto.LeadUploadFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.arpan.campaigntool.domain.LeadUploadFile}.
 */
public interface LeadUploadFileService {
    /**
     * Save a leadUploadFile.
     *
     * @param leadUploadFileDTO the entity to save.
     * @return the persisted entity.
     */
    LeadUploadFileDTO save(LeadUploadFileDTO leadUploadFileDTO);

    /**
     * Partially updates a leadUploadFile.
     *
     * @param leadUploadFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LeadUploadFileDTO> partialUpdate(LeadUploadFileDTO leadUploadFileDTO);

    /**
     * Get all the leadUploadFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeadUploadFileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" leadUploadFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeadUploadFileDTO> findOne(Long id);

    /**
     * Delete the "id" leadUploadFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
