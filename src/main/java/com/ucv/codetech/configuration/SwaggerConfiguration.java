package com.ucv.codetech.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String SWAGGER_TITLE = "CodeTech API Swagger documentation";
    private static final String SWAGGER_DESCRIPTION = "This represents the application's api.";
    private static final String SWAGGER_LICENSE = "Apache License Version 2.0, January 2004";
    private static final String SWAGGER_LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0.txt";

    @Value("${application.version}")
    private String applicationVersion;

    @Bean
    public Docket apiDocumentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ucv.codetech.controller"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(this.apiInfo());
    }

    @Primary
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(getTitle(), getDescription(), applicationVersion, null, getContact(),
                getLicense(), getLicenseUrl(), Collections.emptyList());
    }

    private String getTitle() {
        return SWAGGER_TITLE;
    }

    private String getDescription() {
        return SWAGGER_DESCRIPTION;
    }

    private Contact getContact() {
        return new Contact("Andrei-Cristian Tanase", null, "stonixandrei@yahoo.com," +
                " tanase.andrei.b5f@student.ucv.ro");
    }

    private String getLicense() {
        return SWAGGER_LICENSE;
    }

    private String getLicenseUrl() {
        return SWAGGER_LICENSE_URL;
    }
}
