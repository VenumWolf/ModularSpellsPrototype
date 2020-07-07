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

package com.venumwolf.prototype.modularspells.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AboutCommandTest {
    @Mock
    CommandSender genericSender;

    @Mock
    ConsoleCommandSender consoleSender;

    @Mock
    Player playerSender;

    @Mock
    JavaPlugin plugin;

    AboutCommand command;

    public AboutCommandTest() { }

    @BeforeEach
    protected void setUp() {
        MockitoAnnotations.initMocks(this);
        command = new AboutCommand("command", plugin);
    }

    /**
     * Verifies the correct message is sent to any generic CommandSender.
     */
    @Test
    void doCommandWithGenericSender() {
        assertTrue(command.doCommand(genericSender, "command", new String[0]));
        verify(genericSender).sendMessage(command.message);
    }

    /**
     * Verifies the correct message is sent to ConsoleCommandSenders.
     */
    @Test
    void doCommandWithConsoleSender() {
        assertTrue(command.doCommand(consoleSender, "command", new String[0]));
        verify(consoleSender).sendMessage(command.message);
    }

    /**
     * Verifies the correct message is sent to Players.
     */
    @Test
    void doCommandWithPlayerSender() {
        assertTrue(command.doCommand(playerSender, "command", new String[0]));
        verify(playerSender).sendMessage(command.message);
    }
}