package com.bean;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CalendarSerializer implements JsonSerializer<Calendar>,JsonDeserializer<Calendar>{
       // 实现JsonSerializer接口的serialize（）方法，实现自定义序列化josn
        public JsonElement serialize(Calendar src, Type typeOfSrc,	JsonSerializationContext context) {
            if (src == null) {
                return null;
            } else {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //JsonElement是一个抽象类，实现类分别是JsonObject和JsonPrimitive
                JsonObject ob=new JsonObject();
                ob.addProperty("date", format.format(src.getTime()));
               // return ob ;//结果{"id":0,"date":"2016-04-19","calendar":{"date":"2016-04-19 21:53:54"}}
               return new JsonPrimitive(format.format(src.getTime()));//结果{"id":0,"date":"2016-04-19","calendar":"2016-04-19 21:54:31"}
            }
        }
        //实现JsonDeserializer接口的deserialize（）方法，实现自定义反序列化Object
        public Calendar deserialize(JsonElement json, Type typeOfT,  JsonDeserializationContext context) throws JsonParseException {
            Date date = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = format.parse(json.getAsString());
            } catch (ParseException e) {
                date = null;
            }
            GregorianCalendar  gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);
            return gregorianCalendar;
        }

    }