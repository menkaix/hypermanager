package com.menkaix.hypermanager.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class AttachmentService {

    private static Logger logger = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    private Environment env;

    public boolean upload(MultipartFile file, String projectCode, String originalFile) {

        String attachmentURL = env.getProperty("microservices.attachment.url");
        String apikey = env.getProperty("microservices.apikey");
        String projectDataBucket = env.getProperty("google.cloudstorage.bucket");

        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.post(attachmentURL + "/upload")
                    .header("x-api-key", apikey)
                    .field("file", file)
                    .field("bucket_name", projectDataBucket)
                    .field("destination_blob_name", projectCode + "/" + originalFile)
                    .asString();
            if (response.getStatus() == 200) {
                return true;
            } else {
                logger.error("Failed to upload file. Response code: " + response.getStatus());
                return false;
            }
        } catch (UnirestException e) {
            logger.error("Exception on file upload ", e);
            return false;
        }

    }

}
