package com.reliance.jpl.repository;

import com.reliance.jpl.domain.FieldPossibleValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FieldPossibleValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldPossibleValueRepository extends JpaRepository<FieldPossibleValue, Long> {}
