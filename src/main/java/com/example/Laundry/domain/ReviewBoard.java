// Domain: com.example.Laundry.domain.ReviewBoard.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "review_board")
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @Column(length = 100, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer viewCount;

    private LocalDate regdate;

    private Integer star;

    // Getters and Setters
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }

    public LocalDate getRegdate() { return regdate; }
    public void setRegdate(LocalDate regdate) { this.regdate = regdate; }

    public Integer getStar() { return star; }
    public void setStar(Integer star) { this.star = star; }
}
