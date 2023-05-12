package com.reliance.jpl.repository;

import com.reliance.jpl.domain.DispositionSubmission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DispositionSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionSubmissionRepository extends JpaRepository<DispositionSubmission, Long> {}
