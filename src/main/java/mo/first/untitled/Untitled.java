package mo.first.untitled;

import org.bukkit.plugin.java.JavaPlugin;

public final class Untitled extends JavaPlugin {

    @Override
    public void onEnable() {

        // getServer().getPluginManager().registerEvents(new BlockLocation(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new ReinforcingBlocks(), this);
        getCommand("test").setExecutor(new PlayerCommands());
        getCommand("ClearReinforcements").setExecutor(new PlayerCommands());
        getCommand("rm").setExecutor(new PlayerCommands());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
