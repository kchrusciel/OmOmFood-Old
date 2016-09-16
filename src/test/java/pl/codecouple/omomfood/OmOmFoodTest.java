package pl.codecouple.omomfood;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.codecouple.omomfood.configuration.WebSecurityConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OmOmFood.class)
@Import(WebSecurityConfig.class)
public class OmOmFoodTest {

    @Autowired
    private MockMvc mvc;

//    @Test
//    public void shouldReturn200() throws Exception {
//        this.mvc.perform(get("/login")).andExpect(status().is(200));
//    }
}
