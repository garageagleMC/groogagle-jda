package net.garageagle.groogagle.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
// Tells Spring we want to use values after the "discord" key in application.yaml
@ConfigurationProperties(prefix = "discord")
@Getter
@Setter
public class DiscordConfig {

    String token;
    String defaultPrefix;
    Boolean developerMode;
    String developerGuild;
    List<String> statusMessages;

    String ptGuild;
    String ptQuizLogs;
    String ptJoinLogs;
    String ptWelcomeChannel;
    String ptDriverRole;
    String ptQuestionRole;
}
