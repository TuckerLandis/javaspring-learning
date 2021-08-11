package com.tucknology.springawsapi.profile;

import com.tucknology.springawsapi.bucket.BucketName;
import com.tucknology.springawsapi.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService,
                              FileStore fileStore){
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles(){
        return  userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        //1. check if image is not empty...
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file" + file.getSize());
        }
        //2. ...if file is image...
        if (!Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF).contains(file.getContentType())){
            throw new IllegalStateException(("This file is not a supported image type"));
        }
        //3.... user exists...
        UserProfile user = userProfileDataAccessService
                .getUserProfiles()
                .stream()// creating a stream to filter, need to research this
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst().
                orElseThrow(() -> new IllegalStateException((String.format("User profile %s not found", userProfileId))));

        //4. grab metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        //5. store image in s3, then update database (userProfileImageLink)

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getName(), UUID.randomUUID());

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }


    }
}
