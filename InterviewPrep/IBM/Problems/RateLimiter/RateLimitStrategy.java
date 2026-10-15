package InterviewPrep.IBM.Problems.RateLimiter;

public interface RateLimitStrategy {
    boolean allowRequest(String userId);
}