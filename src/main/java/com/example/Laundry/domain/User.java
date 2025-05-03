package com.example.Laundry.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "`user`")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String addr;

    @Column(length = 2)                 // countryCode 컬럼
    private String countryCode;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 1, nullable = false,
            columnDefinition = "varchar(1) default 'N'")
    private String manager = "N";

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String phone;

    @Column(length = 10)                // dialCode 컬럼
    private String dialCode;

    @Column(length = 100)
    private String profile;

    @Column(length = 100, nullable = false)
    private String pwd;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime regdate;

    protected User() { }

    // 생성자: regdate 는 JPA Auditing(@CreatedDate) 에 의해 자동 설정
    public User(String id,
                String addr,
                String countryCode,
                String email,
                String manager,
                String name,
                String phone,
                String dialCode,
                String profile,
                String pwd) {
        this.id          = id;
        this.addr        = addr;
        this.countryCode = countryCode;
        this.email       = email;
        this.manager     = manager;
        this.name        = name;
        this.phone       = phone;
        this.dialCode    = dialCode;
        this.profile     = profile;
        this.pwd         = pwd;
    }

    // === Getters & Setters ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDialCode() { return dialCode; }
    public void setDialCode(String dialCode) { this.dialCode = dialCode; }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public LocalDateTime getRegdate() { return regdate; }
    public void setRegdate(LocalDateTime regdate) { this.regdate = regdate; }

}
