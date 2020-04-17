package com.ltri.oss.controller;

import com.ltri.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping("/oss/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        return ossService.upload(file);
    }

    @PostMapping("/oss/uploadZip")
    public void uploadZip(@RequestParam("file") MultipartFile file) throws Exception {
        ossService.unZip(file);
    }

}
