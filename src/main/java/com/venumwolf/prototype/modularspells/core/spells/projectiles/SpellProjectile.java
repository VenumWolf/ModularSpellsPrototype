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

package com.venumwolf.prototype.modularspells.core.spells.projectiles;


import com.venumwolf.prototype.modularspells.core.spells.Spell;
import com.venumwolf.prototype.modularspells.core.spells.events.SpellImpactEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.List;

public abstract class SpellProjectile {

    protected Entity caster;
    protected Spell spell;
    protected Location location;
    protected Vector velocity;
    protected double gravity;
    protected PluginManager pluginManager;

    protected boolean hasImpacted = false;
    protected boolean hasCalledImpactEvent = false;

    public SpellProjectile(Spell spell, Entity caster, Location location, Vector velocity, double gravity) {
        this.caster = caster;
        this.spell = spell;
        this.location = location;
        this.velocity = velocity;
        this.gravity = gravity;

        pluginManager = Bukkit.getPluginManager();
    }

    public abstract void playEffect();

    public void tick() {
        if (!hasImpacted) {
            update();
            playEffect();
        } else if (!hasCalledImpactEvent) {
            pluginManager.callEvent(new SpellImpactEvent(spell, caster, location, getImpactedEntity(0.5)));
            hasCalledImpactEvent = true;
        }
    }

    public void update() {
        velocity.setY(velocity.getY() - gravity);
        location.add(velocity);
        hasImpacted = hasImpacted();
    }

    public boolean hasImpacted() {
        Entity impactedEntity = getImpactedEntity(0.5);
        return hasImpacted || !isInAir() || (impactedEntity != null && !impactedEntity.equals(caster));
    }

    private boolean isInAir() {
        Material locationMaterial = location.getBlock().getType();
        return locationMaterial == Material.AIR
                || locationMaterial == Material.CAVE_AIR
                || locationMaterial == Material.VOID_AIR;
    }

    private Entity getImpactedEntity(double impactDistance) {
        Entity entity = null;
        Collection<Entity> entities = location.getNearbyEntities(impactDistance, impactDistance, impactDistance);
        if (!entities.isEmpty())
            entity = (Entity) entities.toArray()[0];
        return entity;
    }

    public Entity getCaster() {
        return caster;
    }

}
