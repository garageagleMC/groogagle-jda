package net.garageagle.groogagle.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.awt.*;

public class Helpers {
    public static ReplyCallbackAction ConfirmAction(IReplyCallback callback, String msg, String confirmText, String denyText) {
        MessageEmbed embed = new EmbedBuilder()
                .setColor(Color.red)
                .setDescription(msg)
                .build();

        return callback.replyEmbeds(embed)
                .addActionRow(
                    Button.success("confirm-"+callback.getId(), confirmText),
                    Button.danger("deny-"+callback.getId(), denyText)
                );
    }
    public static ReplyCallbackAction NoPermission(IReplyCallback callback) {
        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Sorry!")
                .setColor(Color.red)
                .setDescription("You don't have permission to do that!")
                .build();
        return callback.replyEmbeds(embed)
                .setEphemeral(true);
    }
}
