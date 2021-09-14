package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.LeadAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LeadAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadAssignmentRepository extends JpaRepository<LeadAssignment, Long> {}
