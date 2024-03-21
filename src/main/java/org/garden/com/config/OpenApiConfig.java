package org.garden.com.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition (
        info = @Info(
                title = "Garden Shop api",
                description = "Garden Shop", version = "1.0.0",
                contact = @Contact(
                        name = "Stsiapan Samuliou, Sergei Lapidus, Anton Kuklinsky, Viktor Balagurchik",
                        url = "https://github.com/s-samuliou/GardenStoreTelran")

        )
)
@SecurityScheme(
        name = "basicauth",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig {
}
