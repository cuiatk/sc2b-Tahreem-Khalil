/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * 
     * testing strategy for guessFollowGraph and influencers methods.
     * 
     * partitioning for guessFollowGraph(list(tweets)) -> followsGraph
     * tweets.size(): 0,1,>1
     * author: 1,>1
     * followers of user: 0,1,>1
     * check that user doesn't follow self
     * check that author who has no followers but follow another user
     * 
     * partitioning for influencers(followGraph) -> greatestInfluencers
     * followGraph.size(): 0,1,>1
     * influencers: 0,1,>1
     * check that user is the top influencer
     * check that user is lowest influencer
     * check that user lies in the middle of the list
     * 
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
	
	    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
	    @SuppressWarnings("unused")
	    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
	    private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
	    
	    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
	    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "@rivest talk in 30 minutes #hype", d2);
	    private static final Tweet tweet3 = new Tweet(3, "the_rock", 
	            " Everyone say hi to the lady reading my phone over my shoulder.", d2);
	    private static final Tweet tweet4 = new Tweet(4, "yassGirl", "to be or not to be. @byeBoss @yoloGirl", d4);
	    private static final Tweet tweet5 = new Tweet(5, "keke_12", "hi @fake_friend and @self_absorbed_b wow your "
	    		+ "usernames actually does match your personality.", d4);
	    private static final Tweet tweet6 = new Tweet(6, "alyssa", "@bbitdiddle is it reasonable to talk about rivest "
	    		+ "so much? when will it end!!", d4);
	    private static final Tweet tweet7 = new Tweet(7, "theRealTahreem", "to be or not to be.", d4);
	    private static final Tweet tweet8 = new Tweet(8, "jessiJ", "you go girl.@theRealTahreem", d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    /**
     * test case: followers graph is empty
     * tweet.size: 0
     * followgraph.count: 0
     * author = 0
     */
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }
    
    /**
     * test case: one tweet one author and multiple followers
     * tweet.size: =1
     * followgraph.count: >1
     * author = 1
     */
    @Test
    public void testGuessFollowsGraphOneAuthorMultipleFollowers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
        
        System.out.println(followsGraph);
        Map<String, Set<String>> ourFollowGraph = new HashMap<>(); 
        ourFollowGraph.put("yassgirl", new HashSet<String>(Arrays.asList("yologirl","byeboss")));
        
        assertEquals("multiple followers", ourFollowGraph, followsGraph);
    }
    
    /**
     * test case: one tweet one author and no followers
     * tweet.size: =1
     * followgraph.count: 0
     * author = 1
     */
    @Test
    public void testGuessFollowsGraphOneAuthorNoFollowers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        
        System.out.println(followsGraph);
        Map<String, Set<String>> ourFollowGraph = new HashMap<>();
        ourFollowGraph.put("the_rock", new HashSet<String>(Arrays.asList()));
        
        assertEquals("no followers", ourFollowGraph, followsGraph);
     
    }
    
    /**
     * test case: multiple tweet multiple author and no followers
     * tweet.size: >1
     * followgraph.count: 0
     * author = >1
     */
    @Test
    public void testGuessFollowsGraphMultipleAuthorNoFollowers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3, tweet7));

        System.out.println(followsGraph);
        Map<String, Set<String>> ourFollowGraph = new HashMap<>(); 
        ourFollowGraph.put("therealtahreem", new HashSet<String>(Arrays.asList()));
        ourFollowGraph.put("the_rock", new HashSet<String>(Arrays.asList()));
       
        assertEquals("no followers", ourFollowGraph, followsGraph);
    }
    
    /**
     * test case: followers graph, multiple tweet multiple author and no followers
     * tweet.size: >1
     * followgraph.count: >1
     * author = >1
     */
    @Test
    public void testGuessFollowsGraphMultipleAuthorMultipleFollowers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4, tweet5));
        assertEquals("expected multiple followers",2, followsGraph.size());
        
        System.out.println(followsGraph);
        Map<String, Set<String>> ourFollowGraph = new HashMap<>(); 
        ourFollowGraph.put("keke_12", new HashSet<String>(Arrays.asList("fake_friend","self_absorbed_b")));
        ourFollowGraph.put("yassgirl", new HashSet<String>(Arrays.asList("yologirl","byeboss")));
        assertEquals("multiple followers", ourFollowGraph, followsGraph);
    }
    
    /**
     * test case: followers graph one tweet one author and one followers
     * tweet.size: 1
     * followgraph.count: 1
     * author = 1
     */
    @Test
    public void testGuessFollowsGraphOneAuthorOneFollowers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        assertEquals("expected 1 followers",1, followsGraph.size());
        
        assertTrue(followsGraph.get("bbitdiddle").contains("rivest"));
    }
    
    /**
     * test case: followers graph contains 0 followers
     * tweet.size: 1
     * followgraph.count: 0
     * check that user doesn't follow self
     * author = 1
     */
    @Test
    public void testGuessFollowsGraphAuthorIsNotSelfFollower() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        
        assertFalse("expected not to follow self",followsGraph.get("bbitdiddle").contains(tweet2.getAuthor()));
    }
    
    /**
     * test case: author no followers but follow another user
     * tweet.size: 1
     * followgraph.count: 1 
     * author = 1
     */
    @Test
    public void testNoFollowerButFollowOtherAuthor() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet6));
        assertEquals("expected 1 followers",1, followsGraph.size());
        assertTrue("expected user bbitdiddle follow self but follow others",followsGraph.get("alyssa").contains("bbitdiddle"));
    }
    
    /**
     * test case: influencer list is empty
     * followGraph 0
     * influencers in followGraph 0
     */
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty()); 
    }

    
    /**
     * test case: influencer list is not empty
     * followGraph 1
     * influencers in followGraph 1
     */
    @Test
    public void testInfluencersNotEmpty() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet8));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println(influencers);
        assertFalse("expected non empty list", influencers.isEmpty());
        assertEquals("expected 1 influencers",1, influencers.size());
    }
    
    /**
     * test case: influencer list contain multiple set multiple influencers
     * followGraph >1
     * influencers in followGraph >1
     */
    @Test
    public void testInfluencersHasMoreInfluencers() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4, tweet2));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println(followsGraph);
        System.out.println(influencers);
        assertFalse("expected non empty list", influencers.isEmpty());
        assertEquals("expected 3 influencers",3, influencers.size());
        
    }
    
    /**
     * test case: users who have 0 influence(folowers)
     * followGraph >1
     * influencers in followGraph 0
     */
    @Test
    public void testInfluencersWhoHasZeroInfluencers() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet7));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println(followsGraph);
        System.out.println(influencers);
        assertEquals("expected 0 influencers",0, influencers.size());
        
    }
    
    /**
     * test case: check that first user has the most influence(followers)
     * followGraph >1
     * influencers in followGraph >1
     * top influencer = yoloGirl
     */
    @Test
    public void testInfluencersWhoIsTheGreatestInfluencer() {
    	Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4, tweet2));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println(followsGraph);
        System.out.println(influencers);
        
        assertEquals("expected yologirl", "yologirl", influencers.get(0));   
        
    }
    
    /**
     * test case: check who has the lowest influence
     * followGraph >1
     * influencers in followGraph >1
     * lowest influencer = rivest
     */
    @Test
    public void testInfluencersWhoHasTheLowestInfluence() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet4, tweet3));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println(influencers+" >1 ");
        
        assertFalse("Expected non-empty list", influencers.isEmpty());
        assertEquals("Expected three followers", 3, influencers.size());
        assertTrue("Expected lowest influencer to have the least number of followers", influencers.get(2).equalsIgnoreCase("rivest"));
    }
    
    /**
     * test case: check who has the lowest influence
     * followGraph >1
     * influencers in followGraph >1
     * middle influencer = byeBoss
     */
    @Test
    public void testInfluencersUserInTheMiddleList() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet6, tweet2, tweet4, tweet3,tweet8));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        System.out.println(influencers+" >1 ");
        
        assertFalse("Expected non-empty list", influencers.isEmpty());
        assertEquals("Expected five followers", 5, influencers.size());
        assertTrue("Expected middle influencer to have the least number of followers", influencers.get(2).equalsIgnoreCase("byeBoss"));
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
