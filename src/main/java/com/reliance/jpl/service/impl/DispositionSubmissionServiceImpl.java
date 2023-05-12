package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.DispositionSubmission;
import com.reliance.jpl.repository.DispositionSubmissionRepository;
import com.reliance.jpl.service.DispositionSubmissionService;
import com.reliance.jpl.service.dto.DispositionSubmissionDTO;
import com.reliance.jpl.service.mapper.DispositionSubmissionMapper;
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
 * Service Implementation for managing {@link DispositionSubmission}.
 */
@Service
@Transactional
public class DispositionSubmissionServiceImpl implements DispositionSubmissionService {

    private final Logger log = LoggerFactory.getLogger(DispositionSubmissionServiceImpl.class);

    private final DispositionSubmissionRepository dispositionSubmissionRepository;

    private final DispositionSubmissionMapper dispositionSubmissionMapper;

    public DispositionSubmissionServiceImpl(
        DispositionSubmissionRepository dispositionSubmissionRepository,
        DispositionSubmissionMapper dispositionSubmissionMapper
    ) {
        this.dispositionSubmissionRepository = dispositionSubmissionRepository;
        this.dispositionSubmissionMapper = dispositionSubmissionMapper;
    }

    @Override
    public DispositionSubmissionDTO save(DispositionSubmissionDTO dispositionSubmissionDTO) {
        log.debug("Request to save DispositionSubmission : {}", dispositionSubmissionDTO);
        DispositionSubmission dispositionSubmission = dispositionSubmissionMapper.toEntity(dispositionSubmissionDTO);
        dispositionSubmission = dispositionSubmissionRepository.save(dispositionSubmission);
        return dispositionSubmissionMapper.toDto(dispositionSubmission);
    }

    @Override
    public DispositionSubmissionDTO update(DispositionSubmissionDTO dispositionSubmissionDTO) {
        log.debug("Request to update DispositionSubmission : {}", dispositionSubmissionDTO);
        DispositionSubmission dispositionSubmission = dispositionSubmissionMapper.toEntity(dispositionSubmissionDTO);
        dispositionSubmission = dispositionSubmissionRepository.save(dispositionSubmission);
        return dispositionSubmissionMapper.toDto(dispositionSubmission);
    }

    @Override
    public Optional<DispositionSubmissionDTO> partialUpdate(DispositionSubmissionDTO dispositionSubmissionDTO) {
        log.debug("Request to partially update DispositionSubmission : {}", dispositionSubmissionDTO);

        return dispositionSubmissionRepository
            .findById(dispositionSubmissionDTO.getId())
            .map(existingDispositionSubmission -> {
                dispositionSubmissionMapper.partialUpdate(existingDispositionSubmission, dispositionSubmissionDTO);

                return existingDispositionSubmission;
            })
            .map(dispositionSubmissionRepository::save)
            .map(dispositionSubmissionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispositionSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DispositionSubmissions");
        return dispositionSubmissionRepository.findAll(pageable).map(dispositionSubmissionMapper::toDto);
    }

    /**
     *  Get all the dispositionSubmissions where Call is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DispositionSubmissionDTO> findAllWhereCallIsNull() {
        log.debug("Request to get all dispositionSubmissions where Call is null");
        return StreamSupport
            .stream(dispositionSubmissionRepository.findAll().spliterator(), false)
            .filter(dispositionSubmission -> dispositionSubmission.getCall() == null)
            .map(dispositionSubmissionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispositionSubmissionDTO> findOne(Long id) {
        log.debug("Request to get DispositionSubmission : {}", id);
        return dispositionSubmissionRepository.findById(id).map(dispositionSubmissionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DispositionSubmission : {}", id);
        dispositionSubmissionRepository.deleteById(id);
    }
}
