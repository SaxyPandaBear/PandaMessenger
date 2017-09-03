package edu.gmu.cs477.pandamessenger;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

public class PandaMessengerApplication extends Application {
    private static final String CONSUMER_KEY = BuildConfig.TWITTER_CONSUMER_KEY;
    private static final String CONSUMER_SECRET = BuildConfig.TWITTER_CONSUMER_SECRET;

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET))
                .debug(true)
                .build();
        Twitter.initialize(config);
    }
}
