package com.drew.surfphotos.rest.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import static com.drew.surfphotos.common.config.Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES;
import com.drew.surfphotos.exception.ApplicationException;
import com.drew.surfphotos.exception.ValidationException;
import com.drew.surfphotos.rest.model.UploadImageREST;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import static org.apache.commons.fileupload.disk.DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


@Provider
@ApplicationScoped
@Consumes(MULTIPART_FORM_DATA)
public class UploadImageMessageBodyReader implements MessageBodyReader<UploadImageREST> {

    private File tempDirectory;

    @PostConstruct
    private void postConstruct() {
        try {
            tempDirectory = Files.createTempDirectory("upload").toFile();
        } catch (IOException ex) {
            throw new ApplicationException("Can't create temp directory", ex);
        }
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return UploadImageREST.class.isAssignableFrom(type);
    }

    @Override
    public UploadImageREST readFrom(Class<UploadImageREST> type, Type genericType, Annotation[] annotations,
                                    MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        DiskFileItemFactory factory = new DiskFileItemFactory(DEFAULT_SIZE_THRESHOLD, tempDirectory);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_UPLOADED_PHOTO_SIZE_IN_BYTES);
        try {
            List<FileItem> items = upload.parseRequest(new JAXRSRequestContext(httpHeaders, entityStream, mediaType.toString()));
            for (FileItem fileItem : items) {
                if (!fileItem.isFormField()) {
                    return new UploadImageREST(fileItem);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException("Can't parse multipart request: "+e.getMessage(), e);
        }
        throw new ValidationException("Missing content");
    }
}
