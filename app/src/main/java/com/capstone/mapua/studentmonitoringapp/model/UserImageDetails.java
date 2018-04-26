package com.capstone.mapua.studentmonitoringapp.model;

/**
 * Created by jj on 4/26/2018.
 */

public class UserImageDetails {

    private String studentId;
    private String fileId;
    private String contentType;
    private String originalFileName;
    private String fileSuffix;
    private String fileNameNoSuffix;
    private byte[] content;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
