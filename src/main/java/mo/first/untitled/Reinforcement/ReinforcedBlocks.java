package mo.first.untitled.Reinforcement;

import org.bukkit.Location;

public class ReinforcedBlocks {

    private Location location;
    private int requiredHits;
    private int reinforcementLevel;

    public ReinforcedBlocks (Location location, int reinforcementLevel){

        this.location = location;
        this.reinforcementLevel = reinforcementLevel;
        this.requiredHits = 0;

    }

    public int getReinforcementLevel() {
        return reinforcementLevel;
    }

    public void setReinforcementLevel(int reinforcementLevel) {
        this.reinforcementLevel = reinforcementLevel;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getRequiredHits() {
        return requiredHits;
    }

    public void setRequiredHits(int requiredHits) {
        this.requiredHits = requiredHits;
    }
}
