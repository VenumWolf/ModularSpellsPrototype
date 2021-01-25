package com.venumwolf.prototype.modularspells.core.providers;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
import org.bukkit.entity.Entity;

/**
 * Set and access the a Spell for the provided entity.
 */
public interface SpellProvider {
    Spell getActiveSpell(Entity caster);
    void setActiveSpell(Entity caster, Spell spell);
}
