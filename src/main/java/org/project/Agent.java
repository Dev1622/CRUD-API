package org.project;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Agent extends PanacheEntity {

    public String name;
    public int age;
    public String skill;

    public Agent(){}

    Agent(String name,int age,String skill){
        this.name = name;
        this.age = age;
        this.skill = skill;
    }
}
