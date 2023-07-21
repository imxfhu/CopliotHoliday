package com.example.copliotholiday.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.copliotholiday.constants.HolidayConstants;
import com.example.copliotholiday.entity.HolidayEntity;
import com.example.copliotholiday.service.HolidayService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

//create a class called HolidayController
@RestController
@RequestMapping("/holiday")
public class HolidayController {
    //create a private static final Logger called log
    private final static Logger log = LoggerFactory.getLogger(HolidayController.class);
    //create a private HolidayService called holidayService
    @Autowired
    private HolidayService holidayService;

    //create a method called add Holiday
    @PostMapping("/addHoliday")
    public JSONObject addHoliday(@RequestBody JSONObject reqObject) {
        JSONObject respObject = new JSONObject();
        JSONArray holidayList = reqObject.getJSONArray("holidayList");
        if(holidayList == null || holidayList.size() == 0){
            respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_FAILED);
            respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_FAILED);
            return respObject;
        }
        ArrayList<HolidayEntity> holidayEntityList = parseHolidayList(holidayList);
        holidayService.addHoliday(holidayEntityList);
        respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_SUCCESS);
        respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_SUCCESS);
        return respObject;
    }

    //create a method called update Holiday
    @PostMapping("/updateHoliday")
    public JSONObject updateHoliday(@RequestBody JSONObject reqObject) {
        JSONObject respObject = new JSONObject();
        JSONArray holidayList = reqObject.getJSONArray("holidayList");
        if(holidayList == null || holidayList.size() == 0){
            respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_FAILED);
            respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_FAILED);
            return respObject;
        }
        ArrayList<HolidayEntity> holidayEntityList = parseHolidayList(holidayList);
        holidayService.updateHoliday(holidayEntityList);
        respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_SUCCESS);
        respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_SUCCESS);
        return respObject;
    }

    //create a method called remove Holiday
    @PostMapping("/removeHoliday")
    public JSONObject removeHoliday(@RequestBody JSONObject reqObject) {
        JSONObject respObject = new JSONObject();
        JSONArray holidayList = reqObject.getJSONArray("holidayList");
        if(holidayList == null || holidayList.size() == 0){
            respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_FAILED);
            respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_FAILED);
            return respObject;
        }
        ArrayList<HolidayEntity> holidayEntityList = parseHolidayList(holidayList);
        holidayService.deleteHoliday(holidayEntityList);
        respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_SUCCESS);
        respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_SUCCESS);
        return respObject;
    }

    //create a method called getNextYearHolidayByCountry
    @PostMapping("/getNextYearHolidayByCountry")
    public JSONObject getNextYearHolidayByCountry(@RequestBody JSONObject reqObject) {
        JSONObject respObject = new JSONObject();
        String holidayCountry = reqObject.getString("holidayCountry");
        if(StringUtils.isEmpty(holidayCountry)){
            respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_FAILED);
            respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_FAILED);
            return respObject;
        }
        ArrayList<HolidayEntity> holidayEntityList = holidayService.getNextYearHolidayByCountry(holidayCountry);
        respObject.put("holidayList", holidayEntityList);
        respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_SUCCESS);
        respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_SUCCESS);
        return respObject;
    }

    //create a method called getNextHoliday
    @PostMapping("/getNextHoliday")
    public JSONObject getNextHoliday(@RequestBody JSONObject reqObject) {
        JSONObject respObject = new JSONObject();
        String holidayCountry = reqObject.getString("holidayCountry");
        if(StringUtils.isEmpty(holidayCountry)){
            respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_FAILED);
            respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_FAILED);
            return respObject;
        }
        HolidayEntity holidayEntity = holidayService.getNextHoliday(holidayCountry);
        respObject.put("holiday", holidayEntity);
        respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_SUCCESS);
        respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_SUCCESS);
        return respObject;
    }

    //create a method called isHolidayDate
    @PostMapping("/isHolidayDate")
    public JSONObject isHolidayDate(@RequestBody JSONObject reqObject) {
        JSONObject respObject = new JSONObject();
        String holidayDate = reqObject.getString("holidayDate");
        if(StringUtils.isEmpty(holidayDate)){
            respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_FAILED);
            respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_FAILED);
            return respObject;
        }
        boolean isHolidayDate = holidayService.isHolidayDate(holidayDate);
        respObject.put("isHolidayDate", isHolidayDate);
        respObject.put(HolidayConstants.RESP_CODE, HolidayConstants.RESP_CODE_SUCCESS);
        respObject.put(HolidayConstants.RESP_MSG, HolidayConstants.RESP_MSG_SUCCESS);
        return respObject;
    }

    private ArrayList<HolidayEntity> parseHolidayList(JSONArray holidayList){
        ArrayList<HolidayEntity> holidayEntityList = new ArrayList<>();
        for(int i=0; i<holidayList.size(); i++){
            JSONObject holiday = holidayList.getJSONObject(i);
            if(holiday != null){
                String holidayName = holiday.getString("holidayName");
                String holidayDate = holiday.getString("holidayDate");
                String holidayCountry = holiday.getString("holidayCountry");
                String holidayCountryDesc = holiday.getString("holidayCountryDesc");
                if (StringUtils.isNotBlank(holidayName) && StringUtils.isNotBlank(holidayDate)
                        && StringUtils.isNotBlank(holidayCountry) && StringUtils.isNotBlank(holidayCountryDesc)) {
                    HolidayEntity holidayEntity = new HolidayEntity();
                    holidayEntity.setHolidayName(holidayName);
                    holidayEntity.setHolidayDate(holidayDate);
                    holidayEntity.setHolidayCountry(holidayCountry);
                    holidayEntity.setHolidayCountryDesc(holidayCountryDesc);
                    holidayEntityList.add(holidayEntity);
                }
            }
        }
        return holidayEntityList;
    }

}
