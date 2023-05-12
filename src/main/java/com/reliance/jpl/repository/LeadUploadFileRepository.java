package com.reliance.jpl.repository;

import com.reliance.jpl.domain.LeadUploadFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LeadUploadFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadUploadFileRepository extends JpaRepository<LeadUploadFile, Long> {}
