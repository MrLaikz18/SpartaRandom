package fr.mrlaikz.spartarandom;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TasCMD extends Command {

    private SpartaRandom plugin;
    public TasCMD(SpartaRandom plugin) {
        super("tas");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer pp = (ProxiedPlayer) sender;

            if(pp.hasPermission("spartarandom.tas") && args.length == 2) {

                int timer = Integer.parseInt(args[0]);
                int nbr = Integer.parseInt(args[1]);
                List<ProxiedPlayer> pls = new ArrayList<ProxiedPlayer>();

                for(ProxiedPlayer pps : plugin.getProxy().getPlayers()) {
                    if(!pp.hasPermission("spartarandom.bypass")) {
                        pls.add(pps);
                    }
                }
                if(nbr < pls.size()) {
                    List<ProxiedPlayer> wins = new ArrayList<ProxiedPlayer>();
                    Chrono c = new Chrono(plugin, pp, timer, nbr, winners -> {
                        plugin.getProxy().broadcast("§3========== §6§lSparta'§c§lRandom §3==========");
                        plugin.getProxy().broadcast("§6Voici le(s) gagnant(s):");
                        for (ProxiedPlayer p : winners) {
                            plugin.getProxy().broadcast("§a" + p.getName());
                        }
                    });
                } else {
                    pp.sendMessage("§c§lTirage Impossible");
                }

            }

        }
    }
}
