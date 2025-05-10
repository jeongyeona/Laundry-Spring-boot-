// Domain: com.example.Laundry.domain.FaqBoard.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "faq_board")
public class FaqBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @Column(length = 20)
    private String category;

    @Column(length = 100, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime regdate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    // Getters and Setters
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getRegdate() { return regdate; }
    public void setRegdate(LocalDateTime regdate) { this.regdate = regdate; }

    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; }
}