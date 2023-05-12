package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.Lead;
import com.reliance.jpl.repository.LeadRepository;
import com.reliance.jpl.service.LeadService;
import com.reliance.jpl.service.dto.LeadDTO;
import com.reliance.jpl.service.mapper.LeadMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Lead}.
 */
@Service
@Transactional
public class LeadServiceImpl implements LeadService {

    private final Logger log = LoggerFactory.getLogger(LeadServiceImpl.class);

    private final LeadRepository leadRepository;

    private final LeadMapper leadMapper;

    public LeadServiceImpl(LeadRepository leadRepository, LeadMapper leadMapper) {
        this.leadRepository = leadRepository;
        this.leadMapper = leadMapper;
    }

    @Override
    public LeadDTO save(LeadDTO leadDTO) {
        log.debug("Request to save Lead : {}", leadDTO);
        Lead lead = leadMapper.toEntity(leadDTO);
        lead = leadRepository.save(lead);
        return leadMapper.toDto(lead);
    }

    @Override
    public LeadDTO update(LeadDTO leadDTO) {
        log.debug("Request to update Lead : {}", leadDTO);
        Lead lead = leadMapper.toEntity(leadDTO);
        lead = leadRepository.save(lead);
        return leadMapper.toDto(lead);
    }

    @Override
    public Optional<LeadDTO> partialUpdate(LeadDTO leadDTO) {
        log.debug("Request to partially update Lead : {}", leadDTO);

        return leadRepository
            .findById(leadDTO.getId())
            .map(existingLead -> {
                leadMapper.partialUpdate(existingLead, leadDTO);

                return existingLead;
            })
            .map(leadRepository::save)
            .map(leadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Leads");
        return leadRepository.findAll(pageable).map(leadMapper::toDto);
    }

    public Page<LeadDTO> findAllWithEagerRelationships(Pageable pageable) {
        return leadRepository.findAllWithEagerRelationships(pageable).map(leadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeadDTO> findOne(Long id) {
        log.debug("Request to get Lead : {}", id);
        return leadRepository.findOneWithEagerRelationships(id).map(leadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Lead : {}", id);
        leadRepository.deleteById(id);
    }
}
