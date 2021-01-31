package fr.zencraft.futurixel.batc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import io.github.waterfallmc.waterfall.event.ProxyDefineCommandsEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.event.EventHandler;

public class Batc extends Plugin implements Listener {

	ConfigUtil config = new ConfigUtil(this);

	@Override
	public void onEnable() {

		System.out.println("[Batc] Plugin enabled");
		PluginManager pm = getProxy().getPluginManager();
		pm.registerListener(this, this);
		pm.registerCommand(this, new Commands("batc", this));
		try {
			config.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {

	}

	@EventHandler(priority = 127)
	public void onProxyDefineCommands(ProxyDefineCommandsEvent event) {
		if (event.getReceiver() instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) event.getReceiver();
			if (!p.hasPermission("batc.bypass")) {
				if(config.getMode() == BatcMode.BLACKLIST) {
					Map<String, Command> result = new HashMap<String, Command>();
					result.putAll(event.getCommands());
					for(Entry<String, Command> entry : event.getCommands().entrySet()) {
						if(config.getFilter().contains(entry.getKey())) {
							result.remove(entry.getKey());
						}
					}
					event.getCommands().clear();
					event.getCommands().putAll(result);
				}else if(config.getMode() == BatcMode.SHOWONLY) {
					event.getCommands().clear();
					for(String s : config.getFilter()) {
						event.getCommands().put(s, null);
					}
				}
			}
		}
	}

}
