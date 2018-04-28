package tutoapp.com.tutoappstudent.Objects;

import android.location.Location;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

public class TutorShip implements Serializable {
    String Motivo;
    String idUserStudent;
    LatLng location;
    String TopicId;
    Date date;
    public double getLatitude(){
        return location.latitude;
    }
    public double getLongitude(){
        return location.longitude;
    }

    public TutorShip(String motivo, String idUserStudent, LatLng location, String topicId) {
        Motivo = motivo;
        this.idUserStudent = idUserStudent;
        this.location = location;
        TopicId = topicId;
    }

    @Override
    public String toString() {
        return "TutorShip{" +
                "Motivo='" + Motivo + '\'' +
                ", idUserStudent='" + idUserStudent + '\'' +
                ", location=" + location +
                ", TopicId='" + TopicId + '\'' +
                '}';
    }

    public String getMotivo() {
        return Motivo;
    }

    public String getIdUserStudent() {
        return idUserStudent;
    }

    public String getTopicId() {
        return TopicId;
    }

    public Date getDate() {
        return date;
    }

    public TutorShip() {
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public void setIdUserStudent(String idUserStudent) {
        this.idUserStudent = idUserStudent;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
