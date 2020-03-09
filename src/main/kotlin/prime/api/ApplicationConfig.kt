package prime.api.impl

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application


@ApplicationPath("/api")
@OpenAPIDefinition(
        info = Info(
                title = "Payment Interface API",
                version = "1.0.0"
        )
)
class ApplicationConfig : Application() {

}
