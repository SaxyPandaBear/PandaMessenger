package edu.gmu.cs477.pandamessenger.fragment;

public enum SocialMediaFragmentType {
    HOME("Home Fragment"),
    TWITTER("Twitter Fragment");

    private String description;
    SocialMediaFragmentType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
