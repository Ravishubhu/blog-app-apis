package com.backendpractice.blog.services.impl;

import com.backendpractice.blog.services.FileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

  @Override
  public String uploadImage(String path, MultipartFile file) throws IOException {
    // File Name
    var name = file.getOriginalFilename();
    var randomId = UUID.randomUUID().toString();
    var completeFileName =
        randomId.concat(
            Optional.ofNullable(name).map(e -> e.substring(e.lastIndexOf("."))).orElse(""));

    // FullPath
    var filePathWithName = path + File.separator + completeFileName;

    // Create folder if not created
    var f = new File(path);
    if (!f.exists()) {
      f.mkdir();
    }

    // file Copy
    Files.copy(file.getInputStream(), Paths.get(filePathWithName));
    return completeFileName;
  }

  @Override
  public InputStream serveFile(String path, String fileName) throws FileNotFoundException {
    String fullPathWithName = path + File.separator + fileName;
    // db logic to return inputStream
    return new FileInputStream(fullPathWithName);
  }
}
