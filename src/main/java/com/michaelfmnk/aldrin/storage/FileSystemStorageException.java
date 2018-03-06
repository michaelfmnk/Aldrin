package com.michaelfmnk.aldrin.storage;

public class FileSystemStorageException extends RuntimeException {
    public FileSystemStorageException(String msg) {
        super(msg);
    }
    public FileSystemStorageException(String msg, Throwable couse){
        super(msg, couse);
    }
}
