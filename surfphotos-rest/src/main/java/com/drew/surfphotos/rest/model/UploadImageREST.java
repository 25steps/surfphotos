package com.drew.surfphotos.rest.model;

import com.drew.surfphotos.common.model.AbstractMimeTypeImageResource;
import com.drew.surfphotos.model.ImageResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.fileupload.FileItem;

import java.io.IOException;

@ApiModel("UploadImage")
public class UploadImageREST {

    @ApiModelProperty(hidden = true)
    private final FileItem fileItem;

    public UploadImageREST(FileItem fileItem) {
        this.fileItem = fileItem;
    }

    @ApiModelProperty(hidden = true)
    public ImageResource getImageResource() {
        return new FileItemImageResource();
    }


    private class FileItemImageResource extends AbstractMimeTypeImageResource {

        @Override
        protected String getContentType() {
            return fileItem.getContentType();
        }

        @Override
        public String toString() {
            return String.format("%s(%s)", getClass().getSimpleName(), fileItem);
        }

        @Override
        protected void copyContent() throws Exception {
            fileItem.write(getTempPath().toFile());
        }

        @Override
        protected void deleteTempResources() throws IOException {
            fileItem.delete();
        }
    }
}
