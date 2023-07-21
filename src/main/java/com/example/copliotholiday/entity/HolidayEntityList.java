package com.example.copliotholiday.entity;

import java.util.ArrayList;

public class HolidayEntityList {
    private ArrayList<HolidayEntity> holidayList;

    private boolean isUpdated = true;

    public HolidayEntityList() {
        this.holidayList = new ArrayList<HolidayEntity>();
    }

    public void addHoliday(HolidayEntity holiday) {
        holidayList.add(holiday);
    }

    public void cleanHoliday() {
        this.holidayList.clear();
    }

    public ArrayList<HolidayEntity> getHolidayList() {
        return holidayList;
    }

    public void setHolidayList(ArrayList<HolidayEntity> holidayList) {
        this.holidayList = holidayList;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }
}
