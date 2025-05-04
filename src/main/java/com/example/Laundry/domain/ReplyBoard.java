// Domain: com.example.Laundry.domain.ReplyBoard.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "reply_board")
public class ReplyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rnum;

    @Column(name = "ref_num", nullable = false)
    private Integer refNum;

    @Column(length = 100)
    private String writer;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDate regdate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    // Getters and Setters
    public Integer getRnum() { return rnum; }
    public void setRnum(Integer rnum) { this.rnum = rnum; }

    public Integer getRefNum() { return refNum; }
    public void setRefNum(Integer refNum) { this.refNum = refNum; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDate getRegdate() { return regdate; }
    public void setRegdate(LocalDate regdate) { this.regdate = regdate; }

    public LocalDate getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDate updateDate) { this.updateDate = updateDate; }
}