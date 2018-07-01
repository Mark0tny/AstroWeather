package com.kotu.astroweather.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;
    private int highTemperature;
    private int lowTemperature;
    private String day;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }


    public int getHighTemperature() {
        return highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public String getDay() {
        return day;
    }

    @Override
    public void populate(JSONObject data) {
        code = data.optInt("code");
        temperature = data.optInt("temp");
        description = data.optString("text");
        highTemperature = data.optInt("high");
        lowTemperature = data.optInt("low");
        day = data.optString("day");

    }

    @Override
    public JSONObject toJSON(){
        JSONObject data = new JSONObject();

        try {
            data.put("code",code);
            data.put("temp",temperature);
            data.put("text",description);
            data.put("high",highTemperature);
            data.put("low",lowTemperature);
            data.put("day",day);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }
}
