package edu.gmu.cs477.pandamessenger.util;

import edu.gmu.cs477.pandamessenger.fragment.SocialMediaFragmentType;

/**
 * Exception that is thrown when a social media application fails
 * or is unable to authenticate with given credentials
 */
public class LoginException extends RuntimeException {

    private SocialMediaFragmentType socialMediaType;

    public LoginException(SocialMediaFragmentType type, String message) {
        super(message);
        this.socialMediaType = type;
    }

    public SocialMediaFragmentType getSocialMediaType() {
        return socialMediaType;
    }

    public void setSocialMediaType(SocialMediaFragmentType socialMediaType) {
        this.socialMediaType = socialMediaType;
    }
}
