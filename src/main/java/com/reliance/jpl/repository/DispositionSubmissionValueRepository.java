package com.reliance.jpl.repository;

import com.reliance.jpl.domain.DispositionSubmissionValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DispositionSubmissionValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionSubmissionValueRepository extends JpaRepository<DispositionSubmissionValue, Long> {}
