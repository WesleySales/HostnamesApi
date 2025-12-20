package com.wesley.ValidadorHostnames.entities.MongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
@Document(collection = "allowed_hostnames")
public class AllowedHostname {

    @Id
    private String id;

    @Indexed(unique = true)
    private String hostname;

    @Field("ativo_1")
    private boolean ativo;

    public AllowedHostname() {}


    public String getHostname() {
        return hostname;
    }

    public String getId() {
        return id;
    }

}