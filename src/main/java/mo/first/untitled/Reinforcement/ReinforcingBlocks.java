package mo.first.untitled.Reinforcement;

import mo.first.untitled.PlayerHandling.GroupReinforcements;
import mo.first.untitled.PlayerHandling.PlayerReinforcements;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReinforcingBlocks implements Listener {

    private static HashMap<Location, ReinforcedBlocks> reinforcedBlocksMap = new HashMap<>();
    public static final int REQUIRED_HITS_DIAMOND = 1000;
    public static final int REQUIRED_HITS_IRON = 100;

    @EventHandler
    public void blockReinforcing (PlayerInteractEvent e) {

        Player player = e.getPlayer();
        UUID playerID = player.getUniqueId();
        PlayerReinforcements playerReinforcements;
        GroupReinforcements groupReinforcements;

        // Checks if the player is currently within the playerReinforcementHashMap, if he is not, a new instance will be created.
        if (PlayerReinforcements.playerReinforcementsHashMap.containsKey(player.getUniqueId())) {
            playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(playerID);
        } else {
            playerReinforcements = new PlayerReinforcements(e.getPlayer());
            PlayerReinforcements.playerReinforcementsHashMap.put(playerID, playerReinforcements);
        }
        boolean singleReinforcement = playerReinforcements.isReinforcementMode();
        boolean groupReinforcment = playerReinforcements.isGroupReinforcementMode();

        groupReinforcements = GroupReinforcements.getPublicGroupReinforcement().getOrDefault(playerID, null);

        if (groupReinforcment) {
            groupReinforcement(player, groupReinforcements, e);
        }


        if (singleReinforcement) {
            singleReinforcement(player, playerReinforcements, e);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Player player = e.getPlayer();
        UUID playerID = player.getUniqueId();
        PlayerReinforcements playerReinforcements;
        GroupReinforcements groupReinforcements;

        // Checks if the player is currently within the playerReinforcementHashMap, if he is not, a new instance will be created.
        if (PlayerReinforcements.playerReinforcementsHashMap.containsKey(player.getUniqueId())){
            playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(playerID);
        } else {
            playerReinforcements = new PlayerReinforcements(e.getPlayer());
            PlayerReinforcements.playerReinforcementsHashMap.put(playerID, playerReinforcements);
        }

        boolean bypassReinforcement = playerReinforcements.isReinforcementMode();

        groupReinforcements = GroupReinforcements.getPublicGroupReinforcement().getOrDefault(playerID, null);

        // Checks if player is in reinforcementMode and the block is in his Array, if true the block will break regardless of reinforcement

        // Grabs the location of the BlockBreakEvent and passes it to blockLocation as a Location object
        Location blockLocation = e.getBlock().getLocation();


        // Checks if the block was within the reinforcedBlockMap HashMap, else pass.
        if (reinforcedBlocksMap.containsKey(blockLocation)) {
            ReinforcedBlocks reinforcedBlocks = reinforcedBlocksMap.get(blockLocation);

            int reinforcementLevel = reinforcedBlocks.getReinforcementLevel();

            reinforcedBlocks.setRequiredHits(reinforcedBlocks.getRequiredHits() + 1);

            // If the player is bypassing reinforcements and contains the block in their personal array, they can break the block without reaching the reinforcement level
            if (reinforcedBlocks.getRequiredHits() > reinforcementLevel || (bypassReinforcement && playerReinforcements.getPlayerReinforcedBlocks().contains(reinforcedBlocks) || groupReinforcements.getGroupReinforcedBlocks().contains(reinforcedBlocks))) {
                // If the hits required is greater than the reinforcementLevel, the block is removed from the HashMap and the block is destroyed
                reinforcedBlocksMap.remove(blockLocation);

                List<ReinforcedBlocks> playerBlocks = playerReinforcements.getPlayerReinforcedBlocks();
                List<ReinforcedBlocks> groupBlocks = groupReinforcements.getGroupReinforcedBlocks();

                for (int i = 0; i < playerBlocks.size(); i++) {
                    if (playerBlocks.get(i).getLocation().equals(blockLocation)) {
                        playerBlocks.remove(i);
                        break;
                    }
                }
                for (int i = 0; i < groupBlocks.size(); i++) {
                    if (groupBlocks.get(i).getLocation().equals(blockLocation)) {
                        groupBlocks.remove(i);
                        break;
                    }
                }

                playerReinforcements.getPlayerName().sendMessage("You have broken this block!");
            } else {
                // If the required hits are not reached, the event is cancelled.
                e.setCancelled(true);
                playerReinforcements.getPlayerName().sendMessage("You have hit this block " + reinforcedBlocks.getRequiredHits() + " times");
            }
        }
    }

    // Command should only be available to admins
    public void removeAllReinforcements(Player player){

        // If the HashMap is empty, the player is told there is no reinforced blocks to remove.
        if (reinforcedBlocksMap.isEmpty()){
            player.sendMessage(ChatColor.AQUA + "There is no reinforced blocks");
        } else {
            for (Map.Entry<Location, ReinforcedBlocks> entry : reinforcedBlocksMap.entrySet()){
                Location location = entry.getKey();
                ReinforcedBlocks reinforcedBlocks = entry.getValue();

                String locationStr = locationToString(location);
                String reinforcedBlocksStr = reinforcedBlocksToString(reinforcedBlocks);

                player.sendMessage("Location: " + locationStr + ", Reinforced Block: " + reinforcedBlocksStr);

            }
            reinforcedBlocksMap.clear();
            player.sendMessage(ChatColor.RED + "All reinforcements removed");
        }
    }

    private String locationToString(Location location){
        return "X: " + location.getX() + ", y: " + location.getY() + ", z:" + location.getZ();
    }

    private String reinforcedBlocksToString(ReinforcedBlocks reinforcedBlocks) {
        return "Required Hits: " + reinforcedBlocks.getRequiredHits();
    }

    private void ironReinforcement(ItemStack itemInHand, Player player, Block blockClicked, boolean group) {
        player.sendMessage("You reinforced this block at " + blockClicked.getLocation());
        ReinforcedBlocks reinforcedBlocks = new ReinforcedBlocks(blockClicked.getLocation(), REQUIRED_HITS_IRON);
        reinforcedBlocksMap.put(blockClicked.getLocation(), reinforcedBlocks);

        if (group) {
            GroupReinforcements groupReinforcements = GroupReinforcements.publicGroupReinforcement.get(player.getUniqueId());
            groupReinforcements.getGroupReinforcedBlocks().add(reinforcedBlocks);
        } else {
            PlayerReinforcements playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(player.getUniqueId());
            playerReinforcements.getPlayerReinforcedBlocks().add(reinforcedBlocks);
        }

        if (itemInHand.getAmount() > 0) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }

    private void diamondReinforcement(ItemStack itemInHand, Player player, Block blockClicked, boolean group) {
        player.sendMessage("You reinforced this block at " + blockClicked.getLocation());
        ReinforcedBlocks reinforcedBlocks = new ReinforcedBlocks(blockClicked.getLocation(), REQUIRED_HITS_DIAMOND);
        reinforcedBlocksMap.put(blockClicked.getLocation(), reinforcedBlocks);

        if (group) {
            GroupReinforcements groupReinforcements = GroupReinforcements.publicGroupReinforcement.get(player.getUniqueId());
            groupReinforcements.getGroupReinforcedBlocks().add(reinforcedBlocks);
        } else {
            PlayerReinforcements playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(player.getUniqueId());
            playerReinforcements.getPlayerReinforcedBlocks().add(reinforcedBlocks);
        }


        if (itemInHand.getAmount() > 0) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }
    }

    private void groupReinforcement(Player player, GroupReinforcements groupReinforcements, PlayerInteractEvent e) {

        boolean group = true;

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block blockClicked = e.getClickedBlock();
            ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();

            // Performs a check to see if a block is in the HashMap and already reinforced
            if (reinforcedBlocksMap.containsKey(blockClicked.getLocation())) {
                player.sendMessage("This block is already reinforced");
                return;
            }

            // Checking if there is an item in hand and the value is not null
            if (itemInHand != null && itemInHand.getType() != null) {

                // Passes itemInHand to the itemType variable to run checks with that.
                Material itemType = itemInHand.getType();

                // If the item is diamond or an iron ingot, the block is added to the HashMap as a new reinforcedBlock location
                // The itemInHand is then - 1 from the stack or replaced with air if there is 1 item left.
                if (itemType == Material.DIAMOND) {
                    diamondReinforcement(itemInHand, player, blockClicked, group);
                }
                if (itemType == Material.IRON_INGOT) {
                    ironReinforcement(itemInHand, player, blockClicked, group);
                }
            }
        }
    }

    private void singleReinforcement(Player player, PlayerReinforcements playerReinforcements, PlayerInteractEvent e) {

        boolean group = false;

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block blockClicked = e.getClickedBlock();
            ItemStack itemInHand = e.getPlayer().getInventory().getItemInMainHand();

            // Performs a check to see if a block is in the HashMap and already reinforced
            if (reinforcedBlocksMap.containsKey(blockClicked.getLocation())) {
                player.sendMessage("This block is already reinforced");
                return;
            }

            // Checking if there is an item in hand and the value is not null
            if (itemInHand != null && itemInHand.getType() != null) {

                // Passes itemInHand to the itemType variable to run checks with that.
                Material itemType = itemInHand.getType();

                // If the item is diamond or an iron ingot, the block is added to the HashMap as a new reinforcedBlock location
                // The itemInHand is then - 1 from the stack or replaced with air if there is 1 item left.
                if (itemType == Material.DIAMOND) {
                    diamondReinforcement(itemInHand, player, blockClicked, group);
                }
                if (itemType == Material.IRON_INGOT) {
                    ironReinforcement(itemInHand, player, blockClicked, group);
                }
            }
        }
    }
}
