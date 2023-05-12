package com.reliance.jpl.repository;

import com.reliance.jpl.domain.AttributeKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AttributeKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeKeyRepository extends JpaRepository<AttributeKey, Long> {}
