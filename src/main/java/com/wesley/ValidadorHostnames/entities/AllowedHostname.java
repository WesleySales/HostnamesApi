package com.wesley.ValidadorHostnames.entities;

import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;
//import org.springframework.data.annotation.Id;

@Entity
@Table(name = "allowed_hosts")
@Profile("h2")
public class AllowedHostname {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hostname;

    @Column(nullable = false)
    private boolean wildcard;

    public String getHostname() {
        return hostname;
    }

    @Override
    public String toString() {
        return "hostname: '" + hostname+"'";
    }
}