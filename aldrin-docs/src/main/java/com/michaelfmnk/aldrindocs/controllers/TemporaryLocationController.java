package com.michaelfmnk.aldrindocs.controllers;

import com.michaelfmnk.aldrindocs.dtos.DocumentDto;
import com.michaelfmnk.aldrindocs.services.StorageService;
import lombok.AllArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping(value = Api.ROOT_PATH)
public class TemporaryLocationController {
    private final StorageService storageService;

    @PostMapping(Api.TemporaryStorage.TEMPORARY_LOCATION)
    public DocumentDto uploadTemporaryDocument(@RequestParam("file") MultipartFile file)
            throws TikaException, IOException, SAXException {
        return storageService.uploadFile(file);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(Api.TemporaryStorage.TEMPORARY_FILE_BY_ID)
    public void deleteTemporaryDocument(@PathVariable("file_id") UUID fileId) throws IOException {
        storageService.deleteTemporaryFile(fileId);
    }
}
