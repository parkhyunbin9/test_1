package com.reco.prob.domain.history;

import com.reco.prob.domain.customer.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface CollectHistoryRepository extends JpaRepository<CollectHistory, Long> {

    List<CollectHistory> findByCollectTimeGreaterThanEqualAndCollectTimeLessThan(LocalDateTime start, LocalDateTime end);

    List<CollectHistory> findByCustomer(Customer customer);

    List<CollectHistory> findByCustomer(Customer customer, Pageable pageable);

}
