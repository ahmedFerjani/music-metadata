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

    @Override
    public String saveImageFromUrl(String url, String name, String extension) throws IOException, URISyntaxException {

        // Ensure directory exists
        File directory = getOrCreateStorageDirectory();

        // Generate full file name
        String fileName = name + "." + extension;
        File file = new File(directory, fileName);

        try (InputStream inputStream = new URI(url).toURL().openStream();
                FileOutputStream outputStream = new FileOutputStream(file)) {

            StreamUtils.copy(inputStream, outputStream);
        }

        return fileName;
    }

    /**
     * Creates the storage directory if it does not exist
     */
    protected File getOrCreateStorageDirectory() {
        File directory = new File(storageDirectory);

        // Check if path exists but is not a directory
        if (directory.exists() && !directory.isDirectory()) {
            throw new RuntimeException("Path exists but is not a directory: " + storageDirectory);
        }

        // Create directory if it doesn't exist
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create storage directory: " + storageDirectory);
            }
        }

        return directory;
    }

    // Setter for testing purposes
    public void setStorageDirectory(String storageDirectory) {
        this.storageDirectory = storageDirectory;
    }
}
