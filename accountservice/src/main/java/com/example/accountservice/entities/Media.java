package com.example.accountservice.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MEDIA")
public class Media {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "account_id")
    private long account_id;

    @Column(name = "serialNumber")
    private String mediaSerialNumber;

    @Column(name = "expireDate")
    private Date expireDate;

    @Column(name = "cvv2")
    private String cvv2;

    @Column(name = "pin1")
    private String pin1;  //pin1 must be encrypted

    @Column(name = "pin2")
    private String pin2;  //pin2 must be encrypted

}
