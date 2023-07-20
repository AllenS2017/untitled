package mo.first.untitled.DeathHandling;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.lang.reflect.Array;

public class PlayerDeath implements Listener {

    @EventHandler
    public void playerLighteningDeath(PlayerDeathEvent e){

    e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());

    }

    @EventHandler
    public void signOnDeath(PlayerDeathEvent e){
        Player player = e.getEntity();

        player.getWorld().getBlockAt(player.getLocation()).setType(Material.OAK_SIGN);
        Sign sign = (Sign) player.getWorld().getBlockAt(player.getLocation()).getState();

        String deathMessage = e.getDeathMessage();

        int length;
        if (deathMessage == null){
            return;

        } else {
            length = deathMessage.length();
        }

        if (length > 60){
            deathMessage = deathMessage.substring(0,60);
        }

        for (int i = 0; i < 4; i++){
            int start = i * 15;

            if (start < length) {

                int end = Math.min(start + 15, deathMessage.length());
                String line = deathMessage.substring(start, end);
                sign.setLine(i, line);
            } else {
                break;
            }
        }
        sign.update();
    }
}
