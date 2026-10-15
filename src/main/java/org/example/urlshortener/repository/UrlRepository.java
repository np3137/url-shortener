package org.example.urlshortener.repository;

import org.example.urlshortener.model.UrlMapping;

import java.util.Optional;

public interface UrlRepository
{
   Optional<UrlMapping> findByShortCode(String code);
   Optional<UrlMapping> findByLongUrl(String url);
   boolean saveIfAbsent(UrlMapping mapping);
   void delete(String code);
}
