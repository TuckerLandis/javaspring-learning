package com.tucknology.springawsapi.datastore;

import com.tucknology.springawsapi.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Repository
public class FakeUserProfileDatastore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "someone", null));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "someoneElse", null));
    }

    public List <UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
