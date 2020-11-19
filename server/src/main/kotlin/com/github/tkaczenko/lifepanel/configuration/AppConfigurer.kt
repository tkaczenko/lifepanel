package com.github.tkaczenko.lifepanel.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.context.support.ReloadableResourceBundleMessageSource


/**
 * @author Andrii Tkachenko
 */
open class AppConfigurer {
    @Bean
    @Scope("prototype")
    open fun logger(injectionPoint: InjectionPoint): Logger {
        return LoggerFactory.getLogger(
            injectionPoint.methodParameter?.containingClass // constructor
                ?: injectionPoint.field?.declaringClass) // or field injection
    }

    @Bean
    open fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:messages/label")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }
}
