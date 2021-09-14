package com.arpan.campaigntool.service.impl;

import com.arpan.campaigntool.domain.LeadAssignment;
import com.arpan.campaigntool.repository.LeadAssignmentRepository;
import com.arpan.campaigntool.service.LeadAssignmentService;
import com.arpan.campaigntool.service.dto.LeadAssignmentDTO;
import com.arpan.campaigntool.service.mapper.LeadAssignmentMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LeadAssignment}.
 */
@Service
@Transactional
public class LeadAssignmentServiceImpl implements LeadAssignmentService {

    private final Logger log = LoggerFactory.getLogger(LeadAssignmentServiceImpl.class);

    private final LeadAssignmentRepository leadAssignmentRepository;

    private final LeadAssignmentMapper leadAssignmentMapper;

    public LeadAssignmentServiceImpl(LeadAssignmentRepository leadAssignmentRepository, LeadAssignmentMapper leadAssignmentMapper) {
        this.leadAssignmentRepository = leadAssignmentRepository;
        this.leadAssignmentMapper = leadAssignmentMapper;
    }

    @Override
    public LeadAssignmentDTO save(LeadAssignmentDTO leadAssignmentDTO) {
        log.debug("Request to save LeadAssignment : {}", leadAssignmentDTO);
        LeadAssignment leadAssignment = leadAssignmentMapper.toEntity(leadAssignmentDTO);
        leadAssignment = leadAssignmentRepository.save(leadAssignment);
        return leadAssignmentMapper.toDto(leadAssignment);
    }

    @Override
    public Optional<LeadAssignmentDTO> partialUpdate(LeadAssignmentDTO leadAssignmentDTO) {
        log.debug("Request to partially update LeadAssignment : {}", leadAssignmentDTO);

        return leadAssignmentRepository
            .findById(leadAssignmentDTO.getId())
            .map(
                existingLeadAssignment -> {
                    leadAssignmentMapper.partialUpdate(existingLeadAssignment, leadAssignmentDTO);

                    return existingLeadAssignment;
                }
            )
            .map(leadAssignmentRepository::save)
            .map(leadAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LeadAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeadAssignments");
        return leadAssignmentRepository.findAll(pageable).map(leadAssignmentMapper::toDto);
    }

    /**
     *  Get all the leadAssignments where Call is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LeadAssignmentDTO> findAllWhereCallIsNull() {
        log.debug("Request to get all leadAssignments where Call is null");
        return StreamSupport
            .stream(leadAssignmentRepository.findAll().spliterator(), false)
            .filter(leadAssignment -> leadAssignment.getCall() == null)
            .map(leadAssignmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LeadAssignmentDTO> findOne(Long id) {
        log.debug("Request to get LeadAssignment : {}", id);
        return leadAssignmentRepository.findById(id).map(leadAssignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LeadAssignment : {}", id);
        leadAssignmentRepository.deleteById(id);
    }
}
