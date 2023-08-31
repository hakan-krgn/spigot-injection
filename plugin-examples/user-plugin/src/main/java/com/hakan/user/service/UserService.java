package com.hakan.user.service;

import com.hakan.basicdi.annotations.Autowired;
import com.hakan.basicdi.annotations.PostConstruct;
import com.hakan.basicdi.annotations.Service;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

	private final Map<UUID, Player> players;

	@Autowired
	public UserService() {
		this.players = new HashMap<>();
	}

	@PostConstruct
	public void init() {
		Bukkit.getOnlinePlayers().forEach(this::add);
	}


	public Map<UUID, Player> getPlayers() {
		return this.players;
	}

	public Player getByUid(UUID uuid) {
		return this.players.get(uuid);
	}

	public void add(Player player) {
		this.players.put(player.getUniqueId(), player);
	}

	public void remove(Player player) {
		this.players.remove(player.getUniqueId());
	}

	public void remove(UUID uuid) {
		this.players.remove(uuid);
	}

	public void clear() {
		this.players.clear();
	}
}
