package InterviewPrep.IBM.Problems.RateLimiter;

import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimitStrategy {

    // tokens       — current number of available tokens (can be fractional between refills).
    // lastRefillTime — when we last computed a refill, used to calculate elapsed time.
    private static class Bucket {
        double tokens;
        long lastRefillTime;

        Bucket(double tokens, long lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }

    private final int capacity;
    private final double refillRatePerMillis;
    private final Clock clock;

    private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    public TokenBucketRateLimiter(int capacity, double refillRatePerSecond, Clock clock) {
        this.capacity = capacity;
        this.refillRatePerMillis = refillRatePerSecond / 1000.0;
        this.clock = clock;
    }

    @Override
    public boolean allowRequest(String userId) {
        // New users start with a full bucket.
        Bucket bucket = userBuckets.computeIfAbsent(userId, id -> new Bucket(capacity, currentTime()));

        // Only one thread can consume or refill a particular user's bucket at a time.
        synchronized (bucket) {
            long currentTime = currentTime();

            // Add tokens earned since the last request.
            refill(bucket, currentTime);

            // Not enough tokens — deny.
            if (bucket.tokens < 1) {
                return false;
            }

            // Consume one token and allow.
            bucket.tokens--;
            return true;
        }
    }

    // Refill: tokens accumulate proportional to elapsed time, capped at capacity.
    // This is a "lazy refill" — no background thread needed.
    private void refill(Bucket bucket, long currentTime) {
        long elapsedTime = currentTime - bucket.lastRefillTime;
        double tokensToAdd = elapsedTime * refillRatePerMillis;
        bucket.tokens = Math.min(capacity, bucket.tokens + tokensToAdd);
        bucket.lastRefillTime = currentTime;
    }

    private long currentTime() {
        return clock.millis();
    }
}