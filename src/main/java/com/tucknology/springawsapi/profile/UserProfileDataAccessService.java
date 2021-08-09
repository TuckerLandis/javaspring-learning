package com.tucknology.springawsapi.profile;

import com.tucknology.springawsapi.datastore.FakeUserProfileDatastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserProfileDataAccessService {

    // this line of code is what changes to switch to a real database once front end is done
    private final FakeUserProfileDatastore fakeUserProfileDatastore;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDatastore fakeUserProfileDatastore) {
        this.fakeUserProfileDatastore = fakeUserProfileDatastore;
    }

    List<UserProfile> getUserProfiles() {
        return fakeUserProfileDatastore.getUserProfiles();
    }
}
