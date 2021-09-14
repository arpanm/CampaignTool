package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.DispositionSubmissionValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DispositionSubmissionValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionSubmissionValueRepository extends JpaRepository<DispositionSubmissionValue, Long> {}
