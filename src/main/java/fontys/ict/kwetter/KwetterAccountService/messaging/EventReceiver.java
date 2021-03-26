package fontys.ict.kwetter.KwetterAccountService.messaging;

import com.google.gson.Gson;
import fontys.ict.kwetter.KwetterAccountService.events.CreateAccountEvent;
import fontys.ict.kwetter.KwetterAccountService.models.Account;
import fontys.ict.kwetter.KwetterAccountService.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class EventReceiver {

    @Autowired
    private AccountRepository accountRepository;

    private Logger log = LoggerFactory.getLogger(EventReceiver.class);

    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = "create-account-queue")
    public void receive(Message message) {
        System.out.println("received the event!");
        log.info("Received event in service authentication: {}", new String(message.getBody()));
        Gson gson = new Gson();
        String json = new String(message.getBody());
        CreateAccountEvent event = gson.fromJson(json, CreateAccountEvent.class);
        Account acc = new Account();
        acc.setId(event.getId());
        acc.setName(event.getUsername());
        accountRepository.save(acc);
    }
}
