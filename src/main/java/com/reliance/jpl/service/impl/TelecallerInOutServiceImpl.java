package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.TelecallerInOut;
import com.reliance.jpl.repository.TelecallerInOutRepository;
import com.reliance.jpl.service.TelecallerInOutService;
import com.reliance.jpl.service.dto.TelecallerInOutDTO;
import com.reliance.jpl.service.mapper.TelecallerInOutMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TelecallerInOut}.
 */
@Service
@Transactional
public class TelecallerInOutServiceImpl implements TelecallerInOutService {

    private final Logger log = LoggerFactory.getLogger(TelecallerInOutServiceImpl.class);

    private final TelecallerInOutRepository telecallerInOutRepository;

    private final TelecallerInOutMapper telecallerInOutMapper;

    public TelecallerInOutServiceImpl(TelecallerInOutRepository telecallerInOutRepository, TelecallerInOutMapper telecallerInOutMapper) {
        this.telecallerInOutRepository = telecallerInOutRepository;
        this.telecallerInOutMapper = telecallerInOutMapper;
    }

    @Override
    public TelecallerInOutDTO save(TelecallerInOutDTO telecallerInOutDTO) {
        log.debug("Request to save TelecallerInOut : {}", telecallerInOutDTO);
        TelecallerInOut telecallerInOut = telecallerInOutMapper.toEntity(telecallerInOutDTO);
        telecallerInOut = telecallerInOutRepository.save(telecallerInOut);
        return telecallerInOutMapper.toDto(telecallerInOut);
    }

    @Override
    public TelecallerInOutDTO update(TelecallerInOutDTO telecallerInOutDTO) {
        log.debug("Request to update TelecallerInOut : {}", telecallerInOutDTO);
        TelecallerInOut telecallerInOut = telecallerInOutMapper.toEntity(telecallerInOutDTO);
        telecallerInOut = telecallerInOutRepository.save(telecallerInOut);
        return telecallerInOutMapper.toDto(telecallerInOut);
    }

    @Override
    public Optional<TelecallerInOutDTO> partialUpdate(TelecallerInOutDTO telecallerInOutDTO) {
        log.debug("Request to partially update TelecallerInOut : {}", telecallerInOutDTO);

        return telecallerInOutRepository
            .findById(telecallerInOutDTO.getId())
            .map(existingTelecallerInOut -> {
                telecallerInOutMapper.partialUpdate(existingTelecallerInOut, telecallerInOutDTO);

                return existingTelecallerInOut;
            })
            .map(telecallerInOutRepository::save)
            .map(telecallerInOutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelecallerInOutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TelecallerInOuts");
        return telecallerInOutRepository.findAll(pageable).map(telecallerInOutMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelecallerInOutDTO> findOne(Long id) {
        log.debug("Request to get TelecallerInOut : {}", id);
        return telecallerInOutRepository.findById(id).map(telecallerInOutMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TelecallerInOut : {}", id);
        telecallerInOutRepository.deleteById(id);
    }
}
