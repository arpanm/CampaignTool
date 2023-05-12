package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.AttributeKey;
import com.reliance.jpl.repository.AttributeKeyRepository;
import com.reliance.jpl.service.AttributeKeyService;
import com.reliance.jpl.service.dto.AttributeKeyDTO;
import com.reliance.jpl.service.mapper.AttributeKeyMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AttributeKey}.
 */
@Service
@Transactional
public class AttributeKeyServiceImpl implements AttributeKeyService {

    private final Logger log = LoggerFactory.getLogger(AttributeKeyServiceImpl.class);

    private final AttributeKeyRepository attributeKeyRepository;

    private final AttributeKeyMapper attributeKeyMapper;

    public AttributeKeyServiceImpl(AttributeKeyRepository attributeKeyRepository, AttributeKeyMapper attributeKeyMapper) {
        this.attributeKeyRepository = attributeKeyRepository;
        this.attributeKeyMapper = attributeKeyMapper;
    }

    @Override
    public AttributeKeyDTO save(AttributeKeyDTO attributeKeyDTO) {
        log.debug("Request to save AttributeKey : {}", attributeKeyDTO);
        AttributeKey attributeKey = attributeKeyMapper.toEntity(attributeKeyDTO);
        attributeKey = attributeKeyRepository.save(attributeKey);
        return attributeKeyMapper.toDto(attributeKey);
    }

    @Override
    public AttributeKeyDTO update(AttributeKeyDTO attributeKeyDTO) {
        log.debug("Request to update AttributeKey : {}", attributeKeyDTO);
        AttributeKey attributeKey = attributeKeyMapper.toEntity(attributeKeyDTO);
        attributeKey = attributeKeyRepository.save(attributeKey);
        return attributeKeyMapper.toDto(attributeKey);
    }

    @Override
    public Optional<AttributeKeyDTO> partialUpdate(AttributeKeyDTO attributeKeyDTO) {
        log.debug("Request to partially update AttributeKey : {}", attributeKeyDTO);

        return attributeKeyRepository
            .findById(attributeKeyDTO.getId())
            .map(existingAttributeKey -> {
                attributeKeyMapper.partialUpdate(existingAttributeKey, attributeKeyDTO);

                return existingAttributeKey;
            })
            .map(attributeKeyRepository::save)
            .map(attributeKeyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AttributeKeyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AttributeKeys");
        return attributeKeyRepository.findAll(pageable).map(attributeKeyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AttributeKeyDTO> findOne(Long id) {
        log.debug("Request to get AttributeKey : {}", id);
        return attributeKeyRepository.findById(id).map(attributeKeyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AttributeKey : {}", id);
        attributeKeyRepository.deleteById(id);
    }
}
