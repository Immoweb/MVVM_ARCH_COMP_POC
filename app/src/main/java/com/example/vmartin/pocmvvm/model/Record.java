package com.example.vmartin.pocmvvm.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Calendar;
import java.util.Locale;


public class Record implements Parcelable {

    private static final String STATIC_MAPS_IMAGE_SIZED_URL = "https://maps.googleapis.com/maps/api/staticmap?center=%1$s&zoom=18&size=640x320&markers=color:blue%%7C%2$s";

    public static final Creator<Record> CREATOR = new Creator<Record>() {
        public Record createFromParcel(Parcel source) {
            return new Record(source);
        }

        public Record[] newArray(int size) {
            return new Record[size];
        }
    };
    private String datasetid;
    private String recordid;
    private FoodTruckPlace fields;
    private Geometry geometry;

    public Record() {

    }

    public Record(Parcel in) {
        this.datasetid = in.readString();
        this.recordid = in.readString();
        this.fields = in.readParcelable(FoodTruckPlace.class.getClassLoader());
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        return !(recordid != null ? !recordid.equals(record.recordid) : record.recordid != null);

    }

    @Override
    public int hashCode() {
        return recordid != null ? recordid.hashCode() : 0;
    }

    public String getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(String datasetid) {
        this.datasetid = datasetid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public FoodTruckPlace getFields() {
        return fields;
    }

    public void setFields(FoodTruckPlace fields) {
        this.fields = fields;
    }


    public String getImageUrl() {
        double lat = getFields().getCoordonneesWgs84().get(0);
        double lng = getFields().getCoordonneesWgs84().get(1);
        String urlCoordinates = lat + "," + lng;
        return String.format(STATIC_MAPS_IMAGE_SIZED_URL, urlCoordinates, urlCoordinates);
    }

    @Override
    public String toString() {
        return "{" + fields + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.datasetid);
        dest.writeString(this.recordid);
        dest.writeParcelable(this.fields, 0);
        dest.writeParcelable(this.geometry, 0);
    }



    public String getFoodTruck() {
        String foodTruckName = "No food truck today";
        switch (Calendar.getInstance(Locale.US).get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                if (!TextUtils.isEmpty(getFields().getLundi())) {
                    foodTruckName = getFields().getLundi();
                }
                break;
            case Calendar.TUESDAY:
                if (!TextUtils.isEmpty(getFields().getMardi())) {
                    foodTruckName = getFields().getMardi();
                }
                break;
            case Calendar.WEDNESDAY:
                if (!TextUtils.isEmpty(getFields().getMercredi())) {
                    foodTruckName = getFields().getMercredi();
                }
                break;
            case Calendar.THURSDAY:
                if (!TextUtils.isEmpty(getFields().getJeudi())) {
                    foodTruckName = getFields().getJeudi();
                }
                break;
            case Calendar.FRIDAY:
                if (!TextUtils.isEmpty(getFields().getVendredi())) {
                    foodTruckName = getFields().getVendredi();
                }
                break;
            case Calendar.SATURDAY:
                if (!TextUtils.isEmpty(getFields().getSamedi())) {
                    foodTruckName = getFields().getSamedi();
                }
                break;
        }
        return foodTruckName;
    }
}
