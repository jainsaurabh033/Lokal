package com.lokal.lokal.entity;

import com.lokal.lokal.enumm.StoreStatus;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false, unique = true)
    private User seller;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StoreStatus status = StoreStatus.PENDING;

    public Store(){

    }

    public Store(String name){
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public User getSeller(){
        return seller;
    }

    public void setSeller(User seller){
        this.seller = seller;
    }

    public StoreStatus getStatus(){
        return status;
    }

    public void setStatus(StoreStatus status){
        this.status = status;
    }
}
