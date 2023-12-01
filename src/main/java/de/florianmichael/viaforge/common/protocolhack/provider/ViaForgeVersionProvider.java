/*
 * This file is part of ViaForge - https://github.com/FlorianMichael/ViaForge
 * Copyright (C) 2021-2023 FlorianMichael/EnZaXD and contributors
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

package de.florianmichael.viaforge.common.protocolhack.provider;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import de.florianmichael.viaforge.common.ViaForgeCommon;
import net.raphimc.vialoader.util.VersionEnum;

public class ViaForgeVersionProvider extends BaseVersionProvider {

    @Override
    public int getClosestServerProtocol(UserConnection connection) throws Exception {
        if (connection.isClientSide() && !ViaForgeCommon.getManager().getPlatform().isSingleplayer().get()) {
            try {
                System.out.println(connection.getChannel().attr(ViaForgeCommon.VF_NETWORK_MANAGER).get());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return connection.getChannel().attr(ViaForgeCommon.VF_NETWORK_MANAGER).get().viaForge$getTrackedVersion().getVersion();
        }
        return super.getClosestServerProtocol(connection);
    }
    
}
