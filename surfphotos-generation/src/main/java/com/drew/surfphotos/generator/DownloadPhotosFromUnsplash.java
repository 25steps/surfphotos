package com.drew.surfphotos.generator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 *Данная утилита позволяет дернуть сервис api unsplash и скачать рандомные фотки с фильтром surf и заданным количеством
 */
public class DownloadPhotosFromUnsplash {
    public static void main(String[] args) throws IOException {
        new DownloadPhotosFromUnsplash().execute(
                "https://api.unsplash.com/photos/random?query=surf&count=30",
                Paths.get("surfphotos-generation/external/test-data/photos"));
    }

    /**
     *Метод скачивания фотографий с unsplash
     * @param unsplashSourceUrl
     * Передача url согласно спецификации api
     * @param destinationDirectoryPath
     * куда будет сохранён результат
     * @throws IOException
     */
    public void execute(String unsplashSourceUrl, Path destinationDirectoryPath) throws IOException {
        createDestinationIfNecessary(destinationDirectoryPath);
        Response response = getPhotoLinks(unsplashSourceUrl);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            parseValidResponse(response, destinationDirectoryPath);
        } else {
            displayErrorMessage(response);
        }
    }

    /**
     * Метод, который создаёт папку сохранения, если она отсутствует
     * @param destinationDirectoryPath
     * Путь к папке
     * @throws IOException
     */
    protected void createDestinationIfNecessary(Path destinationDirectoryPath) throws IOException {
        if(!Files.exists(destinationDirectoryPath)){
            Files.createDirectories(destinationDirectoryPath);
        }
    }

    /**
     *Данный метод возвращает объект Response с линками фотографий
     * @param url
     * @return
     */
    protected Response getPhotoLinks(String url) {
        Client client = ClientBuilder.newClient();
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        client.register(new JacksonJaxbJsonProvider(objectMapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));

        return client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Accept-Version", "v1")
                .header("Authorization", "Client-ID " + getSystemEnvironmentVariable("UNSPLASH_KEY"))
                .get();
    }

    /**
     * Метод для считывания значения переменной
     * @param name
     * @return
     */
    protected String getSystemEnvironmentVariable(String name) {
        Map<String, String> env = System.getenv();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            if (entry.getKey().equals(name)) {
                return entry.getValue();
            }
        }
        throw new IllegalStateException("System variable not defined: " + name);
    }

    /**
     * Данный метод обрабатывает полученный response объект, получает кол-цию item и при помощи цикла указываем куда будут
     * сохранятся фотографии и с каким именем
     * @param response
     * @param destinationDirectoryPath
     */
    protected void parseValidResponse(Response response, Path destinationDirectoryPath) {
        System.out.println("X-Ratelimit-Remaining=" + response.getHeaderString("X-Ratelimit-Remaining"));

        List<Item> items = response.readEntity(new GenericType<List<Item>>() {
        });
        int id = 10;
        for (Item item : items) {
            Path file = Paths.get(String.format("%s/%s.jpg", destinationDirectoryPath.toAbsolutePath().toString(), id));
            downloadImage(item.getLinks().getDownload(), file);
            id++;
            System.out.println("Successful downloaded " + item.getLinks().getDownload());
        }
    }

    /**
     * Метод создаёт новый запрос и при помощи полученных url скачивает фотографию с сайта unsplash
     * @param url
     * @param file
     */
    protected void downloadImage(String url, Path file) {
        Response response = ClientBuilder.newClient()
                .register(new FollowRedirectFilter())
                .target(url)
                .request("image/jpeg")
                .get();
        try (InputStream in = response.readEntity(InputStream.class)) {
            Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ex) {
            System.err.println("Download file failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Чтобы работал редирект по 401 статусу
     */
    protected static class FollowRedirectFilter implements ClientResponseFilter {

        @Override
        public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
            if (responseContext.getStatusInfo().getFamily() == Response.Status.Family.REDIRECTION) {
                Response resp = requestContext.getClient()
                        .target(responseContext.getLocation())
                        .request()
                        .method(requestContext.getMethod());
                responseContext.setEntityStream((InputStream) resp.getEntity());
                responseContext.setStatusInfo(resp.getStatusInfo());
                responseContext.setStatus(resp.getStatus());
            }
        }
    }

    /**
     * Если не удалось получить корректный ответ от api
     * @param response
     */
    protected void displayErrorMessage(Response response) {
        System.out.println(String.format("Status: %s %s", response.getStatusInfo().getStatusCode(), response.getStatusInfo().getReasonPhrase()));
        System.out.println(response.readEntity(String.class));
    }


    /**
     *Для того чтобы обработать ответ создадим структуру, которая будет получать нужныме нам параметры из json ответа
     * при помощи Java Api RESTful Web Services
     */
    protected static class Item {

        private Links links;

        public Links getLinks() {
            return links;
        }

        public void setLinks(Links links) {
            this.links = links;
        }
    }

    protected static class Links {

        private String download;

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }
    }
}
