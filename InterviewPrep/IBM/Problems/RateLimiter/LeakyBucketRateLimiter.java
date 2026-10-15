package InterviewPrep.IBM.Problems.RateLimiter;

import java.time.Clock;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeakyBucketRateLimiter implements RateLimitStrategy {

    // waterLevel  — how full the bucket is. Each request adds 1. Leaks drain it over time.
    // lastLeakTime — when we last computed a drain, used to calculate elapsed time.
    private static class Bucket {
        double waterLevel;
        long lastLeakTime;

        Bucket(long currentTime) {
            this.lastLeakTime = currentTime;
        }
    }

    private final int capacity;
    private final double leakRatePerMillis;
    private final Clock clock;

    private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    public LeakyBucketRateLimiter(int capacity, double leakRatePerSecond, Clock clock) {
        this.capacity = capacity;
        this.leakRatePerMillis = leakRatePerSecond / 1000.0;
        this.clock = clock;
    }

    @Override
    public boolean allowRequest(String userId) {
        // New users start with an empty bucket.
        Bucket bucket = userBuckets.computeIfAbsent(userId, id -> new Bucket(currentTime()));

        // Only one thread can fill or drain a particular user's bucket at a time.
        synchronized (bucket) {
            long currentTime = currentTime();

            // Drain water that has leaked out since the last request.
            leak(bucket, currentTime);

            // Bucket full — deny.
            if (bucket.waterLevel >= capacity) {
                return false;
            }

            // Add one unit of water and allow.
            bucket.waterLevel++;
            return true;
        }
    }

    // Leak: drain water proportional to elapsed time, floor at 0.
    // This is a "lazy drain" — no background thread needed.
    private void leak(Bucket bucket, long currentTime) {
        long elapsedTime = currentTime - bucket.lastLeakTime;
        double leaked = elapsedTime * leakRatePerMillis;
        bucket.waterLevel = Math.max(0, bucket.waterLevel - leaked);
        bucket.lastLeakTime = currentTime;
    }

    private long currentTime() {
        return clock.millis();
    }
}