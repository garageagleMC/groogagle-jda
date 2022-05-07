package net.garageagle.groogagle.listeners;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.garageagle.groogagle.config.DiscordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QuizListener extends ListenerAdapter {

    @Autowired
    private DiscordConfig discordConfig;

    @Autowired
    private JDA jda;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if ( !event.getChannelType().isGuild() ) return;
        if ( !event.getGuild().getId().equals(discordConfig.getPtGuild()) ) return;
        if ( !event.getChannel().getName().startsWith("question-") ) return;

        String questionNumber = event.getChannel().getName().split("-")[1];
        jda.getTextChannelById(discordConfig.getPtQuizLogs()).sendMessageFormat(
                "Q%s **||** %s:  `%s`",
                questionNumber,
                event.getAuthor().getAsMention(),
                event.getMessage().getContentRaw()
        ).queue();
        event.getMessage().delete().queue();
    }
}
