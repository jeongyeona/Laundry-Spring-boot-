// Domain: com.example.Laundry.domain.NoticeBoardComment.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDate;

@Entity
@Table(name = "notice_board_comment")
public class NoticeBoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer num;

    @Column(length = 100)
    private String writer;

    @Column(length = 500)
    private String content;

    @Column(name = "target_id", length = 100)
    private String targetId;

    @Column(name = "ref_group")
    private Integer refGroup;

    @Column(name = "comment_group")
    private Integer commentGroup;

    @Column(name = "deleted", columnDefinition = "char(3)", length = 3, nullable = false)
    private String deleted = "no";

    private LocalDate regdate;

    // Getters and Setters
    public Integer getNum() { return num; }
    public void setNum(Integer num) { this.num = num; }

    public String getWriter() { return writer; }
    public void setWriter(String writer) { this.writer = writer; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getTargetId() { return targetId; }
    public void setTargetId(String targetId) { this.targetId = targetId; }

    public Integer getRefGroup() { return refGroup; }
    public void setRefGroup(Integer refGroup) { this.refGroup = refGroup; }

    public Integer getCommentGroup() { return commentGroup; }
    public void setCommentGroup(Integer commentGroup) { this.commentGroup = commentGroup; }

    public String getDeleted() { return deleted; }
    public void setDeleted(String deleted) { this.deleted = deleted; }

    public LocalDate getRegdate() { return regdate; }
    public void setRegdate(LocalDate regdate) { this.regdate = regdate; }
}