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

package com.venumwolf.prototype.modularspells.core.utils.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PluginCommandTest {

    static class PermissionPassTestCommand extends PluginCommand {

        public PermissionPassTestCommand(String name, JavaPlugin plugin) {
            super(name, plugin);
        }

        @Override
        public boolean doCommand(CommandSender sender, String commandLabel, String[] args) {
            return true;
        }

        /*
         * Always returns true.  Simulates a passed permission check.
         */
        @Override
        public boolean testPermission(CommandSender target) {
            return true;
        }
    }

    static class PermissionFailTestCommand extends PluginCommand {

        public PermissionFailTestCommand(String name, JavaPlugin plugin) {
            super(name, plugin);
        }

        @Override
        public boolean doCommand(CommandSender sender, String commandLabel, String[] args) {
            return true;
        }

        /*
         * Always returns false.  Simulates a failed permission check.
         */
        @Override
        public boolean testPermission(CommandSender target) {
            return false;
        }
    }

    @Mock
    JavaPlugin plugin;

    @Mock
    CommandSender commandSender;

    // The two implementations simulate passing and failing checks.  The tested code is the same in both.
    PluginCommand permissionPassCommand;
    PluginCommand permissionFailCommand;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        permissionPassCommand = new PermissionPassTestCommand("pass", plugin);
        permissionFailCommand = new PermissionFailTestCommand("fail", plugin);
    }

    @Test
    void executePassPermissionCheck() {
        assertTrue(permissionPassCommand.execute(commandSender, "pass", new String[0]));
    }

    @Test
    void executeFailPermissionCheck() {
        assertFalse(permissionFailCommand.execute(commandSender, "fail", new String[0]));
    }

    @Test
    void getPlugin() {
        assertEquals(permissionPassCommand.getPlugin(), plugin);
    }
}