package sg.edu.nus.iss.day38exampleserver.repository;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class SpacesRepository {
    
    @Autowired
    private AmazonS3 s3;

    public URL upload(String title, MultipartFile file) throws IOException {
        // Add custom metadata (Optional)
        Map<String, String> userData = new HashMap<>();
        userData.put("title", title);
        userData.put("filename", file.getOriginalFilename());
        userData.put("upload-date", (new Date()).toString());

        // Add object's metadata (Mandatory)
        ObjectMetadata objMetaData = new ObjectMetadata();
        objMetaData.setContentType(file.getContentType());
        objMetaData.setContentLength(file.getSize());
        objMetaData.setUserMetadata(userData);

        // Generate a random key name
        String key = UUID.randomUUID().toString().substring(0, 8);

        // dashe - bucket name
        // key - key
        // file bytes
        // metadata
        PutObjectRequest putReq = new PutObjectRequest("kai", key, 
            file.getInputStream(), objMetaData);
        // Make this file publicly accessible
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        PutObjectResult result = s3.putObject(putReq);
        System.out.printf(">>> result: %s\n".formatted(result));

        return s3.getUrl("kai", key);

    }
}
