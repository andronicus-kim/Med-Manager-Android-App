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
    private String frequency;
    private String start_date;
    private String end_date;

    public Medication() {
    }

    protected Medication(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
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

    public String getName() {
        return name;
    }

    public Medication(String id, String name, String description, String frequency, String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
        dest.writeString(frequency);
        dest.writeString(start_date);
        dest.writeString(end_date);
    }
}
