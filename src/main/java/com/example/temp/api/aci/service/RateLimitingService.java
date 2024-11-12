package com.example.temp.api.aci.service;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class RateLimitingService {
	

    private final Map<String, RateLimit> rateLimitMap = new WeakHashMap<>();

    public synchronized boolean isLimitExceeded(String userId) {
        RateLimit rateLimit = rateLimitMap.computeIfAbsent(userId, k -> new RateLimit());

        return rateLimit.isLimitExceeded();
    }

    private static class RateLimit {
        private static final int MAX_REQUESTS = 20;
        private static final long RESET_INTERVAL = TimeUnit.MINUTES.toMillis(1);

        private int requestCount = 0;
        private long lastResetTime = System.currentTimeMillis();

        synchronized boolean isLimitExceeded() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastResetTime > RESET_INTERVAL) {
                requestCount = 0;
                lastResetTime = currentTime;
            }

            if (requestCount < MAX_REQUESTS) {
                requestCount++;
                return false;
            } else {
                return true;
            }
        }
    }
    /*

    private final Map<String, RateLimit> rateLimitMap = new ConcurrentHashMap<>();

    public boolean isLimitExceeded(String userId) {
        RateLimit rateLimit = rateLimitMap.computeIfAbsent(userId, k -> new RateLimit());

        return rateLimit.incrementAndCheck();
    }

    @Data
    private static class RateLimit {
        private int requestCount = 0;
        private long lastResetTime = System.currentTimeMillis();

        boolean incrementAndCheck() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastResetTime > TimeUnit.MINUTES.toMillis(1)) {
                requestCount = 0;
                lastResetTime = currentTime;
            }

            requestCount++;
            return requestCount > 20;
        }
    }*/
}