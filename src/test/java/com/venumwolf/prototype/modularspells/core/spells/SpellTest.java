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
import com.venumwolf.prototype.modularspells.core.spells.effects.TestEffects;
import com.venumwolf.prototype.modularspells.core.spells.events.SpellEvent;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
     *
     * Use one of {@link TestEffects}'s implementations for testing.
     *
     */
    Effect effect;

    /**
     * Set up a new Spell and Effect instance before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spell = new Spell(mockPluginManager);
        effect = new TestEffects.MessageTestEffect("test");
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
        List<Effect> effects = getEffectsList();
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
        List<Effect> effects = getEffectsList();
        spell.effects.addAll(effects);
        spell.removeAllEffects(effects);
        assertEquals(0, spell.effects.size());
    }

    /**
     * Verify effects are returned.
     */
    @Test
    void getEffects() {
        List<Effect> effects = getEffectsList();
        spell.effects.addAll(effects);
        assertEquals(effects, spell.getEffects());
    }

    /**
     * A helper-method which returns a list of test effects.
     * @return A list of test effects.
     */
    private List<Effect> getEffectsList() {
        ArrayList<Effect> effects = new ArrayList<>();
        effects.add(new TestEffects.MessageTestEffect("test1"));
        effects.add(new TestEffects.MessageTestEffect("test2"));
        effects.add(new TestEffects.MessageTestEffect("test3"));
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
}