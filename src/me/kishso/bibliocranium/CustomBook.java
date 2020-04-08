package me.kishso.bibliocranium;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.BookMeta;
import java.util.List;
import java.util.UUID;

public class CustomBook {

    public static BookMeta sign(BookMeta bookMeta){
        for(int i = 1; i <= bookMeta.getPageCount(); i++){
            //Adds a Unique UUID to each page
            bookMeta.setPage(i,(bookMeta.getPage(i)+","+ UUID.randomUUID().toString()).trim());
        }
        return bookMeta;
    }

    public static boolean isDisplayOnly(BookMeta bookMeta){
        List<String> lore = bookMeta.getLore();
        if(lore != null) {
            return lore.contains("Display Only");
        }else{
            return false;
        }
    }

    public static Inventory open(BookMeta bookMeta){
                Inventory inv = Bukkit.createInventory(null,54,"Bibliocranium");
                if(CustomBook.isDisplayOnly(bookMeta)){
                    inv = Bukkit.createInventory(null,54,"Bibliocranium : Display Only");
                }
                for(int i = 1; i <= bookMeta.getPageCount(); i++) {
                    CustomSkull skull = new CustomSkull(bookMeta.getPage(i));
                    inv.addItem(skull.getSkull());
                }
                return inv;
    }

}
