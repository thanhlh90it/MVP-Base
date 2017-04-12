package vmodev.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by thanhle on 7/15/16.
 */
public class GsonUtils {
    public static Gson createGson() {
        Gson gson = new GsonBuilder()
//                .setDateFormat(DateUtils.DATE_TIME_FORMAT)
                .registerTypeAdapter(Date.class, new GtmDateTypeAdapter())
                .create();
        return gson;
    }

    private static class GtmDateTypeAdapter implements JsonSerializer<Date>,
            JsonDeserializer<Date> {
        private final DateFormat dateFormat;

        private GtmDateTypeAdapter() {
            dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT, Locale.US);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public synchronized JsonElement serialize(Date date, Type type,
                                                  JsonSerializationContext jsonSerializationContext) {
            synchronized (dateFormat) {
                String dateFormatAsString = dateFormat.format(date);
                return new JsonPrimitive(dateFormatAsString);
            }
        }

        @Override
        public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                             JsonDeserializationContext jsonDeserializationContext) {
            try {
                synchronized (dateFormat) {
                    return dateFormat.parse(jsonElement.getAsString());
                }
            } catch (ParseException e) {
                throw new JsonSyntaxException(jsonElement.getAsString(), e);
            }
        }
    }
}
