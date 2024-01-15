package fr.mrlaikz.spartarandom;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Chrono {

    private ScheduledTask task;
    private int timer;
    private List<ProxiedPlayer> winners = new ArrayList<ProxiedPlayer>();
    private List<ProxiedPlayer> pls = new ArrayList<ProxiedPlayer>();

    public Chrono(Plugin plugin, ProxiedPlayer owner, int time, int nbr, Consumer<List<ProxiedPlayer>> winnersConsumer) {
        this.timer = time;
        task = plugin.getProxy().getScheduler().schedule(plugin, () -> {

            if (timer == 0) {

                if(pls.size() >= nbr) {
                    for (int i = 0; i < nbr; i++) {
                        Random r = new Random();
                        int ind = r.nextInt(pls.size());
                        winners.add(pls.get(ind));
                        pls.remove(pls.get(ind));
                    }
                    winnersConsumer.accept(winners);
                }
                task.cancel();
            }

            if(timer == 300) {
                for(ProxiedPlayer pp : plugin.getProxy().getPlayers()) {
                    if(!pp.hasPermission("spartarandom.bypass")) {
                        pls.add(pp);
                    }
                }
            }

            if((timer%3600 == 0 || timer%900 == 0 || timer == 10 || timer == 3 || timer == 2 || timer == 1) && timer != 0) {
                plugin.getProxy().broadcast("§3========== §6§lSparta'§c§lRandom §3==========");
                plugin.getProxy().broadcast("§6§lTirage au sort dans " + timer / 3600 + "h " + (timer / 60)%60 + "m " + timer % 60 + "s");
            }

            timer--;

        }, 0, 1, TimeUnit.SECONDS);
    }

    public int getTimer() {
        return timer;
    }

}
