package org.winterdev.SakuraHeads.Listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

import org.bukkit.configuration.file.FileConfiguration;
import org.winterdev.SakuraHeads.SakuraHeads;
import org.winterdev.SakuraHeads.Util.ColorUtil;

public class HeadsOnDeath implements Listener {

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        FileConfiguration config = SakuraHeads.getPlugin().getConfig();
        String tag = config.getString("Messages.tag");
        Player player = e.getPlayer();

        if (e.getPlayer().getKiller() == null) return;

        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();

        String killerMessage = config.getString("Messages.killer-message");
        killerMessage = killerMessage.replace("%killer%", player.getKiller().getName());

        itemMeta.setOwningPlayer(player);
        itemMeta.setLore(Collections.singletonList(killerMessage));

        item.setItemMeta(itemMeta);
        World world = player.getWorld();
        world.dropItem(player.getLocation(), item);

        String deathMessage = config.getString("Messages.death-message");
        deathMessage = deathMessage.replace("%name%", player.getName());

        player.sendMessage(ColorUtil.color(tag + deathMessage));
    }
}
