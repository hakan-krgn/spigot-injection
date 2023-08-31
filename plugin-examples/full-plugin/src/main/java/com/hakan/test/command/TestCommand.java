package com.hakan.test.command;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.spinjection.command.annotations.Command;
import com.hakan.spinjection.command.annotations.CommandParam;
import com.hakan.spinjection.command.annotations.Executor;
import com.hakan.spinjection.command.annotations.Subcommand;
import com.hakan.test.service.TestService;
import org.bukkit.command.CommandSender;

@Command(
	name = "test",
	aliases = {"t"},
	description = "Test command",
	usage = "/test"
)
public class TestCommand {

	private final TestService service;

	@Autowired
	public TestCommand(TestService service) {
		this.service = service;
	}

	@Subcommand(
		permission = "test.command.delete",
		permissionMessage = "You don't have permission to use this command."
	)
	public void delete(@Executor CommandSender executor,
					   @CommandParam Long id) {
		this.service.delete(id);

		executor.sendMessage("Entity has been deleted with id (" + id + ")");
	}
}
