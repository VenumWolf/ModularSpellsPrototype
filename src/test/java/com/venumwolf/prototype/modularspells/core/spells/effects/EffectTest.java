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
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.PluginManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EffectTest {
    Effect effect;

    Spell spell;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        effect = new TestEffects.MessageTestEffect("test");
        spell = new Spell(mock(PluginManager.class));
        spell.addEffect(effect);
    }

    @Test
    void applyToAllEntities() {
        List<Entity> entities = generateMockedEntities(10);
        effect.applyToAllEntities(entities, spell);
        entities.forEach(entity -> verify(entity).sendMessage("test"))
;    }

    @Test
    void applyToAllLocations() {
        List<Location> locations = generateMockedLocations(10);
        effect.applyToAllLocations(locations, spell);
        locations.forEach(location -> verify(location).getNearbyPlayers(1));
    }

    private List<Entity> generateMockedEntities(int count) {
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < count; i ++) {
            entities.add(generateMockedEntity());
        }
        return entities;
    }

    private Entity generateMockedEntity() {
        return mock(Entity.class);
    }

    private List<Location> generateMockedLocations(int count) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < count; i ++) {
            locations.add(generateMockedLocation());
        }
        return locations;
    }

    private Location generateMockedLocation() {
        return mock(Location.class);
    }

    @Test
    void getType() {
        assertEquals(EffectType.CASTER, effect.getType());
    }
}