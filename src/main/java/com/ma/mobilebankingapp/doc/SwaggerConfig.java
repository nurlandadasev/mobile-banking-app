package com.ma.mobilebankingapp.doc;

import com.fasterxml.classmate.TypeResolver;
import com.ma.mobilebankingapp.exceptions.ExceptionResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    private final TypeResolver typeResolver;

    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }


    public static final Contact DEFAULT_CONTACT = new Contact("Nurlan Dadashov", "M-A", "nurlandadasev@gmail.com");


    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Banking Service", "Banking API Documentation", "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList()
    );


    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(Arrays.asList("application/json"));



    private static Response responseMessage(String status, String description) {
        return new ResponseBuilder().code(status)
                .description(description)
                .representation(MediaType.APPLICATION_JSON)
                .apply(SwaggerConfig::buildDefaultModel)
                .isDefault(true)
                .build();
    }

    private static void buildDefaultModel(final RepresentationBuilder representationBuilder) {
        representationBuilder.model(
                msBuilder ->
                        msBuilder.name("ExceptionResponse")
                                .referenceModel(rmsBuilder ->
                                        rmsBuilder.key(
                                                mkBuilder ->
                                                        mkBuilder.isResponse(true)
                                                                .qualifiedModelName(
                                                                        qmnBuilder -> qmnBuilder.name("ExceptionResponse")
                                                                                .namespace("com.ma.mobilebankingapp.exceptions")
                                                                                .build())
                                                                .build())
                                                .build())
                                .build());
    }



    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .additionalModels(typeResolver.resolve(ExceptionResponse.class))
                .globalResponses(HttpMethod.GET,
                        Collections.singletonList(responseMessage("400", "Validation exception. Please check if you are entering the parameters correctly.")
                        )
                )
                .globalResponses(HttpMethod.POST,
                        Collections.singletonList(responseMessage("400", "Validation exception. Please check if you are entering the parameters correctly.")
                        )
                )
                .globalResponses(HttpMethod.DELETE,
                        Collections.singletonList(responseMessage("400", "Validation exception. Please check if you are entering the parameters correctly.")
                        )
                )
                .globalResponses(HttpMethod.PUT,
                        Collections.singletonList(responseMessage("400", "Validation exception. Please check if you are entering the parameters correctly.")
                        )
                )
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                ;
    }


}
