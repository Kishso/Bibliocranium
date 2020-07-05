package me.kishso.bibliocranium;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

        @Override
        public void onEnable() { //When Plugin is Enabled
            this.getServer().getPluginManager().registerEvents(new BibliocraniumEvents(), this);
            //Register Commands
                this.getCommand("bibliocranium").setExecutor(new BibliocraniumCommand());
                this.getCommand("bibliocranium").setTabCompleter(new BibliocraniumTabCompleter());
        }



        @Override
        public void onDisable() { //When Plugin is Disabled
        }

}

