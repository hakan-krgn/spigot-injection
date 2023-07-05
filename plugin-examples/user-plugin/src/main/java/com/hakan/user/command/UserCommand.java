package com.hakan.user.command;

import com.hakan.injection.annotations.Autowired;
import com.hakan.spinjection.command.annotations.Command;
import com.hakan.spinjection.command.annotations.Executor;
import com.hakan.spinjection.command.annotations.Subcommand;
import com.hakan.user.service.UserService;
import org.bukkit.command.CommandSender;

@Command(
        name = "test",
        aliases = {"t"},
        description = "Test command",
        usage = "/test"
)
public class UserCommand {

    private final UserService service;

    @Autowired
    public UserCommand(UserService service) {
        this.service = service;
    }

    @Subcommand(
            permission = "test.command.clear",
            permissionMessage = "You don't have permission to use this command."
    )
    public void clear(@Executor CommandSender executor) {
        this.service.clear();

        executor.sendMessage("§aUsers cleared.");
    }
}
