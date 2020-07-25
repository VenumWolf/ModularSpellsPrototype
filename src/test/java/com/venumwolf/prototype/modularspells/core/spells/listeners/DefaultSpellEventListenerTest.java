package com.venumwolf.prototype.modularspells.core.spells.listeners;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
import com.venumwolf.prototype.modularspells.core.spells.events.SpellCastEvent;
import com.venumwolf.prototype.modularspells.core.spells.events.SpellPrecastEvent;
import org.bukkit.entity.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DefaultSpellEventListenerTest {

    /**
     * A mock Spell for testing.
     */
    @Mock
    Spell spell;

    /**
     * A mock Entity for testing.
     */
    @Mock
    Entity entity;

    DefaultSpellEventListener listener;

    /**
     * Initialize the mocks and create a listener instance before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listener = new DefaultSpellEventListener();
    }

    /**
     * Verify the SpellPrecastEvent handler will run spell.cast() when the event has not been canceled.
     */
    @Test
    void precastNotCancelled() {
        SpellPrecastEvent event = new SpellPrecastEvent(spell, entity);
        listener.onSpellPrecastEvent(event);
        verify(spell).cast(entity);
    }

    /**
     * Verify the SpellPrecastEvent listener will not run spell.cast() when the event has been canceled.
     */
    @Test
    void precastCancelled() {
        SpellPrecastEvent event = new SpellPrecastEvent(spell, entity);
        event.setCancelled(true);
        listener.onSpellPrecastEvent(event);
        verify(spell, never()).cast(entity);
    }

    /**
     * Verify the SpellCastEvent listener will run the spell.applyCasterEffects(), and spell.launchProjectileEffects()
     * methods when the event has not been canceled.
     */
    @Test
    void castNotCancelled() {
        SpellCastEvent event = new SpellCastEvent(spell, entity);
        listener.onSpellCastEvent(event);
        verify(spell).applyCasterEffects(entity);
        verify(spell).launchProjectileEffects(entity);
    }

    /**
     * Verify the SpellCastEvent listener will still run the spell.applyCasterEffects(), and
     * spell.launchProjectileEffects() methods when the event has been canceled.
     */
    @Test
    void castCancelled() {
        SpellCastEvent event = new SpellCastEvent(spell, entity);
        event.setCancelled(true);
        listener.onSpellCastEvent(event);
        verify(spell, never()).applyCasterEffects(entity);
        verify(spell, never()).launchProjectileEffects(entity);
    }
}