package com.reliance.jpl.repository;

import com.reliance.jpl.domain.LeadAssociation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeadAssociation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadAssociationRepository extends JpaRepository<LeadAssociation, Long> {}
