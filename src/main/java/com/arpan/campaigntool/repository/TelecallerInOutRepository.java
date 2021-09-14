package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.TelecallerInOut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TelecallerInOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelecallerInOutRepository extends JpaRepository<TelecallerInOut, Long> {}
