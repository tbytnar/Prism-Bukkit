package me.botsko.prism.commands;

import me.botsko.prism.Il8n;
import me.botsko.prism.Prism;
import me.botsko.prism.commandlibs.CallInfo;
import me.botsko.prism.commandlibs.Executor;
import me.botsko.prism.commandlibs.SubHandler;
import me.botsko.prism.utils.ItemUtils;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class WhatCommand extends Executor {

    /**
     * Constructor.
     *
     * @param prism Plugin
     */
    public WhatCommand(Plugin prism) {
        super(prism, "command", "prism");
        setupCommands();
    }

    private void setupCommands() {
        addSub("what", "prism.what").setHandler(new SubHandler() {
            @Override
            public void handle(CallInfo call) {
                final ItemStack item = call.getPlayer().getInventory().getItemInMainHand();
                Prism.messenger.sendMessage(call.getPlayer(),
                        Prism.messenger.playerHeaderMsg(Il8n.getMessage("what-header",":")));
                // TODO: Better material formatting
                TextComponent out =  TextComponent.builder()
                        .append(Il8n.getMessage("what-item-name",": ").color(NamedTextColor.DARK_AQUA))
                        .append(TextComponent.of(item.getType().toString().toLowerCase()))
                        .append(TextComponent.newline())
                        .append(Il8n.getMessage("what-alias",": ").color(NamedTextColor.DARK_AQUA))
                        .append(TextComponent.of(Prism.getItems().getAlias(item.getType(),null)))
                        .append(TextComponent.newline())
                        .append(Il8n.getMessage("what-id",": "))
                        .append(TextComponent.of(item.getType().toString()))
                        .append(TextComponent.newline())
                        .append(Il8n.getMessage("what-display-name",": ").color(NamedTextColor.DARK_AQUA))
                        .append(TextComponent.of(ItemUtils.getItemFullNiceName(item)))
                        .colorIfAbsent(NamedTextColor.WHITE)
                        .build();
                Prism.messenger.sendMessage(call.getPlayer(),out);
            }

            @Override
            public List<String> handleComplete(CallInfo call) {
                return null;
            }
        });
    }
}
