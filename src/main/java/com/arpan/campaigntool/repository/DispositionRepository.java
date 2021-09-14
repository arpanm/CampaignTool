package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.Disposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Disposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispositionRepository extends JpaRepository<Disposition, Long> {}
