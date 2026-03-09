package com.fiapchallenge.garage.adapters.inbound.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Public", description = "Public endpoints API")
public interface PublicResourceOpenApiSpec {

    @Operation(summary = "Obter robots.txt", description = "Retorna o arquivo robots.txt para crawlers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo robots.txt retornado com sucesso",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping(produces = "text/plain")
    ResponseEntity<String> robots();

    @Operation(summary = "Obter sitemap.xml", description = "Retorna o sitemap XML do site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sitemap XML retornado com sucesso",
                    content = @Content(mediaType = "application/xml"))
    })
    @GetMapping(produces = "application/xml")
    ResponseEntity<String> sitemap();

    @Operation(summary = "Pagina inicial", description = "Exibe status de saude e link para documentacao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagina inicial com status OK",
                    content = @Content(mediaType = "text/html"))
    })
    @GetMapping(produces = "text/html")
    ResponseEntity<String> home();
}
