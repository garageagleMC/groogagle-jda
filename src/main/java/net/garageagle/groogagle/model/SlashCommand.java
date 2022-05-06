package net.garageagle.groogagle.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Getter
@Setter
public abstract class SlashCommand extends ListenerAdapter {

    @Autowired
    protected JDA jda;

    String name;
    String description;

    List<OptionData> options;
    List<SubcommandGroupData> subcommandGroups;
    List<SubcommandData> subcommands;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if ( !event.getName().equals(name) ) return;
        log.info("{} ({}) used slash command: \"{}\"",
                event.getUser().getAsTag(),
                event.getUser().getId(),
                event.getCommandString()
                );
        executeCommand(event);
    }

    public abstract void executeCommand(SlashCommandInteractionEvent event);
}
