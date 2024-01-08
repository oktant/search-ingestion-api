package com.prisjakt.searchingestionapi.service;


import com.prisjakt.searchingestionapi.entity.Offer;
import com.prisjakt.searchingestionapi.entity.Product;
import com.prisjakt.searchingestionapi.repository.OfferRepository;
import com.prisjakt.searchingestionapi.repository.ProductRepository;
import dto.OutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private OfferRepository offerRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductService(OfferRepository offerRepository, ProductRepository productRepository) {
        this.offerRepository = offerRepository;
        this.productRepository = productRepository;
    }

    public List<OutputDto> upsertProduct(Product product) {
        List<OutputDto> outputDtoList = new ArrayList<>();
        productRepository.save(product);
        List<Offer> offerList = offerRepository.findAllByRelatedProductId(product.getProductId());
        if (!offerList.isEmpty()) {
            outputDtoList.add(new OutputDto("Upsert a searchable product document", product.getProductId(), product.getProductName(),
                    offerList.stream().map(Offer::getOfferName).collect(Collectors.toList())));
        }
        return outputDtoList;
    }

    public List<OutputDto> deleteProduct(String productId) {
        productRepository.deleteById(productId);
        return List.of(new OutputDto("Delete a searchable product document", productId, "", new ArrayList<>()));

    }
}
