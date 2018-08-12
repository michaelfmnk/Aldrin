package com.michaelfmnk.aldrindocs.services;

import com.michaelfmnk.aldrindocs.dtos.DocumentDto;
import com.michaelfmnk.aldrindocs.entities.Document;
import com.michaelfmnk.aldrindocs.validation.IfNullReturnNull;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {
    @IfNullReturnNull
    public DocumentDto toDto(Document entity) {
        return DocumentDto.builder()
                .fileId(entity.getFileId())
                .dataId(entity.getDataId())
                .size(entity.getSize())
                .mime(entity.getMime())
                .documentName(entity.getDocumentName())
                .build();
    }

}
