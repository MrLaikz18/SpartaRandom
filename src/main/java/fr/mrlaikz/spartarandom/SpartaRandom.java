package fr.mrlaikz.spartarandom;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public final class SpartaRandom extends Plugin {

    @Override
    public void onEnable() {
        getLogger().info("Plugin Actif");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TasCMD(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin Inactif");
    }
}
