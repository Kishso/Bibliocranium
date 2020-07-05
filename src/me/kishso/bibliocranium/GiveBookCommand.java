package me.kishso.bibliocranium;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class GiveBookCommand {

    public GiveBookCommand(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta meta = (BookMeta)book.getItemMeta();
            meta.setPages(new String[54]);
            meta.setAuthor("Bibliocranium Plugin");
            if(strings.length > 0 && strings[0] != null){
                meta.setTitle(strings[0]);
            }else{
                meta.setTitle("Bibliocranium Book");
            }
            book.setItemMeta(meta);
            player.getInventory().addItem(book);
        }
    }
}
