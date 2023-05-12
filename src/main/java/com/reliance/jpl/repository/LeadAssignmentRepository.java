package com.reliance.jpl.repository;

import com.reliance.jpl.domain.LeadAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeadAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadAssignmentRepository extends JpaRepository<LeadAssignment, Long> {}
