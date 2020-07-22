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
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides a collection of Effect implementations created specifically for testing purposes.
 */
public class TestEffects {

    /**
     * An Effect which sends a message to the spell caster.
     */
    public static class MessageTestEffect extends Effect {

        String message;

        public MessageTestEffect(String message) {
            super(EffectType.CASTER);
            this.message = message;
        }

        @Override
        public void applyToEntity(Entity target, Map<String, Object> settings) {
            target.sendMessage(message);
        }

        @Override
        public void applyToLocation(Location location, Map<String, Object> settings) {
            applyToAllEntities(new ArrayList<>(location.getNearbyPlayers(1)), settings);
        }
    }
}
