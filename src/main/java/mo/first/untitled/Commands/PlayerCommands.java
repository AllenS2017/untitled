package mo.first.untitled.Commands;

import mo.first.untitled.PlayerHandling.GroupReinforcements;
import mo.first.untitled.PlayerHandling.PlayerReinforcements;
import mo.first.untitled.Reinforcement.ReinforcingBlocks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.swing.*;
import java.util.UUID;

public class PlayerCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if (sender instanceof Player){
            Player p = (Player) sender;
            UUID playerID = p.getUniqueId();

            if (command.getName().equalsIgnoreCase("test")){
                p.sendMessage(ChatColor.GOLD + "Wassup");
            }

            if(command.getName().equalsIgnoreCase("ClearReinforcements")){
                new ReinforcingBlocks().removeAllReinforcements(p);
            }
            if (command.getName().equalsIgnoreCase("bunker")) {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.RED + "You need to provide arguments for bunker");
                    return false;
                }

                String subcommand = args[0];
                PlayerReinforcements playerReinforcements;
                if (PlayerReinforcements.playerReinforcementsHashMap.containsKey(playerID)) {
                    playerReinforcements = PlayerReinforcements.playerReinforcementsHashMap.get(playerID);
                } else {
                    playerReinforcements = new PlayerReinforcements(p);
                    PlayerReinforcements.playerReinforcementsHashMap.put(playerID, playerReinforcements);
                }

                if (subcommand.equalsIgnoreCase("rm")) {

                    playerReinforcements.changeReinforcementMode(p);
                    return true;
                }
                if (subcommand.equalsIgnoreCase("placemode")) {
                    if (args.length > 1) {
                        String groupName = args[1];

                        GroupReinforcements groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(groupName, null);
                        if (groupReinforcements == null) {
                            p.sendMessage(ChatColor.RED + "This group name" + groupName + "does not exist, please check your spelling");
                            return false;
                        }
                        playerReinforcements.changePlaceMode(p, true, p.getInventory().getItemInMainHand());
                    } else {
                        playerReinforcements.changePlaceMode(p, false, p.getInventory().getItemInMainHand());
                    }
                    return true;
                }

                if (subcommand.equalsIgnoreCase("grouprm")) {
                    if (playerReinforcements.getGroupReinforcements() == null) {
                        p.sendMessage(ChatColor.RED + " You are not part of a group");
                        return false;
                    }
                    playerReinforcements.changeGroupReinforcementMode(p);
                    return true;

                }
                if (subcommand.equalsIgnoreCase("changegroup")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "You need to specify a name for the group");
                        return false;
                    }

                    String groupName = args[1];

                    GroupReinforcements groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(groupName, null);
                    if (groupReinforcements == null) {
                        p.sendMessage(ChatColor.RED + "This group name" + groupName + "does not exist, please check your spelling");
                        return false;
                    }
                    playerReinforcements.setGroupReinforcements(groupReinforcements);
                    p.sendMessage(ChatColor.AQUA + "You have changed to group " + playerReinforcements.getGroupReinforcements().getNameOfGroup());
                    return true;

                }
                if (subcommand.equalsIgnoreCase("mygroups")) {
                    GroupReinforcements.displayAssociatedGroups(p);
                    return true;
                }
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
                if (subcommand.equalsIgnoreCase("add")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "You need to specify a name to add");
                        return false;
                    }
                    if (args.length < 3) {
                        p.sendMessage(ChatColor.RED + "You need to specify the group name");
                        return false;
                    }

                    String playerName = args[1];
                    Player playerAdded = Bukkit.getPlayer(playerName);
                    String groupName = args[2];

                    GroupReinforcements groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(groupName, null);
                    if (groupReinforcements == null) {
                        p.sendMessage(ChatColor.RED + "This group name" + groupName + "does not exist, please check your spelling");
                        return false;
                    }
                    if (playerAdded == null) {
                        p.sendMessage(ChatColor.RED + "Player " + playerName + " is not online");
                        return false;
                    }

                    groupReinforcements.addToGroup(p, playerAdded, groupReinforcements);
                    p.sendMessage(ChatColor.AQUA + playerAdded.getName() + " has been added to the group");
                    return true;
                }
                if (subcommand.equalsIgnoreCase("remove")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "You need to specify a name to remove");
                        return false;
                    }
                    if (args.length < 3) {
                        p.sendMessage(ChatColor.RED + "You need to specify the group name");
                        return false;
                    }

                    String playerName = args[1];
                    Player playerToRemove = Bukkit.getPlayer(playerName);
                    String groupName = args[2];

                    GroupReinforcements groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(groupName, null);
                    if (groupReinforcements == null) {
                        p.sendMessage(ChatColor.RED + "This group name" + groupName + "does not exist, please check your spelling");
                        return false;
                    }
                    if (playerToRemove == null) {
                        p.sendMessage(ChatColor.RED + "Player " + playerName + " is not online");
                        return false;
                    }

                    groupReinforcements.removeFromGroup(p, playerToRemove, groupReinforcements);
                    p.sendMessage(ChatColor.RED + playerToRemove.getName() + " has been removed from the group");
                    return true;
                }
                if (subcommand.equalsIgnoreCase("giveOwnership")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "You need to specify a name to give ownership to");
                        return false;
                    }
                    if (args.length < 3) {
                        p.sendMessage(ChatColor.RED + "You need to specify the group name");
                        return false;
                    }

                    String playerName = args[1];
                    Player playerToGiveOwnership = Bukkit.getPlayer(playerName);
                    String groupName = args[2];

                    GroupReinforcements groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(groupName, null);
                    if (groupReinforcements == null) {
                        p.sendMessage(ChatColor.RED + "This group name" + groupName + "does not exist, please check your spelling");
                        return false;
                    }
                    if (playerToGiveOwnership == null) {
                        p.sendMessage(ChatColor.RED + "Player " + playerName + " is not online");
                        return false;
                    }

                    groupReinforcements.giveOwnership(p, playerToGiveOwnership, groupReinforcements);
                    p.sendMessage(ChatColor.AQUA + playerToGiveOwnership.getName() + " has been given ownership of the group");
                    return true;
                }
                if (subcommand.equalsIgnoreCase("display")) {
                    if (args.length < 2) {
                        p.sendMessage(ChatColor.RED + "You need to specify a name to give ownership to");
                        return false;
                    }

                    String groupName = args[1];
                    GroupReinforcements groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(groupName, null);
                    if (groupReinforcements == null) {
                        p.sendMessage(ChatColor.RED + "This group name" + groupName + "does not exist, please check your spelling");
                        return false;
                    }

                    groupReinforcements.displayGroupMembers(p, groupReinforcements);
                    return true;
                }

            }
        }
        return false;
    }
//      TODO: OOptimize code to stop repeating so much
//    private GroupReinforcements convertStringToGroup(Player p, GroupReinforcements groupReinforcements, String str){
//        groupReinforcements = GroupReinforcements.getGroupNames().getOrDefault(str, null);
//        if (groupReinforcements == null) {
//            p.sendMessage(ChatColor.RED + "This group name" + str + "does not exist, please check your spelling");
//            return null;
//        }
//        return groupReinforcements;
//    }
}
