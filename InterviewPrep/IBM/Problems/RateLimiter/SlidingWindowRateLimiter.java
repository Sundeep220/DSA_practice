package InterviewPrep.IBM.Problems.RateLimiter;

import InterviewPrep.IBM.Problems.RateLimiter.RateLimitStrategy;

import java.time.Clock;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimitStrategy {

    // Stores the exact timestamp of every request in the current window.
    // Timestamps older than (now - windowSize) are evicted on each call.
    private static class UserWindow {
        Deque<Long> requestTimes = new ArrayDeque<>();
    }

    private final int maxRequests;
    private final long windowSizeMillis;
    private final Clock clock;

    private final Map<String, UserWindow> userWindows = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeMillis, Clock clock) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.clock = clock;
    }

    @Override
    public boolean allowRequest(String userId) {
        // Create an empty log for this user on first request.
        UserWindow window = userWindows.computeIfAbsent(userId, id -> new UserWindow());

        // Only one thread can update a particular user's log at a time.
        synchronized (window) {
            long currentTime = clock.millis();

            // The rolling window starts here — anything before this is expired.
            long windowStart = currentTime - windowSizeMillis;

            // Evict timestamps that fall outside the sliding window.
            while (!window.requestTimes.isEmpty() && window.requestTimes.peekFirst() < windowStart) {
                window.requestTimes.pollFirst();
            }

            // Limit reached — deny.
            if (window.requestTimes.size() >= maxRequests) {
                return false;
            }

            // Record this request and allow.
            window.requestTimes.addLast(currentTime);
            return true;
        }
    }
}