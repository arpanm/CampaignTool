package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.FieldPossibleValue;
import com.reliance.jpl.repository.FieldPossibleValueRepository;
import com.reliance.jpl.service.FieldPossibleValueService;
import com.reliance.jpl.service.dto.FieldPossibleValueDTO;
import com.reliance.jpl.service.mapper.FieldPossibleValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FieldPossibleValue}.
 */
@Service
@Transactional
public class FieldPossibleValueServiceImpl implements FieldPossibleValueService {

    private final Logger log = LoggerFactory.getLogger(FieldPossibleValueServiceImpl.class);

    private final FieldPossibleValueRepository fieldPossibleValueRepository;

    private final FieldPossibleValueMapper fieldPossibleValueMapper;

    public FieldPossibleValueServiceImpl(
        FieldPossibleValueRepository fieldPossibleValueRepository,
        FieldPossibleValueMapper fieldPossibleValueMapper
    ) {
        this.fieldPossibleValueRepository = fieldPossibleValueRepository;
        this.fieldPossibleValueMapper = fieldPossibleValueMapper;
    }

    @Override
    public FieldPossibleValueDTO save(FieldPossibleValueDTO fieldPossibleValueDTO) {
        log.debug("Request to save FieldPossibleValue : {}", fieldPossibleValueDTO);
        FieldPossibleValue fieldPossibleValue = fieldPossibleValueMapper.toEntity(fieldPossibleValueDTO);
        fieldPossibleValue = fieldPossibleValueRepository.save(fieldPossibleValue);
        return fieldPossibleValueMapper.toDto(fieldPossibleValue);
    }

    @Override
    public FieldPossibleValueDTO update(FieldPossibleValueDTO fieldPossibleValueDTO) {
        log.debug("Request to update FieldPossibleValue : {}", fieldPossibleValueDTO);
        FieldPossibleValue fieldPossibleValue = fieldPossibleValueMapper.toEntity(fieldPossibleValueDTO);
        fieldPossibleValue = fieldPossibleValueRepository.save(fieldPossibleValue);
        return fieldPossibleValueMapper.toDto(fieldPossibleValue);
    }

    @Override
    public Optional<FieldPossibleValueDTO> partialUpdate(FieldPossibleValueDTO fieldPossibleValueDTO) {
        log.debug("Request to partially update FieldPossibleValue : {}", fieldPossibleValueDTO);

        return fieldPossibleValueRepository
            .findById(fieldPossibleValueDTO.getId())
            .map(existingFieldPossibleValue -> {
                fieldPossibleValueMapper.partialUpdate(existingFieldPossibleValue, fieldPossibleValueDTO);

                return existingFieldPossibleValue;
            })
            .map(fieldPossibleValueRepository::save)
            .map(fieldPossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldPossibleValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FieldPossibleValues");
        return fieldPossibleValueRepository.findAll(pageable).map(fieldPossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FieldPossibleValueDTO> findOne(Long id) {
        log.debug("Request to get FieldPossibleValue : {}", id);
        return fieldPossibleValueRepository.findById(id).map(fieldPossibleValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FieldPossibleValue : {}", id);
        fieldPossibleValueRepository.deleteById(id);
    }
}
