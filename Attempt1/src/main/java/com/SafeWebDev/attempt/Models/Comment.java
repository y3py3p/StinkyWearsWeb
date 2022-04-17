package com.SafeWebDev.attempt.Models;

import javax.persistence.*;
import java.lang.*;

@Entity
@Table(name="comment_table")
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column
    private String content;
    private String user="default";

    public String getContent() {
        return content;
    }

    public String getUser() {
        return user;
    }

    public void setContent(String contenido) {
        this.content = contenido;
    }

    public void setUser(String usuario) {
        this.user = usuario;
    }
}
