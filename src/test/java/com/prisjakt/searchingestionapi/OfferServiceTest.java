package com.prisjakt.searchingestionapi;

import com.prisjakt.searchingestionapi.Entity.Offer;
import com.prisjakt.searchingestionapi.Entity.Product;
import com.prisjakt.searchingestionapi.repository.OfferRepository;
import com.prisjakt.searchingestionapi.repository.ProductRepository;
import com.prisjakt.searchingestionapi.service.OfferService;
import dto.OutputDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    Offer offer;


    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private ProductRepository productRepository;

    @Before
    public void setUp() {
        offer = new Offer();
        offer.setOfferId("o1");
        offer.setOfferId("product1");
        offer.setRelatedProductId("p1");
    }

    @Test
    public void upsertOfferIfProductDoesNotExist() {

        List<OutputDto> actual = offerService.upsertOffer(offer);
        List<OutputDto> ls = new ArrayList<OutputDto>();
        assertEquals(0, actual.size());
    }

    @Test
    public void upsertOfferIfOneProductExists() {
        Product pr = new Product();
        pr.setProductId("p1");
        pr.setProductName("pname");
        Optional<Product> opt = Optional.of(pr);
        when(productRepository.findById(offer.getRelatedProductId())).thenReturn(opt);
        List<OutputDto> actual = offerService.upsertOffer(offer);
        assertEquals(1, actual.size());
    }

    @Test
    public void upsertDeleteOneProductWithoutOfferExists() {
        Offer offerChange = new Offer();
        offerChange.setOfferId("o1");
        offerChange.setOfferId("product1");
        offerChange.setRelatedProductId("p2");
        Optional<Offer> offerChangeOpt = Optional.of(offerChange);
        List<Offer> offerList = Collections.singletonList(offer);
        when(offerRepository.findById(offer.getOfferId())).thenReturn(offerChangeOpt);
        when(offerRepository.findAllByRelatedProductId(offerChangeOpt.get().getRelatedProductId()))
                .thenReturn(offerList);
        List<OutputDto> actual = offerService.upsertOffer(offer);
        assertEquals(1, actual.size());
        assertEquals("Delete a searchable product document", actual.get(0).getOperationDesc());
    }


}
