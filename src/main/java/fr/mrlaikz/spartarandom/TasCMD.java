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
    private Chrono c;
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
                List<ProxiedPlayer> wins = new ArrayList<ProxiedPlayer>();
                c = new Chrono(plugin, pp, timer, nbr, winners -> {
                    if(winners.size()>0) {
                        plugin.getProxy().broadcast("§3========== §6§lSparta'§c§lRandom §3==========");
                        plugin.getProxy().broadcast("§6Voici le(s) gagnant(s):");
                        for (ProxiedPlayer p : winners) {
                            plugin.getProxy().broadcast("§a" + p.getName());
                        }
                    }
                });

            }

            if(args.length == 1 && args[0].equalsIgnoreCase("info")) {
                int timer = c.getTimer();
                pp.sendMessage("§3========== §6§lSparta'§c§lRandom §3==========");
                pp.sendMessage("§6§lTirage au sort dans " + timer / 3600 + "h " + (timer / 60)%60 + "m " + timer % 60 + "s");
            }

        }
    }
}
