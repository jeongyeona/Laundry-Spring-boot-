package com.example.Laundry.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * 회원 정보 엔티티
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(length = 100)
    private String id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String pwd;

    @Column(length = 200)
    private String addr;

    @Column(length = 20)
    private String phone;

    @Column(name = "country_code", length = 2, insertable = false, updatable = false)
    private String countryCode;

    @Column(name = "dial_code", length = 10, insertable = false, updatable = false)
    private String dialCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({
            @JoinColumn(name = "country_code", referencedColumnName = "country_code"),
            @JoinColumn(name = "dial_code", referencedColumnName = "dial_code")
    })
    private CountryPhone countryPhone;

    /**
     * 관리자 여부 (Y/N), 기본값 'N'
     */
    @Column(name = "manager", length = 1, nullable = false, columnDefinition = "char(1) default 'N'")
    private String manager = "N";

    @Column(length = 100)
    private String profile;

    @Column(nullable = false)
    private LocalDate regdate;

    protected User() {
        // JPA 기본 생성자
    }

    public User(String id,
                String name,
                String email,
                String pwd,
                String addr,
                String phone,
                String countryCode,
                String dialCode,
                String manager,
                String profile,
                LocalDate regdate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.addr = addr;
        this.phone = phone;
        this.countryCode = countryCode;
        this.dialCode = dialCode;
        setManager(manager);
        this.profile = profile;
        this.regdate = regdate;
    }

    // === Getters & Setters ===

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public String getAddr() { return addr; }
    public void setAddr(String addr) { this.addr = addr; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String CountryCode) { this.phone = phone; }

    public String getDialCode() { return dialCode; }
    public void setdialCode(String DialCode) { this.phone = phone; }

    public CountryPhone getCountryPhone() { return countryPhone; }
    public void setCountryPhone(CountryPhone countryPhone) {
        this.countryPhone = countryPhone;
        if (countryPhone != null) {
            this.countryCode = countryPhone.getCountryCode();
            this.dialCode    = countryPhone.getDialCode();
        } else {
            this.countryCode = null;
            this.dialCode    = null;
        }
    }

    public String getManager() { return manager; }
    public void setManager(String manager) {
        this.manager = (manager == null || manager.isBlank()) ? "N" : manager;
    }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }

    public LocalDate getRegdate() { return regdate; }
    public void setRegdate(LocalDate regdate) { this.regdate = regdate; }
}
