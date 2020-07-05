package me.kishso.bibliocranium;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BibliocraniumEvents implements Listener{

    Main plugin = Main.getPlugin(Main.class);
    static ArrayList<Inventory> openTradeInvs = new ArrayList<>();
    static ArrayList<Inventory> openRemoveInvs = new ArrayList<>();

    @EventHandler
    public void dropHeadOnDeath(PlayerDeathEvent event){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(event.getEntity());
        List<String> lore = new ArrayList<>();
        lore.add(event.getDeathMessage());
        meta.setLore(lore);
        head.setItemMeta(meta);
        event.getEntity().getLocation().getWorld().dropItem(event.getEntity().getLocation(),head);
    }

    @EventHandler
    public void bookOpened(PlayerInteractEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if(item.getItemMeta() instanceof BookMeta && (event.getAction().equals(Action.RIGHT_CLICK_AIR))){
            BookMeta bookMeta = (BookMeta)item.getItemMeta();
            if(bookMeta.getAuthor().equals("Bibliocranium Plugin")) {
                event.setCancelled(true);
                Inventory inv = CustomBook.open(bookMeta);
                openTradeInvs.add(inv);
                event.getPlayer().openInventory(inv);
            }
        }
    }

    @EventHandler
    public void lecturnOpened(InventoryOpenEvent event) {
        if(event.getInventory().getType().equals(InventoryType.LECTERN)) {
            ItemStack item = event.getInventory().getItem(0);
            if(item != null && item.getItemMeta() instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta)item.getItemMeta();
                if(bookMeta.getAuthor().equals("Bibliocranium Plugin")) {
                    event.setCancelled(true);
                    Inventory inv = CustomBook.open(bookMeta);
                    openTradeInvs.add(inv);
                    event.getPlayer().openInventory(inv);
                }
            }
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if(openTradeInvs.contains(event.getClickedInventory())){
            event.setCancelled(true);
            if(event.getView().getTitle().contains("Display Only")){
                event.getWhoClicked().sendMessage("Book is set to Display Only");
            }else{
                if(event.getCursor() != null && event.getCursor().getItemMeta() instanceof SkullMeta){
                    InventoryView invWin = event.getView();
                    ItemStack clickedItem = invWin.getTopInventory().getItem(event.getSlot());

                    clickedItem = clickedItem.clone();
                    int amt = event.getCursor().getAmount();
                    clickedItem.setAmount(amt);
                    event.getCursor().setAmount(0);
                    event.getWhoClicked().getInventory().addItem(clickedItem);
                }
            }
        }else if(openRemoveInvs.contains(event.getClickedInventory())){
            event.setCancelled(true);
            event.getClickedInventory().setItem(event.getRawSlot(),null);
            if(event.getWhoClicked() instanceof Player){
                Player player = (Player) event.getWhoClicked();
                ItemStack hand = player.getInventory().getItemInMainHand();
                if(hand.getItemMeta() instanceof BookMeta){
                    BookMeta meta = (BookMeta) hand.getItemMeta();
                    if(meta.getAuthor().equals("Bibliocranium Plugin")){
                        meta.setPage(event.getRawSlot()+1,"");
                    }
                    hand.setItemMeta(meta);
                }
                player.getInventory().setItemInMainHand(hand);
            }
        }
    }

    @EventHandler
    public void closeGUI(InventoryCloseEvent event){
        openTradeInvs.remove(event.getInventory());
        openRemoveInvs.remove(event.getInventory());
    }


    NamespacedKey nameKey = new NamespacedKey(plugin, "DisplayName");

    @EventHandler
    public void placeHead(BlockPlaceEvent event){
        if(event.getBlock().getType().equals(Material.PLAYER_HEAD)){
            ItemStack head = event.getItemInHand();
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
            String displayName = skullMeta.getDisplayName();

            TileState tileState = (TileState) event.getBlockPlaced().getState();
            PersistentDataContainer dataContainer = tileState.getPersistentDataContainer();
            dataContainer.set(nameKey,PersistentDataType.STRING, displayName);
            tileState.update();
        }
    }

    @EventHandler
    public void breakHead(BlockBreakEvent event){
        if(event.getBlock().getType().equals(Material.PLAYER_HEAD)){
            Block block = event.getBlock();
            BlockState blockState = block.getState();

            TileState tileState = (TileState) blockState;
            String displayName = tileState.getPersistentDataContainer().get(nameKey,PersistentDataType.STRING);

            event.setDropItems(false);
            Object[] drops = block.getDrops().toArray();
            ItemStack drop = (ItemStack) drops[0];
            ItemMeta dropMeta = drop.getItemMeta();
            dropMeta.setDisplayName(displayName);
            drop.setItemMeta(dropMeta);
            block.getWorld().dropItem(block.getLocation(),drop);

        }
    }

}
