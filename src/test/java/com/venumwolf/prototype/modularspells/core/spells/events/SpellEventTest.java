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

package com.venumwolf.prototype.modularspells.core.spells.events;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class SpellEventTest {
    @Mock
    Spell spell;

    SpellEvent event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        event = new SpellEvent(spell);
    }

    @Test
    void getSpell() {
        assertEquals(spell, event.getSpell());
    }

    @Test
    void getHandlers() {
        assertNotNull(event.getHandlers());
    }

    @Test
    void isHandlerList() {
        assertNotNull(SpellEvent.getHandlerList());
    }

    @Test
    void istCancelledTrue() {
        event.setCancelled(true);
        assertTrue(event.isCancelled());
    }

    @Test
    void isCancelledFalse() {
        event.setCancelled(false);
        assertFalse(event.isCancelled());
    }
}