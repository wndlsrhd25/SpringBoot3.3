package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customers") //지정 안하면 customer
@Getter
@Setter
@DynamicUpdate
public class Customer {

    @Id //Primary Key

    //pk의 sequential 값을 자동으로 증가하는 전략
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false,name = "cust_id")
    private String customerId;

    @Column(nullable = false, name = "cust_name")
    private String customerName;

}