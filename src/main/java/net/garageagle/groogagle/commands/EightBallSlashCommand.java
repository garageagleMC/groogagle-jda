package net.garageagle.groogagle.commands;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.garageagle.groogagle.model.SlashCommand;
import net.garageagle.groogagle.util.Helpers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class EightBallSlashCommand extends SlashCommand {

    List<String> responses = List.of(
            "It is certain.",
            "It is decidedly so.",
            "Without a doubt.",
            "Yes definitely.",
            "You may rely on it.",
            "As I see it, yes.",
            "Most likely.",
            "Outlook good.",
            "Yes.",
            "Signs point to yes.",
            "Reply hazy, try again.",
            "Ask again later.",
            "Better not tell you now.",
            "Cannot predict now.",
            "Concentrate and ask again.",
            "Don't count on it.",
            "My reply is no.",
            "My sources say no.",
            "Outlook not so good.",
            "Very doubtful."
            );

    public EightBallSlashCommand() {
        setName("8ball");
        setDescription("Ask the Magic 8-Ball a question!");
        setOptions(List.of(new OptionData(
                OptionType.STRING,
                "question",
                "The question for the Magic 8 Ball.",
                true
                )
        ));
    }

    @Override
    public void executeCommand(SlashCommandInteractionEvent event) {
        String question = event.getOption("question", "Am I dumb?", OptionMapping::getAsString);

        event.replyFormat("**Q:** %s\n**A:** %s",
                question,
                responses.get(new Random().nextInt(responses.size()))
        ).queue();
    }
}
