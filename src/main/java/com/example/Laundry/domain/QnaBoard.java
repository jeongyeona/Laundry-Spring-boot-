// Domain: com.example.Laundry.domain.QnaBoard.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "qna_board")
public class QnaBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @Column(length = 100, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate regdate;

    @Column(length = 100, nullable = false)
    private String orgFileName;

    @Column(length = 100, nullable = false)
    private String saveFileName;

    private Integer fileSize;

    @Column(name = "check_reply", nullable = false)
    private Integer checkReply;

    // Getters and Setters
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDate getRegdate() { return regdate; }
    public void setRegdate(LocalDate regdate) { this.regdate = regdate; }

    public String getOrgFileName() { return orgFileName; }
    public void setOrgFileName(String orgFileName) { this.orgFileName = orgFileName; }

    public String getSaveFileName() { return saveFileName; }
    public void setSaveFileName(String saveFileName) { this.saveFileName = saveFileName; }

    public Integer getFileSize() { return fileSize; }
    public void setFileSize(Integer fileSize) { this.fileSize = fileSize; }

    public Integer getCheckReply() { return checkReply; }
    public void setCheckReply(Integer checkReply) { this.checkReply = checkReply; }
}