package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.DispositionSubmission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DispositionSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionSubmissionRepository extends JpaRepository<DispositionSubmission, Long> {}
