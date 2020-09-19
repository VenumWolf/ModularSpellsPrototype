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

import com.venumwolf.prototype.modularspells.core.spells.Spell;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Provides a global registry of in-flight projectile UUIDs and the spells that casted them.  Used primarily to check
 * if a projectile was cast by a spell, then to retrieve the spell it was cast by.
 */
public class ProjectileRegistry {
    private static final Map<UUID, Spell> registry = new HashMap<>();

    /**
     * Add a projectile UUID, and its Spell to the registry.
     * @param uuid  The UUID of the projectile.
     * @param spell The Spell associated with the projectile.
     */
    public static void register(UUID uuid, Spell spell) {
        registry.putIfAbsent(uuid, spell);
    }

    /**
     * Remove a specific projectile from the registry.
     * @param uuid The UUID of the projectile.
     */
    public static void remove(UUID uuid) {
        registry.remove(uuid);
    }

    /**
     * Clear all projectiles from the registry.
     */
    public static void clear() {
        registry.clear();
    }

    /**
     * Check if the registry contains a UUID.
     * @param uuid The projectile UUID.
     * @return     True if the UUID is present in the registry, otherwise false.
     */
    public static boolean contains(UUID uuid) {
        return registry.containsKey(uuid);
    }

    /**
     * Get the spell associated with a projectile UUID.
     * @param uuid The UUID of the projectile.
     * @return     The spell associated wit the UUID, or an empty Spell if the UUID is not present.
     */
    public static Spell getSpell(UUID uuid) {
        return registry.getOrDefault(uuid, new Spell());
    }
}
