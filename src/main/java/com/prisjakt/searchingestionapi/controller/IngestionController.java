package com.prisjakt.searchingestionapi.controller;

import com.prisjakt.searchingestionapi.entity.Offer;
import com.prisjakt.searchingestionapi.entity.Product;
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
    public ResponseEntity<List<OutputDto>> deleteProduct(
            @PathVariable String productId) {
        return new ResponseEntity<>(productService.deleteProduct(productId),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/offers/{offerId}")
    public ResponseEntity<List<OutputDto>> deleteOffer(
            @PathVariable String offerId) {
        return new ResponseEntity<>(offerService.deleteOffer(offerId), HttpStatus.OK);
    }

}
