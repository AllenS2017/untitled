package mo.first.untitled.PlayerHandling;

import mo.first.untitled.Reinforcement.ReinforcedBlocks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerReinforcements {

    private Player playerName;
    private ArrayList<ReinforcedBlocks> playerReinforcedBlocks = new ArrayList<ReinforcedBlocks>();
    private boolean reinforcementMode;
    public static HashMap<UUID, PlayerReinforcements> playerReinforcementsHashMap = new HashMap<>();
    private boolean groupReinforcementMode;
    private GroupReinforcements groupReinforcements;
    private boolean reinforcementPlaceMode;

    public PlayerReinforcements(Player playerName) {
        this.playerName = playerName;
        this.groupReinforcements = null;
        this.reinforcementMode = false;
        this.groupReinforcementMode = false;
        this.reinforcementPlaceMode = false;
    }

    public void changeReinforcementMode(Player player) {
        this.playerName = player;

        if (this.isReinforcementMode()) {
            playerName.sendMessage(ChatColor.RED + "Reinforcement mode has been turned off");
            this.setReinforcementMode(false);
        } else if (!this.isReinforcementMode() || this.isGroupReinforcementMode()) {
            playerName.sendMessage(ChatColor.AQUA + "Group reinforcement mode for " + this.groupReinforcements.getNameOfGroup() + " has been turned off for individual reinforcement");
            this.setReinforcementMode(true);
            this.setGroupReinforcementMode(false);
        } else {
            playerName.sendMessage(ChatColor.AQUA + "Reinforcement mode has been turned on");
            this.setReinforcementMode(true);
        }
    }

    public void changeGroupReinforcementMode(Player player) {
        this.playerName = player;

        if (this.isGroupReinforcementMode()) {
            playerName.sendMessage("You have now turned off reinforcement mode for  " + this.groupReinforcements.getNameOfGroup());
            this.setGroupReinforcementMode(false);
        } else if (!this.isGroupReinforcementMode() || this.isReinforcementMode()) {
            playerName.sendMessage("You have switched to Group reinforcement mode to " + this.groupReinforcements.getNameOfGroup());
            this.setGroupReinforcementMode(true);
            this.setReinforcementMode(false);
        } else {
            playerName.sendMessage("You have now turned on reinforcement mode for " + this.groupReinforcements.getNameOfGroup());
            this.setGroupReinforcementMode(true);
        }
    }

    public void changePlaceMode(Player player, boolean forGroup) {
        // forGroup is true when player wants to place blocks for a group
        // forGroup is false when player wants to place blocks for himself
        if (forGroup) {
            // Setting for group reinforcement mode
            this.setGroupReinforcementMode(true);
            this.setReinforcementMode(false);
            player.sendMessage(ChatColor.AQUA + "You have started group reinforcement place mode");
        } else {
            // Setting for individual reinforcement mode
            this.setGroupReinforcementMode(false);
            this.setReinforcementMode(true);
            player.sendMessage(ChatColor.AQUA + "You have started individual reinforcement place mode");
        }

        this.setReinforcementPlaceMode(true); // setting place mode on when either group or individual reinforcement mode is chosen
    }

    public void stopPlaceMode(Player player) {
        // turn off reinforcementPlaceMode
        this.setReinforcementPlaceMode(false);
        player.sendMessage(ChatColor.AQUA + "You have stopped the reinforcement place mode");
    }

    public void setGroupReinforcements(GroupReinforcements groupReinforcements) {
        if (!groupReinforcements.getGroupReinforcementsHashMap().containsKey(this.playerName.getUniqueId())) {
            playerName.sendMessage(ChatColor.RED + "You are not a part of this group");
            return;
        }

        this.groupReinforcements = groupReinforcements;
    }

    public GroupReinforcements getGroupReinforcements() {
        return groupReinforcements;
    }

    public boolean isReinforcementPlaceMode() {
        return reinforcementPlaceMode;
    }

    public void setReinforcementPlaceMode(boolean reinforcementPlaceMode) {
        this.reinforcementPlaceMode = reinforcementPlaceMode;
    }

    public boolean isGroupReinforcementMode() {
        return groupReinforcementMode;
    }

    public void setGroupReinforcementMode(boolean groupReinforcementMode) {
        this.groupReinforcementMode = groupReinforcementMode;
    }

    public static HashMap<UUID, PlayerReinforcements> getPlayerReinforcementsHashMap() {
        return playerReinforcementsHashMap;
    }

    public static void setPlayerReinforcementsHashMap(HashMap<UUID, PlayerReinforcements> playerReinforcementsHashMap) {
        PlayerReinforcements.playerReinforcementsHashMap = playerReinforcementsHashMap;
    }

    public boolean isReinforcementMode() {
        return reinforcementMode;
    }

    public void setReinforcementMode(boolean reinforcementMode) {
        this.reinforcementMode = reinforcementMode;
    }

    public Player getPlayerName() {
        return playerName;
    }

    public void setPlayerName(Player playerName) {
        this.playerName = playerName;
    }

    public ArrayList<ReinforcedBlocks> getPlayerReinforcedBlocks() {
        return playerReinforcedBlocks;
    }

    public void setPlayerReinforcedBlocks(ArrayList<ReinforcedBlocks> playerReinforcedBlocks) {
        this.playerReinforcedBlocks = playerReinforcedBlocks;
    }
}
