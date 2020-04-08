package me.kishso.bibliocranium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Event implements Listener{

        //CustomSkull skull = new CustomSkull();
        Main plugin = Main.getPlugin(Main.class);

        @EventHandler
        public void playerDeath(PlayerDeathEvent event){
            Player player = event.getEntity();
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwningPlayer(player);
            List<String> lore = new ArrayList<String>();
            lore.add(event.getDeathMessage());
            skullMeta.setLore(lore);
            skull.setItemMeta(skullMeta);
            event.getDrops().add(skull);
        }

        @EventHandler
        public void bookSigned(PlayerEditBookEvent event) {
            BookMeta bookMeta = event.getNewBookMeta();
            if(bookMeta.getTitle().contains("BC") && event.isSigning()) {
                event.setNewBookMeta(CustomBook.sign(bookMeta));
            }
        }

        @EventHandler
        public void bookOpened(PlayerInteractEvent event) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            if(item.getType().equals(Material.WRITTEN_BOOK) && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
                BookMeta bookMeta = (BookMeta)item.getItemMeta();
                if(bookMeta.getTitle().contains("BC")) {
                    event.setCancelled(true);
                    event.getPlayer().openInventory(CustomBook.open(bookMeta));
                }
            }
        }

        @EventHandler
        public void lecturnOpened(InventoryOpenEvent event) {
            if(event.getInventory().getType().equals(InventoryType.LECTERN)) {
                ItemStack item = event.getInventory().getItem(0);
                if(item.getType().equals(Material.WRITTEN_BOOK)) {
                    BookMeta bookMeta = (BookMeta)item.getItemMeta();
                    if(bookMeta.getTitle().contains("BC")) {
                        event.setCancelled(true);
                        event.getPlayer().openInventory(CustomBook.open(bookMeta));
                    }
                }
            }
        }

        /*@EventHandler
        public void inventoryClick(InventoryClickEvent event) {
            if(event.getView().getTitle().contains("Display Only")) {
                event.setCancelled(true);
                return;
            }
            if(event.getView().getTitle().contains("HeadBooks")) {
                if(event.getView().getTopInventory().equals(event.getClickedInventory())) {
                    if(event.getWhoClicked().hasPermission("hb.useBooks")) {
                        ItemStack item = event.getCurrentItem();
                        event.setCancelled(true);
                        if(event.isRightClick()) {
                            item.setAmount(16);
                        }
                        if(event.isShiftClick()) {
                            item.setAmount(64);
                        }
                        event.getWhoClicked().getInventory().addItem(item);
                        item.setAmount(1);
                    }else {
                        event.setCancelled(true);
                        event.getWhoClicked().sendMessage(ChatColor.RED+"You do not have permission");
                    }
                }
            }
        }

        @EventHandler
        public void blockPlace (BlockPlaceEvent event) {
        }

        @EventHandler
        public void blockBreak (BlockBreakEvent event) {
            if(event.getBlock().getType().equals(Material.PLAYER_HEAD)) {
                Block block = event.getBlock();
                Skull meta = (Skull)block.getState();
                String uuid = meta.getOwningPlayer().getUniqueId().toString();
                if(headMap.containsKey(uuid)) {
                    Object[] drops = event.getBlock().getDrops().toArray();
                    ItemStack drop = (ItemStack)drops[0];
                    ItemMeta dropMeta = drop.getItemMeta();
                    dropMeta.setDisplayName(ChatColor.BOLD+headMap.get(uuid));
                    drop.setItemMeta(dropMeta);
                    event.setDropItems(false);
                    if(event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
                        event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), drop);
                    }
                }
            }
        }*/
}