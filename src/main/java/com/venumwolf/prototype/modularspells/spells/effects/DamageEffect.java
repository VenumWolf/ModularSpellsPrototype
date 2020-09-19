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
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class DamageEffect extends Effect {

    private double damage;

    /**
     * Initialize with an EffectType value, and damage amount.
     *
     * @param type   The EffectType for the effect.
     * @param damage The amount of damage to deal to the target.
     */
    public DamageEffect(EffectType type, double damage) {
        super(type);
        this.damage = damage;
    }

    @Override
    public void applyToEntity(Entity target, Spell spell) {
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).damage(damage);
        }
    }

    @Override
    public void applyToLocation(Location location, Spell spell) {
        // Can't damage a location, so do nothing.
    }
}
