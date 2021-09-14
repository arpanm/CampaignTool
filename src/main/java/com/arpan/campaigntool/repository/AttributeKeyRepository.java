package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.AttributeKey;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributeKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeKeyRepository extends JpaRepository<AttributeKey, Long> {}
