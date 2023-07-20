package mo.first.untitled.Commands;

import mo.first.untitled.PlayerHandling.GroupReinforcements;
import mo.first.untitled.PlayerHandling.PlayerReinforcements;
import mo.first.untitled.Reinforcement.ReinforcingBlocks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;
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
            if (command.getName().equalsIgnoreCase("rm")) {
                UUID playerID = p.getUniqueId();

                PlayerReinforcements playerReinforcements;
                if (PlayerReinforcements.playerReinforcementsHashMap.containsKey(playerID)) {
                    playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(playerID);
                } else {
                    playerReinforcements = new PlayerReinforcements(p);
                    PlayerReinforcements.playerReinforcementsHashMap.put(playerID, playerReinforcements);
                }
                GroupReinforcements groupReinforcements;
                groupReinforcements = GroupReinforcements.getPublicGroupReinforcement().getOrDefault(playerID, null);


                playerReinforcements.changeReinforcementMode(p, groupReinforcements);
                return true;
            }
            if (command.getName().equalsIgnoreCase("bunker")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "You need to provide arguments for bunker");
                    return false;
                }

                String subcommand = args[0];

                if (subcommand.equalsIgnoreCase("create")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "You need to specify a name for the group");
                        return false;
                    }

                    String groupName = args[1];
                    GroupReinforcements group = new GroupReinforcements(p, groupName);
                    p.sendMessage(ChatColor.AQUA + "New group " + groupName + "created");
                    return true;

                }
            }
        }
        return false;
    }
}
