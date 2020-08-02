/*
 * Copyright (C) 2020 VenumWolf
 *
 * This file is part of Modular Spells.
 *
 * Modular Spells is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Modular Spells is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Modular Spells.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.venumwolf.prototype.modularspells.listeners;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class SpellCastListener implements Listener {

    /**
     * The plugin to which this class is associated.  The plugin is needed for creating NamespacedKey objects for
     * persistent data lookups.
     */
    private JavaPlugin plugin;

    /**
     * The spell to be cast.
     * <p>
     * For the time being, the spell is hard-coded as there is no spell retrieval system.  Such a system is planned in
     * the near future.
     * <p>
     * TODO: Implement a per-entity spell retrieval system so spells can be picked and chosen for each spell caster.
     */
    private Spell spell;

    public SpellCastListener(JavaPlugin plugin, Spell spell) {
        this.plugin = plugin;
        this.spell = spell;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (isItemUseAction(event.getAction()) && isWandItem(event.getItem())){
            spell.trigger(event.getPlayer());
            event.setCancelled(true);
        }
    }

    private boolean isWandItem(ItemStack item) {
        boolean isWand = false;
        try {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer data = meta.getPersistentDataContainer();
            isWand = data.get(new NamespacedKey(plugin, "isWand"), PersistentDataType.BYTE) != 0;
        } catch (NullPointerException e) {}
        return isWand;
    }

    private boolean isItemUseAction(Action action) {
        return ((action == Action.RIGHT_CLICK_AIR) || (action == Action.RIGHT_CLICK_BLOCK));
    }
}
