package com.reliance.jpl.repository;

import com.reliance.jpl.domain.Lead;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class LeadRepositoryWithBagRelationshipsImpl implements LeadRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Lead> fetchBagRelationships(Optional<Lead> lead) {
        return lead.map(this::fetchLocations);
    }

    @Override
    public Page<Lead> fetchBagRelationships(Page<Lead> leads) {
        return new PageImpl<>(fetchBagRelationships(leads.getContent()), leads.getPageable(), leads.getTotalElements());
    }

    @Override
    public List<Lead> fetchBagRelationships(List<Lead> leads) {
        return Optional.of(leads).map(this::fetchLocations).orElse(Collections.emptyList());
    }

    Lead fetchLocations(Lead result) {
        return entityManager
            .createQuery("select lead from Lead lead left join fetch lead.locations where lead is :lead", Lead.class)
            .setParameter("lead", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Lead> fetchLocations(List<Lead> leads) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, leads.size()).forEach(index -> order.put(leads.get(index).getId(), index));
        List<Lead> result = entityManager
            .createQuery("select distinct lead from Lead lead left join fetch lead.locations where lead in :leads", Lead.class)
            .setParameter("leads", leads)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
