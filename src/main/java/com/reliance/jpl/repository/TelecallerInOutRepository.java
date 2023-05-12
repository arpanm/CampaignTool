package com.reliance.jpl.repository;

import com.reliance.jpl.domain.TelecallerInOut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TelecallerInOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelecallerInOutRepository extends JpaRepository<TelecallerInOut, Long> {}
