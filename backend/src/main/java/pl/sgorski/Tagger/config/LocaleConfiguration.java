package pl.sgorski.Tagger.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Log4j2
@Configuration
public class LocaleConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        log.debug("Creating Locale Resolver bean");
        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("en-US"));
        return slr;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
}
