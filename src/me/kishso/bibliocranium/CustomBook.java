package me.kishso.bibliocranium;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.BookMeta;
import java.util.List;

public class CustomBook {


    /**
     * Checks to see if a book's lore contains the String "Display Only"
     *
     * @param bookMeta BookMeta of the corresponding Book
     * @return true if the Book is set to Display Only, false otherwise.
     */
    public boolean isDisplayOnly(BookMeta bookMeta){
        List<String> lore = bookMeta.getLore();
        if(lore != null) {
            return lore.contains("Display Only");
        }else{
            return false;
        }
    }

    /**
     * Reads a book and returns an Inventory of all skulls
     * written inside.
     *
     * @param bookMeta The BookMeta of the book trying to be opened.
     * @return
     */
    public static Inventory open(BookMeta bookMeta){

        Inventory inv = Bukkit.createInventory(null,54,"Bibliocranium");

        for(int i = 1; i <= bookMeta.getPageCount(); i++) {
            if(!bookMeta.getPage(i).isEmpty()) {
                CustomSkull skull = new CustomSkull(bookMeta.getPage(i));
                inv.setItem(i-1,skull.getSkull());
            }
        }
        return inv;
    }

}
