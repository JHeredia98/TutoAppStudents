package tutoapp.com.tutoappstudent.ObjectsPersistance;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Materia extends RealmObject {
    RealmList<Tema> temas;
    String Nombre;
    Integer id;

}
