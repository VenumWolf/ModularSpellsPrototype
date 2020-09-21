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

package com.venumwolf.prototype.modularspells.spells.effects;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
import com.venumwolf.prototype.modularspells.core.spells.effects.Effect;
import com.venumwolf.prototype.modularspells.core.spells.effects.EffectType;
import com.venumwolf.prototype.modularspells.core.spells.projectiles.ProjectileSystem;
import com.venumwolf.prototype.modularspells.core.spells.projectiles.SpellProjectile;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * An Effect that shoots a SpellProjectile.
 */
public class ProjectileEffect extends Effect {

    private ProjectileSystem projectileSystem;
    private double launchVelocity;

    /**
     * Default constructor requiring a reference to the plugin's ProjectileSystem, and a launch velocity.
     * @param projectileSystem The ProjectileSystem required to handle the shooting and calculations of the projectile.
     * @param launchVelocity   How fast to launch the projectile.
     */
    public ProjectileEffect(ProjectileSystem projectileSystem, double launchVelocity) {
        super(EffectType.PROJECTILE);
        this.projectileSystem = projectileSystem;
        this.launchVelocity = launchVelocity;
    }

    @Override
    public void applyToEntity(Entity entity, Spell spell) {
        if (entity instanceof LivingEntity) {
            LivingEntity caster = (LivingEntity) entity;
            Location casterEyeLocation = caster.getEyeLocation().clone();
            Vector casterLookDirection = casterEyeLocation.getDirection().normalize();
            Vector projectileVelocity = casterLookDirection.multiply(launchVelocity);
            projectileSystem.shoot(new SpellProjectile(spell, caster, casterEyeLocation, projectileVelocity, 0.05) {
                @Override
                public void playEffect() {
                    World world = location.getWorld();
                    world.spawnParticle(Particle.FLAME, location, 1, 0.02, 0.02, 0.02, 0.02, null, true);
                    world.spawnParticle(Particle.SMOKE_NORMAL, location, 3, 0.1, 0.1, 0.1, 0.02, null, true);
                    world.spawnParticle(Particle.LAVA, location, 1, 0.1, 0.1, 0.1, 0.03, null, true);
                }
            });
        }
    }

    @Override
    public void applyToLocation(Location location, Spell spell) {/* Effect requires an entity. */}
}
