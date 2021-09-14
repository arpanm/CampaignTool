package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.Call;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Call entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CallRepository extends JpaRepository<Call, Long> {}
