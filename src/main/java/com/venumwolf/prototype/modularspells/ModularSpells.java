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

package com.venumwolf.prototype.modularspells;

import com.venumwolf.prototype.modularspells.commands.AboutCommand;
import com.venumwolf.prototype.modularspells.core.utils.command.map.CommandMapper;
import com.venumwolf.prototype.modularspells.core.utils.command.map.PluginCommandMapper;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class ModularSpells extends JavaPlugin {
    private final Logger logger = getLogger();
    private final CommandMapper commandMapper = new PluginCommandMapper(this);

    @Override
    public void onEnable() {
        logLicenseNotice();
        registerCommands();
    }

    private void logLicenseNotice() {
        logger.info(Messages.COPYRIGHT_NOTICE);
        logger.info(Messages.STARTUP_NOTICE);
    }

    private void registerCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new AboutCommand("mspells-about", this));
        commandMapper.addAll("ModularSpells", commands);
    }

}
