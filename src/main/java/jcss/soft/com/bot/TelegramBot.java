package jcss.soft.com.bot;

import jcss.soft.com.components.GeminiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("telegram.username")
    private String username;

    @Autowired
    private GeminiClient geminiClient;

    public TelegramBot(String botName, String botToken) {
        super(botToken);
        this.username = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            var chatId = message.getChatId();

            log.info("Message received: {}", message.getChatId());
            log.info("From: {}", message.getFrom().getUserName());
            var messageText = message.getText();
            String response = geminiClient.run(messageText);
            try {
                execute(new SendMessage(chatId.toString(), response));
            } catch (TelegramApiException e) {
                log.error("Exception during processing telegram api: {}", e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }
}
