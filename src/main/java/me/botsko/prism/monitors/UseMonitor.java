package me.botsko.prism.monitors;

import me.botsko.prism.Prism;
import me.botsko.prism.utils.MiscUtils;
import me.botsko.prism.utils.TypeUtils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UseMonitor {
    protected final List<String> blocksToAlertOnPlace;
    protected final List<String> blocksToAlertOnBreak;
    private final Prism plugin;
    private ConcurrentHashMap<String, Integer> countedEvents = new ConcurrentHashMap<>();

    /**
     * Constructor.
     *
     * @param plugin Prism
     */
    public UseMonitor(Prism plugin) {
        this.plugin = plugin;
        blocksToAlertOnPlace = plugin.getConfig().getStringList("prism.alerts.uses.item-placement");
        blocksToAlertOnPlace.replaceAll(String::toUpperCase);
        blocksToAlertOnBreak = plugin.getConfig().getStringList("prism.alerts.uses.item-break");
        blocksToAlertOnBreak.replaceAll(String::toUpperCase);
        resetEventsQueue();
    }

    protected void incrementCount(String playername, String msg) {

        int count = 0;
        if (countedEvents.containsKey(playername)) {
            count = countedEvents.get(playername);
        }
        count++;
        countedEvents.put(playername, count);

        msg = ChatColor.GRAY + playername + " " + msg;
        if (count == 5) {
            msg = playername + " continues - pausing warnings.";
        }
        List<BaseComponent> send = new ArrayList<>();
        TextComponent c = new TextComponent(msg);
        c.setColor(net.md_5.bungee.api.ChatColor.GRAY);
        send.add(c);
        if (count <= 5) {
            if (plugin.getConfig().getBoolean("prism.alerts.uses.log-to-console")) {
                plugin.alertPlayers(null, send);
                Prism.log(TypeUtils.colorize(msg));
            }

            // Log to commands
            List<String> commands = plugin.getConfig().getStringList("prism.alerts.uses.log-commands");
            MiscUtils.dispatchAlert(msg, commands);
        }
    }

    private boolean checkFeatureShouldCancel(Player player) {

        // Ensure enabled
        if (!plugin.getConfig().getBoolean("prism.alerts.uses.enabled")) {
            return true;
        }

        // Ignore players who would see the alerts
        if (plugin.getConfig().getBoolean("prism.alerts.uses.ignore-staff")
                && player.hasPermission("prism.alerts")) {
            return true;
        }

        // Ignore certain ranks
        return player.hasPermission("prism.bypass-use-alerts");
    }

    /**
     * Alert on block place.
     *
     * @param player Player
     * @param block  Block.
     */
    public void alertOnBlockPlacement(Player player, Block block) {

        // Ensure enabled
        if (checkFeatureShouldCancel(player)) {
            return;
        }

        final String playername = player.getName();
        final String blockType = "" + block.getType();

        // Ensure we're tracking this block
        if (blocksToAlertOnPlace.contains(blockType) || blocksToAlertOnPlace.contains(block.getType().name())) {
            final String alias = Prism.getItems().getAlias(block.getType(), block.getBlockData());
            incrementCount(playername, "placed " + alias);
        }
    }

    /**
     * Alert on break.
     *
     * @param player Player
     * @param block  block.
     */
    public void alertOnBlockBreak(Player player, Block block) {

        // Ensure enabled
        if (checkFeatureShouldCancel(player)) {
            return;
        }

        final String playername = player.getName();
        final String blockType = "" + block.getType();

        // Ensure we're tracking this block
        if (blocksToAlertOnBreak.contains(blockType) || blocksToAlertOnBreak.contains(block.getType().name())) {
            final String alias = Prism.getItems().getAlias(block.getType(), block.getBlockData());
            incrementCount(playername, "broke " + alias);
        }
    }

    /**
     * Alert on use.
     *
     * @param player Player.
     * @param useMsg msg/
     */
    public void alertOnItemUse(Player player, String useMsg) {

        // Ensure enabled
        if (checkFeatureShouldCancel(player)) {
            return;
        }

        final String playerName = player.getName();
        incrementCount(playerName, useMsg);

    }

    /**
     * Alert on xray.
     *
     * @param player Player
     * @param useMsg message
     */
    public void alertOnVanillaXray(Player player, String useMsg) {

        if (checkFeatureShouldCancel(player)) {
            return;
        }

        final String playerName = player.getName();
        incrementCount(playerName, useMsg);

    }

    private void resetEventsQueue() {
        plugin.getServer().getScheduler()
                .scheduleSyncRepeatingTask(plugin, () -> countedEvents = new ConcurrentHashMap<>(),
                        7000L, 7000L);
    }
}