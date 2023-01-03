package io.project.AviaticketsBot.service;


import com.vdurmont.emoji.EmojiParser;
import io.project.AviaticketsBot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig botConfig;
    static final String HELP_TEXT = "How to use bot?\n\n" +
            " You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message.\n\n" +
            "Type /help to see this message again\n\n" +
            "Type /fromToDate to write airport name FROM and TO and to write a date\n\n";

    public TelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
        List<BotCommand> commandsList = new ArrayList<>();
        commandsList.add(new BotCommand("/start", "Hello message"));
        commandsList.add(new BotCommand("/help", "how to use"));

        commandsList.add(new BotCommand("/fromToDate", "type an airport name with only three letters from which you want to have a flight, type airport of the destination and type a date"));


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

            switch (messageText) {
                case "/start":

                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;

                case "/fromToDate":
                    sendMessage(chatId, "Type city or country FROM");
                    break;

                default:
                    sendMessage(chatId, "Could not find the command!");

            }
        }

    }



    private void startCommandReceived(long chatId, String name) {

        String answer = EmojiParser.parseToUnicode("Hello, " + name + ", nice to meet you!"+" :grinning:\n\n" +
                "I use only airport names with 3 letters\n\n" +
                "example: LGW IST 2023.01.12)");

        log.info("Replied to user " + name);
        sendMessage(chatId, answer);

    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup=new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("start");
        row.add("help");
        row.add("from\nto\nwhen");

        keyboardMarkup.setKeyboard(keyboardRows);

        sendMessage.setReplyMarkup(keyboardMarkup);



        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

}
