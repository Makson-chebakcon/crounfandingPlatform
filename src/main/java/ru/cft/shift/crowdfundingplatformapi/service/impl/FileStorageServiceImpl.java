package ru.cft.shift.crowdfundingplatformapi.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.cft.shift.crowdfundingplatformapi.configuration.MinioConfiguration;
import ru.cft.shift.crowdfundingplatformapi.dto.FileMetaInformationDto;
import ru.cft.shift.crowdfundingplatformapi.entity.FileMetaInformation;
import ru.cft.shift.crowdfundingplatformapi.exception.InternalException;
import ru.cft.shift.crowdfundingplatformapi.exception.NotFoundException;
import ru.cft.shift.crowdfundingplatformapi.repository.MetaInformationRepository;
import ru.cft.shift.crowdfundingplatformapi.service.FileStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;
    private final MinioConfiguration minioConfiguration;
    private final MetaInformationRepository metaInformationRepository;


    @Override
    public FileMetaInformationDto getFileMetaInformation(UUID fileId) {
        FileMetaInformation fileMetaInformation = metaInformationRepository
                .findById(fileId)
                .orElseThrow(() -> new NotFoundException("Файл не найден"));

        return new FileMetaInformationDto(fileMetaInformation.getId(), fileMetaInformation.getName());
    }

    @Override
    public FileMetaInformationDto uploadFile(MultipartFile file) {
        try {
            FileMetaInformation fileMetaInformation = new FileMetaInformation(
                    UUID.randomUUID(),
                    file.getOriginalFilename()
            );

            PutObjectArgs putObjectArgs = buildPutObjectArgs(fileMetaInformation, file);
            minioClient.putObject(putObjectArgs);
            fileMetaInformation = metaInformationRepository.save(fileMetaInformation);

            return new FileMetaInformationDto(fileMetaInformation.getId(), fileMetaInformation.getName());

        } catch (Exception e) {
            throw new InternalException("Ошибка во время загрузки файла в S3", e);
        }
    }

    @Override
    public Pair<String, byte[]> downloadFileAndFilename(UUID fileId) {
        String filename = metaInformationRepository
                .findById(fileId)
                .orElseThrow(() -> new NotFoundException("Файл не найден"))
                .getName();

        GetObjectArgs getObjectArgs = GetObjectArgs
                .builder()
                .bucket(minioConfiguration.getBucket())
                .object(fileId.toString())
                .build();

        try (var in = minioClient.getObject(getObjectArgs)) {
            return Pair.of(filename, in.readAllBytes());
        } catch (Exception e) {
            throw new InternalException("Ошибка при скачивании файла из S3 c id = " + fileId, e);
        }
    }

    @Override
    public void existFiles(List<UUID> ids) {
        for (UUID id : ids) {
            existFile(id);
        }
    }

    @Override
    public void existFile(UUID id) {
        if (!metaInformationRepository.existsById(id)) {
            throw new NotFoundException("Файл с id " + id + " не найден");
        }
    }

    private PutObjectArgs buildPutObjectArgs(FileMetaInformation fileMetaInformation, MultipartFile file) throws IOException {
        byte[] content = file.getBytes();

        return PutObjectArgs
                .builder()
                .bucket(minioConfiguration.getBucket())
                .object(fileMetaInformation.getId().toString())
                .stream(new ByteArrayInputStream(content), content.length, -1)
                .build();
    }

}
