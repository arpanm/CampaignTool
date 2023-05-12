package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.AttributePossibleValue;
import com.reliance.jpl.repository.AttributePossibleValueRepository;
import com.reliance.jpl.service.AttributePossibleValueService;
import com.reliance.jpl.service.dto.AttributePossibleValueDTO;
import com.reliance.jpl.service.mapper.AttributePossibleValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributePossibleValue}.
 */
@Service
@Transactional
public class AttributePossibleValueServiceImpl implements AttributePossibleValueService {

    private final Logger log = LoggerFactory.getLogger(AttributePossibleValueServiceImpl.class);

    private final AttributePossibleValueRepository attributePossibleValueRepository;

    private final AttributePossibleValueMapper attributePossibleValueMapper;

    public AttributePossibleValueServiceImpl(
        AttributePossibleValueRepository attributePossibleValueRepository,
        AttributePossibleValueMapper attributePossibleValueMapper
    ) {
        this.attributePossibleValueRepository = attributePossibleValueRepository;
        this.attributePossibleValueMapper = attributePossibleValueMapper;
    }

    @Override
    public AttributePossibleValueDTO save(AttributePossibleValueDTO attributePossibleValueDTO) {
        log.debug("Request to save AttributePossibleValue : {}", attributePossibleValueDTO);
        AttributePossibleValue attributePossibleValue = attributePossibleValueMapper.toEntity(attributePossibleValueDTO);
        attributePossibleValue = attributePossibleValueRepository.save(attributePossibleValue);
        return attributePossibleValueMapper.toDto(attributePossibleValue);
    }

    @Override
    public AttributePossibleValueDTO update(AttributePossibleValueDTO attributePossibleValueDTO) {
        log.debug("Request to update AttributePossibleValue : {}", attributePossibleValueDTO);
        AttributePossibleValue attributePossibleValue = attributePossibleValueMapper.toEntity(attributePossibleValueDTO);
        attributePossibleValue = attributePossibleValueRepository.save(attributePossibleValue);
        return attributePossibleValueMapper.toDto(attributePossibleValue);
    }

    @Override
    public Optional<AttributePossibleValueDTO> partialUpdate(AttributePossibleValueDTO attributePossibleValueDTO) {
        log.debug("Request to partially update AttributePossibleValue : {}", attributePossibleValueDTO);

        return attributePossibleValueRepository
            .findById(attributePossibleValueDTO.getId())
            .map(existingAttributePossibleValue -> {
                attributePossibleValueMapper.partialUpdate(existingAttributePossibleValue, attributePossibleValueDTO);

                return existingAttributePossibleValue;
            })
            .map(attributePossibleValueRepository::save)
            .map(attributePossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttributePossibleValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttributePossibleValues");
        return attributePossibleValueRepository.findAll(pageable).map(attributePossibleValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributePossibleValueDTO> findOne(Long id) {
        log.debug("Request to get AttributePossibleValue : {}", id);
        return attributePossibleValueRepository.findById(id).map(attributePossibleValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributePossibleValue : {}", id);
        attributePossibleValueRepository.deleteById(id);
    }
}
