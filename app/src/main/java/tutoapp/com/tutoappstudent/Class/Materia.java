package tutoapp.com.tutoappstudent.Class;

/**
 * Created by jufeh on 4/8/2018.
 */



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Materia {

    @SerializedName("nombre_Materia")
    @Expose
    private String nombreMateria;
    @SerializedName("id_Materia")
    @Expose
    private String idMateria;

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

}