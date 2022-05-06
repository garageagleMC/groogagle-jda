package net.garageagle.groogagle.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.garageagle.groogagle.model.SlashCommand;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import java.awt.*;
import java.util.Random;

@Component
public class PingSlashCommand extends SlashCommand {

    public PingSlashCommand() {
        setName("ping");
        setDescription("Pings the bot.");
    }

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) {
        MessageEmbed pingingEmbed = new EmbedBuilder()
                .setColor(Color.cyan)
                .setDescription("Pinging...")
                .build();

        long time = System.currentTimeMillis();
        event.replyEmbeds(pingingEmbed)
                .queue(response -> {
                    jda.getRestPing().queue(restPing -> {
                        MessageEmbed pongEmbed = new EmbedBuilder()
                                .setColor(Color.cyan)
                                // 1% chance to get "Pang!" instead
                                .setTitle(new Random().nextFloat() < 0.01 ? "Pang!" : "Pong!")
                                .setDescription("Bot: %dms\nGateway: %dms\nREST API: %dms".formatted(
                                        System.currentTimeMillis() - time,
                                        jda.getGatewayPing(),
                                        restPing))
                                .build();
                        response.editOriginalEmbeds(pongEmbed).queue();
                    });
                });
    }
}
