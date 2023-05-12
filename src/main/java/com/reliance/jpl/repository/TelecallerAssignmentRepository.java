package com.reliance.jpl.repository;

import com.reliance.jpl.domain.TelecallerAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TelecallerAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelecallerAssignmentRepository extends JpaRepository<TelecallerAssignment, Long> {}
