package com.drew.surfphotos.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "access_token", schema = "public")
public class AccessToken extends AbstractDomain{
    @Id
    @Basic(optional = false)
    @Column(unique = true, nullable = false)
    @NotNull
    private String token;

    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
