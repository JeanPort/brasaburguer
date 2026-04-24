package com.jean.brasaburguer.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalStorage implements StorageService{

    @Value("${app.storage.path}")
    private String storagePath;

    @Override
    public String upload(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();

            Path path = Paths.get(storagePath, fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();

        } catch (IOException e) {
            throw new StorageServiceExeption("Erro ao salvar arquivo " + e.getLocalizedMessage());
        }
    }
}
