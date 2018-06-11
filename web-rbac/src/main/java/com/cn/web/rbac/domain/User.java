package com.cn.web.rbac.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "username", length = 50)
    private String username;
    @Column(name = "password", length = 200)
    private String password;
    @Column(name = "salt", length = 100)
    private String salt;
    @Column(name = "email", length = 20)
    private String email;
    @Column(name = "mobile", length = 20)
    private String mobile;
    @Column(name = "gender")
    private Short gender;
    @Column(name = "avatar", length = 200)
    private String avatar;
    @Column(name = "state", length = 1)
    private String state;
    @Column(name = "last_visit")
    private Long lastVisit;
    @Column(name = "login_count")
    private Long loginCount;
    @Column(name = "department", length = 200)
    private String department;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Short getGender() {
        return gender;
    }

    public void setGender(Short gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Long lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }
}
