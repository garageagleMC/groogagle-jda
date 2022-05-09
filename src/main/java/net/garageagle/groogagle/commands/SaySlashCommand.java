package net.garageagle.groogagle.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.garageagle.groogagle.model.SlashCommand;
import net.garageagle.groogagle.util.Helpers;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class SaySlashCommand extends SlashCommand {

    List<String> allowedUsers = List.of("165195486746116096",
                                        "212344168364638214",
                                        "159714126128611328");

    public SaySlashCommand() {
        setName("say");
        setDescription("Makes the bot speak.");
        setOptions(List.of(new OptionData(
                OptionType.STRING,
                "message",
                "The message content for the bot to send.",
                true
                ), new OptionData(
                OptionType.CHANNEL,
                "channel",
                "The channel for the bot to send in.",
                false
                ).setChannelTypes(ChannelType.TEXT)
        ));
    }

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) {
        if ( !allowedUsers.contains(event.getUser().getId()) ) {
            Helpers.NoPermission(event).queue();
            return;
        }

        String message = event.getOption("message", "Hello!", OptionMapping::getAsString);
        TextChannel channel = event.getOption("channel", event.getTextChannel(), OptionMapping::getAsTextChannel);

        event.replyFormat("Sent message in %s!", channel.getAsMention())
                .setEphemeral(true)
                .queue();
        channel.sendMessage(message).queue();
    }
}
