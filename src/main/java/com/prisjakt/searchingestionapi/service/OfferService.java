package com.prisjakt.searchingestionapi.service;

import com.prisjakt.searchingestionapi.Entity.Offer;
import com.prisjakt.searchingestionapi.Entity.Product;
import com.prisjakt.searchingestionapi.repository.OfferRepository;
import com.prisjakt.searchingestionapi.repository.ProductRepository;
import dto.OutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {
    private OfferRepository offerRepository;
    private ProductRepository productRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository, ProductRepository productRepository) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
    }

    public List<OutputDto> upsertOffer(Offer offer) {
        List<OutputDto> outputDtoList = new ArrayList<>();
        Optional<Offer> offerUpdatableOption = offerRepository.findById(offer.getOfferId());
        if (offerUpdatableOption.isPresent()) {
            List<Offer> offers = offerRepository.findAllByRelatedProductId(offerUpdatableOption.get().getRelatedProductId());
            if (offers.size() == 1 && !offer.getRelatedProductId().equals(offerUpdatableOption.get().getRelatedProductId())) {
                productRepository.deleteById(offers.get(0).getRelatedProductId());
                outputDtoList.add(new OutputDto("Delete a searchable product document",
                        "", "", new ArrayList<>()));
            }
        }
        offerRepository.save(offer);
        if (offer.getRelatedProductId() == null) {
            offer.setRelatedProductId("");
        }
        Optional<Product> productOptional = productRepository.findById(offer.getRelatedProductId());
        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            outputDtoList.add(new OutputDto("Upsert an offer", product.getProductId(), product.getProductName(),
                    offerRepository.findAllByRelatedProductId(product.getProductId()).stream().map(Offer::getOfferId).collect(Collectors.toList())));
        }

        return outputDtoList;
    }

    public void deleteOffer(String offerId) {
        offerRepository.deleteById(offerId);

    }

}
