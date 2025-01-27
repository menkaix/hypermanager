package com.menkaix.hypermanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/attachment")
@RestController
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
        @RequestParam("file") MultipartFile file,
        @RequestParam("projectCode") String projectCode,
        @RequestParam("originalFile") String originalFile) {  

        if(attachmentService.upload(file, projectCode, originalFile)) {
            return ResponseEntity.ok("Uploaded");
        }
        else{
            return ResponseEntity.badRequest().body("Failed to upload");
        }

    }

    
}
