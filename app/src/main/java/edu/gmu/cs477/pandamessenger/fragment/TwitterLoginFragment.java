package edu.gmu.cs477.pandamessenger.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import edu.gmu.cs477.pandamessenger.R;
import edu.gmu.cs477.pandamessenger.util.LogConstants;

/**
 * Fragment interface to login to Twitter
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTwitterLoginInteraction} interface
 * to handle interaction events.
 * Use the {@link TwitterLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TwitterLoginFragment extends Fragment {

    private TwitterLoginButton loginButton;

    private OnTwitterLoginInteraction mListener;

    public TwitterLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TwitterLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TwitterLoginFragment newInstance() {
        TwitterLoginFragment fragment = new TwitterLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: If already logged in, skip this fragment and use logged in fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_twitter_login, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        // If no session is available, then ask the user to login
        if (session == null) {
            // View is only accessible in onViewCreated or afterwards, so just use this lifecycle hook
            // to initialize the login button.
            loginButton = view.findViewById(R.id.twitter_login_button);
            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    // Do something with result, which provides a TwitterSession for making API calls
                    // TODO: Find a way to persist logged in state - Docs say that login is automatically persisted
                    // On success, instantiate a logged in fragment
                    if (mListener != null) {
                        Log.d(LogConstants.FRAGMENT_ACTION, "Successfully logged in to Twitter");
                        mListener.onSuccessfulTwitterLogin();
                    }
                    else {
                        Log.d(LogConstants.INTERNAL_ERROR, "Listener is null");
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    // Do something on failure
                    // TODO: Pop up for failed login?, reset state?
                    throw exception;
                }
            });
        } else {
            // if already logged in, go to actively logged in fragment instead of attempting to log in
            Log.d(LogConstants.APP_STATE_UPDATE, "Routing to ActiveTwitterFragment view");
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ActiveTwitterFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTwitterLoginInteraction) {
            mListener = (OnTwitterLoginInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTwitterLoginInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTwitterLoginInteraction {
        void onSuccessfulTwitterLogin();
    }


}
