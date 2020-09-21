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
import com.venumwolf.prototype.modularspells.core.spells.Spell;
import com.venumwolf.prototype.modularspells.core.spells.effects.EffectType;
import com.venumwolf.prototype.modularspells.core.spells.effects.ProjectileSystem;
import com.venumwolf.prototype.modularspells.core.spells.listeners.DefaultSpellEventListener;
import com.venumwolf.prototype.modularspells.core.utils.command.map.CommandMapper;
import com.venumwolf.prototype.modularspells.core.utils.command.map.PluginCommandMapper;
import com.venumwolf.prototype.modularspells.listeners.SpellCastListener;
import com.venumwolf.prototype.modularspells.spells.effects.DamageEffect;
import com.venumwolf.prototype.modularspells.spells.effects.ExplosionEffect;
import com.venumwolf.prototype.modularspells.spells.effects.ProjectileEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public final class ModularSpells extends JavaPlugin {
    private final Logger logger = getLogger();
    private final CommandMapper commandMapper = new PluginCommandMapper(this);
    private final ProjectileSystem projectileSystem = new ProjectileSystem();

    @Override
    public void onEnable() {
        logLicenseNotice();
        registerEventListeners();
        registerCommands();
        registerWandRecipe();
        projectileSystem.runTaskTimer(this, 0 , 1);
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

    private void registerEventListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SpellCastListener(this, getSpell()), this);
        pluginManager.registerEvents(new DefaultSpellEventListener(), this);
    }

    private Spell getSpell() {
        Spell spell = new Spell();
        spell.addEffect(new ProjectileEffect(projectileSystem, 2));
        spell.addEffect(new DamageEffect(EffectType.IMPACT, 5));
        spell.addEffect(new ExplosionEffect(EffectType.IMPACT, 1));
        return spell;
    }

    private void registerWandRecipe() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        meta.addEnchant(Enchantment.CHANNELING, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName("Magic Wand");
        meta.setLore((Arrays.asList("Allows you to cast magic spells.")));
        meta.getPersistentDataContainer()
                .set(new NamespacedKey(this, "isWand"), PersistentDataType.BYTE, (byte) 1);
        wand.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(this, "magic_wand");
        ShapelessRecipe recipe = new ShapelessRecipe(key, wand);
        recipe.addIngredient(Material.STICK);
        recipe.addIngredient(Material.EMERALD);
        Bukkit.addRecipe(recipe);
    }
}
