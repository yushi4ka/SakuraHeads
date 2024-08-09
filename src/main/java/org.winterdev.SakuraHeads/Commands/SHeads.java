package org.winterdev.SakuraHeads.Commands;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.winterdev.SakuraHeads.SakuraHeads;
import org.winterdev.SakuraHeads.Util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

public class SHeads implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        } else {
            FileConfiguration config = SakuraHeads.getPlugin().getConfig();
            if (strings[0].equals("reload")) {
                String tag = config.getString("Messages.tag");
                if (player.hasPermission("sakuraheads.admin")) {
                    SakuraHeads.getPlugin().reloadConfig();
                    config = SakuraHeads.getPlugin().getConfig();
                    String reloadMessage = config.getString("Messages.cmd-reload-message");
                    player.sendMessage(reloadMessage);
                } else {
                    String nopermissionMessage = config.getString("Messages.cmd-no-permission");
                    player.sendMessage(ColorUtil.color(tag + nopermissionMessage));
                }
            } else if (strings[0].equals("give-head")) {
                String tag = config.getString("Messages.tag");
                if (player.hasPermission("sakuraheads.admin")) {
                    ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta itemMeta = (SkullMeta) item.getItemMeta();

                    String killerMessage = config.getString("Messages.killer-message");
                    killerMessage = killerMessage.replace("%killer%", player.getName());

                    itemMeta.setOwningPlayer(player);
                    itemMeta.setLore(Collections.singletonList(killerMessage));

                    item.setItemMeta(itemMeta);
                    World world = player.getWorld();
                    world.dropItem(player.getLocation(), item);

                    String deathMessage = config.getString("Messages.death-message");
                    deathMessage = deathMessage.replace("%name%", player.getName());

                    player.sendMessage(deathMessage);
                } else {
                    String nopermissionMessage = config.getString("Messages.cmd-no-permission");
                    player.sendMessage(ColorUtil.color(tag + nopermissionMessage));
                }
            }

            return false;
        }

    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tab = new ArrayList<>();
        if (strings.length == 1 && commandSender.hasPermission("sakuraheads.admin")) {
            tab.add("reload");
            tab.add("give-head");
        }
        return tab.isEmpty() ? new ArrayList<>() : tab;
    }
}
