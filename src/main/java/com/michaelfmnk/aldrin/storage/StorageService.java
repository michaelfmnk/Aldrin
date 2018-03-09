package com.michaelfmnk.aldrin.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    /**
     * storage service is getting ready for usage;
     * method is called on application startup
     */
    void init();


    /**
     * storage service saves file
     * @param file multipart file
     */
    void store(MultipartFile file);


    /**
     * @returns Stream of Paths for all sored files
     */
    Stream<Path> loadAll();


    /**
     * Finds file with the specified filename in the file storage location
     * and returns Path
     * @param filename string
     * @return Path to file with the specified filename
     */
    Path load(String filename);

    /**
     * @param filename string
     * @return Resource for specified filename
     */
    Resource loadAsResource(String filename);


    /**
     * storage service deletes all files from the storage
     */
    void deleteAll();
}
