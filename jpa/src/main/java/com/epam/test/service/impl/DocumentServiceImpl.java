package com.epam.test.service.impl;

import static org.apache.commons.io.FilenameUtils.getExtension;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epam.test.converter.DocumentConverter;
import com.epam.test.dto.DocumentDto;
import com.epam.test.entity.Document;
import com.epam.test.enums.DocumentType;
import com.epam.test.exception.FileUploadException;
import com.epam.test.exception.NotFoundException;
import com.epam.test.repository.DocumentRepository;
import com.epam.test.service.DocumentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentConverter documentConverter;

    @Override
    public DocumentDto update(MultipartFile file) {
        DocumentDto document = new DocumentDto();
        try {
            String extension = getExtension(file.getOriginalFilename());
            document.setType(DocumentType.getByName(extension));
            document.setData(file.getBytes());
        } catch (IOException | IllegalArgumentException e) {
            throw new FileUploadException(e);
        }
        return update(document);
    }

    @Override
    public DocumentDto update(DocumentDto dto) {
        Document document = documentConverter.toEntity(dto);
        if (document.getId() != null) {
            documentRepository.findById(document.getId());
        }
        return documentConverter.toDto(documentRepository.save(document));
    }

    @Override
    public void delete(Integer id) {
        documentRepository.deleteById(id);
    }

    @Override
    public DocumentDto get(Integer id) {
        return documentConverter.toDto(documentRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Page<DocumentDto> getAll(Pageable pageable) {
        return documentRepository.findAll(pageable).map(documentConverter::toDto);
    }
}
