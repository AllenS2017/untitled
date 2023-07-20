package mo.first.untitled.FunctionTesting;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockLocation implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        Player p = event.getPlayer();
        Block block = event.getBlock();

        p.sendMessage("You broke this block at " + block.getX() + " " + block.getY() + " " + block.getZ());

    }

}
