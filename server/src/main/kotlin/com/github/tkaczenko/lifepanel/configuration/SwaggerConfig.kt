package com.github.tkaczenko.lifepanel.configuration

import com.github.tkaczenko.lifepanel.api.model.BasicApiInfo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.Contact
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


/**
 * @author Andrii Tkachenko
 */
@Configuration
@EnableSwagger2
class SwaggerConfig : SwaggerConfigurer() {
    @Bean
    @ConfigurationProperties(prefix = "api.server")
    fun basicApiInfo(): BasicApiInfo {
        return BasicApiInfo()
    }

    @Bean
    fun docketServerApi10(@Qualifier("basicApiInfo") basicApiInfo: BasicApiInfo): Docket {
        val brokerPath = String.format("/server/%s/**", basicApiInfo.version)
        return Docket(DocumentationType.SWAGGER_2)
            .securityContexts(listOf(securityContext(brokerPath)))
            .securitySchemes(listOf(basicAuthScheme()))
            .apiInfo(buildApiInfo(basicApiInfo))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant(brokerPath))
            .build()
            .securitySchemes(buildSecurityScheme())
    }

    private fun buildApiInfo(basicApiInfo: BasicApiInfo): ApiInfo {
        return ApiInfoBuilder()
            .title(basicApiInfo.title)
            .description(basicApiInfo.description)
            .contact(Contact(basicApiInfo.name, basicApiInfo.url, basicApiInfo.email))
            .version(basicApiInfo.version)
            .build()
    }

    private fun buildSecurityScheme(): List<SecurityScheme?> {
        return listOf<SecurityScheme>(
            BasicAuth("basicAuth")
        )
    }
}
