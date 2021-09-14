package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.AttributePossibleValue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AttributePossibleValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributePossibleValueRepository extends JpaRepository<AttributePossibleValue, Long> {}
