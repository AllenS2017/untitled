package mo.first.untitled;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (sender instanceof Player){
            Player p = (Player) sender;

            if (command.getName().equalsIgnoreCase("test")){
                p.sendMessage(ChatColor.GOLD + "Wassup");
            }

            if(command.getName().equalsIgnoreCase("ClearReinforcements")){
                new ReinforcingBlocks().removeAllReinforcements(p);
            }
            if (command.getName().equalsIgnoreCase("rm")){
                UUID playerID = p.getUniqueId();

                PlayerReinforcements playerReinforcements;
                if (PlayerReinforcements.playerReinforcementsHashMap.containsKey(playerID)){
                    playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(playerID);
                } else {
                    playerReinforcements = new PlayerReinforcements(p);
                    PlayerReinforcements.playerReinforcementsHashMap.put(playerID, playerReinforcements);
                }
                playerReinforcements.changeReinforcementMode(p);
                return true;
            }
        }
        return false;
    }
}
