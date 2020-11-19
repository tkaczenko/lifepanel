package com.github.tkaczenko.lifepanel.configuration

import springfox.documentation.builders.PathSelectors
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.BasicAuth
import springfox.documentation.service.SecurityReference
import springfox.documentation.service.SecurityScheme
import springfox.documentation.spi.service.contexts.SecurityContext


/**
 * @author Andrii Tkachenko
 */
open class SwaggerConfigurer {
    protected fun securityContext(path: String?): SecurityContext {
        return SecurityContext.builder()
            .securityReferences(listOf(basicAuthReference()))
            .forPaths(PathSelectors.ant(path))
            .build()
    }

    protected fun basicAuthScheme(): SecurityScheme {
        return BasicAuth("basicAuth")
    }

    private fun basicAuthReference(): SecurityReference {
        return SecurityReference("basicAuth", arrayOfNulls<AuthorizationScope>(0))
    }
}
