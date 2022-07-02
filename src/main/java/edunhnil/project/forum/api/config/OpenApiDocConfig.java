package edunhnil.project.forum.api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Forum API", version = "${api.version}", contact = @Contact(name = "Eric Chen", email = "dev.tranviethailinh@gmail.com", url = "https://github.com/devtranviethailinh"),
        // license = @License(
        // name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
        // ),
        termsOfService = "${tos.uri}", description = "${api.description}"), servers = @Server(url = "${api.server.url}", description = "Production"))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class OpenApiDocConfig {

}
