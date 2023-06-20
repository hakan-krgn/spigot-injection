package com.hakan.test.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hakan.injection.command.annotations.Command;
import com.hakan.injection.command.annotations.Parameter;
import com.hakan.injection.annotations.Component;
import com.hakan.test.service.TestService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Singleton
@Component
public class TestCommand {

    private final TestService service;

    @Inject
    public TestCommand(TestService service) {
        this.service = service;
    }

    @Command(
            name = "test",
            usage = "/test",
            aliases = {"test2"},
            description = "test command"
    )
    public void execute(CommandSender executor,
                        @Parameter Player target,
                        @Parameter String message,
                        @Parameter int amount) {
        for (int i = 0; i < amount; i++) {
            this.service.sendMessage(target, message);
        }
    }
}
