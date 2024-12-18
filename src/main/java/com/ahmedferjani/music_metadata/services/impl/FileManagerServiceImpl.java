package com.ahmedferjani.music_metadata.services.impl;

import com.ahmedferjani.music_metadata.services.FileManagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class FileManagerServiceImpl implements FileManagerService {
    @Value("${image.storage.directory}")
    private String storageDirectory;

    public String saveImageFromUrl(String url, String name, String extension) throws IOException, URISyntaxException {
        InputStream inputStream = new URI(url).toURL().openStream();

        // Generate the full file name based on the provided name and extension
        String fileName = name + '.' + extension;
        File file = new File(storageDirectory, fileName);

        // Save the image file
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            StreamUtils.copy(inputStream, outputStream);
        }

        return fileName;
    }
}
