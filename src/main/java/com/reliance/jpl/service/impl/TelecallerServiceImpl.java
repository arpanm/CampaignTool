package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.Telecaller;
import com.reliance.jpl.repository.TelecallerRepository;
import com.reliance.jpl.service.TelecallerService;
import com.reliance.jpl.service.dto.TelecallerDTO;
import com.reliance.jpl.service.mapper.TelecallerMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Telecaller}.
 */
@Service
@Transactional
public class TelecallerServiceImpl implements TelecallerService {

    private final Logger log = LoggerFactory.getLogger(TelecallerServiceImpl.class);

    private final TelecallerRepository telecallerRepository;

    private final TelecallerMapper telecallerMapper;

    public TelecallerServiceImpl(TelecallerRepository telecallerRepository, TelecallerMapper telecallerMapper) {
        this.telecallerRepository = telecallerRepository;
        this.telecallerMapper = telecallerMapper;
    }

    @Override
    public TelecallerDTO save(TelecallerDTO telecallerDTO) {
        log.debug("Request to save Telecaller : {}", telecallerDTO);
        Telecaller telecaller = telecallerMapper.toEntity(telecallerDTO);
        telecaller = telecallerRepository.save(telecaller);
        return telecallerMapper.toDto(telecaller);
    }

    @Override
    public TelecallerDTO update(TelecallerDTO telecallerDTO) {
        log.debug("Request to update Telecaller : {}", telecallerDTO);
        Telecaller telecaller = telecallerMapper.toEntity(telecallerDTO);
        telecaller = telecallerRepository.save(telecaller);
        return telecallerMapper.toDto(telecaller);
    }

    @Override
    public Optional<TelecallerDTO> partialUpdate(TelecallerDTO telecallerDTO) {
        log.debug("Request to partially update Telecaller : {}", telecallerDTO);

        return telecallerRepository
            .findById(telecallerDTO.getId())
            .map(existingTelecaller -> {
                telecallerMapper.partialUpdate(existingTelecaller, telecallerDTO);

                return existingTelecaller;
            })
            .map(telecallerRepository::save)
            .map(telecallerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TelecallerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Telecallers");
        return telecallerRepository.findAll(pageable).map(telecallerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TelecallerDTO> findOne(Long id) {
        log.debug("Request to get Telecaller : {}", id);
        return telecallerRepository.findById(id).map(telecallerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Telecaller : {}", id);
        telecallerRepository.deleteById(id);
    }
}
