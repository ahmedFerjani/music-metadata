package com.ahmedferjani.music_metadata.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = { "image.storage.directory=target/test-images" })
class FileManagerServiceImplTest {

    @InjectMocks
    private FileManagerServiceImpl fileManagerService;

    @TempDir
    Path tempDir; // temporary directory provided by JUnit

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        fileManagerService.setStorageDirectory(tempDir.toFile().getAbsolutePath());
    }

    @Test
    void saveImageFromUrl_shouldSaveFile_whenValidUrl() throws IOException, URISyntaxException {
        URL resource = getClass().getResource("/test-image.jpg");
        assertNotNull(resource, "Test resource should exist");

        String fileName = fileManagerService.saveImageFromUrl(resource.toString(), "myImage", "jpg");

        File savedFile = new File(tempDir.toFile(), fileName);
        assertTrue(savedFile.exists(), "File should be created");
        assertEquals("myImage.jpg", fileName);
    }

    @Test
    void saveImageFromUrl_shouldCreateDirectory_ifNotExists() throws IOException, URISyntaxException {
        File newDir = new File(tempDir.toFile(), "newDir");
        fileManagerService.setStorageDirectory(newDir.getAbsolutePath());

        // Call saveImageFromUrl to indirectly test directory creation
        URL resource = getClass().getResource("/test-image.jpg");
        assertNotNull(resource);

        fileManagerService.saveImageFromUrl(resource.toString(), "image", "jpg");

        assertTrue(newDir.exists());
        assertTrue(newDir.isDirectory());

        FileSystemUtils.deleteRecursively(newDir);
    }

    @Test
    void getOrCreateStorageDirectory_shouldThrowException_whenCannotCreate() throws IOException {
        // Create a file to block directory creation
        File file = tempDir.resolve("blockedDir").toFile();
        boolean created = file.createNewFile();
        assertTrue(created, "Temporary file should be created to block directory");

        fileManagerService.setStorageDirectory(file.getAbsolutePath());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileManagerService.getOrCreateStorageDirectory());

        assertTrue(exception.getMessage().contains("Path exists but is not a directory"));
    }

}
