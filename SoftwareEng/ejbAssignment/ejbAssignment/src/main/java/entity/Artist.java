/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;


import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

/**
 *
 * @author dawid
 */

@Entity
@Table(name = "artists")
@NamedQueries(
{
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a")
})
public class Artist implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "artistid")
    private Integer artistid;
    
    @Size(max = 255)
    @Column(name = "surname")
    private String surname;
    
    @Size(max = 255)
    @Column(name = "firstname")
    private String firstname;
    
    @Size(max = 255)
    @Column(name = "nationality")
    private String nationality;
    
    @Column(name = "yob")
    private Short yob;
    
    @Lob
    @Size(max = 2147483647)
    @Column(name = "biography")
    private String biography;
    
    @OneToMany(mappedBy = "artistid")
    private Collection<Artwork> artworkCollection;
    
    public Artist(){}
    
    public Artist(Integer artistid){
        this.artistid = artistid;
    }
    
    public Artist(Integer authorid, String surname, String firstname, Short yob, String nationality, String biography, Collection<Artwork> artworkCollection)
    {
        this.artistid = authorid;
        this.surname = surname;
        this.firstname = firstname;
        this.yob = yob;
        this.nationality = nationality;
        this.biography = biography;
        this.artworkCollection = artworkCollection;
    }
    
    public Integer getArtistid()
    {
        return artistid;
    }

    public void setArtistid(Integer artistid)
    {
        this.artistid = artistid;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public Short getYob()
    {
        return yob;
    }

    public void setYob(Short yob)
    {
        this.yob = yob;
    }

    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }

    public String getBiography()
    {
        return biography;
    }

    public void setBiography(String biography)
    {
        this.biography = biography;
    }

    public Collection<Artwork> getArtworkCollection()
    {
        return artworkCollection;
    }

    public void setArtworkCollectoin(Collection<Artwork> artworkCollection)
    {
        this.artworkCollection= artworkCollection;
    }
}