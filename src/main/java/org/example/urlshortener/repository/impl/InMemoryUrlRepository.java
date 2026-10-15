package org.example.urlshortener.repository.impl;

import org.example.urlshortener.model.UrlMapping;
import org.example.urlshortener.repository.UrlRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUrlRepository implements UrlRepository
{
   private final Map<String, UrlMapping> codeToUrl = new HashMap<>();

   private final Map<String, UrlMapping> urlToCode = new HashMap<>();

   @Override
   public synchronized Optional<UrlMapping> findByShortCode(String code)
   {
      return Optional.ofNullable(codeToUrl.get(code));
   }

   @Override
   public synchronized Optional<UrlMapping> findByLongUrl(String url)
   {
      return Optional.ofNullable(urlToCode.get(url));
   }

   @Override
   public synchronized boolean saveIfAbsent(UrlMapping urlMapping)
   {
       String shortCode = urlMapping.getShortCode();
       String longUrl = urlMapping.getLongUrl();

       if(codeToUrl.containsKey(shortCode) || urlToCode.containsKey(longUrl))
       {
           return false;
       }
       codeToUrl.putIfAbsent(shortCode, urlMapping);
       urlToCode.putIfAbsent(longUrl, urlMapping);
       return true;
   }

   @Override
   public synchronized void delete(String code)
   {
      UrlMapping urlMapping = codeToUrl.remove(code);
      if(urlMapping!=null)
      {
         urlToCode.remove(urlMapping.getLongUrl());
      }
   }
}
