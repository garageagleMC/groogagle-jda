package net.garageagle.groogagle.meta;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.garageagle.groogagle.config.DiscordConfig;
import net.garageagle.groogagle.model.SlashCommand;
import net.garageagle.groogagle.services.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class SlashCommandAttatcher {

    private final ApplicationContext applicationContext;
    private final BotService botService;
    private final DiscordConfig discordConfig;


    @Autowired
    public SlashCommandAttatcher(final ApplicationContext applicationContext, final BotService botService, final DiscordConfig discordConfig) throws InterruptedException {
        this.applicationContext = applicationContext;
        this.botService = botService;
        this.discordConfig = discordConfig;

        registerSlashCommands();
    }

    private void registerSlashCommands() throws InterruptedException {
        final Map<String, SlashCommand> commands = applicationContext.getBeansOfType(SlashCommand.class);

        // Then we attach the slash commands to JDA that we autowired from services.BotService
        for (SlashCommand command : commands.values()) {
            log.trace("registerSlashCommands() SlashCommand={}", command);
            log.info("Registering Slash Command \"{}\"", command.getName());

            CommandCreateAction upsertCommand;
            if ( !discordConfig.getDeveloperMode() ) {
                upsertCommand = botService.getJDA()
                        .upsertCommand(command.getName(), command.getDescription());
            } else {
                upsertCommand = botService.getJDA().getGuildById(discordConfig.getDeveloperGuild())
                        .upsertCommand(command.getName(), command.getDescription());
            }
            if ( command.getOptions() != null ) upsertCommand.addOptions(command.getOptions());
            if ( command.getSubcommandGroups() != null ) upsertCommand.addSubcommandGroups(command.getSubcommandGroups());
            if ( command.getSubcommands() != null ) upsertCommand.addSubcommands(command.getSubcommands());
            upsertCommand.queue();
        }
    }
}
