package com.example.tinyurl.service;


import com.example.tinyurl.entity.Url;
import com.example.tinyurl.exception.ShortUrlNotFoundException;
import com.example.tinyurl.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final UrlRepository urlRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    // Long URl Convert Short url
    public Url createShortUrl(String originalUrl){
        String shortCode = generateShortCode();

        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(shortCode);

        return  urlRepository.save(url);


    }

    // Short Code se Original URL  find
    public Url getOriginalUrl(String shortCode) {

        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new ShortUrlNotFoundException(
                                "Short URL not found: " + shortCode
                        )
                );
    }

    // Random ShortCode generate
    private String generateShortCode() {

        SecureRandom random = new SecureRandom();
        StringBuilder shortCode = new StringBuilder();

        do {
            shortCode.setLength(0);

            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                shortCode.append(
                        CHARACTERS.charAt(
                                random.nextInt(CHARACTERS.length())
                        )
                );
            }

        } while (urlRepository.existsByShortCode(shortCode.toString()));

        return shortCode.toString();
    }

}
