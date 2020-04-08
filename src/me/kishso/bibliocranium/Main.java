package me.kishso.bibliocranium;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

        @Override
        public void onEnable() { //When Plugin is Enabled
            this.getServer().getPluginManager().registerEvents(new Event(), this);
        }



        @Override
        public void onDisable() { //When Plugin is Disabled
        }

}

