/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;


/**
 *
 * @author dawid
 */
@Entity
@Table(name = "artworks")
@NamedQueries(
{
    @NamedQuery(name = "Artwork.findAll", query = "SELECT a FROM Artwork a")
})
public class Artwork implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "artworkid")
    private Integer artworkid;

    
    @JoinColumn(name = "artistid", referencedColumnName = "artistid")
    @ManyToOne
    private Artist artistid;
    
    @Size(max = 255)
    @Column(name = "title")
    private String title;
    
    @Size(max=25000)
    @Column(name = "description")
    private String description;
    
    
    @Size(max = 255)
    @Column(name = "medium")
    private String medium;
    
    
    @Size(max=255)
    @Column(name = "imagename")
    private String imagename;
    
    public void setArtist(Artist artistid){
        this.artistid = artistid;
    }
    
    public Artist getArtist(){
        return this.artistid;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setDescription(String desc){
        this.description = desc;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    
    public void setMedium(String med){
        this.medium = med;
    }
    
    public String getMedium(){
        return this.medium;
    }
    
    public void setImagename(String image){
        this.imagename = image;
    }
    
    public String getImagename(){
        return this.imagename;
    }
    
    public Integer getArtworkid() {
        return artworkid;
    }

    public void setArtworkid(Integer id) {
        this.artworkid = id;
    }

    public Artwork()
    {
    }
    
}
