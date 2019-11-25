package com.cat.demo.ws;

import com.cat.demo.Assist.StringTools;
import com.cat.demo.Assist.WSAssist;
import com.cat.demo.Config.ConfigUtil;
import com.cat.demo.Object.WeatherInfo;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WSAssist _WSAssist;

    /**
     * Get Citie
     * @return Citie
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getCities")
    public String getCities() throws Exception{

        Properties properties = ConfigUtil.getProps();

        List<String> list = new ArrayList();

        for (String key : properties.stringPropertyNames()) {
            list.add(key);
        }

        return StringTools.toStringByJson(list);

    }

    /**
     * Get current weather
     * @param city city
     * @return weather info
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getCurrentData")
    public String getCurrentData(@RequestParam(value = "city") String city) throws Exception{
        city = upperFirstLetter(city);

        // get position
        String geoPos = ConfigUtil.getProperty(city);

        if(geoPos != null){
            String url = new StringBuffer().append(_WSAssist.URL).append(_WSAssist.API)
                    .append("/").append(geoPos).toString();

            logger.info("Weather Service URL is : " + url);
            URL urlcon = null;
            try {
                urlcon = new URL(url);
                logger.info("url:" + url);
                HttpURLConnection htpcon = (HttpURLConnection)urlcon.openConnection();
                htpcon.setRequestMethod("GET");
                htpcon.setDoOutput(true);
                htpcon.setDoInput(true);
                htpcon.setUseCaches(false);
                htpcon.setDefaultUseCaches(false);
                htpcon.setConnectTimeout(600000);
                htpcon.setReadTimeout(600000);
                htpcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.13) Gecko/2009073021 Firefox/3.0.13");
                htpcon.setRequestProperty( "Cache-Control",  "no-cache" );
                htpcon.setRequestProperty( "Pragma",  "no-cache" );
                htpcon.setRequestProperty( "Expires",  "0" );
                htpcon.connect();

                int responseCode = htpcon.getResponseCode();
                logger.info("Response code = " + responseCode);
                if(responseCode == 200){
                    InputStream in = htpcon.getInputStream();

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String json = "";
                    while ((json = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json);
                    }

                    JSONObject jsonStr = JSONObject.fromObject(stringBuilder.toString());
                    String currently = jsonStr.getString("currently");

                    // get weather object from json
                    Type type = new TypeToken<WeatherInfo>() {}.getType();
                    WeatherInfo weatherInfo = StringTools.toTByJson(currently, type);
                    weatherInfo.setCity(city);

                    // covert format for date
                    Date time = new Date(weatherInfo.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("EEEE hh:mm a", Locale.ENGLISH);
                    weatherInfo.setStrtime(format.format(time));

                    // convert Temperatur
                    BigDecimal temp = new BigDecimal(weatherInfo.getTemperature())
                            .subtract(new BigDecimal("32"))
                            .divide(new BigDecimal("1.8"), 0, BigDecimal.ROUND_HALF_UP);
                    weatherInfo.setTemperature(temp.toString());
                    weatherInfo.setWindSpeed(new BigDecimal(weatherInfo.getWindSpeed()).setScale(0,BigDecimal.ROUND_HALF_UP).toString());

                    return StringTools.toStringByJson(weatherInfo);
                } else {
                    logger.error("Response code = " + responseCode);
                    return null;
                }

            } catch (IOException e) {
                logger.error("Fail to get weather from WS.", e);
                throw e;
            }
        } else {
            logger.warn("Can not get weather for " + city);
            return null;
        }

    }

    /** UpperCase for first charactor
     *
     * @param letter
     * @return
     */
    public String upperFirstLetter(String letter){
        letter = letter.toLowerCase();
        return letter.substring(0, 1).toUpperCase()+letter.substring(1);
    }
}
