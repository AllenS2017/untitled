package mo.first.untitled.PlayerHandling;

import mo.first.untitled.Reinforcement.ReinforcedBlocks;
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
