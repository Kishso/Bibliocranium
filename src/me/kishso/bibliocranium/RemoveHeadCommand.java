package me.kishso.bibliocranium;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class RemoveHeadCommand{

    public RemoveHeadCommand(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            ItemStack hand = player.getInventory().getItemInMainHand();
            if(hand.getItemMeta() instanceof BookMeta){
                BookMeta meta = (BookMeta) hand.getItemMeta();
                if(meta.getAuthor().equals("Bibliocranium Plugin")){
                    Inventory inv = CustomBook.open(meta);
                    BibliocraniumEvents.openRemoveInvs.add(inv);
                    player.openInventory(inv);
                }
            }
            player.sendMessage("Must be holding a Bibliocranium Book in your main hand.");
        }
    }
}
