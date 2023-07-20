package mo.first.untitled.PlayerHandling;

import mo.first.untitled.Reinforcement.ReinforcedBlocks;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class GroupReinforcements {

    private Player groupAdmin;
    private ArrayList<ReinforcedBlocks> groupReinforcedBlocks = new ArrayList<>();
    private ArrayList<Player> playerArrayList = new ArrayList<>();
    private String nameOfGroup;
    private static HashMap<UUID, GroupReinforcements> publicGroupReinforcement = new HashMap<>();
    private HashMap<UUID, GroupReinforcements> groupReinforcementsHashMap = new HashMap<>();

    private static HashMap<String, GroupReinforcements> groupNames = new HashMap<>();

    public GroupReinforcements(Player groupAdmin, String nameOfGroup) {
        if (groupNames.containsKey(nameOfGroup)) {
            throw new IllegalArgumentException("The group name " + nameOfGroup + " is already taken.");
        } else {
            publicGroupReinforcement.put(groupAdmin.getUniqueId(), this);
            groupNames.put(nameOfGroup, this);
            this.playerArrayList.add(groupAdmin);
            this.groupAdmin = groupAdmin;
            this.nameOfGroup = nameOfGroup;
        }
    }

    public void addToGroup(Player groupAdmin, Player player, GroupReinforcements groupReinforcements) {
        if (groupReinforcements == null) {
            groupAdmin.sendMessage(ChatColor.RED + "You attempted to add someone to a group that does not exist");
            return;
        }
        if (player.getPlayer() == null) {
            groupAdmin.sendMessage(ChatColor.RED + "This player does not exist");
            return;
        }
        if (groupAdmin != this.getGroupAdmin()) {
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
        if (player.getPlayer() == null) {
            groupAdmin.sendMessage(ChatColor.RED + "This player does not exist");
            return;
        }
        if (groupAdmin != this.getGroupAdmin()) {
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

    public void giveOwnership(Player groupAdmin, Player player, GroupReinforcements groupReinforcements) {
        if (groupReinforcements == null) {
            groupAdmin.sendMessage(ChatColor.RED + "You attempted to add someone to a group that does not exist");
            return;
        }
        if (player == null) {
            groupAdmin.sendMessage(ChatColor.RED + "This player does not exist");
            return;
        }
        if (groupAdmin != this.getGroupAdmin()) {
            groupAdmin.sendMessage(ChatColor.RED + "You are not a group admin");
            return;
        }
        if (!groupReinforcements.getPlayerArrayList().contains(player)) {
            groupAdmin.sendMessage(ChatColor.RED + "To give ownership, the player must be in the group");
            return;
        }
        this.setGroupAdmin(player);
        player.sendMessage(ChatColor.AQUA + "You have been given ownership of the group");
        groupAdmin.sendMessage(ChatColor.RED + "You have relinquished ownership of the group to " + player.getName());
    }

    public void displayGroupMembers(Player player, GroupReinforcements groupReinforcements) {
        if (player != this.getGroupAdmin() && !this.getPlayerArrayList().contains(player)) {
            player.sendMessage(ChatColor.RED + "You are not in this group");
            return;
        }
        if (groupReinforcements.getPlayerArrayList().isEmpty()) {
            player.sendMessage(ChatColor.RED + "There is no one in the group besides the admin");
            return;
        }
        player.sendMessage(ChatColor.AQUA + "The group admin is: " + this.getGroupAdmin() + " and the members are " + groupReinforcements.getPlayerNamesAsString());
    }

    public static void displayAssociatedGroups(Player player) {
        // We'll collect the names of the groups to which the player belongs in this list
        List<String> playerGroups = new ArrayList<>();

        // Go through each group in the publicGroupReinforcement map
        for (Map.Entry<UUID, GroupReinforcements> entry : publicGroupReinforcement.entrySet()) {
            GroupReinforcements group = entry.getValue();

            // If this group contains the player in its playerArrayList, add the group's name to the playerGroups list
            if (group.getPlayerArrayList().contains(player)) {
                playerGroups.add(group.getNameOfGroup());
            }
        }

        // Now print the names of the groups. We join them with a comma for a cleaner output
        String groupsString = String.join(", ", playerGroups);
        player.sendMessage(ChatColor.AQUA + "You are a member of the following groups: " + groupsString);
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

    public String getPlayerNamesAsString() {
        return playerArrayList.stream()
                .map(Player::getName) // get each player's name
                .collect(Collectors.joining(", ")); // join them with a comma
    }

    @Override
    public String toString() {
        return "GroupReinforcements: " + getPlayerNamesAsString();
    }

    public static HashMap<String, GroupReinforcements> getGroupNames() {
        return groupNames;
    }

    public static void setGroupNames(HashMap<String, GroupReinforcements> groupNames) {
        GroupReinforcements.groupNames = groupNames;
    }
}
