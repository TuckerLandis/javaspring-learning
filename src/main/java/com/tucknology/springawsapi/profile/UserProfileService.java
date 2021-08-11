package com.tucknology.springawsapi.profile;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService){
        this.userProfileDataAccessService = userProfileDataAccessService;
    }

    List<UserProfile> getUserProfiles(){
        return  userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1. check if image is not empty
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file" + file.getSize());
        }
        //2. if file is image
        if (!Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF).contains(file.getContentType())
        //3. user exists
        //4. grab metadata
        //5. store image in s3, then update database (userProfileImageLink)



    }
}
