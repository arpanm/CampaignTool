package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.LeadUploadFile;
import com.reliance.jpl.repository.LeadUploadFileRepository;
import com.reliance.jpl.service.LeadUploadFileService;
import com.reliance.jpl.service.dto.LeadUploadFileDTO;
import com.reliance.jpl.service.mapper.LeadUploadFileMapper;
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
    public LeadUploadFileDTO update(LeadUploadFileDTO leadUploadFileDTO) {
        log.debug("Request to update LeadUploadFile : {}", leadUploadFileDTO);
        LeadUploadFile leadUploadFile = leadUploadFileMapper.toEntity(leadUploadFileDTO);
        leadUploadFile = leadUploadFileRepository.save(leadUploadFile);
        return leadUploadFileMapper.toDto(leadUploadFile);
    }

    @Override
    public Optional<LeadUploadFileDTO> partialUpdate(LeadUploadFileDTO leadUploadFileDTO) {
        log.debug("Request to partially update LeadUploadFile : {}", leadUploadFileDTO);

        return leadUploadFileRepository
            .findById(leadUploadFileDTO.getId())
            .map(existingLeadUploadFile -> {
                leadUploadFileMapper.partialUpdate(existingLeadUploadFile, leadUploadFileDTO);

                return existingLeadUploadFile;
            })
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
