package org.example.urlshortener.exception;

public class InValidUrlException extends IllegalArgumentException
{
   public InValidUrlException(String message)
   {
          super(message);
   }
}
