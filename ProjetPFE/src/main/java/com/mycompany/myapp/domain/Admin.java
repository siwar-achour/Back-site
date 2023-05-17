package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Document(collection = "admin")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("role_name")
    private String roleName;

    @Field("user_name")
    private String userName;
    
    @Field("Password")
    private String password;


    @DBRef
    @Field("chauffeur")
    @JsonIgnoreProperties(value = { "admin" }, allowSetters = true)
    private Set<Chauffeur> chauffeurs = new HashSet<>();

    @DBRef
    @Field("client")
    @JsonIgnoreProperties(value = { "admin" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Admin id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public Admin roleName(String roleName) {
        this.setRoleName(roleName);
        return this;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return this.userName;
    }

    public Admin userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return this.password;
    }

    public Admin userPassword(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Chauffeur> getChauffeurs() {
        return this.chauffeurs;
    }

    public void setChauffeurs(Set<Chauffeur> chauffeurs) {
        if (this.chauffeurs != null) {
            this.chauffeurs.forEach(i -> i.setAdmin(null));
        }
        if (chauffeurs != null) {
            chauffeurs.forEach(i -> i.setAdmin(this));
        }
        this.chauffeurs = chauffeurs;
    }

    public Admin chauffeurs(Set<Chauffeur> chauffeurs) {
        this.setChauffeurs(chauffeurs);
        return this;
    }

    public Admin addChauffeur(Chauffeur chauffeur) {
        this.chauffeurs.add(chauffeur);
        chauffeur.setAdmin(this);
        return this;
    }

    public Admin removeChauffeur(Chauffeur chauffeur) {
        this.chauffeurs.remove(chauffeur);
        chauffeur.setAdmin(null);
        return this;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setAdmin(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setAdmin(this));
        }
        this.clients = clients;
    }

    public Admin clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Admin addClient(Client client) {
        this.clients.add(client);
        client.setAdmin(this);
        return this;
    }

    public Admin removeClient(Client client) {
        this.clients.remove(client);
        client.setAdmin(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        return id != null && id.equals(((Admin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Admin{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
