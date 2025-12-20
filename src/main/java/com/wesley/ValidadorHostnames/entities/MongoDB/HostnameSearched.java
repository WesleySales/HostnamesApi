package com.wesley.ValidadorHostnames.entities.MongoDB;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Document(collection = "hostnames_searched") // Nome da coleção no Atlas
public class HostnameSearched {

    @Id
    private String id;

    @Field("hostname_id")
    private String hostnameId;

//    private String hostname;

    @Field("checked_at")
    private LocalDateTime checkedAt;

    private String status;
    private Double latencia;

    public HostnameSearched() {}

    // Getters e Setters
    public String getHostnameId() { return hostnameId; }
    public void setHostnameId(String hostnameId) { this.hostnameId = hostnameId; }

    public LocalDateTime getCheckedAt() { return checkedAt; }
    public void setCheckedAt(LocalDateTime checkedAt) { this.checkedAt = checkedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getLatencia() { return latencia; }
    public void setLatencia(Double latencia) { this.latencia = latencia; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getHostname() {
//        return hostname;
//    }
//
//    public void setHostname(String hostname) {
//        this.hostname = hostname;
//    }
}