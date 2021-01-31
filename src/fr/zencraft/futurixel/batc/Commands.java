package fr.zencraft.futurixel.batc;

import java.io.IOException;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Commands extends Command{
	
	private Batc main;

	public Commands(String name, Batc main) {
		super(name);
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender.hasPermission("batc.admin")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reload")) {
					try {
						main.config.loadConfig();
						sender.sendMessage(TextComponent.fromLegacyText("§aConfiguration rechargée avec succès."));
					} catch (IOException e) {
						if(sender instanceof ProxiedPlayer) {
							sender.sendMessage(TextComponent.fromLegacyText("§cErreur lors du rechargement, veuillez consulter la console."));
						}
						e.printStackTrace();
					}
					
				}else {
					sendHelp(sender);
				}
			}else {
				sendHelp(sender);
			}
		}else {
			sender.sendMessage(TextComponent.fromLegacyText("§cVous n'êtes pas autorisé à utiliser ceci ..."));
		}
	}
	
	private void sendHelp(CommandSender sender) {
		sender.sendMessage(TextComponent.fromLegacyText("§7[§cBATC§7] Bungee Anti Tab Complete"));
		sender.sendMessage(TextComponent.fromLegacyText("§7>> §e/batc reload §9- §7permet de recharger la configuration"));
	}

}
