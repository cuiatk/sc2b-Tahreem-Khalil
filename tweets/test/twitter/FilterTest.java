/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * 
     * testing strategies for writtenBy, inTimespan, Containing methods
     * 
     * partition for writtenBy(tweets, username) -> tweetsWrittenBy
     * tweet.size(): 0,1,>1
     * username found in tweets: 0,1,>1
     * 
     * partition for inTimespan(tweets, Timespan) -> tweetsWrittenInTimespan
     * tweet.size(): 0,1,>1
     * tweets in a particular Timespan: 0,1,>1
     * 
     * partition for Containing(tweets, words) -> tweetsContainCertainWords
     * tweet.size(): 0,1,>1
     * word.size(): 1, >1
     * word: upper case, lower case
     * tweets containg words: 0,1,>1
     * 
     * 
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    @SuppressWarnings("unused")
    private static final Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
    @SuppressWarnings("unused")
    private static final Instant d4 = Instant.parse("2016-02-17T13:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "bbitdiddle", 
            " Everyone say hi to the lady reading my phone over my shoulder.", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    /**
     * test case: 1 tweets one author
     * tweet.size: =1
     * username in tweets: =1
     */
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    
     /**
     * test case: 2 tweets one author
     * tweet.size: >1
     * username in tweets: =1
     */
    @Test
    public void testTwoTweetsOneAuthor() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "bbitdiddle");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.containsAll(Arrays.asList(tweet2, tweet3)));
    }
    
    /**
     * test case: 2 tweets 2 different authors
     * tweet.size: >1
     * username in tweets: >1
     */
    @Test
    public void testTwoTweetsTwoAuthors() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "bbitdiddle");
        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet2));
        
        assertEquals("expected singleton list", 1, writtenBy2.size());
        assertTrue("expected list to contain tweet", writtenBy2.contains(tweet1));
    }
    
    /**
     * test case: tweets written by no author
     *  tweet.size: >1
     * username in tweets: 0
     */
    @Test
    public void testAllTweetsNoAuthor() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3), "Tahreem_k");
        
        assertEquals("expected no tweet", 0, writtenBy.size());
    }
    
    /**
     * test case: tweets written in timespan
     *  tweet.size: >1
     * username in tweets: 0
     */
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    /**
     * test case: one tweet in timespan
     * tweet.size(): >1
     * tweets in a particular Timespan: 1
     */
    @Test
    public void testInTimespanOneTweetOneResult() {
        Instant testStart = Instant.parse("2016-02-17T11:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T13:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList( tweet1, tweet2, tweet3), new Timespan(testStart, testEnd));
        
        assertEquals("expected one tweet", 0, inTimespan.size());
    }
    
    /**
     * test case: no tweet in timespan
     * tweet.size(): >1
     * tweets in a particular Timespan: 0
     */
    @Test
    public void testInTimespanNoTweetResult() {
        Instant testStart = Instant.parse("2016-02-17T10:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList( tweet1, tweet2, tweet3), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.contains(tweet2));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet2));
    }
    
    /**
     * test case: tweets containig word
     * tweet.size(): >1
     * word.size(): 1
     * word: ignore case
     * tweets containg words: 1
     */
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    /**
     * test case: show tweets that have multiple same words
     * tweet.size(): >1
     * word.size(): >1
     * word: ignore case
     * tweets containg words: >1
     */
    @Test
    public void testContainingTweetsMultipleWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("talk", "lady","hi"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    /**
     * test case: no tweets that have same words
     * tweet.size(): >1
     * word.size(): >1
     * word: ignore case
     * tweets containg words: 0
     */
    @Test
    public void testContainingNoTweetsHaveWords() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("far", "move","hotel bar"));
        
        assertEquals("expected no tweet", 0, containing.size());
    }
    
    /**
     * test case: show tweets that have multiple same words in upper case
     * tweet.size(): >1s
     * word.size(): >1
     * word: upper case
     * tweets containg words: >1
     */
    @Test
    public void testContainingTweetsMultipleWordsUpperCase() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3), Arrays.asList("Talk", "LADY","HI"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
