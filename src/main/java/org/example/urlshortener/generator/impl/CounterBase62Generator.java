package org.example.urlshortener.generator.impl;

import org.example.urlshortener.generator.Base62Encoder;
import org.example.urlshortener.generator.ShortCodeGenerator;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class CounterBase62Generator implements ShortCodeGenerator
{
    private final AtomicLong counter;
    private final Base62Encoder encoder;

    public CounterBase62Generator(long initVal, Base62Encoder encoder)
    {
       this.counter = new AtomicLong(initVal);
       this.encoder = Objects.requireNonNull(encoder);
    }

    @Override
    public String generate()
    {
        long id = counter.updateAndGet(current -> {
            if(current == Long.MAX_VALUE)
            {
                throw new IllegalArgumentException("Counter Exhausted");
            }
            return current + 1;
        });
        return encoder.encode(id);
    }
}
