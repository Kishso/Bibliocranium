package me.kishso.bibliocranium;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class AddHeadCommand{

    CustomSkull skull = null;

    public AddHeadCommand(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof Player){
            Player player = (Player)commandSender;
            ItemStack hand = player.getInventory().getItemInMainHand();
            if(hand.getItemMeta() instanceof BookMeta){
                BookMeta meta = (BookMeta) hand.getItemMeta();
                if(meta.getAuthor().equals("Bibliocranium Plugin")){
                    try{
                        Conversation conversation = convoFact.buildConversation(player);
                        conversation.addConversationAbandonedListener(conversationAbandonedEvent -> {
                            for (int page = 1; page <= meta.getPageCount(); page++) {
                                if (meta.getPage(page).isEmpty()) {
                                    meta.setPage(page, skull.toString());
                                    player.sendMessage(ChatColor.GREEN+"Success");
                                    break;
                                }
                            }
                            hand.setItemMeta(meta);
                            player.getInventory().setItemInMainHand(hand);
                        });
                        conversation.begin();
                    }catch(IndexOutOfBoundsException e){
                        //Do Nothing
                    }
                }else{
                    player.sendMessage("Must be Holding a Bibliocranium Book in your Main Hand.");
                }
            }
        }
    }

    //Conversation Factory
    ConversationFactory convoFact = new ConversationFactory(Main.getPlugin(Main.class))
            .withModality(true)
            .withPrefix(new AddHeadConversationPrefix())
            .withFirstPrompt(new AskNamePrompt())
            .withEscapeSequence("/quit")
            .withTimeout(20)
            .thatExcludesNonPlayersWithMessage("Go away evil console!");

    private class AskNamePrompt extends StringPrompt {

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.WHITE+"Enter a name for this item: ";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            conversationContext.setSessionData("name",s);
            return new AskTexturePrompt();
        }
    }

    private class AskTexturePrompt extends StringPrompt{

        @Override
        public String getPromptText(ConversationContext conversationContext) {
            return ChatColor.WHITE+"Copy and Paste the texture value for this item: ";
        }

        @Override
        public Prompt acceptInput(ConversationContext conversationContext, String s) {
            skull = new CustomSkull(conversationContext.getSessionData("name")+","+s);
            return Prompt.END_OF_CONVERSATION;
        }
    }

    private class AddHeadConversationPrefix implements ConversationPrefix{
        @Override
        public String getPrefix(ConversationContext conversationContext) {
            return ChatColor.BLUE+"Add Head>>";
        }
    }


}
