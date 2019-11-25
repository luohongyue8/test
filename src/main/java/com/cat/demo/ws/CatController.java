package com.cat.demo.ws;

import com.cat.demo.Assist.StringTools;
import com.cat.demo.Assist.WSAssist;
import com.cat.demo.Object.*;
import com.google.gson.reflect.TypeToken;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cat")
public class CatController {

    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WSAssist _WSAssist;


    /**
     * Get Cats
     * @return Citie
     */
    @RequestMapping(method = RequestMethod.POST, value = "/getCats")
    public String getCats() throws Exception{



            String url =_WSAssist.URLCAT;

            logger.info("Cat Service URL is : " + url);
            URL urlcon = null;
            try {
                urlcon = new URL(url);
                logger.info("url:" + url);
                HttpURLConnection htpcon = (HttpURLConnection) urlcon.openConnection();
                htpcon.setRequestMethod("GET");
                htpcon.setDoOutput(true);
                htpcon.setDoInput(true);
                htpcon.setUseCaches(false);
                htpcon.setDefaultUseCaches(false);
                htpcon.setConnectTimeout(600000);
                htpcon.setReadTimeout(600000);
                htpcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.5; en-US; rv:1.9.0.13) Gecko/2009073021 Firefox/3.0.13");
                htpcon.setRequestProperty("Cache-Control", "no-cache");
                htpcon.setRequestProperty("Pragma", "no-cache");
                htpcon.setRequestProperty("Expires", "0");
                htpcon.connect();

                int responseCode = htpcon.getResponseCode();
                logger.info("Response code = " + responseCode);
                if (responseCode == 200) {
                    InputStream in = htpcon.getInputStream();

                    StringBuilder stringBuilder = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String json = "";
                    while ((json = bufferedReader.readLine()) != null) {
                        stringBuilder.append(json);
                    }

                    // get Owner object from json
                    Type type = new TypeToken<List<CatOwnerInfo>>() {}.getType();
                    List<CatOwnerInfo> catOwnerInfo = StringTools.toTByJson(stringBuilder.toString(), type);
                    List<CatInfo> maleCats = new ArrayList<>();
                    List<CatInfo> famaleCats = new ArrayList<>();
                    catOwnerInfo.stream().forEach(s -> {
                        if(s.getPets() != null && s.getPets().size() > 0){
                            if ("Male".equals(s.getGender())) {
                                maleCats.addAll(s.getPets());
                            } else {
                                famaleCats.addAll(s.getPets());
                            }
                        }

                    });

                    GenderCatInfo genderCatInfo = new GenderCatInfo();

                    genderCatInfo.setMaleCats(maleCats.stream().sorted(Comparator.comparing(CatInfo::getName)).collect(Collectors.toList()));
                    genderCatInfo.setFemaleCats(famaleCats.stream().sorted(Comparator.comparing(CatInfo::getName)).collect(Collectors.toList()));

                    return StringTools.toStringByJson(genderCatInfo);
                } else {
                    logger.error("Response code = " + responseCode);
                    return null;
                }
            } catch (IOException e) {
                logger.error("Fail to get cats from WS", e);
                throw e;
            }

    }

}
