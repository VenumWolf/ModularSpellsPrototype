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

/**
 * Signals the start of a spell cast.  This pre-cast phase should be used to perform pre-checks and to cancel the spell
 * if need be.
 */
public class SpellPrecastEvent extends SpellEvent {
    /**
     * A basic constructor accepting a Spell object.
     *
     * @param spell The Spell object to be used in the spell cast.
     */
    public SpellPrecastEvent(Spell spell) {
        super(spell);
    }
}
