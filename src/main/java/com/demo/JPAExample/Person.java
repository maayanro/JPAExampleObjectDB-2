package com.demo.JPAExample;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Entity
public class Person {

    public enum Gender {MALE, FEMALE}

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private Date birthDate;
    private Gender gender;
    private Person spouse;
    private List<Person> kids;

    protected Person() {
    }

    public Person(String name, Date birthDate, Gender gender) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long ID) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Person getSpouse() {
        return spouse;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public List<Person> getKids() {
        return kids;
    }

    public void setKids(List<Person> kids) {
        this.kids = kids;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", spouse=" + spouse +
                ", kids=" + kids +
                '}';
    }
}
