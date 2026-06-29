package jcss.soft.com;

import jcss.soft.com.components.GeminiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TelegramServiceApplication {

//    private final GeminiClient client;

	public static void main(String[] args) {
		SpringApplication.run(TelegramServiceApplication.class, args);
	}

//    @Override
//    public void run(String... args) throws Exception {
//        client.run("What is kubernetes");
//    }
}
