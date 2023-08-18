package org.example;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessageSender {

    public void responseParser(HttpResponse response) {

        //To add time stamp
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        //Get url and statusCode
        String uri = response.uri().toString();
        String statusCode = String.valueOf(response.statusCode());

        Map<String, String> brokenUrlMap = new HashMap<>();
        Map<String, String> fixedUrlMap = new HashMap<>();
        List<Map<String, String>> brokenUrls = new ArrayList<>();
        List<Map<String, String>> fixedUrls = new ArrayList<>();

        Bot bot = new Bot();
        ConfigPropertiesReader reader = new ConfigPropertiesReader();

        //If statusCode  not 2xx
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            //Put object that keeps broken uri and statusCode
            brokenUrlMap.put(uri, statusCode);
            //If object not exist add it to list , these block for preventing to error same message again and again
            if (!brokenUrls.contains(brokenUrlMap)) {
                brokenUrls.add(brokenUrlMap);
                //Delete broken url from fixedUrl list (it was fixed now it broke)
                fixedUrls.removeIf(key -> key.containsKey(uri));
                //send message to telegram
                try {
                    bot.sendText(Long.valueOf(reader.getValueFromFile("chatId").replace("L","")), "ERROR" + "\n" + "url name: " + uri + "\n" +
                            "Status code: " + statusCode + "\n" + formatter.format(date));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        //If status code 2xx
        else {
            //Put object that keeps fixed uri and statusCode
            fixedUrlMap.put(uri, statusCode);
            //If object not exist add it to list , these block for preventing to success same message again and again
            if (!fixedUrls.contains(fixedUrlMap)) {
                //Delete fixed url from brokenUrl list (it was broken now it fixed)
                brokenUrls.removeIf(key -> key.containsKey(uri));
                try {
                    bot.sendText(Long.valueOf(reader.getValueFromFile("chatId").replace("L","")), "SUCCESS" + "\n" + "url name: " + uri + "\n" +
                            "Status code: " + statusCode + "\n" + formatter.format(date));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }
}
