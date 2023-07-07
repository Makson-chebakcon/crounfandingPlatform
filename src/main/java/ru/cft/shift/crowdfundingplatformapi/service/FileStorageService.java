package ru.cft.shift.crowdfundingplatformapi.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shift.crowdfundingplatformapi.dto.FileMetaInformationDto;

import java.util.UUID;

public interface FileStorageService {

    FileMetaInformationDto getFileMetaInformation(UUID fileId);

    FileMetaInformationDto uploadFile(MultipartFile file);

    Pair<String, byte[]> downloadFileAndFilename(UUID fileId);

}
