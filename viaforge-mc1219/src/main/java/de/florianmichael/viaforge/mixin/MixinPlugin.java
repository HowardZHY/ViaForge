/*
 * This file is part of ViaForge - https://github.com/FlorianMichael/ViaForge
 * Copyright (C) 2021-2025 FlorianMichael/EnZaXD <florian.michael07@gmail.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.florianmichael.viaforge.mixin;

import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.VersionInfo;
import org.apache.logging.log4j.LogManager;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {

    private static boolean is21_9;

    private static boolean is21_11;

    @Override
    public void onLoad(String mixinPackage) {}

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("21_8")) {
            return !is21_9;
        } else if (mixinClassName.contains("21_9")) {
            return is21_9;
        }
        if (mixinClassName.contains("21_10")) {
            return !is21_11;
        } else if (mixinClassName.contains("21_11")) {
            return is21_11;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String name, ClassNode targetClass, String mixin, IMixinInfo iMixinInfo) {}

    @Override
    public void postApply(String name, ClassNode targetClass, String mixin, IMixinInfo iMixinInfo) {}

    private static int parseVersion(String version) {
        String[] parts = version.split("\\.");
        if (parts.length > 2) {
            return Integer.parseInt(parts[2]);
        } else {
            return 0;
        }
    }

    static {
        String version;
        boolean lex = false;
        try {
            Class<?> neo = Class.forName("net.neoforged.fml.loading.FMLLoader");
        } catch (ClassNotFoundException ignored) {
            lex = true;
        }
        if (lex) {
            version = net.minecraftforge.fml.loading.FMLLoader.versionInfo().mcVersion();
            LogManager.getLogger("ViaForge").info("Detected: {} LexForge", version);
            is21_9 = parseVersion(version) > 8;
            is21_11 = parseVersion(version) > 10;
        } else {
            Method current = null;
            try {
                current = FMLLoader.class.getDeclaredMethod("getCurrent");
            } catch (ReflectiveOperationException ignored) {}
            if (current != null) {
                is21_9 = true;
                try {
                    VersionInfo info = (VersionInfo) FMLLoader.class.
                        getDeclaredMethod("getVersionInfo").invoke(current.invoke(null));
                    version = info.mcVersion();
                    LogManager.getLogger("ViaForge").info("Detected: {} NeoForge", version);
                    is21_11 = parseVersion(version) > 10;
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            } else {
                LogManager.getLogger("ViaForge").info("Detected 1.21.8 or earlier NeoForge");
            }
        }
    }
}
