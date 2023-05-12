package com.reliance.jpl.repository;

import com.reliance.jpl.domain.Call;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Call entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallRepository extends JpaRepository<Call, Long> {}
