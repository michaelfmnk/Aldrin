package com.michaelfmnk.aldrin.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements  StorageService {

    /**
     * location where files are stored
     */
    private final Path rootLocation;

    @Autowired
    FileSystemStorageService(StorageProperties properties){
        this.rootLocation = Paths.get(properties.getLocation());
    }


    /**
     * creates directory for storing files
     * @throws FileSystemStorageException if storage couldn't have been initialized
     */
    @Override
    public void init() {
        try{
            Files.createDirectories(rootLocation);
        }catch (IOException e){
            throw new FileSystemStorageException("Could not initialize storage", e);
        }
    }

    /**
     * Saves multipart file to storage. Checks whether file is not empty
     * and doesn't start with '..'. Then copies the received file to rootLocation.
     * @param file multipart file
     * @return Path to saved file
     * @throws FileSystemStorageException
     */
    @Override
    public Path store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try{
            if (filename.isEmpty()){
                throw new FileSystemStorageException("couldn't save an empty file "+ filename);
            }
            if (filename.contains("..")){
                throw new FileSystemStorageException(
                        "Can not store with relative path outside current directory "+ filename);
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            return rootLocation.resolve(file.getOriginalFilename());

        }catch (IOException e){
            throw new FileSystemStorageException("Failed to store file " + filename, e);
        }
    }


    /**
     * Walks through rootLocation(depth = 1) and
     * @return Stream of Paths for all sored files
     * @throws FileSystemStorageException if IOException
     */
    @Override
    public Stream<Path> loadAll() {
        try{
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }catch (IOException e){
            throw new FileSystemStorageException("Failed to read sored files", e);
        }
    }


    /**
     * Finds file with the specified filename in the file storage location
     * and returns Path
     * @param filename string
     * @return Path to file with the specified filename
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * Crates Resource for a file. Checks whether it exists and is readable
     * @param filename string
     * @return Resource for specified filename.
     * @throws FileSystemStorageException
     * @throws StorageFileNotFoundException
     */
    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new FileSystemStorageException(
                        "couldn't read file: " + filename);
            }
        }catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("couldn't read file: " + filename, e);
        }
    }


    /**
     * deletes all files from the storage
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
