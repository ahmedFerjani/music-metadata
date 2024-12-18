package com.ahmedferjani.music_metadata.services;

import java.io.IOException;
import java.net.URISyntaxException;

public interface FileManagerService {

    /**
     * Downloads an image from the specified URL and saves it to a local directory with the given name and extension.
     *
     * @param url       the URL of the image to download
     * @param name      the desired name of saved image file (without extension)
     * @param extension the desired extension of saved image file
     * @return the full name of the saved image with extension
     * @throws IOException        if an I/O error occurs during the download or save process
     * @throws URISyntaxException if URL is malformed
     */
    String saveImageFromUrl(String url, String name, String extension) throws IOException, URISyntaxException;
}
