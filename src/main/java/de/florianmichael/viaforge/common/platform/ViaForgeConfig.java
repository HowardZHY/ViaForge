package de.florianmichael.viaforge.common.platform;

import com.viaversion.viaversion.util.Config;
import com.viaversion.viaversion.util.Pair;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViaForgeConfig extends Config {
    public final static String CLIENT_SIDE_VERSION = "client-side-version";
    public final static String VERIFY_SESSION_IN_OLD_VERSIONS = "verify-session-in-old-versions";
    public final static String ALLOW_BETACRAFT_AUTHENTICATION = "allow-betacraft-authentication";
    public final static String SHOW_PROTOCOL_VERSION_IN_F3 = "show-protocol-version-in-f3";

    public final static String SHOW_MAIN_MENU_BUTTON = "show-main-menu-button";
    public final static String SHOW_MULTIPLAYER_BUTTON = "show-multiplayer-button";
    public final static String SHOW_DIRECT_CONNECT_BUTTON = "show-direct-connect-button";
    public final static String SHOW_ADD_SERVER_BUTTON = "show-add-server-button";

    public final static String VIA_FORGE_BUTTON_POSITION = "via-forge-button-position";
    public final static String ADD_SERVER_SCREEN_BUTTON_POSITION = "add-server-screen-button-position";

    /**
     * @param configFile The location of where the config is loaded/saved.
     */
    public ViaForgeConfig(File configFile) {
        super(configFile);
        reload();
    }

    @Override
    public URL getDefaultConfigURL() {
        return getClass().getClassLoader().getResource("assets/viaforge/config.yml");
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }

    @Override
    public void set(String path, Object value) {
        super.set(path, value);
        save(); // Automatically save the config when something changes
    }

    public int getClientSideVersion() {
        return getInt(CLIENT_SIDE_VERSION, 0);
    }

    public void setClientSideVersion(final int version) {
        set(CLIENT_SIDE_VERSION, version);
    }

    public boolean isVerifySessionInOldVersions() {
        return getBoolean(VERIFY_SESSION_IN_OLD_VERSIONS, true);
    }

    public boolean isAllowBetacraftAuthentication() {
        return getBoolean(ALLOW_BETACRAFT_AUTHENTICATION, true);
    }

    public boolean isShowProtocolVersionInF3() {
        return getBoolean(SHOW_PROTOCOL_VERSION_IN_F3, true);
    }

    public boolean isShowMainMenuButton() {
        return getBoolean(SHOW_MAIN_MENU_BUTTON, true);
    }

    public boolean isShowMultiplayerButton() {
        return getBoolean(SHOW_MULTIPLAYER_BUTTON, true);
    }

    public boolean isShowDirectConnectButton() {
        return getBoolean(SHOW_DIRECT_CONNECT_BUTTON, true);
    }

    public boolean isShowAddServerButton() {
        return getBoolean(SHOW_ADD_SERVER_BUTTON, true);
    }

    public ButtonPosition getViaForgeButtonPosition() {
        return ButtonPosition.valueOf(getString(VIA_FORGE_BUTTON_POSITION, ButtonPosition.TOP_LEFT.name()));
    }

    public ButtonPosition getAddServerScreenButtonPosition() {
        return ButtonPosition.valueOf(getString(ADD_SERVER_SCREEN_BUTTON_POSITION, ButtonPosition.TOP_LEFT.name()));
    }

    public enum ButtonPosition {
        // Button width: 100
        // Button height: 20
        // Button margin for both coordinates: 5
        TOP_LEFT((width, height) -> new Pair<>(5, 5)),
        TOP_RIGHT((width, height) -> new Pair<>(width - 100 - 5, 5)),
        BOTTOM_LEFT((width, height) -> new Pair<>(5, height - 20 - 5)),
        BOTTOM_RIGHT((width, height) -> new Pair<>(width - 100 - 5, height - 20 - 5));

        private final PositionInvoker invoker;

        ButtonPosition(final PositionInvoker invoker) {
            this.invoker = invoker;
        }

        public Pair<Integer, Integer> getPosition(int width, int height) {
            return invoker.invoke(width, height);
        }

        public interface PositionInvoker {

            Pair<Integer, Integer> invoke(int width, int height);
        }
    }
}
