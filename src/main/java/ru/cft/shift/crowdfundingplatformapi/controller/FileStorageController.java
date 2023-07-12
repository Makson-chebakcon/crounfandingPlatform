package ru.cft.shift.crowdfundingplatformapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shift.crowdfundingplatformapi.dto.FileMetaInformationDto;
import ru.cft.shift.crowdfundingplatformapi.service.FileStorageService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "Файловое хранилище")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "Получить метаинформацию о файле")
    @GetMapping("/{fileId}/meta-inf")
    public ResponseEntity<FileMetaInformationDto> getMetaInformation(@PathVariable UUID fileId) {
        return ResponseEntity.ok(fileStorageService.getFileMetaInformation(fileId));
    }

    @Operation(summary = "Загрузить файл в файловое хранилище")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<FileMetaInformationDto> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.uploadFile(file));
    }

    @Operation(summary = "Скачать файл из файлового хранилища")
    @GetMapping(value = "/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(@PathVariable UUID fileId) {
        Pair<String, byte[]> fileAndFilename = fileStorageService.downloadFileAndFilename(fileId);

        return ResponseEntity
                .ok()
                .header(
                        "Content-Disposition",
                        String.format("filename=%s", fileAndFilename.getLeft())
                )
                .body(fileAndFilename.getRight());
    }

}
