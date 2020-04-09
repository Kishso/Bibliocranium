package me.kishso.bibliocranium;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class Event implements Listener{

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
            if(event.isSigning() && bookMeta.getTitle().contains("BC")) {
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
                if(item != null && item.getType().equals(Material.WRITTEN_BOOK)) {
                    BookMeta bookMeta = (BookMeta)item.getItemMeta();
                    if(bookMeta.getTitle().contains("BC")) {
                        event.setCancelled(true);
                        event.getPlayer().openInventory(CustomBook.open(bookMeta));
                    }
                }
            }
        }

        @EventHandler
        public void inventoryClick(InventoryClickEvent event) {
            if(event.getView().getTitle().contains("Bibliocranium") && event.getClickedInventory().equals(event.getView().getTopInventory())){
                event.setCancelled(true);
                if(event.getView().getTitle().contains("Display Only")){
                    event.getWhoClicked().sendMessage("Book is set to Display Only");
                }else{
                    if(event.getCursor().getType().equals(Material.PLAYER_HEAD)){
                        InventoryView invWin = event.getView();
                        ItemStack clickedItem = invWin.getTopInventory().getItem(event.getSlot());
                        if(clickedItem != null){
                            clickedItem = clickedItem.clone();
                            int amt = event.getCursor().getAmount();
                            clickedItem.setAmount(amt);
                            event.getCursor().setAmount(0);
                            event.getWhoClicked().getInventory().addItem(clickedItem);
                        }
                    }
                }
            }
        }



        @EventHandler
        public void placeHead(BlockPlaceEvent event){
            if(event.getBlock().getType().equals(Material.PLAYER_HEAD)){
                ItemStack head = event.getItemInHand();
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                String displayName = skullMeta.getDisplayName();
                BlockState blockState = event.getBlockPlaced().getState();
                blockState.setMetadata("DisplayName", new FixedMetadataValue(plugin,displayName));
            }
        }

        @EventHandler
        public void breakHead(BlockBreakEvent event){
            if(event.getBlock().getType().equals(Material.PLAYER_HEAD)){
                Block block = event.getBlock();
                BlockState blockState = block.getState();
                if(blockState.hasMetadata("DisplayName")){
                    event.setDropItems(false);
                    Object[] data = blockState.getMetadata("DisplayName").toArray();
                    FixedMetadataValue metadataValue = (FixedMetadataValue) data[0];
                    String displayName = metadataValue.asString();
                    Object[] drops = block.getDrops().toArray();
                    ItemStack drop = (ItemStack) drops[0];
                    ItemMeta dropMeta = drop.getItemMeta();
                    dropMeta.setDisplayName(displayName);
                    drop.setItemMeta(dropMeta);
                    block.getWorld().dropItem(block.getLocation(),drop);
                }
            }
        }

}
