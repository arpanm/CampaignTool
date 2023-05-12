package com.reliance.jpl.repository;

import com.reliance.jpl.domain.AttributePossibleValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttributePossibleValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributePossibleValueRepository extends JpaRepository<AttributePossibleValue, Long> {}
