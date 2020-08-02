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

package com.venumwolf.prototype.modularspells.listeners;

import com.venumwolf.prototype.modularspells.core.spells.Spell;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class SpellCastListenerTest {
    @Mock
    JavaPlugin plugin;

    @Mock
    Player player;

    @Mock
    Block block;

    @Mock
    BlockFace blockFace;

    @Mock
    ItemFactory itemFactory;

    @Mock
    Spell spell;


    SpellCastListener listener;

    @Before
    void mockBukkit() { }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        listener = new SpellCastListener(plugin, spell);
    }

    /**
     * Verifies the spell is triggered, and the event is cancelled when a wand item is right-clicked.
     */
    @Test
    void useWandOnAir() {
        Action action = Action.RIGHT_CLICK_AIR;
        PlayerInteractEvent event = callAndReturnEvent(action, getWand());
        verify(spell).trigger(any(Entity.class));
        assertEquals(Result.DENY, event.useItemInHand());
    }

    /**
     * Verifies the spell is not triggered, and the event is not cancelled when a normal item is right-clicked in the
     * air.
     */
    @Test
    void useItemOnAir() {
        Action action = Action.RIGHT_CLICK_AIR;
        PlayerInteractEvent event = callAndReturnEvent(action, new ItemStack(Material.STONE));
        verify(spell, never()).trigger(any(Entity.class));
        assertEquals(Result.ALLOW, event.useItemInHand());
    }

    /**
     * Verifies the spell is triggered, and the event is cancelled when a wand item is right-clicked.
     */
    @Test
    void useWandOnBlock() {
        Action action = Action.RIGHT_CLICK_BLOCK;
        PlayerInteractEvent event = callAndReturnEvent(action, getWand());
        verify(spell).trigger(any(Entity.class));
        assertEquals(Result.DENY, event.useInteractedBlock());
    }

    /**
     * Verifies the spell is not triggered, and the event is not cancelled when a normal item is right-clicked on a
     * block.
     */
    @Test
    void useItemOnBlock() {
        Action action = Action.RIGHT_CLICK_BLOCK;
        PlayerInteractEvent event = callAndReturnEvent(action, new ItemStack(Material.STONE));
        verify(spell, never()).trigger(any(Entity.class));
        assertEquals(Result.ALLOW, event.useInteractedBlock());
    }

    /**
     * Verifies the spell is not triggered, and the event is not cancelled on a left-click.
     */
    @Test
    void attackAirWithWand() {
        Action action = Action.LEFT_CLICK_AIR;
        PlayerInteractEvent event = callAndReturnEvent(action, getWand());
        verify(spell, never()).trigger(any(Entity.class));
        assertEquals(Result.ALLOW, event.useItemInHand());
    }

    /**
     * Verifies the spell is not triggered, and the event is not cancelled on a left-click.
     */
    @Test
    void attackAirWithItem() {
        Action action = Action.LEFT_CLICK_BLOCK;
        PlayerInteractEvent event = callAndReturnEvent(action, new ItemStack(Material.STONE));
        verify(spell, never()).trigger(any(Entity.class));
        assertEquals(Result.ALLOW, event.useItemInHand());
    }

    /**
     * Verifies the spell is not triggered, and the event is not cancelled on a left-click.
     */
    @Test
    void attackBlockWithWand() {
        Action action = Action.LEFT_CLICK_AIR;
        PlayerInteractEvent event = callAndReturnEvent(action, getWand());
        verify(spell, never()).trigger(any(Entity.class));
        assertEquals(Result.ALLOW, event.useInteractedBlock());
    }

    /**
     * Verifies the spell is not triggered, and the event is not cancelled on a left-click.
     */
    @Test
    void attackBlockWithItem() {
        Action action = Action.LEFT_CLICK_BLOCK;
        PlayerInteractEvent event = callAndReturnEvent(action, new ItemStack(Material.STONE));
        verify(spell, never()).trigger(any(Entity.class));
        assertEquals(Result.ALLOW, event.useInteractedBlock());
    }

    private ItemStack getWand() {
        ItemStack item = new ItemStack(Material.STICK);
        makeItemAWand(item);
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(plugin, "isWand"), PersistentDataType.BYTE, (byte) 1);
        return item;
    }

    private void makeItemAWand(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.set(new NamespacedKey(plugin, "isWand"), PersistentDataType.BYTE, (byte) 1);
    }

    private PlayerInteractEvent callAndReturnEvent(Action action, ItemStack item) {
        PlayerInteractEvent event = new PlayerInteractEvent(player, action, item, block, blockFace);
        listener.onPlayerInteract(event);
        return event;
    }
}