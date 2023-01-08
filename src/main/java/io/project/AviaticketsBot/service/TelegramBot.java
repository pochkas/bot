package io.project.AviaticketsBot.service;



import com.vdurmont.emoji.EmojiParser;
import io.project.AviaticketsBot.config.BotConfig;
import io.project.AviaticketsBot.model.Data;
import io.project.AviaticketsBot.model.FlightResponse;
import lombok.extern.slf4j.Slf4j;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.springframework.http.*;


import java.util.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig botConfig;

    @Autowired
    private  FlightService flightService;

    static final String HELP_TEXT = "How to use bot?\n\n" +
            " You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message.\n\n" +
            "Type /help to see this message again\n\n" +
            "Type /fromtodate to type departure airport (from), arrival airport (to) and a date of the flight\n\n";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> commandsList = new ArrayList<>();
        commandsList.add(new BotCommand("/start", "Hello message"));
        commandsList.add(new BotCommand("/help", "how to use"));
        commandsList.add(new BotCommand("/fromtodate", "to type departure airport (from), arrival airport (to) and a date of the flight"));


        try {
            this.execute(new SetMyCommands(commandsList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String[] command = messageText.split("/");

            log.info("Commands 0" + command[1]);

            if (command[1].equals("start")) {

                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());

            } else if (command[1].equals("help")) {

                sendMessage(chatId, HELP_TEXT);

            } else if (command[1].startsWith("fromtodate")) {

                Message inputMessage = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatIdUser = inputMessage.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inputMessage.getText());
                FlightResponse flightData=flightService.getFlights(response);

                List<String> list=new ArrayList<>();
                for(int i=0; i<flightData.data.length;i++) {
                    list.add((i+1+") ")+flightData.data[i].toString()+"\n");
                }

                for(String l:list) {
                    sendMessage(chatId, l);
                }

            } else {

                sendMessage(chatId, "Could not find the command!");

            }
        }

    }


    private void startCommandReceived(long chatId, String name) {

        String answer = EmojiParser.parseToUnicode("Hello, " + name + ", nice to meet you!" + " :grinning:\n\n" +
                "I use only airport name contains 3 letters\n\n" +
                "example: /fromtodate LGW IST 2023-01-12");


        log.info("Replied to user " + name);
        sendMessage(chatId, answer);

    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("start");
        row.add("help");
        row.add("fromtodate");

        keyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setReplyMarkup(keyboardMarkup);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }

    }



    public String parseMessage(String textMessage) {

        String[] list = textMessage.split(" ");

        String departureAirport = list[1];
        String arrivalAirport = list[2];
        String date = list[3];

        return "https://flight-info-api.p.rapidapi.com/schedules?version=v1" + "&DepartureDate="+ date +"&DepartureAirport="+ departureAirport + "&ArrivalAirport=" + arrivalAirport;


    }


}
