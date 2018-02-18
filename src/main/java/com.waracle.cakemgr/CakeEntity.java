package com.waracle.cakemgr;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "Cakes", uniqueConstraints = {@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "TITLE")})
public class CakeEntity implements Serializable {

    private static final long serialVersionUID = -1798070786993154676L;

    public CakeEntity(){}

    public CakeEntity(String title, String description, String image){
        this.title = title;
        this.description = description;
        this.image = image;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer cakeId;

    @JsonProperty("title")
    @Column(name = "TITLE", unique = true, nullable = false, length = 100)
    private String title;

    @JsonProperty("desc")
    @Column(name = "DESCRIPTION", unique = false, nullable = false, length = 100)
    private String description;

    @JsonProperty("image")
    @Column(name = "IMAGE", unique = false, nullable = false, length = 300)
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CakeEntity{" +
                "cakeId=" + cakeId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}