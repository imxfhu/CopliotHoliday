package com.example.copliotholiday.service;
import com.example.copliotholiday.entity.HolidayEntity;
import com.example.copliotholiday.entity.HolidayEntityList;
import com.example.copliotholiday.utils.CSVUtils;
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
        //TODO
        return null;
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