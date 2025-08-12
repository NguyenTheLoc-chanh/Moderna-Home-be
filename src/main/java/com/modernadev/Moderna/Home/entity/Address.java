package com.modernadev.Moderna.Home.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.joda.time.LocalDateTime;

@Data
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String ward;       // Xã/Phường/Thị trấn
    private String district;
    private String city;
    private String postalCode;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

}
