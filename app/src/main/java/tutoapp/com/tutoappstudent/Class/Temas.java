
package tutoapp.com.tutoappstudent.Class;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temas {

    @SerializedName("Temas")
    @Expose
    private ArrayList<Tema> temas = null;

    public ArrayList<Tema> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<Tema> temas) {
        this.temas = temas;
    }

}
