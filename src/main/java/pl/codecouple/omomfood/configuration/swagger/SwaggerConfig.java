package pl.codecouple.omomfood.configuration.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Krzysztof Chruściel.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getAPIInfoForSwagger());
    }

    /**
     * This method creates {@link ApiInfo} for Swagger.
     * @return <code>ApiInfo</code> object with API information.
     */
    private ApiInfo getAPIInfoForSwagger() {
        return new ApiInfoBuilder()
                .title("OmOmFood REST API")
                .description("API for OmOmFood")
                .contact(new Contact("Krzysztof Chrusciel", "http://codecouple.pl", "test@o2.pl"))
                .license("License of API")
                .licenseUrl("https://raw.githubusercontent.com/kchrusciel/OmOmFood/master/LICENSE")
                .version("1.0")
                .build();
    }
}
