package org.example.urlshortener.model;

import java.time.Instant;

public class UrlMapping
{
   private final String longUrl;
   private final String shortCode;
   private final Instant createdAt;
   private final Instant expiresAt;

    public UrlMapping(String longUrl, String shortCode, Instant createdAt, Instant expiresAt)
    {
        this.longUrl = longUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public String getLongUrl()
    {
        return longUrl;
    }

    public String getShortCode()
    {
        return shortCode;
    }

    public Instant getCreatedAt()
    {
        return createdAt;
    }

    public Instant getExpiresAt()
    {
        return expiresAt;
    }

    public boolean isExpired(Instant now)
    {
       return expiresAt!=null && !now.isBefore(expiresAt);
    }
}
