package Problems.Heaps.Hard;

import java.util.*;

class Tweet implements Comparable<Tweet> {
    int time;
    int tweetId;

    Tweet(int t, int id){
        this.time = t;
        this.tweetId = id;
    }

    public int compareTo(Tweet that){
        // Store in decreasing order
        return that.time - this.time;
    }
}


class User {
    int userId;
    HashSet<Integer> followers;
    LinkedList<Tweet> tweets;

    User(int userId){
        this.userId = userId;
        followers = new HashSet<>();
        tweets = new LinkedList<>();
    }

    public void addTweet(Tweet tweet){
        this.tweets.addFirst(tweet);
    }

    public void addFollower(int followeeId){
        this.followers.add(followeeId);
    }

    public void removeFollower(int followeeId){
        this.followers.remove(followeeId);
    }
}

class Twitter {
    HashMap<Integer, User> userMap;
    int counter;
    public Twitter() {
        this.userMap = new HashMap<>();
        this.counter = 0;
    }

    public void postTweet(int userId, int tweetId) {
        counter++;
        if(!userMap.containsKey(userId)){
            userMap.put(userId, new User(userId));
        }

        User user = userMap.get(userId);
        user.addTweet(new Tweet(counter, tweetId));
    }

    public List<Integer> getNewsFeed(int userId) {
        if(!userMap.containsKey(userId))
            return new ArrayList<>();

        PriorityQueue<Tweet> pq = new PriorityQueue<>();
        User user = userMap.get(userId);
        int count = 0;
        // Get self tweets + Tweets of followers
        for(int follower: user.followers){
            for(Tweet tweet: userMap.get(follower).tweets){
                pq.offer(tweet);
                count++;
                if(count > 10) break;
            }
        }
        count = 0;
        for(Tweet tweet: user.tweets){
            pq.offer(tweet);
            count++;
            if(count > 10) break;
        }

        List<Integer> res = new ArrayList<>();
        int index = 0;
        while(!pq.isEmpty() && index < 10){
            res.add(pq.poll().tweetId);
            index++;
        }
        return res;
    }

    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)){
            userMap.put(followerId, new User(followerId));
        }

        if(!userMap.containsKey(followeeId)){
            userMap.put(followeeId, new User(followeeId));
        }

        User user = userMap.get(followerId);
        user.addFollower(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || !userMap.containsKey(followeeId))
            return;

        User user = userMap.get(followerId);
        user.removeFollower(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */

