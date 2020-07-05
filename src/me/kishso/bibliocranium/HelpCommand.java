package me.kishso.bibliocranium;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;

public class HelpCommand {

    public HelpCommand(CommandSender commandSender, String[] args){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta)book.getItemMeta();
        ArrayList<String> pages = new ArrayList<>();
        pages.add(
            "\n"+
            "\n"+
            "\n"+
            " Bibliocranium Plugin\n"+
            "    By Kishso\n"
        );
        pages.add(
            "Bibliocranium is a plugin"+
            "built to enable players more"+
            "creative freedom through player"+
            "heads, a player can create any "+
            "head they wish with just a "+
            "texture value of the head in mind."+
            "First a player needs a specific book"+
            "to access the plugin's features"+
            "with the getbook [title] command."
        );
        pages.add(
            "Each book can hold up to 54"+
            "heads in total. To add a head"+
            "just hold the book in your mainhand"+
            "and run the addhead command"+
            "you will then be prompted for"+
            "a name and the texture value."+
            "The head will be placed in the next"+
            "empty slot."
        );
        pages.add(
            "Next to remove a head, hold"+
            "the book and run the removehead"+
            "command. Then just click on all the"+
            "heads you wish to remove and close"+
            "the inventory."
        );
        meta.setPages(pages);
        book.setItemMeta(meta);
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            player.openBook(book);
        }

    }
}
