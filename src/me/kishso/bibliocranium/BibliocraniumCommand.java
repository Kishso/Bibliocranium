package me.kishso.bibliocranium;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class BibliocraniumCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch (strings[0]){
            case "help":
                new HelpCommand(commandSender,Arrays.copyOfRange(strings,1,strings.length));
                break;
            case "getbook":
                new GiveBookCommand(commandSender, Arrays.copyOfRange(strings,1,strings.length));
                break;
            case "addhead":
                new AddHeadCommand(commandSender, Arrays.copyOfRange(strings,1,strings.length));
                break;
            case "removehead":
                new RemoveHeadCommand(commandSender, Arrays.copyOfRange(strings,1,strings.length));
                break;
            default:
                return false;
        }
        return true;
    }
}
