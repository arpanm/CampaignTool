package com.reliance.jpl.repository;

import com.reliance.jpl.domain.Disposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Disposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionRepository extends JpaRepository<Disposition, Long> {}
