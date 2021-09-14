package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.TelecallerAssignment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TelecallerAssignment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelecallerAssignmentRepository extends JpaRepository<TelecallerAssignment, Long> {}
