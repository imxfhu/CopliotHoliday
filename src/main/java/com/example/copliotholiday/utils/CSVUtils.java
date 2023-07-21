package com.example.copliotholiday.utils;

import com.example.copliotholiday.entity.HolidayEntity;
import com.example.copliotholiday.entity.HolidayEntityList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class CSVUtils {
    public static final String CSV_SEPARATOR = ",";
    public static final String filePath = "src/main/resources/static/holiday.csv";
    public static final String REMOVE_HOLIDAY = "REMOVE_HOLIDAY";
    private static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);

    private static HolidayEntityList holidayEntityList = new HolidayEntityList();

    //create a method called parseCSVFile base on path
    public static HolidayEntityList parseCSVFile() {
        //create a try-catch block
        try {
            //check if path is empty
            if (StringUtils.isEmpty(filePath)) {
                logger.error("parse csv file failed, path is empty");
                return null;
            }
            //check if file is not exist
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("parse csv file failed, file is not exist, path = [{}]", filePath);
                return null;
            }
            boolean updatedHoliday = holidayEntityList.isUpdated();
            if (!updatedHoliday) {
                logger.info("no update for the holiday, return current file data size = [{}]", holidayEntityList.getHolidayList().size());
                return holidayEntityList;
            }
            //parse CSV file
            //create a BufferedReader called br
            BufferedReader br = new BufferedReader(new FileReader(file));
            //create a String called line
            String line;
            //create a while loop
            ArrayList<HolidayEntity> holidayList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                //create a String[] called values
                String[] values = line.split(CSV_SEPARATOR);
                if(values != null && values.length==4){
                    HolidayEntity holdayEntity = new HolidayEntity();
                    holdayEntity.setHolidayCountry(values[0]);
                    holdayEntity.setHolidayCountryDesc(values[1]);
                    holdayEntity.setHolidayDate(values[2]);
                    holdayEntity.setHolidayName(values[3]);
                    holidayList.add(holdayEntity);
                }
            }
            if(holidayList.size()>0){
                holidayEntityList.cleanHoliday();
                holidayEntityList.setHolidayList(holidayList);
                holidayEntityList.setUpdated(false);
                logger.info("update for the holiday data");
            }
        } catch (Exception e) {
            logger.error("parse csv file failed, path = [{}]", filePath, e);
        }
        return holidayEntityList;
    }

    public static boolean updateHoliday(ArrayList<HolidayEntity> newHolidayList) {
        if (newHolidayList == null || newHolidayList.size() == 0) {
            logger.info("no any update, new holiday list is empty");
            return false;
        }
        HolidayEntityList currentHolidayEntityList = getHolidayEntityList();
        if (currentHolidayEntityList == null || currentHolidayEntityList.getHolidayList().size() == 0) {
            logger.info("current csv file is empty, write new data into csv file");
            currentHolidayEntityList.setUpdated(true);
            writeCSVFile(newHolidayList);
            return true;
        }
        ArrayList<HolidayEntity> cuHolidayList = currentHolidayEntityList.getHolidayList();
        ArrayList<HolidayEntity> finalHolidayList = new ArrayList<HolidayEntity>();
        Map<String, HolidayEntity> newHolidayMap = new HashMap<>();
        for (int j=0;j<newHolidayList.size();j++) {
            HolidayEntity newHoliday = newHolidayList.get(j);
            newHolidayMap.put(newHoliday.getHolidayCountry()+"#"+newHoliday.getHolidayDate(),newHoliday);
        }
        for(int i=0;i<cuHolidayList.size();i++){
            HolidayEntity cuHoliday = cuHolidayList.get(i);
            HolidayEntity updateHoliday = null;
            boolean newAdd = false;
            for (int j=0;j<newHolidayList.size();j++) {
                HolidayEntity newHoliday = newHolidayList.get(j);
                if(cuHoliday.getHolidayDate().equals(newHoliday.getHolidayDate())
                        && cuHoliday.getHolidayCountry().equals(newHoliday.getHolidayCountry())){
                    updateHoliday = newHoliday;
                    newHolidayMap.remove(newHoliday.getHolidayCountry()+"#"+newHoliday.getHolidayDate());
                }
            }
            if(updateHoliday != null) {
                finalHolidayList.add(updateHoliday);
            }else {
                finalHolidayList.add(cuHoliday);
            }
        }
        if(newHolidayMap.keySet().size()>0){
            for(Map.Entry<String, HolidayEntity> entry: newHolidayMap.entrySet()){
                finalHolidayList.add(entry.getValue());
            }
        }
        logger.info("finalHolidayList  size = [{}]",finalHolidayList.size());
        if(finalHolidayList.size()>0){
            currentHolidayEntityList.setUpdated(true);
            return writeCSVFile(finalHolidayList);
        }
        return false;
    }

    public static boolean removeHoliday(ArrayList<HolidayEntity> rmHolidayList) {
        if (rmHolidayList == null || rmHolidayList.size() == 0) {
            logger.info("no any remove need, remove holiday list is empty");
            return false;
        }
        HolidayEntityList currentHolidayEntityList = getHolidayEntityList();
        if (currentHolidayEntityList == null || currentHolidayEntityList.getHolidayList().size() == 0) {
            logger.info("current csv file is empty, no need remove");
            return false;
        }
        ArrayList<HolidayEntity> cuHolidayList = currentHolidayEntityList.getHolidayList();
        ArrayList<HolidayEntity> finalHolidayList = new ArrayList<HolidayEntity>();
        for (int j=0;j<cuHolidayList.size();j++){
            HolidayEntity cuHoliday = cuHolidayList.get(j);
            boolean remove = false;
            for(int i=0;i<rmHolidayList.size();i++){
                HolidayEntity rmHoliday = rmHolidayList.get(i);
                if(cuHoliday.getHolidayDate().equals(rmHoliday.getHolidayDate())
                        && cuHoliday.getHolidayCountry().equals(rmHoliday.getHolidayCountry())){
                    remove = true;
                }
            }
            if(!remove){
                finalHolidayList.add(cuHoliday);
            }
        }
        logger.info("removeHoliday: finalHolidayList  size = [{}]",finalHolidayList.size());
        if(finalHolidayList.size()>0){
            currentHolidayEntityList.setUpdated(true);
            return writeCSVFile(finalHolidayList);
        }
        return false;
    }

    public static boolean writeCSVFile(ArrayList<HolidayEntity> holidayList) {
        //delete the old file
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            logger.error("old file deleted exception", e);
            return false;
        }
        File outFile = new File(filePath);
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        try{
            out = new FileOutputStream(outFile);
            osw = new OutputStreamWriter(out, "utf-8");
            for(int i=0;i<holidayList.size();i++){
                HolidayEntity holidayEntity = holidayList.get(i);
                String data = holidayEntity.getHolidayCountry()+CSV_SEPARATOR
                        +holidayEntity.getHolidayCountryDesc()+CSV_SEPARATOR
                        +holidayEntity.getHolidayDate()+CSV_SEPARATOR
                        +holidayEntity.getHolidayName();
                System.out.println("write date = "+data);
                osw.write(data+"\r\n");
            }
            osw.flush();
            out.close();
            osw.close();
        }catch (IOException e) {
            logger.error("write csv file failed, path = [{}]", filePath, e);
            return false;
        }
        return true;
    }

    public static HolidayEntityList getHolidayEntityList() {
        return parseCSVFile();
    }
}
