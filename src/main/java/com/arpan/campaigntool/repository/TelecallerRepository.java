package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.Telecaller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Telecaller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelecallerRepository extends JpaRepository<Telecaller, Long> {}
