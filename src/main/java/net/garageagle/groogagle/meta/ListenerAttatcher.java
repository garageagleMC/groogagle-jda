package net.garageagle.groogagle.meta;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.garageagle.groogagle.services.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class ListenerAttatcher {

    private final ApplicationContext applicationContext;
    private final BotService botService;

    // This is another way of Autowiring in Spring Boot
    // Instead of doing so above the field, we instead autowire the constructor and spring boot will find
    // All the required arguments for it and injecting the proper classes
    @Autowired
    public ListenerAttatcher(final ApplicationContext applicationContext, final BotService botService) {
        this.applicationContext = applicationContext;
        this.botService = botService;

        addListenersToJDA();
    }

    private void addListenersToJDA() {
        // Application context is what holds all the beans (components, services, etc.) in spring boot
        // Using it we will find all classes that extend ListenerAdapter and initialize them
        final Map<String, ListenerAdapter> listeners = applicationContext.getBeansOfType(ListenerAdapter.class);

        // Then we attach the listeners to JDA that we autowired from services.BotService
        for (ListenerAdapter listener : listeners.values()) {
            log.trace("addListenersToJDA() Listener={}", listener);
            botService.getJDA().addEventListener(listener);
        }
    }
}
