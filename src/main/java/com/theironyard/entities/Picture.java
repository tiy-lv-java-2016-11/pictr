package com.theironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pictures")
public class Picture {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String filename;
    @Column
    private String ogFilename;
    @Column
    private String caption;
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne
    private User user;

    public Picture() {
    }

    public Picture(String filename, String ogFilename, String caption, User user) {
        this.filename = filename;
        this.ogFilename = ogFilename;
        this.caption = caption;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOgFilename() {
        return ogFilename;
    }

    public void setOgFilename(String ogFilename) {
        this.ogFilename = ogFilename;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
