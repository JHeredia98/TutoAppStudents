package tutoapp.com.tutoappstudent.Class;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Materias {

    @SerializedName("Materias")
    @Expose
    private ArrayList<Materia> materias = null;

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

}