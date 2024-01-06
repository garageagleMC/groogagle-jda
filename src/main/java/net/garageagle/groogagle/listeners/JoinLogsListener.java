package net.garageagle.groogagle.listeners;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.garageagle.groogagle.config.DiscordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JoinLogsListener extends ListenerAdapter {

    @Autowired
    private DiscordConfig discordConfig;

    @Autowired
    private JDA jda;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if ( !event.getGuild().getId().equals(discordConfig.getPtGuild()) ) return;

        TextChannel join_channel = jda.getTextChannelById(discordConfig.getPtJoinLogs());
        TextChannel welcome_channel = jda.getTextChannelById(discordConfig.getPtWelcomeChannel());
        join_channel.sendMessageFormat(
                "%s, welcome to the Purple Team Discord Server! Be sure to read %s! Members in server: **%d**",
                event.getMember().getAsMention(),
                welcome_channel.getAsMention(),
                event.getGuild().getMemberCount()
        ).queue();

        join_channel.getManager()
                .setTopic("Members in server: %d".formatted(event.getGuild().getMemberCount()))
                .queue();

        Role driverRole = event.getGuild().getRoleById(discordConfig.getPtDriverRole());
        Role questionRole = event.getGuild().getRoleById(discordConfig.getPtQuestionRole());
        event.getGuild().addRoleToMember(event.getMember(), driverRole).queue();
        event.getGuild().addRoleToMember(event.getMember(), questionRole).queue();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        if ( !event.getGuild().getId().equals(discordConfig.getPtGuild()) ) return;

        TextChannel join_channel = jda.getTextChannelById(discordConfig.getPtJoinLogs());
        join_channel.sendMessageFormat(
                "**%s** left the Purple Team Discord Server. Members in server: **%s**",
                event.getUser().getAsTag(),
                event.getGuild().getMemberCount()
        ).queue();

        join_channel.getManager()
                .setTopic("Members in server: %d".formatted(event.getGuild().getMemberCount()))
                .queue();
    }

    @Override
    public void onGuildBan(GuildBanEvent event) {
        if ( !event.getGuild().getId().equals(discordConfig.getPtGuild()) ) return;

        TextChannel join_channel = jda.getTextChannelById(discordConfig.getPtJoinLogs());
        join_channel.sendMessageFormat(
                "**%s** was **BANNED** from the Purple Team Discord Server! Members in server: **%s**",
                event.getUser().getAsTag(),
                event.getGuild().getMemberCount()
        ).queue();

        join_channel.getManager()
                .setTopic("Members in server: %d".formatted(event.getGuild().getMemberCount()))
                .queue();
    }
}
