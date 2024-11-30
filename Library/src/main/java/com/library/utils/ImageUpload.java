package com.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class ImageUpload {

    private static final Logger logger = LoggerFactory.getLogger(ImageUpload.class);


    private final String UPLOAD_FOLDER = "C:\\Users\\egor2\\3_course\\e-store\\Library\\src\\main\\resources\\static\\img\\image-product";


    public boolean uploadFile(MultipartFile file) {
        boolean isUpload = false;
        try {

            String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();


            Files.copy(file.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separator + uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
            logger.info("File uploaded successfully: " + uniqueFileName);
        } catch (IOException e) {
            logger.error("Error uploading file", e);
        }
        return isUpload;
    }

    public boolean checkExist(MultipartFile multipartFile) {
        boolean isExist = false;
        try {
            File file = new File(UPLOAD_FOLDER + "\\" + multipartFile.getOriginalFilename());
            isExist = file.exists();
            if (isExist) {
                logger.info("File already exists: " + multipartFile.getOriginalFilename());
            }
        } catch (Exception e) {
            logger.error("Error checking if file exists", e);
        }
        return isExist;
    }
}
