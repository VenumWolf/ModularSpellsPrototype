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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class EffectTest {
    static class TestEffect extends Effect {

        public TestEffect() {
            super(EffectType.CASTER);
        }

        @Override
        public void applyToEntity(Entity target, Map<String, Object> settings) {
            target.sendMessage("test");
        }

        @Override
        public void applyToLocation(Location location, Map<String, Object> settings) {
            location.clone();
        }
    }
    @Mock
    Entity entity1;

    @Mock
    Entity entity2;

    @Mock
    Location location1;

    @Mock
    Location location2;

    Effect effect = new TestEffect();
    Map<String, Object> settings = new HashMap<>();
    List<Entity> entities = new ArrayList<>();
    List<Location> locations = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        entities.add(entity1);
        entities.add(entity2);
        locations.add(location1);
        locations.add(location2);
    }

    @Test
    void applyToAllEntities() {
        effect.applyToAllEntities(entities, settings);
        verify(entity1).sendMessage("test");
        verify(entity2).sendMessage("test");
;    }

    @Test
    void applyToAllLocations() {
        effect.applyToAllLocations(locations, settings);
        verify(location1).clone();
        verify(location2).clone();
    }
    @Test
    void getType() {
        assertEquals(EffectType.CASTER, effect.getType());
    }

}