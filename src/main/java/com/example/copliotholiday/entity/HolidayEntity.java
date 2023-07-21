package com.example.copliotholiday.entity;

public class HolidayEntity {
    private String holidayName;
    private String holidayDate;
    private String holidayCountry;
    private String holidayCountryDesc;

    public HolidayEntity() {
    }

    public HolidayEntity(String holidayName, String holidayDate, String holidayCountry, String holidayCountryDesc) {
        this.holidayName = holidayName;
        this.holidayDate = holidayDate;
        this.holidayCountry = holidayCountry;
        this.holidayCountryDesc = holidayCountryDesc;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public String getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(String holidayDate) {
        this.holidayDate = holidayDate;
    }

    public String getHolidayCountry() {
        return holidayCountry;
    }

    public void setHolidayCountry(String holidayCountry) {
        this.holidayCountry = holidayCountry;
    }

    public String getHolidayCountryDesc() {
        return holidayCountryDesc;
    }

    public void setHolidayCountryDesc(String holidayCountryDesc) {
        this.holidayCountryDesc = holidayCountryDesc;
    }

}
