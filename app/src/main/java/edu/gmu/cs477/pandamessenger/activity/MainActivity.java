package edu.gmu.cs477.pandamessenger.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import edu.gmu.cs477.pandamessenger.R;
import edu.gmu.cs477.pandamessenger.fragment.ActiveTwitterFragment;
import edu.gmu.cs477.pandamessenger.fragment.HomeFragment;
import edu.gmu.cs477.pandamessenger.fragment.SocialMediaFragmentType;
import edu.gmu.cs477.pandamessenger.fragment.TwitterLoginFragment;
import edu.gmu.cs477.pandamessenger.util.LogConstants;

public class MainActivity extends Activity implements
        TwitterLoginFragment.OnTwitterLoginInteraction,
        HomeFragment.OnHomeFragmentInteraction {

    // default state is HomeFragment
    private SocialMediaFragmentType currentlyActiveFragment = SocialMediaFragmentType.HOME;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentlyActiveFragment = SocialMediaFragmentType.HOME;
                    updateFragmentView();
                    break;
                case R.id.navigation_twitter:
                    currentlyActiveFragment = SocialMediaFragmentType.TWITTER;
                    updateFragmentView();
                    break;
                case R.id.navigation_dashboard:
                    currentlyActiveFragment = SocialMediaFragmentType.TWITTER;
                    updateFragmentView();
                    break;
                case R.id.navigation_notifications:
                    currentlyActiveFragment = SocialMediaFragmentType.TWITTER;
                    updateFragmentView();
                    break;
                default:
                    currentlyActiveFragment = SocialMediaFragmentType.HOME;
                    updateFragmentView();
                    break;
            }
            return true;
        }

    };

    private void updateFragmentView() {
        Log.d(LogConstants.APP_STATE_UPDATE, String.format("Current active fragment is %s", currentlyActiveFragment));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createNewFragment())
                .addToBackStack(null)
                .commit();
    }

    private Fragment createNewFragment() {
        switch (currentlyActiveFragment) {
            case HOME: return HomeFragment.newInstance();
            case TWITTER: return TwitterLoginFragment.newInstance();
            default: return HomeFragment.newInstance();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                Log.d(LogConstants.APP_STATE_UPDATE, "Activity already has saved instance state. Terminating creation.");
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Fragment fragment = createNewFragment();
            Log.d(LogConstants.FRAGMENT_CREATION, String.format("New %s created", currentlyActiveFragment));

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // fragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(LogConstants.FRAGMENT_ACTION, String.format("%s performed an action", currentlyActiveFragment));

        // Pass the activity result to the fragment, which will then pass the result to the login
        // button.
        Fragment fragment = getFragmentManager().findFragmentById(getActiveFragmentId());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private int getActiveFragmentId() {
        switch (currentlyActiveFragment) {
            case HOME: return R.id.fragment_home;
            case TWITTER: return R.id.fragment_twitter_login;
            default: return R.id.fragment_home;
        }
    }

    /**
     * Method hook that indicates that the fragment responsible for logging into Twitter returned
     * successfully
     *
     * If so, then replace the active fragment with an ActiveTwitterFragment to use Twitter API calls
     */
    @Override
    public void onSuccessfulTwitterLogin() {
        Log.d(LogConstants.APP_STATE_UPDATE, "Routing to ActiveTwitterFragment view");
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ActiveTwitterFragment.newInstance())
                .commit();
    }


    @Override
    public void onHomeFragmentInteraction(Uri uri) {
        // TODO: Not sure what goes here
    }
}
