package com.drew.surfphotos.generator.component;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Объект PhotoGenerator считывает все скачанные фото с unsplash.com и создаёт их коллекцию, перемещивает и равномерно
 * раскидывает по всем профилям
 */
@ApplicationScoped
public class PhotoGenerator {
    private final Random random = new Random();

    private final List<String> fileNames = getAllTestPhotos();

    private int index = 0;

    public List<String> generatePhotos(int photoCount) {
        List<String> photos = new ArrayList<>();
        for (int i = 0; i < photoCount; i++) {
            photos.add(getPhoto());
        }
        return Collections.unmodifiableList(photos);
    }

    private String getPhoto(){
        if(index >= fileNames.size()) {
            index = 0;
        }
        return fileNames.get(index++);
    }

    private List<String> getAllTestPhotos() {
        List<String> list = new ArrayList<>();
        Path rootPath = Paths.get("surfphotos-generation/external/test-data/photos");
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootPath)) {
            for (Path path : directoryStream) {
                list.add(path.toAbsolutePath().toString());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Collections.shuffle(list);
        return list;
    }
}
