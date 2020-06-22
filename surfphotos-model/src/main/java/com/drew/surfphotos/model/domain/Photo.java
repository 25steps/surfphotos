package com.drew.surfphotos.model.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(catalog = "surfphotos", schema = "public")
public class Photo extends AbstractDomain {
    @Id
    @Basic(optional = false)
    @Column(unique = true, nullable = false, updatable = false)
    @SequenceGenerator(name = "photo_generator", sequenceName = "photo_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "photo_generator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "small_url", nullable = false, length = 255, updatable = false)
    private String smallUrl;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "large_url", nullable = false, length = 255, updatable = false)
    private String largeUrl;

    @NotNull
    @Size(max = 255)
    @Basic(optional = false)
    @Column(name = "original_url", nullable = false, length = 255, updatable = false)
    private String originalUrl;

    @Min(0)
    @Basic(optional = false)
    @Column(nullable = false)
    private long views;

    @Min(0)
    @Basic(optional = false)
    @Column(nullable = false)
    private long downloads;

    @NotNull
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Profile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getLargeUrl() {
        return largeUrl;
    }

    public void setLargeUrl(String largeUrl) {
        this.largeUrl = largeUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
