package fr.zencraft.futurixel.batc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigUtil {
	
	private Batc main;
	public ConfigUtil(Batc main) {this.main = main;}
	
	private static Configuration configuration = null;
	
	private BatcMode mode;
	private List<String> filter;
	
	public void loadConfig() throws IOException {
		
		if (!main.getDataFolder().exists()) {
            main.getDataFolder().mkdir();
		}

        File file = new File(main.getDataFolder(), "config.yml");
   
        if (!file.exists()) {
            try (InputStream in = main.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

		configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(main.getDataFolder(), "config.yml"));
		
		if(!isSet("mode")) {
			configuration.set("mode" , "BLACKLIST");
			setMode(BatcMode.BLACKLIST);
		}else {
			String modestring = configuration.getString("mode");
			if(getModeFromString(modestring)!=null) {
				setMode(getModeFromString(modestring));
			}else {
				System.out.println("§c[BATC] Erreur de configuration du mode ! Veuillez vérifier votre config.yml ! §eUtilisation du mode BLACKLIST par défaut");
				setMode(BatcMode.BLACKLIST);
			}
		}
		
		if(!isSet("commands")) {
			configuration.set("commands", Arrays.asList("ban","mute","kick"));
			setFilter(Arrays.asList("ban","mute","kick"));
		}else {
			setFilter(configuration.getStringList("commands"));
		}
	}
	
	private boolean isSet(String path) {
		Object obj = configuration.get(path);
		if (obj != null) {
			return true;
		}
		return false;
	}
	
	private BatcMode getModeFromString(String s) {
		switch(s) {
		case "BLACKLIST":
			return BatcMode.BLACKLIST;
		case "SHOWONLY":
			return BatcMode.SHOWONLY;
		}
		return null;
	}

	public List<String> getFilter() {
		return filter;
	}

	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

	public BatcMode getMode() {
		return mode;
	}

	public void setMode(BatcMode mode) {
		this.mode = mode;
	}

}
