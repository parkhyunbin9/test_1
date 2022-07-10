package com.reco.prob.domain.imagemeta;

import com.reco.prob.domain.history.CollectHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectImageMetaRepository extends JpaRepository<CollectImageMeta, Long> {
    boolean existsByName(String name);

    List<CollectImageMeta> findByNameStartsWith(String name);

    List<CollectImageMeta> findByCollectHistory(CollectHistory history);
}
