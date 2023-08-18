

package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    ConfigPropertiesReader reader = new ConfigPropertiesReader();

    @Override
    public String getBotUsername() {

        try {
            return reader.getValueFromFile("botUserName");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {

        try {
            return reader.getValueFromFile("botToken");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        //When user text , get userId
        System.out.println(update);
        var msg = update.getMessage();
        var user = msg.getFrom();
        var userId = user.getId();
        System.out.println(user.getFirstName() + " wrote " + msg.getText());
        System.out.println(userId);

    }

    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }

}


