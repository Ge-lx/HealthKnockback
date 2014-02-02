package io.github.gelx_.healthknockback;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class HealthKnockback extends JavaPlugin implements Listener{

	public static double STRENGTH;
	
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		this.saveResource("config.yml", false);
		STRENGTH = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config.yml")).getDouble("Strength");
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent evt){
		if(evt.getEntity() instanceof Player && receivesDamage((Player) evt.getEntity(), evt.getDamage())){
			Player player = (Player) evt.getEntity();
			
			Vector damagerLoc = evt.getDamager().getLocation().toVector();
			Vector damageeLoc = evt.getEntity().getLocation().toVector();
			
			Vector v = damageeLoc.clone().subtract(damagerLoc).normalize();
			v.multiply(0.5);
			v.setY(0.1);
			int i = (int) (11 - (player.getHealth() / 2) );
			v.multiply(i);
			
			player.setVelocity(v);
		}
	}
	
	private boolean receivesDamage(Player victim, double damage){
		return !(victim.getNoDamageTicks() > (float) victim.getMaximumNoDamageTicks() / 2.0F && damage <= victim.getLastDamage());
	}
	
}
