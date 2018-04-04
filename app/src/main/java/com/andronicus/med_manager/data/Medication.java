package com.andronicus.med_manager.data;

/**
 * Created by andronicus on 4/3/2018.
 */

public class Medication {

    private String id;
    private String name;
    private String description;
    private String frequency;
    private String start_date;
    private String end_date;

    public Medication() {
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
