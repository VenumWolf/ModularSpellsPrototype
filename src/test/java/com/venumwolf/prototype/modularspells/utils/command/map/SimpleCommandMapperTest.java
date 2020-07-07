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

package com.venumwolf.prototype.modularspells.utils.command.map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class SimpleCommandMapperTest {
    @Mock
    CommandMap commandMap;

    @Mock
    Command command;

    SimpleCommandMapper commandMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        commandMapper = new SimpleCommandMapper("fallback", commandMap, Logger.getLogger("test"));
    }

    @Test
    void addCommandOnly() {
        commandMapper.add(command);
        verify(commandMap).register("fallback", command);
    }

    @Test
    void addCommandWithFallback() {
        commandMapper.add("fallback", command);
        verify(commandMap).register("fallback", command);
    }

    @Test
    void addAll() {
        List<Command> commands = new ArrayList<>();
        commands.add(command);
        commandMapper.addAll("fallback", commands);
        verify(commandMap).registerAll("fallback", commands);
    }
}