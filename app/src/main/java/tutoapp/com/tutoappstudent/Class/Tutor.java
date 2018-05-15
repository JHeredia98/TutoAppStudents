package tutoapp.com.tutoappstudent.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tutor {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("codFirebase")
    @Expose
    private String codFirebase;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodFirebase() {
        return codFirebase;
    }

    public void setCodFirebase(String codFirebase) {
        this.codFirebase = codFirebase;
    }
}
