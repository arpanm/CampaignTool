package com.arpan.campaigntool.service.impl;

import com.arpan.campaigntool.domain.DispositionSubmissionValue;
import com.arpan.campaigntool.repository.DispositionSubmissionValueRepository;
import com.arpan.campaigntool.service.DispositionSubmissionValueService;
import com.arpan.campaigntool.service.dto.DispositionSubmissionValueDTO;
import com.arpan.campaigntool.service.mapper.DispositionSubmissionValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DispositionSubmissionValue}.
 */
@Service
@Transactional
public class DispositionSubmissionValueServiceImpl implements DispositionSubmissionValueService {

    private final Logger log = LoggerFactory.getLogger(DispositionSubmissionValueServiceImpl.class);

    private final DispositionSubmissionValueRepository dispositionSubmissionValueRepository;

    private final DispositionSubmissionValueMapper dispositionSubmissionValueMapper;

    public DispositionSubmissionValueServiceImpl(
        DispositionSubmissionValueRepository dispositionSubmissionValueRepository,
        DispositionSubmissionValueMapper dispositionSubmissionValueMapper
    ) {
        this.dispositionSubmissionValueRepository = dispositionSubmissionValueRepository;
        this.dispositionSubmissionValueMapper = dispositionSubmissionValueMapper;
    }

    @Override
    public DispositionSubmissionValueDTO save(DispositionSubmissionValueDTO dispositionSubmissionValueDTO) {
        log.debug("Request to save DispositionSubmissionValue : {}", dispositionSubmissionValueDTO);
        DispositionSubmissionValue dispositionSubmissionValue = dispositionSubmissionValueMapper.toEntity(dispositionSubmissionValueDTO);
        dispositionSubmissionValue = dispositionSubmissionValueRepository.save(dispositionSubmissionValue);
        return dispositionSubmissionValueMapper.toDto(dispositionSubmissionValue);
    }

    @Override
    public Optional<DispositionSubmissionValueDTO> partialUpdate(DispositionSubmissionValueDTO dispositionSubmissionValueDTO) {
        log.debug("Request to partially update DispositionSubmissionValue : {}", dispositionSubmissionValueDTO);

        return dispositionSubmissionValueRepository
            .findById(dispositionSubmissionValueDTO.getId())
            .map(
                existingDispositionSubmissionValue -> {
                    dispositionSubmissionValueMapper.partialUpdate(existingDispositionSubmissionValue, dispositionSubmissionValueDTO);

                    return existingDispositionSubmissionValue;
                }
            )
            .map(dispositionSubmissionValueRepository::save)
            .map(dispositionSubmissionValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispositionSubmissionValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DispositionSubmissionValues");
        return dispositionSubmissionValueRepository.findAll(pageable).map(dispositionSubmissionValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispositionSubmissionValueDTO> findOne(Long id) {
        log.debug("Request to get DispositionSubmissionValue : {}", id);
        return dispositionSubmissionValueRepository.findById(id).map(dispositionSubmissionValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DispositionSubmissionValue : {}", id);
        dispositionSubmissionValueRepository.deleteById(id);
    }
}
