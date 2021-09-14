package com.arpan.campaigntool.service.impl;

import com.arpan.campaigntool.domain.LeadUploadFile;
import com.arpan.campaigntool.repository.LeadUploadFileRepository;
import com.arpan.campaigntool.service.LeadUploadFileService;
import com.arpan.campaigntool.service.dto.LeadUploadFileDTO;
import com.arpan.campaigntool.service.mapper.LeadUploadFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeadUploadFile}.
 */
@Service
@Transactional
public class LeadUploadFileServiceImpl implements LeadUploadFileService {

    private final Logger log = LoggerFactory.getLogger(LeadUploadFileServiceImpl.class);

    private final LeadUploadFileRepository leadUploadFileRepository;

    private final LeadUploadFileMapper leadUploadFileMapper;

    public LeadUploadFileServiceImpl(LeadUploadFileRepository leadUploadFileRepository, LeadUploadFileMapper leadUploadFileMapper) {
        this.leadUploadFileRepository = leadUploadFileRepository;
        this.leadUploadFileMapper = leadUploadFileMapper;
    }

    @Override
    public LeadUploadFileDTO save(LeadUploadFileDTO leadUploadFileDTO) {
        log.debug("Request to save LeadUploadFile : {}", leadUploadFileDTO);
        LeadUploadFile leadUploadFile = leadUploadFileMapper.toEntity(leadUploadFileDTO);
        leadUploadFile = leadUploadFileRepository.save(leadUploadFile);
        return leadUploadFileMapper.toDto(leadUploadFile);
    }

    @Override
    public Optional<LeadUploadFileDTO> partialUpdate(LeadUploadFileDTO leadUploadFileDTO) {
        log.debug("Request to partially update LeadUploadFile : {}", leadUploadFileDTO);

        return leadUploadFileRepository
            .findById(leadUploadFileDTO.getId())
            .map(
                existingLeadUploadFile -> {
                    leadUploadFileMapper.partialUpdate(existingLeadUploadFile, leadUploadFileDTO);

                    return existingLeadUploadFile;
                }
            )
            .map(leadUploadFileRepository::save)
            .map(leadUploadFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeadUploadFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeadUploadFiles");
        return leadUploadFileRepository.findAll(pageable).map(leadUploadFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeadUploadFileDTO> findOne(Long id) {
        log.debug("Request to get LeadUploadFile : {}", id);
        return leadUploadFileRepository.findById(id).map(leadUploadFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeadUploadFile : {}", id);
        leadUploadFileRepository.deleteById(id);
    }
}
