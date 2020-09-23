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
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public abstract class SpellProjectile {

    protected Entity caster;
    protected Spell spell;
    protected Location location;
    protected Vector velocity;
    protected double gravity;
    protected PluginManager pluginManager;

    protected RayTraceResult impact = null;
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
        if (impact == null) {
            update();
            playEffect();
        } else if (!hasCalledImpactEvent) {
            World world = location.getWorld();
            Vector hitPosition = impact.getHitPosition();
            Location impactLocation = new Location(world, hitPosition.getX(), hitPosition.getY(), hitPosition.getZ());
            pluginManager.callEvent(new SpellImpactEvent(spell, caster, impactLocation, impact.getHitEntity()));
            hasCalledImpactEvent = true;
        }
    }

    public void update() {
        velocity.setY(velocity.getY() - gravity);
        location.add(velocity);
        impact = rayTraceImpact();
    }

    private RayTraceResult rayTraceImpact() {
        World world = location.getWorld();
        return world.rayTrace(location, velocity.clone().normalize(), 2, FluidCollisionMode.ALWAYS, false, 0.5, null);
    }

    public Entity getCaster() {
        return caster;
    }

}
