/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author dawid
 */
public class User {
    
    private String username;
    private String forum;
    
    public User(){}
    
    public void setUsername(String username){
        this.username = username;
    }
    public void setForum(String forum){
        this.forum = forum;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getForum(){
        
        return this.forum;
    }
    
}
