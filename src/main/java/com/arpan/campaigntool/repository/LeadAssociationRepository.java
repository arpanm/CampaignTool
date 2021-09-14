package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.LeadAssociation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LeadAssociation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadAssociationRepository extends JpaRepository<LeadAssociation, Long> {}
