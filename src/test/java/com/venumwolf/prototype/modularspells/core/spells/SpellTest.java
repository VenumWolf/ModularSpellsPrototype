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

package com.venumwolf.prototype.modularspells.core.spells;

import com.venumwolf.prototype.modularspells.core.spells.effects.Effect;
import com.venumwolf.prototype.modularspells.core.spells.effects.EffectType;
import com.venumwolf.prototype.modularspells.core.spells.events.SpellCastEvent;
import com.venumwolf.prototype.modularspells.core.spells.events.SpellPrecastEvent;
import org.bukkit.entity.Player;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SpellTest {

    /**
     * A mocked PluginManager which is provided to the spell for triggering events.
     */
    @Mock
    PluginManager mockPluginManager;

    /**
     * A mock player which is used as the spell caster.
     */
    @Mock
    Player player;

    /**
     * A spell used for testing.
     */
    Spell spell;

    /**
     * A single effect used for testing.
     */
    @Mock
    Effect effect;

    /**
     * Set up a new Spell and Effect instance before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spell = new Spell(mockPluginManager);
    }

    /**
     * Verify the effect is added to the spell.
     */
    @Test
    void addEffect() {
        spell.addEffect(effect);
        assertTrue(spell.effects.contains(effect));
    }

    /**
     * Verify the effect cannot be added more than once.
     */
    @Test
    void addEffectAlreadyExists() {
        spell.addEffect(effect);
        spell.addEffect(effect);
        assertEquals(1, spell.effects.size());
    }

    /**
     * Verify a list of effects can be added.
     */
    @Test
    void addAllEffects() {
        List<Effect> effects = generateMockedEffects(5);
        spell.addAllEffects(effects);
    }

    /**
     * Verify the effect is removed from the spell.
     */
    @Test
    void removeEffect() {
        spell.effects.add(effect);
        spell.removeEffect(effect);
        assertEquals(0, spell.effects.size());
    }

    /**
     * Verify attempts to remove non-existent spells does not raise an exception.
     */
    @Test
    void removeNonExistentEffect() {
        spell.removeEffect(effect);
        assertEquals(0, spell.effects.size());
    }

    /**
     * Verify a whole list of effects can be removed.
     */
    @Test
    void removeAllEffects() {
        List<Effect> effects = generateMockedEffects(5);
        spell.effects.addAll(effects);
        spell.removeAllEffects(effects);
        assertEquals(0, spell.effects.size());
    }

    /**
     * Verify effects are returned.
     */
    @Test
    void getEffects() {
        List<Effect> effects = generateMockedEffects(5);
        spell.effects.addAll(effects);
        assertTrue(effects.containsAll(spell.getEffects()));
    }

    /**
     * Verify only the effects of a requested type are returned.  Check for each type.
     */
    @Test
    void getEffectsOfType() {
        List<Effect> validEffects = generateMockedEffects(3, EffectType.CASTER);
        spell.addAllEffects(generateMockedEffects(3, EffectType.IMPACT));
        spell.addAllEffects(validEffects);
        assertTrue(spell.getEffectsOfType(EffectType.CASTER).containsAll(validEffects));
    }

    /**
     * A helper-method which returns a list of mocked test effects.
     *
     * @param count The number of mocks to create and add to the list.
     * @return      A list of mocked test effects.  The EffectType will be set to CASTER.
     */
    private List<Effect> generateMockedEffects(int count) {
        return generateMockedEffects(count, EffectType.CASTER);
    }

    /**
     * A helper-method which returns a list of mocked test effects.
     * @param count The number of mocks to create and add to the list.
     * @param type  The EffectType the mocks should return on getType().
     * @return      A list of mocked test effects.
     */
    private List<Effect> generateMockedEffects(int count, EffectType type) {
        ArrayList<Effect> effects = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Effect effect = mock(Effect.class);
            when(effect.getType()).thenReturn(type);
            effects.add(effect);
        }
        return effects;
    }

    /**
     * Verify the trigger method triggers a SpellPrecastEvent.
     */
    @Test
    void trigger() {
        spell.trigger(player);
        verify(mockPluginManager).callEvent(any(SpellPrecastEvent.class));
    }

    /**
     * Verify the cast method triggers a SpellCastEvent.
     */
    @Test
    void cast() {
        spell.cast(player);
        verify(mockPluginManager).callEvent(any(SpellCastEvent.class));
    }
}