package com.reliance.jpl.repository;

import com.reliance.jpl.domain.Telecaller;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telecaller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TelecallerRepository extends JpaRepository<Telecaller, Long> {}
