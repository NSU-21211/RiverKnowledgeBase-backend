package nsu.ccfit.ru.upprpo.riverknowledge.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info =
        @Info(title = "Информационный сервис о реках",
                description = "Веб-платформа, созданная для сбора и предоставления информации о различных реках"
        )
)
@Configuration
public class OpenApiConfig {
}
