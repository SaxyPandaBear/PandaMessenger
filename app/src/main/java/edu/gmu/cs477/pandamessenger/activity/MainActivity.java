package edu.gmu.cs477.pandamessenger.activity;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import edu.gmu.cs477.pandamessenger.R;
import edu.gmu.cs477.pandamessenger.fragment.HomeFragment;
import edu.gmu.cs477.pandamessenger.fragment.SocialMediaFragmentType;
import edu.gmu.cs477.pandamessenger.fragment.TwitterFragment;

public class MainActivity extends Activity implements
        TwitterFragment.OnTwitterFragmentInteraction,
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
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, createNewFragment())
                .addToBackStack(null)
                .commit();
    }

    private Fragment createNewFragment() {
        switch (currentlyActiveFragment) {
            case HOME: return HomeFragment.newInstance();
            case TWITTER: return TwitterFragment.newInstance();
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
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            Fragment fragment = createNewFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            // fragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public void onTwitterFragmentInteraction(Uri uri) {

    }


    @Override
    public void onHomeFragmentInteraction(Uri uri) {

    }
}
