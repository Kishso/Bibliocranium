package me.kishso.bibliocranium;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class CustomSkull extends ItemStack{

    private String displayName;
    private String textureValue; //In base64
    private UUID uuid;

    //Manual Constructor
    public CustomSkull(String name, String texture){
        displayName = name;
        textureValue = texture;
        uuid = UUID.randomUUID();
    }

    //Automatic Constructor
    public CustomSkull(String line){
        try {
            String[] data = line.split(",");
            displayName = data[0];
            textureValue = data[1];
            if(data.length > 2){//Reading with a UUID
                uuid = UUID.fromString(data[2]);
            }else{//Reading without a UUID
                uuid = UUID.randomUUID();
            }
        }catch(Exception e){
            System.out.println("Formatted Incorrectly");
        }
    }


    //Skull Get Method (Slightly modified from Stef on Spigot Forums. God Bless this man)
    public ItemStack getSkull() {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        //Texture Value is Blank
        if (textureValue == null || textureValue.isEmpty())
            return skull;
        //Creates Skull Meta and Assigns Display Name
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setDisplayName(ChatColor.BOLD+displayName);
        //Create A Fake Profile
        GameProfile profile = new GameProfile(uuid, null);
        profile.getProperties().put("textures", new Property("textures", new String(textureValue)));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        //Set Meta and Return Skull
        skull.setItemMeta(skullMeta);
        return skull;
    }

    public String toString(){
        return displayName+','+textureValue+','+uuid.toString();
    }
}