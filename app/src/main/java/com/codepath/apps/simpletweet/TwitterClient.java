package com.codepath.apps.simpletweet;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.apps.simpletweet.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import static com.codepath.apps.simpletweet.R.id.tweetBody;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "nuVrDjMAJLTickmo0uv4GCyLl";       // Change this
	public static final String REST_CONSUMER_SECRET = "a0EiDc9p3Lm8WfZpgyeI1PXYNYxhRFwpW3tGDfzwUcYs5z2gBT"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets.com"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	//methods are endpoints
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}


//	GET statuses/home_timeline
//		count=25
//		since_id=1
	public void getHomeTimeline(Boolean loadNext, Long maxId, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("since_id", 1);
		//params.put("oEmbed");
        if(loadNext) {
            params.put("max_id", maxId);
        }
		getClient().get(apiUrl,params, handler);
	}

	public void postTweet(String tweetBody, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status",tweetBody);

        getClient().post(apiUrl, params, handler);
    }
    public void postReplyTweet(String replyToUserName, Long tweetId, String tweetBody, AsyncHttpResponseHandler handler){
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status",tweetBody);
        params.put("in_reply_to_status_id", tweetId);
        params.put("in_reply_to_screen_name" , replyToUserName);

        getClient().post(apiUrl, params, handler);
    }
	//compose tweet
	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
