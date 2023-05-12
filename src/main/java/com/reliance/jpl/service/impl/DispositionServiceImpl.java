package com.reliance.jpl.service.impl;

import com.reliance.jpl.domain.Disposition;
import com.reliance.jpl.repository.DispositionRepository;
import com.reliance.jpl.service.DispositionService;
import com.reliance.jpl.service.dto.DispositionDTO;
import com.reliance.jpl.service.mapper.DispositionMapper;
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
 * Service Implementation for managing {@link Disposition}.
 */
@Service
@Transactional
public class DispositionServiceImpl implements DispositionService {

    private final Logger log = LoggerFactory.getLogger(DispositionServiceImpl.class);

    private final DispositionRepository dispositionRepository;

    private final DispositionMapper dispositionMapper;

    public DispositionServiceImpl(DispositionRepository dispositionRepository, DispositionMapper dispositionMapper) {
        this.dispositionRepository = dispositionRepository;
        this.dispositionMapper = dispositionMapper;
    }

    @Override
    public DispositionDTO save(DispositionDTO dispositionDTO) {
        log.debug("Request to save Disposition : {}", dispositionDTO);
        Disposition disposition = dispositionMapper.toEntity(dispositionDTO);
        disposition = dispositionRepository.save(disposition);
        return dispositionMapper.toDto(disposition);
    }

    @Override
    public DispositionDTO update(DispositionDTO dispositionDTO) {
        log.debug("Request to update Disposition : {}", dispositionDTO);
        Disposition disposition = dispositionMapper.toEntity(dispositionDTO);
        disposition = dispositionRepository.save(disposition);
        return dispositionMapper.toDto(disposition);
    }

    @Override
    public Optional<DispositionDTO> partialUpdate(DispositionDTO dispositionDTO) {
        log.debug("Request to partially update Disposition : {}", dispositionDTO);

        return dispositionRepository
            .findById(dispositionDTO.getId())
            .map(existingDisposition -> {
                dispositionMapper.partialUpdate(existingDisposition, dispositionDTO);

                return existingDisposition;
            })
            .map(dispositionRepository::save)
            .map(dispositionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DispositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dispositions");
        return dispositionRepository.findAll(pageable).map(dispositionMapper::toDto);
    }

    /**
     *  Get all the dispositions where Campaign is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DispositionDTO> findAllWhereCampaignIsNull() {
        log.debug("Request to get all dispositions where Campaign is null");
        return StreamSupport
            .stream(dispositionRepository.findAll().spliterator(), false)
            .filter(disposition -> disposition.getCampaign() == null)
            .map(dispositionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DispositionDTO> findOne(Long id) {
        log.debug("Request to get Disposition : {}", id);
        return dispositionRepository.findById(id).map(dispositionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Disposition : {}", id);
        dispositionRepository.deleteById(id);
    }
}
