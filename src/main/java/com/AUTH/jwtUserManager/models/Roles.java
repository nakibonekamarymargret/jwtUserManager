package com.AUTH.jwtUserManager.models;

import jakarta.persistence.*;

@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY )
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolesEnum name;

    public Roles(){}

    public Roles(RolesEnum name) {
        this.name = name;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }

    public RolesEnum getName(){
        return name;
    }
    public void setName(RolesEnum name){
        this.name=name;
    }
}
