package com.arpan.campaigntool.service.impl;

import com.arpan.campaigntool.domain.Call;
import com.arpan.campaigntool.repository.CallRepository;
import com.arpan.campaigntool.service.CallService;
import com.arpan.campaigntool.service.dto.CallDTO;
import com.arpan.campaigntool.service.mapper.CallMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Call}.
 */
@Service
@Transactional
public class CallServiceImpl implements CallService {

    private final Logger log = LoggerFactory.getLogger(CallServiceImpl.class);

    private final CallRepository callRepository;

    private final CallMapper callMapper;

    public CallServiceImpl(CallRepository callRepository, CallMapper callMapper) {
        this.callRepository = callRepository;
        this.callMapper = callMapper;
    }

    @Override
    public CallDTO save(CallDTO callDTO) {
        log.debug("Request to save Call : {}", callDTO);
        Call call = callMapper.toEntity(callDTO);
        call = callRepository.save(call);
        return callMapper.toDto(call);
    }

    @Override
    public Optional<CallDTO> partialUpdate(CallDTO callDTO) {
        log.debug("Request to partially update Call : {}", callDTO);

        return callRepository
            .findById(callDTO.getId())
            .map(
                existingCall -> {
                    callMapper.partialUpdate(existingCall, callDTO);

                    return existingCall;
                }
            )
            .map(callRepository::save)
            .map(callMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CallDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Calls");
        return callRepository.findAll(pageable).map(callMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CallDTO> findOne(Long id) {
        log.debug("Request to get Call : {}", id);
        return callRepository.findById(id).map(callMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Call : {}", id);
        callRepository.deleteById(id);
    }
}
