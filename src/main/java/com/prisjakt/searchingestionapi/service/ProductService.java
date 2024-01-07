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
            outputDtoList.add(new OutputDto("Upsert a product", product.getProductId(), product.getProductName(),
                    offerList.stream().map(Offer::getOfferId).collect(Collectors.toList())));
        }
        return outputDtoList;
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
