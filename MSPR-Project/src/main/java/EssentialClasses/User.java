package EssentialClasses;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String DocumentID;
    public String prenom;
    private String nom;
    private String photo;
    private ArrayList<String> equipement;

    public User(){}

    public String getDocumentID() {
        return DocumentID;
    }

    public void setDocumentID(String documentID) {
        DocumentID = documentID;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<String> getEquipement() {
        return this.equipement;
    }

    public void setEquipement(ArrayList<String> equipement) {
        this.equipement = equipement;
    }
}
