`/*
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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of effects to be cast.
 */
public class Spell {
    final Set<Effect> effects = new HashSet<>();

    /**
     * Initialize the Spell with no effects.
     */
    public Spell() {
    }

    /**
     * Initialize the spell with default effects.
     *
     * @param effects The starting effect of the spell.
     */
    public Spell(Collection<? extends Effect> effects) {
        this.effects.addAll(effects);
    }

    /**
     * Add and effect to the spell.
     * <p>
     * Requests to add duplicate effects should be ignored.
     *
     * @param effect The effect to add.
     */
    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    /**
     * Add a collection of effects to the spell.
     * <p>
     * Requests to add duplicate effects should be ignored.
     */
    public void addAllEffects(Collection<? extends Effect> effects) {
        this.effects.addAll(effects);
    }

    /**
     * Remove an effect from the spell.
     * <p>
     * Requests to remove non-existent effects should be ignored.
     *
     * @param effect The effect to remove.
     */
    public void removeEffect(Effect effect) {
        effects.remove(effect);
    }

    /**
     * Remove a collection of effects from the spell.
     * <p>
     * Requests to remove non-existent effects should be ignored.
     *
     * @param effects The effects to remove.
     */
    public void removeAllEffects(Collection<? extends Effect> effects) {
        this.effects.removeAll(effects);
    }
}