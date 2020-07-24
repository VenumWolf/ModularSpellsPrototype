package com.venumwolf.prototype.modularspells.core.spells.listeners;

import com.venumwolf.prototype.modularspells.core.spells.events.SpellPrecastEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Provides the default, high-priority spell event listeners.  Most of these are required for the spell system to
 * function, and everything here is designed to be executed last, so each EventHandler will have it's priority set to
 * HIGHEST.
 */
public class DefaultSpellEventListener implements Listener {

    /**
     * Determines if the spell should be cast based on if the spell was cancelled or not.
     *
     * This handler should be executed last, as to allow other systems to do pre-processing or otherwise effect the
     * outcome of the spell.
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onSpellPrecastEvent(SpellPrecastEvent event) {
    }
}
