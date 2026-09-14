package com.example.tinyurl.controller;


import com.example.tinyurl.dto.CreateUrlRequest;
import com.example.tinyurl.entity.Url;
import com.example.tinyurl.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    // Short URL create API
    @PostMapping("/api/shorten")
    public ResponseEntity<?> shortenUrl(
            @Valid @RequestBody CreateUrlRequest request
    ) {
        Url url = urlService.createShortUrl(request.originalUrl());

        return ResponseEntity.ok(
                "Short URL: http://localhost:8080/" + url.getShortCode()
        );
    }


    // Short URL to redirect
    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectUrl(@PathVariable String shortCode) {

        Url url = urlService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url.getOriginalUrl()))
                .build();
    }
}