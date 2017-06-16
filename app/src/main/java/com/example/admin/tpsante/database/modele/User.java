package com.example.admin.tpsante.database.modele;

import com.example.admin.tpsante.database.datasource.Modele;
import com.example.admin.tpsante.database.datasource.e.Type;

import java.util.List;

@Modele.Table("USER")
@Modele.DataBase("sante.db")
public class User extends Modele<User> {

    private String nom;

    @Columne(value = "NOM", type = Type.String)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCreateDataBase() {
        return "CREATE TABLE " + getTable() + "("
                + "ID" + " Integer PRIMARY KEY AUTOINCREMENT, "
                + "NOM Text NOT NULL "
                + ");";
    }

    @Override
    public User build(int id, List<String> valeurs) {
        User user = new User();
        user.setId(id);
        user.setNom(valeurs.get(0));
        return user;
    }

}
