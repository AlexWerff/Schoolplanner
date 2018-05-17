package com.applicationswp.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 *Diese Klasse repraesentiert einen Benutzer.
 */
@Entity
public class Member extends BasicEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long ID;
    @Column(length = 500)
    private String permissions;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String imageUrl;
    private String firstname;
    private String lastname;
    private String nickname;
    private String age;
    private String birthday;
    private String grade;
    private String hobbies;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Course> courses;

    @Transient
    private List<Message> messages;


    /**
     * Contstructor
     */
    public Member(){
        ID =-1;
        permissions = "";
        username = "";
        password = "";
        imageUrl = "-";
        email = "";
        hobbies="";
        firstname ="";
        lastname = "";
        nickname = "";
        this.courses = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    /**
     * Getter fuer die Berechtigungen
     * @return die Berechtigungen
     */
    public String getPermissions() {
        return permissions;
    }

    /**
     * Setter fuer die Berechtigungen
     * @param permissions die Berechtigungen
     */
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    /**
     * Getter fuer den Benutzernamen
     * @return den Benutzernamen
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter fuer den Benutzernamen
     * @param username den Benutzernamen
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter fuer das Passwort
     * @return das Passwort
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter fuer das Passwort
     * @param password das Passwort
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter fuer die Email
     * @return die Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter fuer die Email
     * @param email die Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter fuer die Telefonnummer
     * @return die Telefonnummer
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Setter fuer die Telefonnummer
     * @param phoneNumber die Telefonnummer
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter fuer die Profilfoto Url
     * @return die Profilfoto Url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Setter fuer die Profilfoto Url
     * @param imageUrl die Profilfoto Url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Getter fuer die ID
     * @return die ID
     */
    public long getID() {
        return ID;
    }

    /**
     * Setter fuer die ID
     * @param ID die ID
     */
    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * Setter fuer den Vornamen
     * @return den Vornamen
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter fuer den Vornamen
     * @param firstname den Vornamen
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter fuer den Nachnamen
     * @return den Nachnamen
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter fuer den Nachnamen
     * @param lastname den Nachnamen
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter fuer den Nicknamen
     * @return den Nicknamen
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter fuer den Nicknamen
     * @param nickname den Nicknamen
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter fuer alle Kurse des Benutzers
     * @return alle Kurse des Benutzers
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     * Getter fuer das Alter
     * @return das Alter
     */
    public String getAge() {
        return age;
    }

    /**
     * Setter fuer das Alter
     * @param age das Alter
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Setter fuer die Klasse
     * @return die Klasse
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Setter fuer die Klasse
     * @param grade die Klasse
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Getter fuer alle Nachrichten des Benutzers
     * @return alle Nachrichten des Benutzers
     */
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * Getter fuer den Geburtstag
     * @return den Geburtstag
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * Setter fuer den Geburtstag
     * @param birthday den Geburtstag
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * Getter fuer die Hobbies
     * @return die Hobbies
     */
    public String getHobbies() {
        return hobbies;
    }

    /**
     * Setter fuer die Hobbies
     * @param hobbies die Hobbies
     */
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }


    public String toNameString(){
        return String.format("%s %s",getFirstname(),getLastname());
    }
}
