package mo.first.untitled.PlayerHandling;

import mo.first.untitled.Reinforcement.ReinforcedBlocks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

    public PlayerReinforcements(Player playerName){
        this.playerName = playerName;
        this.groupReinforcements = null;
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

    public GroupReinforcements getGroupReinforcements() {
        return groupReinforcements;
    }

    public void setGroupReinforcements(GroupReinforcements groupReinforcements) {
        if (!groupReinforcements.getGroupReinforcementsHashMap().containsKey(this.playerName.getUniqueId())) {
            playerName.sendMessage(ChatColor.RED + "You are not a part of this group");
            return;
        }

        this.groupReinforcements = groupReinforcements;
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
