package com.arpan.campaigntool.service.impl;

import com.arpan.campaigntool.domain.LeadAssociation;
import com.arpan.campaigntool.repository.LeadAssociationRepository;
import com.arpan.campaigntool.service.LeadAssociationService;
import com.arpan.campaigntool.service.dto.LeadAssociationDTO;
import com.arpan.campaigntool.service.mapper.LeadAssociationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeadAssociation}.
 */
@Service
@Transactional
public class LeadAssociationServiceImpl implements LeadAssociationService {

    private final Logger log = LoggerFactory.getLogger(LeadAssociationServiceImpl.class);

    private final LeadAssociationRepository leadAssociationRepository;

    private final LeadAssociationMapper leadAssociationMapper;

    public LeadAssociationServiceImpl(LeadAssociationRepository leadAssociationRepository, LeadAssociationMapper leadAssociationMapper) {
        this.leadAssociationRepository = leadAssociationRepository;
        this.leadAssociationMapper = leadAssociationMapper;
    }

    @Override
    public LeadAssociationDTO save(LeadAssociationDTO leadAssociationDTO) {
        log.debug("Request to save LeadAssociation : {}", leadAssociationDTO);
        LeadAssociation leadAssociation = leadAssociationMapper.toEntity(leadAssociationDTO);
        leadAssociation = leadAssociationRepository.save(leadAssociation);
        return leadAssociationMapper.toDto(leadAssociation);
    }

    @Override
    public Optional<LeadAssociationDTO> partialUpdate(LeadAssociationDTO leadAssociationDTO) {
        log.debug("Request to partially update LeadAssociation : {}", leadAssociationDTO);

        return leadAssociationRepository
            .findById(leadAssociationDTO.getId())
            .map(
                existingLeadAssociation -> {
                    leadAssociationMapper.partialUpdate(existingLeadAssociation, leadAssociationDTO);

                    return existingLeadAssociation;
                }
            )
            .map(leadAssociationRepository::save)
            .map(leadAssociationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeadAssociationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeadAssociations");
        return leadAssociationRepository.findAll(pageable).map(leadAssociationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeadAssociationDTO> findOne(Long id) {
        log.debug("Request to get LeadAssociation : {}", id);
        return leadAssociationRepository.findById(id).map(leadAssociationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeadAssociation : {}", id);
        leadAssociationRepository.deleteById(id);
    }
}
