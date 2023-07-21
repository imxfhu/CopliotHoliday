package com.example.copliotholiday.service;
import com.example.copliotholiday.entity.HolidayEntity;

import java.util.ArrayList;

//create a interface called HolidayService
public interface HolidayService {
    //create a method called addHoliday
    public void addHoliday(ArrayList<HolidayEntity> holidayList);
    //create a method called updateHoliday
    public void updateHoliday(ArrayList<HolidayEntity> holidayList);
    //create a method called deleteHoliday
    public void deleteHoliday(ArrayList<HolidayEntity> holidayList);
    //create a method called getNextYearHolidayByCountry base on country and date
    public ArrayList<HolidayEntity> getNextYearHolidayByCountry(String holidayCountry);
    //create a method called getNextHoliday base on country and date
    public HolidayEntity getNextHoliday(String holidayCountry);
    //create a method called isHolidayDate base on date
    public boolean isHolidayDate(String holidayDate);
}