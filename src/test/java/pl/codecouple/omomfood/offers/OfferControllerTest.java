package pl.codecouple.omomfood.offers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Krzysztof Chruściel.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OfferServiceImpl offerService;

    @Test
    public void getVehicleShouldReturnMakeAndModel() throws Exception {
        given(this.offerService.getAllOffers())
                .willReturn(Arrays.asList(new Offer("a", "b", "c", "d", BigDecimal.ONE, 1, "e", LocalDateTime.now(), LocalDateTime.now(), null)));

        this.mvc.perform(get("/offers"))
                .andExpect(status().isOk())
                .andExpect(content().string("Honda Civic"));
    }

}