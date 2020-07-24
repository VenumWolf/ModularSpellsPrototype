package com.venumwolf.prototype.modularspells.core.spells.listeners;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listener = new DefaultSpellEventListener();
    }

    @Test
    void precastNotCancelled() {
        SpellPrecastEvent event = new SpellPrecastEvent(spell, entity);
        listener.onSpellPrecastEvent(event);
        verify(spell).cast(entity);
    }

    @Test
    void precastCancelled() {
        SpellPrecastEvent event = new SpellPrecastEvent(spell, entity);
        listener.onSpellPrecastEvent(event);
        verify(spell, never()).cast(entity);
    }
}