package com.andronicus.med_manager.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andronicus on 4/3/2018.
 */

public class Medication implements Parcelable{

    private String id;
    private String name;
    private String description;
    private String no_of_tablets;
    private String frequency;
    private String start_date;
    private String end_date;

    public Medication(){}

    public Medication(String id, String name, String description,
                      String no_of_tablets, String frequency, String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.no_of_tablets = no_of_tablets;
        this.frequency = frequency;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    protected Medication(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        no_of_tablets = in.readString();
        frequency = in.readString();
        start_date = in.readString();
        end_date = in.readString();
    }

    public static final Creator<Medication> CREATOR = new Creator<Medication>() {
        @Override
        public Medication createFromParcel(Parcel in) {
            return new Medication(in);
        }

        @Override
        public Medication[] newArray(int size) {
            return new Medication[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNo_of_tablets() {
        return no_of_tablets;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(no_of_tablets);
        dest.writeString(frequency);
        dest.writeString(start_date);
        dest.writeString(end_date);
    }
}
