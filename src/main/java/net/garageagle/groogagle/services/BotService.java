package net.garageagle.groogagle.services;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.garageagle.groogagle.config.DiscordConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class BotService {

    private final DiscordConfig discordConfig;

    private final JDA jda;

    public BotService(final DiscordConfig discordConfig) throws LoginException, InterruptedException {
        GatewayIntent[] intents = {
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.GUILD_BANS,
        };
        jda = JDABuilder.createDefault(discordConfig.getToken(), Arrays.stream(intents).toList()).build();
        this.discordConfig = discordConfig;
        jda.awaitReady();
        setStatus();
    }

    @Scheduled(cron = "0 */6 * * * *")
    private void setStatus() {
        String status = discordConfig.getStatusMessages().get(new Random().nextInt(discordConfig.getStatusMessages().size()));

        if (status.startsWith("Listening to")) {
            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, status.replaceFirst("Listening to", "")));
        } else if (status.startsWith("Watching")) {
            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.WATCHING, status.replaceFirst("Watching", "")));
        } else if (status.startsWith("Playing")) {
            jda.getPresence().setActivity(Activity.of(Activity.ActivityType.PLAYING, status.replaceFirst("Playing", "")));
        }
    }

    // The bean annotation tells spring boot that JDA is now a bean
    // This means if any other class declares:
    // @Autowired
    // JDA jda;
    // Spring boot will call this method and give this JDA to that class
    // If this class hasn't initialized yet, it will be, logging in the bot, if it has been initialized then
    // Spring boot will use the existing BotService class and thus won't try logging in a second time
    //
    // Spring boot will automatically figure out the order in which to initialize classes so their dependencies
    // are met, this also means A can't @Autowire B if B already @Autowire's A, since spring can't initialize both
    // at the same time
    @Bean
    public JDA getJDA() {
        return jda;
    }
}
