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

package com.venumwolf.prototype.modularspells.core.spells.listeners;

import com.venumwolf.prototype.modularspells.core.spells.events.SpellPrecastEvent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Imposes a delay for spell casting by listening for SpellPrecastEvents and cancel the spell if too little time
 * has passed since the last cast.
 */
public class SpellCoolDownListener implements Listener {
    private final Map<UUID, Long> coolDownMap = new HashMap<>();

    /**
     * Blocks the spell cast if to little time has passed since the last spell cast.
     * @param event The pre-cast event.
     */
    @EventHandler
    public void onSpellPrecastEvent(SpellPrecastEvent event) {
        UUID uuid = event.getCaster().getUniqueId();
        long currentTime = System.currentTimeMillis();
        long coolDownEnd = coolDownMap.getOrDefault(uuid, -1L);
        if (coolDownEnd <= currentTime) {
            coolDownMap.put(uuid, currentTime + 1000);
        } else {
            event.setCancelled(true);
            Entity entity = event.getCaster();
            if (entity instanceof LivingEntity) {
                LivingEntity caster = (LivingEntity) entity;
                Location location = caster.getEyeLocation();
                Vector direction = location.getDirection();
                World world = caster.getWorld();
                world.playSound(location, Sound.BLOCK_FIRE_EXTINGUISH, 0.5F, 1F);
                world.spawnParticle(Particle.ASH, location, 24, direction.getX(), direction.getY() , direction.getZ(), 1, null, true);
            }
        }
    }
}
