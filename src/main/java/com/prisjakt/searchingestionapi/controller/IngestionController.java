package com.prisjakt.searchingestionapi.controller;

import com.prisjakt.searchingestionapi.Entity.Offer;
import com.prisjakt.searchingestionapi.Entity.Product;
import com.prisjakt.searchingestionapi.service.OfferService;
import com.prisjakt.searchingestionapi.service.ProductService;
import dto.OutputDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class IngestionController {

    private final OfferService offerService;
    private final ProductService productService;

    @Autowired
    public IngestionController(
            ProductService productService,
            OfferService offerService
    ) {
        this.productService = productService;
        this.offerService = offerService;
    }

    @PutMapping(value = "/products")
    public ResponseEntity<List<OutputDto>> upsertProduct(
            @Valid @RequestBody Product product) {

        return new ResponseEntity<>(productService.upsertProduct(product),
                HttpStatus.OK);
    }

    @PutMapping(value = "/offers")
    public ResponseEntity<List<OutputDto>> upsertOffer(
            @Valid @RequestBody Offer offer) {

        return new ResponseEntity<>(offerService.upsertOffer(offer),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity deleteProduct(
            @PathVariable String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(productId,
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/offers/{offerId}")
    public ResponseEntity deleteOffer(
            @PathVariable String offerId) {
        offerService.deleteOffer(offerId);
        return new ResponseEntity<>(offerId,
                HttpStatus.OK);
    }

}
