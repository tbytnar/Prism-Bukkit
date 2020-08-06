package me.botsko.prism.commands;

import me.botsko.prism.Il8n;
import me.botsko.prism.Prism;
import me.botsko.prism.commandlibs.CallInfo;
import me.botsko.prism.commandlibs.SubHandler;
import me.botsko.prism.text.ReplaceableTextComponent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Objects;

/**
 * Created for the Ark: Survival Evolved.
 * Created by Narimm on 5/06/2017.
 */
public class PurgeCommand implements SubHandler {

    private final Prism plugin;

    public PurgeCommand(Prism plugin) {
        this.plugin = plugin;
    }

    @Override
    public void handle(CallInfo call) {
        Audience sender = Prism.getAudiences().audience(call.getSender());
        if (call.getArgs().length < 1) {
            Prism.messenger.sendMessage(call.getSender(),
                    Prism.messenger.playerHeaderMsg(TextComponent.builder().content("Prism")
                            .append(" v" + plugin.getPrismVersion()).color(NamedTextColor.GRAY)
                            .build()));
            sender.sendMessage(Prism.messenger.playerSubduedHeaderMsg(ReplaceableTextComponent.builder("purge-report")
                    .replace("<taskCount>", plugin.getSchedulePool().getTaskCount())
                    .replace("<purgesComplete>", plugin.getSchedulePool().getCompletedTaskCount())
                    .replace("<poolString>", plugin.getSchedulePool().toString())
                    .build()));
        } else {
            if (Objects.equals(call.getArgs()[0], "execute")) {
                sender.sendMessage(
                        Prism.messenger.playerHeaderMsg(Il8n.getMessage("purge-execute")));
                Bukkit.getScheduler().runTaskAsynchronously(plugin, plugin.getPurgeManager());
            }
        }

    }

    @Override
    public List<String> handleComplete(CallInfo call) {
        return null;
    }
}
