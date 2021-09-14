package com.arpan.campaigntool.repository;

import com.arpan.campaigntool.domain.Lead;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Lead entity.
 */
@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    @Query(
        value = "select distinct jhiLead from Lead jhiLead left join fetch jhiLead.locations",
        countQuery = "select count(distinct jhiLead) from Lead jhiLead"
    )
    Page<Lead> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct jhiLead from Lead jhiLead left join fetch jhiLead.locations")
    List<Lead> findAllWithEagerRelationships();

    @Query("select jhiLead from Lead jhiLead left join fetch jhiLead.locations where jhiLead.id =:id")
    Optional<Lead> findOneWithEagerRelationships(@Param("id") Long id);
}
