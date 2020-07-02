package com.drew.surfphotos.generator.component;

import com.drew.surfphotos.exception.ApplicationException;
import com.drew.surfphotos.model.domain.Profile;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Объект который генерирует профили исходя из тех данных, которые указаны в profiles.xml, с помощью технологий java.xml.bind
 * загружает xml, парсит и преобразует в коллекцию Profile, а недостающие поля генерирует на лету
 */
@ApplicationScoped
public class ProfileGenerator {
    private final Random random = new Random();

    public List<Profile> generateProfiles() {
        File file = new File("surfphotos-generation/external/test-data/profiles.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Profiles.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Profiles profiles = (Profiles) jaxbUnmarshaller.unmarshal(file);
            final Date created = new Date();
            return Collections.unmodifiableList(profiles.getProfile().stream().map((Profile t) -> {
                t.setUid(String.format("%s-%s", t.getFirstName(), t.getLastName()).toLowerCase());
                t.setEmail(t.getUid()+"@surfphotos.com");
                t.setPhotoCount(random.nextInt(15) + random.nextInt(5) + 3);
                t.setCreated(created);
                return t;
            }).collect(Collectors.toList()));
        } catch (JAXBException e) {
            throw new ApplicationException("Can't load test data from: " + file.getAbsolutePath(), e);
        }
    }

    @XmlRootElement(name = "profiles")
    private static class Profiles {
        private List<Profile> profile;

        public List<Profile> getProfile() {
            return profile;
        }

        public void setProfile(List<Profile> profile) {
            this.profile = profile;
        }
    }
}
