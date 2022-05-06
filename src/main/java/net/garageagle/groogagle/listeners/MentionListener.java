package net.garageagle.groogagle.listeners;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.garageagle.groogagle.config.DiscordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MentionListener extends ListenerAdapter {

    @Autowired
    private DiscordConfig discordConfig;

    @Autowired
    private JDA jda;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if ( event.getAuthor().isBot() ) return;

        if ( event.getMessage().isMentioned(jda.getSelfUser()) ) {
            event.getAuthor().openPrivateChannel()
                    .flatMap(channel ->
                            channel.sendMessage("The text command prefix is `" + discordConfig.getDefaultPrefix() + "`"))
                    .queue();
        }
    }
}
