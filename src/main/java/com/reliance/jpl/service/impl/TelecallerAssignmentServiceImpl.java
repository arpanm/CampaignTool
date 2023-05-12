package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.TelecallerAssignment;
import com.reliance.jpl.repository.TelecallerAssignmentRepository;
import com.reliance.jpl.service.TelecallerAssignmentService;
import com.reliance.jpl.service.dto.TelecallerAssignmentDTO;
import com.reliance.jpl.service.mapper.TelecallerAssignmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TelecallerAssignment}.
 */
@Service
@Transactional
public class TelecallerAssignmentServiceImpl implements TelecallerAssignmentService {

    private final Logger log = LoggerFactory.getLogger(TelecallerAssignmentServiceImpl.class);

    private final TelecallerAssignmentRepository telecallerAssignmentRepository;

    private final TelecallerAssignmentMapper telecallerAssignmentMapper;

    public TelecallerAssignmentServiceImpl(
        TelecallerAssignmentRepository telecallerAssignmentRepository,
        TelecallerAssignmentMapper telecallerAssignmentMapper
    ) {
        this.telecallerAssignmentRepository = telecallerAssignmentRepository;
        this.telecallerAssignmentMapper = telecallerAssignmentMapper;
    }

    @Override
    public TelecallerAssignmentDTO save(TelecallerAssignmentDTO telecallerAssignmentDTO) {
        log.debug("Request to save TelecallerAssignment : {}", telecallerAssignmentDTO);
        TelecallerAssignment telecallerAssignment = telecallerAssignmentMapper.toEntity(telecallerAssignmentDTO);
        telecallerAssignment = telecallerAssignmentRepository.save(telecallerAssignment);
        return telecallerAssignmentMapper.toDto(telecallerAssignment);
    }

    @Override
    public TelecallerAssignmentDTO update(TelecallerAssignmentDTO telecallerAssignmentDTO) {
        log.debug("Request to update TelecallerAssignment : {}", telecallerAssignmentDTO);
        TelecallerAssignment telecallerAssignment = telecallerAssignmentMapper.toEntity(telecallerAssignmentDTO);
        telecallerAssignment = telecallerAssignmentRepository.save(telecallerAssignment);
        return telecallerAssignmentMapper.toDto(telecallerAssignment);
    }

    @Override
    public Optional<TelecallerAssignmentDTO> partialUpdate(TelecallerAssignmentDTO telecallerAssignmentDTO) {
        log.debug("Request to partially update TelecallerAssignment : {}", telecallerAssignmentDTO);

        return telecallerAssignmentRepository
            .findById(telecallerAssignmentDTO.getId())
            .map(existingTelecallerAssignment -> {
                telecallerAssignmentMapper.partialUpdate(existingTelecallerAssignment, telecallerAssignmentDTO);

                return existingTelecallerAssignment;
            })
            .map(telecallerAssignmentRepository::save)
            .map(telecallerAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelecallerAssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TelecallerAssignments");
        return telecallerAssignmentRepository.findAll(pageable).map(telecallerAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelecallerAssignmentDTO> findOne(Long id) {
        log.debug("Request to get TelecallerAssignment : {}", id);
        return telecallerAssignmentRepository.findById(id).map(telecallerAssignmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TelecallerAssignment : {}", id);
        telecallerAssignmentRepository.deleteById(id);
    }
}
