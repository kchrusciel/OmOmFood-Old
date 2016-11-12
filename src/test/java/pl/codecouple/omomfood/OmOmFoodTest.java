package pl.codecouple.omomfood;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pl.codecouple.omomfood.offers.OfferService;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OmOmFoodTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private OfferService offerService;

    @Before
    public void setup() {
        given(this.offerService.getOfferById(10)
        ).willReturn(
                null);
    }

    @Test
    public void test() {
        this.restTemplate.getForEntity("/offer/{id}",
                String.class, "10");
    }

}
