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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class SpellTest {

    Spell spell;
    Effect effect;

    /**
     * Set up a new Spell and Effect instance before each test.
     */
    @BeforeEach
    void setUp() {
        spell = new Spell();
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
}