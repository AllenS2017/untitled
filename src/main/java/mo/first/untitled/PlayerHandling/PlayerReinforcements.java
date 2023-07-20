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

    public PlayerReinforcements(Player playerName){
        this.playerName = playerName;
    }

    public void changeReinforcementMode(Player player, GroupReinforcements groupReinforcements) {
        this.playerName = player;

        if (this.isReinforcementMode()) {
            playerName.sendMessage(ChatColor.RED + "Reinforcement mode has been turned off");
            this.setReinforcementMode(false);
        } else if (!this.isReinforcementMode() || this.isGroupReinforcementMode()) {
            playerName.sendMessage(ChatColor.AQUA + "Group reinforcement mode for " + groupReinforcements.getNameOfGroup() + " has been turned off for individual reinforcement");
            this.setReinforcementMode(true);
            this.setGroupReinforcementMode(false);
        } else {
            playerName.sendMessage(ChatColor.AQUA + "Reinforcement mode has been turned on");
            this.setReinforcementMode(true);
        }
    }

    public void changeGroupReinforcementMode(Player player, GroupReinforcements groupReinforcements) {
        this.playerName = player;

        if (this.isGroupReinforcementMode()) {
            playerName.sendMessage("You have now turned off reinforcement mode for  " + groupReinforcements.getNameOfGroup());
            this.setGroupReinforcementMode(false);
        } else if (!this.isGroupReinforcementMode() || this.isReinforcementMode()) {
            playerName.sendMessage("You have switched to Group reinforcement mode to " + groupReinforcements.getNameOfGroup());
            this.setGroupReinforcementMode(true);
            this.setReinforcementMode(false);
        } else {
            playerName.sendMessage("You have now turned on reinforcement mode for " + groupReinforcements.getNameOfGroup());
            this.setGroupReinforcementMode(true);
        }
    }

///// Getters and setters

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
