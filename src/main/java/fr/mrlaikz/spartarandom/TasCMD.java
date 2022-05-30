package fr.mrlaikz.spartarandom;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TasCMD extends Command {

    private String url = "https://discord.com/api/webhooks/980869051091943444/S6_64kZ-Zpb7y40nTtC28kngRVj-hWk12dPMEs_bpMmQ3T9YZ-Khn1ve2MTVxrwiz0t1";
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
                        DiscordWebhook wh = new DiscordWebhook(url);
                        plugin.getProxy().broadcast("§3========== §6§lSparta'§c§lRandom §3==========");
                        plugin.getProxy().broadcast("§6Voici le(s) gagnant(s):");
                        String dwins = "";
                        for (ProxiedPlayer p : winners) {
                            plugin.getProxy().broadcast("§a" + p.getName());
                            dwins = dwins + p.getName() + " ";
                        }
                        wh.setContent("Bravo aux gagnants: **__" + dwins + "__**");
                        try {
                            wh.execute();
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                pp.sendMessage("§aTirage au sort lancé !");
                //WEBHOOK
                DiscordWebhook wh = new DiscordWebhook(url);
                wh.setContent("@Giveaway Je viens de lancer un nouveau tirage au sort ! Pour tenter de gagner, connectez vous dans **" + timer / 3600 + "h " + (timer / 60)%60 + "m " + timer % 60 + "s**" +
                        " pour tenter de gagner ! **Nombre de gagnants: " + nbr + "**. Voici la récompense de ce giveaway:");
                try {
                    wh.execute();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            if(args.length == 1 && args[0].equalsIgnoreCase("info")) {
                int timer = c.getTimer();
                pp.sendMessage("§3========== §6§lSparta'§c§lRandom §3==========");
                pp.sendMessage("§6§lTirage au sort dans " + timer / 3600 + "h " + (timer / 60)%60 + "m " + timer % 60 + "s");
            }

        }
    }
}
