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
import com.venumwolf.prototype.modularspells.core.spells.effects.EffectType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class MessageEffectTest {

    @Mock
    Player player;

    @Mock
    Location location;

    @Mock
    Spell spell;

    MessageEffect effect;

    final String message = "Test";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockLocation();
    }

    @Test
    void applyToEntityAsCasterEffect() {
        effect = new MessageEffect(EffectType.CASTER, message);
        effect.applyToEntity(player, spell);
        verifyPlayerReceivedMessage(player);
    }

    @Test
    void applyToEntityAsCasterAreaEffect() {
        effect = new MessageEffect(EffectType.CASTER_AREA, message);
        effect.applyToEntity(player, spell);
        verifyPlayersReceivedMessage(location.getNearbyPlayers(0, 0, 0));
    }

    @Test
    void applyToLocationAsCasterAreaEffect() {
        effect = new MessageEffect(EffectType.CASTER_AREA, message);
        effect.applyToLocation(location, spell);
        verifyPlayersReceivedMessage(location.getNearbyPlayers(0, 0, 0));
    }

    @Test
    void applyToEntityAsImpactEffect() {
        effect = new MessageEffect(EffectType.IMPACT_AREA, message);
        effect.applyToEntity(player, spell);
        verifyPlayersReceivedMessage(location.getNearbyPlayers(0, 0, 0));
    }

    @Test
    void applyToLocationAsImpactEffect() {
        effect = new MessageEffect(EffectType.IMPACT_AREA, message);
        effect.applyToLocation(location, spell);
        verifyPlayersReceivedMessage(location.getNearbyPlayers(0, 0, 0));
    }

    private void mockLocation() {
        when(player.getLocation()).thenReturn(location);
        when(location.getNearbyPlayers(anyDouble())).thenReturn(mockPlayers(10));
    }

    private List<Player> mockPlayers(int count) {
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < count; i ++) {
            players.add(mock(Player.class));
        }
        return players;
    }

    private void verifyPlayersReceivedMessage(Collection<Player> players) {
        players.forEach(this::verifyPlayerReceivedMessage);
    }

    private void verifyPlayerReceivedMessage(Player player) {
        verify(player, atMostOnce()).sendMessage(message);
    }
}