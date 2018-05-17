package com.applicationswp.data;

import javax.persistence.*;

/**
 * Created by alexwerff on 27.02.17.
 */
@Entity
public class RegisterCredit extends BasicEntry{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    private int quantity;
    private String password;
    private String title;
    private String description;
    private String permissions;
    private long validDate;


    @Override
    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public long getValidDate() {
        return validDate;
    }

    public void setValidDate(long validDate) {
        this.validDate = validDate;
    }
}
