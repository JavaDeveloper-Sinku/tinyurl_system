package com.example.tinyurl.controller;


import com.example.tinyurl.entity.Url;
import com.example.tinyurl.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    // Short URL create API
    @PostMapping("/api/shorten")
    public ResponseEntity<?> shortenUrl(@RequestParam String originalUrl) {
        Url url = urlService.createShortUrl(originalUrl);
        return ResponseEntity.ok("Short URL: http://localhost:8080/" + url.getShortCode());
    }

    // Short URL to redirect
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectUrl(@PathVariable String shortCode) {
        Optional<Url> url = urlService.getOriginalUrl(shortCode);

        if (url.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(url.get().getOriginalUrl()))
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("URL not found!");
        }
    }
}