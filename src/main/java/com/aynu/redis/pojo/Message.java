package com.aynu.redis.pojo;

import javax.swing.plaf.nimbus.State;
import java.io.Serializable;

/**
 * @author admin
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 8081849731640304905L;

    private Long id;

    private String username;

    private String note;

    public Message() {
    }

    public Message(Long id, String username, String note) {
        this.id = id;
        this.username = username;
        this.note = note;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
