package org.example.urlshortener.generator;

public class Base62Encoder
{
   private static final String ALPHABET =  "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

   public String encode(long number)
   {
      if(number<0)
      {
         throw new IllegalArgumentException("ID Cannot be negative");
      }
      StringBuilder sb = new StringBuilder();
      do
      {
          int remainder = (int)(number % 62);
          sb.append(ALPHABET.charAt(remainder));
          number = number/62;
      }while(number>0);

      return sb.reverse().toString();
   }
}
