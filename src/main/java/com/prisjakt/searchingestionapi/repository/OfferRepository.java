package com.prisjakt.searchingestionapi.repository;

import com.prisjakt.searchingestionapi.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    public List<Offer> findAllByRelatedProductId(String relatedProductId);
}
