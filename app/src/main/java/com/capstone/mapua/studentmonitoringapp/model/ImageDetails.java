package com.capstone.mapua.studentmonitoringapp.model;

import java.net.URI;

/**
 * Created by jj on 5/5/2018.
 */

public class ImageDetails {


    private String id;
    private String requestId;
    private String fileId;
    private String contentType;
    private String originalFileName;
    private String fileSuffix;
    private String fileNameNoSuffix;
    private String content;
    private String uri;
    private String fileMedia;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFileMedia() {
        return fileMedia;
    }

    public void setFileMedia(String fileMedia) {
        this.fileMedia = fileMedia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFileNameNoSuffix() {
        return fileNameNoSuffix;
    }

    public void setFileNameNoSuffix(String fileNameNoSuffix) {
        this.fileNameNoSuffix = fileNameNoSuffix;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
