package com.example.copliotholiday.service;
import com.example.copliotholiday.entity.HolidayEntity;
import com.example.copliotholiday.entity.HolidayEntityList;
import com.example.copliotholiday.utils.CSVUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;

//create a class called HolidayServiceImp
@Service
public class HolidayServiceImp implements HolidayService {

    //create a method called addHoliday
    @Override
    public void addHoliday(ArrayList<HolidayEntity> holidayList) {
        CSVUtils.updateHoliday(holidayList);
    }

    @Override
    public void updateHoliday(ArrayList<HolidayEntity> holidayList) {
        CSVUtils.updateHoliday(holidayList);
    }

    @Override
    public void deleteHoliday(ArrayList<HolidayEntity> holidayList) {
        CSVUtils.removeHoliday(holidayList);
    }

    @Override
    public ArrayList<HolidayEntity> getNextYearHolidayByCountry(String holidayCountry) {
        HolidayEntityList holidayList = CSVUtils.getHolidayEntityList();
        if (holidayList == null || holidayList.getHolidayList() == null || holidayList.getHolidayList().size() == 0) {
            return null;
        }
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        String nextYear = String.valueOf(currentYear+1);
        ArrayList<HolidayEntity> currentHolidayList = holidayList.getHolidayList();
        ArrayList<HolidayEntity> resultHolidayList = new ArrayList<HolidayEntity>();
        for(int i=0; i<currentHolidayList.size(); i++){
            HolidayEntity holiday = currentHolidayList.get(i);
            if(holiday.getHolidayCountry().equals(holidayCountry) && holiday.getHolidayDate().startsWith(nextYear)){
                resultHolidayList.add(holiday);
            }
        }
        return resultHolidayList;
    }

    @Override
    public HolidayEntity getNextHoliday(String holidayCountry) {
        HolidayEntityList holidayList = CSVUtils.getHolidayEntityList();
        if(holidayList == null || holidayList.getHolidayList() == null || holidayList.getHolidayList().size() == 0){
            return null;
        }
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);
        int currentDate = currentYear*10000 + currentMonth*100 + currentDay;
        int nextDate = Integer.MAX_VALUE;
        int holidayDate = 0;
        HolidayEntity holidayEntity = null;
        ArrayList<HolidayEntity> currentHolidayList = holidayList.getHolidayList();
        for(int i=0; i<currentHolidayList.size(); i++){
            HolidayEntity holiday = currentHolidayList.get(i);
            if(holiday.getHolidayCountry().equals(holidayCountry)){
                holidayDate = getHolidayDate(holiday.getHolidayDate());
                if(holidayDate>currentDate && holidayDate<nextDate){
                    nextDate = holidayDate;
                    holidayEntity = holiday;
                }
            }
        }
        return holidayEntity;
    }

    private int getHolidayDate(String holidayDate) {
        if(StringUtils.isBlank(holidayDate)) {
            return 0;
        }
        String[] dateStrs = holidayDate.split("-");
        if(dateStrs == null || dateStrs.length != 3){
            return 0;
        }
        int holidayDateInt = Integer.parseInt(dateStrs[0])*10000 + Integer.parseInt(dateStrs[1])*100 + Integer.parseInt(dateStrs[2]);
        return holidayDateInt;
    }

    @Override
    public boolean isHolidayDate(String holidayDate) {
        HolidayEntityList holidayList = CSVUtils.getHolidayEntityList();
        if (holidayList == null || holidayList.getHolidayList() == null || holidayList.getHolidayList().size() == 0) {
            return false;
        }
        ArrayList<HolidayEntity> currentHolidayList = holidayList.getHolidayList();
        for(int i=0; i<currentHolidayList.size(); i++){
            HolidayEntity holiday = currentHolidayList.get(i);
            if(holiday.getHolidayDate().equals(holidayDate)){
                return true;
            }
        }
        return false;
    }

}