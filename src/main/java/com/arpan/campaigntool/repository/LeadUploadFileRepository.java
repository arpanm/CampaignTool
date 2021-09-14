package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.LeadUploadFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LeadUploadFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LeadUploadFileRepository extends JpaRepository<LeadUploadFile, Long> {}
