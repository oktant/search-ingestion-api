package com.prisjakt.searchingestionapi;

import com.prisjakt.searchingestionapi.entity.Offer;
import com.prisjakt.searchingestionapi.entity.Product;
import com.prisjakt.searchingestionapi.repository.OfferRepository;
import com.prisjakt.searchingestionapi.repository.ProductRepository;
import com.prisjakt.searchingestionapi.service.ProductService;
import dto.OutputDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    Product product;


    @InjectMocks
    private ProductService productService;

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        product = new Product();
        product.setProductId("p1");
        product.setProductName("product1");
    }

    @Test
    public void upsertProductIfNoOfferExist() {
        List<OutputDto> actual = productService.upsertProduct(product);
        List<OutputDto> ls = new ArrayList<OutputDto>();
        assertEquals(0, actual.size());
    }

    @Test
    public void upsertOfferIfOfferExist() {
        Offer offer = new Offer();
        offer.setOfferId("o1");
        offer.setRelatedProductId("p1");
        offer.setOfferName("Offer1 for p1");
        when(offerRepository.findAllByRelatedProductId(product.getProductId())).thenReturn(Arrays.asList(offer));
        List<OutputDto> actual = productService.upsertProduct(product);
        assertEquals(1, actual.size());
        assertEquals("Offer1 for p1", actual.get(0).getOfferNames().get(0));
    }

    @Test
    public void deleteProduct() {
        List <OutputDto> actual = productService.deleteProduct("p1");
        assertEquals(1, actual.size());
        assertEquals("Delete a searchable product document", actual.get(0).getOperationDesc());

    }

}
