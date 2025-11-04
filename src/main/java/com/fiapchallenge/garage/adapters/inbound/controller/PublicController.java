package com.fiapchallenge.garage.adapters.inbound.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController implements PublicControllerOpenApiSpec {

    @GetMapping("/robots.txt")
    @Override
    public ResponseEntity<String> robots() {
        return ResponseEntity.ok("User-agent: *\nDisallow:");
    }

    @GetMapping("/sitemap.xml")
    @Override
    public ResponseEntity<String> sitemap() {
        return ResponseEntity.ok("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n</urlset>");
    }

    @GetMapping("/")
    @Override
    public ResponseEntity<String> home() {
        String html = "<!DOCTYPE html><html><head><title>Garage API</title></head><body>" +
                      "<h1>Health: OK</h1>" +
                      "<p><a href='/swagger-ui/index.html'>API Documentation</a></p>" +
                      "</body></html>";
        return ResponseEntity.ok().header("Content-Type", "text/html").body(html);
    }
}