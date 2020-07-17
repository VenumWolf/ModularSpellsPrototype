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

package com.venumwolf.prototype.modularspells.core.spells.effects;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.Map;

/**
 * Provides the base programming interface for spell effects, as well as some default implementations for convenience
 * methods.
 */
public abstract class Effect {

    /**
     * The type of the effect.  This controls how and when the effect is applied in the spell casting process.
     *
     * See {@link EffectType} for more information.
     */
    protected EffectType type;

    /**
     * Initialize with an EffectType value.
     * @param type The EffectType for the effect.
     */
    public Effect(EffectType type) {
        this.type = type;
    }

    /**
     * Apply the effect to an Entity.  Some effects may instead apply to that entity's location.
     * @param targets  A List of target Entities to which, or at which to apply the effect.
     * @param settings A dictionary-like map of settings which control how the effect should be applied.  The same
     *                 settings are applied to all targets, and there is no way to control which settings are applied to
     *                 which targets.  Valid settings are unique to each effect implementation, so reference the
     *                 documentation for specific effects for more information on their settings.
     */
    public void applyToAllEntities(List<Entity> targets, Map<String, Object> settings) {
        targets.forEach((Entity target) ->
                applyToEntity(target, settings));
    }

    /**
     * Apply the effect to an Entity.  Some effects may instead apply to that entity's location.
     * @param target   A target Entity to which, or at which to apply the effect.
     * @param settings A dictionary-like map of settings which control how the effect should be applied.  Valid settings
     *                 are unique to each effect implementation, so reference the documentation for specific effects for
     *                 more information on their settings.
     */
    public abstract void applyToEntity(Entity target, Map<String, Object> settings);

    /**
     * Apply the effect at a List of Locations.  Some effects may instead apply to any entity at or near the Locations
     * as applicable.
     * @param locations A List of Locations at which to apply the effect.
     * @param settings  A dictionary-like map of settings which control how the effect should be applied.  The same
     *                  settings are applied to all targets, and there is no way to control which settings are applied
     *                  to which targets.  Valid settings are unique to each effect implementation, so reference the
     *                  documentation for specific effects for more information on their settings.
     */
    public void applyToAllLocations(List<Location> locations, Map<String, Object> settings) {
         locations.forEach((Location location) ->
                applyToLocation(location, settings));
    }

    /**
     * Apply the effect to a Location.  Some effects may instead apply to any entity at or near the Location as
     * applicable.
     * @param location A Location at which to apply the effect.
     * @param settings A dictionary-like map of settings which control how the effect should be applied.  Valid settings
     *                 are unique to each effect implementation, so reference the documentation for specific effects for
     *                 more information on their settings.
     */
    public abstract void applyToLocation(Location location, Map<String, Object> settings);

    /**
     * Access the EffectType.
     * @return  The EffectType.
     */
    public EffectType getType() {
        return type;
    }
}
