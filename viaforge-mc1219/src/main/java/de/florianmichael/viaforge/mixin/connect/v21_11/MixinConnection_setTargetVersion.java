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

package de.florianmichael.viaforge.mixin.connect.v21_11;

import de.florianmichael.viaforge.common.platform.VersionTracker;
import de.florianmichael.viaforge.common.protocoltranslator.netty.VFNetworkManager;
import io.netty.channel.ChannelFuture;
import net.minecraft.network.Connection;
import net.minecraft.server.network.EventLoopGroupHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.net.InetSocketAddress;

@Mixin(Connection.class)
public class MixinConnection_setTargetVersion {

    @Inject(method = "connect", at = @At("HEAD"))
    private static void setTargetVersion(InetSocketAddress p_290034_, EventLoopGroupHolder p_450865_, Connection p_290031_, CallbackInfoReturnable<ChannelFuture> cir) {
        final VFNetworkManager mixinConnection = (VFNetworkManager) p_290031_;
        mixinConnection.viaForge$setTrackedVersion(VersionTracker.getServerProtocolVersion(p_290034_.getAddress()));
    }
}
