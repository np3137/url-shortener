package org.example.urlshortener.service;

import org.example.urlshortener.exception.UrlNotFoundException;
import org.example.urlshortener.generator.ShortCodeGenerator;
import org.example.urlshortener.repository.UrlRepository;
import org.example.urlshortener.model.UrlMapping;
import java.time.Duration;
import java.time.Instant;

public class UrlShortenerService
{
    private final int MAX_RETRIES = 3;

   private final String baseUrl;
   private final UrlRepository urlRepository;
   private final ShortCodeGenerator shortCodeGenerator;


    public UrlShortenerService( String baseUrl, UrlRepository urlRepository, ShortCodeGenerator shortCodeGenerator)
    {
        this.baseUrl = baseUrl;
        this.urlRepository = urlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
    }

    public String shorten(String originalUrl, Duration ttl)
    {
       Instant now  =  Instant.now();
       Instant expiresAt = now.plus(ttl);
       for(int attempt = 0; attempt < MAX_RETRIES; attempt++)
       {
           String code = shortCodeGenerator.generate();
           UrlMapping urlMapping = new UrlMapping(originalUrl, code, now,expiresAt);
           if(urlRepository.saveIfAbsent(urlMapping))
           {
              return baseUrl + "/" + code;
           }
       }
       throw new IllegalArgumentException("Unable to generate a unique code");
    }

    public String resolve(String shortCode)
    {
       Instant now = Instant.now();
       UrlMapping urlMapping = urlRepository.findByShortCode(shortCode).orElseThrow(() -> new UrlNotFoundException(shortCode));
       if(urlMapping.isExpired(now))
       {
          throw new IllegalArgumentException(shortCode + " has been expired.");
       }
       return urlMapping.getLongUrl();
    }
}
