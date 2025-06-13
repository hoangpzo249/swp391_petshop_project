/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author HuyHoang
 */
public class GoogleUser {

    private String id;
    private String email;
    private String name;
    private String given_name;
    private String family_name;

    public GoogleUser() {
    }

    public GoogleUser(String id, String email, String name, String given_name, String family_name) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.given_name = given_name;
        this.family_name = family_name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getFamily_name() {
        return family_name;
    }


    
}
