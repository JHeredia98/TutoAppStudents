package tutoapp.com.tutoappstudent.Objects;

import android.location.Location;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;

public class TutorShip implements Serializable {
    String Motivo;
    String idUserStudent;

    String TopicId;
    Double latitude;
    Double longitude;

    String dateString;
    String IdTuto;
    int status;

    public double getLatitude(){
        return latitude;
    }
    public double getLongitude(){
        return longitude;
    }

    public TutorShip(String motivo, String idUserStudent, Double longitude,Double latitude, String topicId) {
        Motivo = motivo;
        this.idUserStudent = idUserStudent;
        this.longitude = longitude;
        TopicId = topicId;
        this.status=0;
        this.IdTuto="";
    }

    public String getIdTuto() {
        return IdTuto;
    }

    public void setIdTuto(String idTuto) {
        IdTuto = idTuto;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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



    public TutorShip() {
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }

    public void setIdUserStudent(String idUserStudent) {
        this.idUserStudent = idUserStudent;
    }

    public void setTopicId(String topicId) {
        TopicId = topicId;
    }

}
