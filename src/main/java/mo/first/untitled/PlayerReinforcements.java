package mo.first.untitled;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerReinforcements {

    private Player playerName;
    private ArrayList<ReinforcedBlocks> playerReinforcedBlocks = new ArrayList<ReinforcedBlocks>();
    private boolean reinforcementMode;
    public static HashMap<UUID, PlayerReinforcements> playerReinforcementsHashMap= new HashMap<>();

    public PlayerReinforcements(Player playerName){
        this.playerName = playerName;
    }

    public void changeReinforcementMode(Player player){
        this.playerName = player;

        if (this.isReinforcementMode()){
            playerName.sendMessage(ChatColor.RED + "Reinforcement mode has been turned off");
            this.setReinforcementMode(false);
        } else {
            playerName.sendMessage(ChatColor.AQUA + "Reinforcement mode has been turned on");
            this.setReinforcementMode(true);
        }
    }

///// Getters and setters
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
