package tutoapp.com.tutoappstudent.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GlobalTutors {

    @SerializedName("tutores")
    @Expose
    private ArrayList<Tutor> tutores = null;

    public ArrayList<Tutor> getTutores() {
        return tutores;
    }

    public void setTutores(ArrayList<Tutor> tutores) {
        this.tutores = tutores;
    }
}
