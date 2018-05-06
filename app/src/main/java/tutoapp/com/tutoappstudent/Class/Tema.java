package tutoapp.com.tutoappstudent.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tema {

    @SerializedName("nombre_Tema")
    @Expose
    private String nombreTema;
    @SerializedName("id_Tema")
    @Expose
    private String idTema;

    public String getNombreTema() {
        return nombreTema;
    }

    public void setNombreTema(String nombreTema) {
        this.nombreTema = nombreTema;
    }

    public String getIdTema() {
        return idTema;
    }

    public void setIdTema(String idTema) {
        this.idTema = idTema;
    }

    public Tema(String nombreTema, String idTema) {
        this.nombreTema = nombreTema;
        this.idTema = idTema;
    }
}