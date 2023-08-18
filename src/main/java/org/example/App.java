package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.Arrays;

public class App {

    public static void main(String[] args) throws TelegramApiException, IOException {

        //Login to telegramBotsApi
        //To find Properties file path
        ConfigPropertiesReader reader = new ConfigPropertiesReader();
        reader.checkCommandLineArgs(args);

        Bot bot = new Bot();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bot);


        //urlList value from file
        String[] urlList = reader.getValueFromFile("urlList").split(";\\s*");
        System.out.println(Arrays.toString(urlList));

        Requester requester = new Requester();
        Arrays.stream(urlList).parallel().forEach(url -> {
                    try {
                        requester.httpGetRequest(url);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


        );

    }


}

