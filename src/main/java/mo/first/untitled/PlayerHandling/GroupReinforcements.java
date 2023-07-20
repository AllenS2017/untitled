package mo.first.untitled.PlayerHandling;

import mo.first.untitled.Reinforcement.ReinforcedBlocks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class GroupReinforcements {

    private Player groupAdmin;
    private ArrayList<ReinforcedBlocks> groupReinforcedBlocks = new ArrayList<>();
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private String nameOfGroup;
    public static HashMap<UUID, GroupReinforcements> publicGroupReinforcement = new HashMap<>();
    private HashMap<UUID, GroupReinforcements> groupReinforcementsHashMap = new HashMap<>();

    private static HashSet<String> groupNames = new HashSet<>();

    public GroupReinforcements(Player groupAdmin, String nameOfGroup) {
        if (groupNames.contains(nameOfGroup)) {
            throw new IllegalArgumentException("The group name " + nameOfGroup + " is already taken.");
        } else {
            publicGroupReinforcement.put(groupAdmin.getUniqueId(), this);
            groupNames.add(nameOfGroup);
            this.groupAdmin = groupAdmin;
            this.nameOfGroup = nameOfGroup;
        }
    }

    public void addToGroup(Player groupAdmin, Player player, GroupReinforcements groupReinforcements) {
        if (groupReinforcements == null) {
            groupAdmin.sendMessage(ChatColor.RED + "You attempted to add someone to a group that does not exist");
            return;
        }
        if (player == null) {
            groupAdmin.sendMessage(ChatColor.RED + "This player does not exist");
            return;
        }
        if (groupAdmin != this.groupAdmin) {
            groupAdmin.sendMessage(ChatColor.RED + "You are not a group admin");
            return;
        }
        if (groupReinforcements.getPlayerArrayList().contains(player)) {
            groupAdmin.sendMessage(ChatColor.RED + "This player is currently inside the group");
            return;
        }
        groupReinforcements.getPlayerArrayList().add(player);
        groupAdmin.sendMessage(ChatColor.AQUA + "You have successfully added " + player.getName() + " to group " + this.getNameOfGroup());
        player.sendMessage(ChatColor.AQUA + "You have been added to " + this.getNameOfGroup());
    }

    public void removeFromGroup(Player groupAdmin, Player player, GroupReinforcements groupReinforcements) {
        if (groupReinforcements == null) {
            groupAdmin.sendMessage(ChatColor.RED + "You attempted to add someone to a group that does not exist");
            return;
        }
        if (player == null) {
            groupAdmin.sendMessage(ChatColor.RED + "This player does not exist");
            return;
        }
        if (groupAdmin != this.groupAdmin) {
            groupAdmin.sendMessage(ChatColor.RED + "You are not a group admin");
            return;
        }
        if (!groupReinforcements.getPlayerArrayList().contains(player)) {
            groupAdmin.sendMessage(ChatColor.RED + "This player is not in the group");
            return;
        }
        groupReinforcements.getPlayerArrayList().remove(player);
        groupAdmin.sendMessage(ChatColor.AQUA + "You have successfully removed " + player.getName() + " to group " + this.getNameOfGroup());
        player.sendMessage(ChatColor.RED + "You have been removed from " + this.getNameOfGroup());
    }

    public HashMap<UUID, GroupReinforcements> getGroupReinforcementsHashMap() {
        return groupReinforcementsHashMap;
    }

    public void setGroupReinforcementsHashMap(HashMap<UUID, GroupReinforcements> groupReinforcementsHashMap) {
        this.groupReinforcementsHashMap = groupReinforcementsHashMap;
    }

    public static HashMap<UUID, GroupReinforcements> getPublicGroupReinforcement() {
        return publicGroupReinforcement;
    }

    public static void setPublicGroupReinforcement(HashMap<UUID, GroupReinforcements> publicGroupReinforcement) {
        GroupReinforcements.publicGroupReinforcement = publicGroupReinforcement;
    }

    public Player getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Player groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public ArrayList<ReinforcedBlocks> getGroupReinforcedBlocks() {
        return groupReinforcedBlocks;
    }

    public void setGroupReinforcedBlocks(ArrayList<ReinforcedBlocks> groupReinforcedBlocks) {
        this.groupReinforcedBlocks = groupReinforcedBlocks;
    }

    public ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        this.playerArrayList = playerArrayList;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }
}
