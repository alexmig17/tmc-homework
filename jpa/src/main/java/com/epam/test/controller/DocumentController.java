package com.epam.test.controller;

import static java.lang.String.format;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epam.test.dto.DocumentDto;
import com.epam.test.enums.DocumentType;
import com.epam.test.service.DocumentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Integer id) {
        DocumentDto document = documentService.get(id);
        byte[] pdf = document.getData();
        ByteArrayResource resource = new ByteArrayResource(pdf);
        MediaType mediaType;
        if (document.getType() == DocumentType.PDF) {
            mediaType = MediaType.APPLICATION_PDF;
        } else {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return ResponseEntity.ok().contentType(mediaType).contentLength(pdf.length).body(resource);
    }

    @GetMapping(params = "new")
    public String documentNew() {
        return "documentForm";
    }

    @PostMapping
    public String addDocument(@RequestParam MultipartFile file) {
        Integer documentId = documentService.update(file).getId();
        return format("redirect:/book?new&documentId=%d", documentId);
    }
}
