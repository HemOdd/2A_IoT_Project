package com.example.team_2a_security.data_classes;

/**
 * Class to represent each blueprint(image) object
 */
public class Blueprints {

    private String blueprintURI; //URI of image

    public Blueprints(){

    }

    public Blueprints (String blueprintURI){

        this.blueprintURI = blueprintURI;
    }


    public String getBlueprintURI() {
        return blueprintURI;
    }

    public void setBlueprintURI(String blueprintURI) {
        this.blueprintURI = blueprintURI;
    }
}
