package me.botsko.prism;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;

public class Messenger {


    private final AudienceProvider audienceProvider;
    private final String pluginName;

    /**
     * Build the class.
     *
     * @param pluginName String
     */
    @SuppressWarnings("WeakerAccess")
    public Messenger(String pluginName, AudienceProvider provider) {
        this.pluginName = pluginName;
        this.audienceProvider = provider;
    }

    public void sendMessage(CommandSender sender, Component message) {
        ((BukkitAudiences) audienceProvider).audience(sender).sendMessage(message);
    }

    /**
     * Get the header.
     *
     * @param msg the message to prefix.
     * @return String.
     */
    public TextComponent playerHeaderMsg(TextComponent msg) {
        if (msg != null) {
            TextComponent coloredMessage = msg.colorIfAbsent(NamedTextColor.WHITE);
            return TextComponent.builder()
                    .content(pluginName)
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .append(coloredMessage)
                    .build();
        }
        return TextComponent.empty();
    }

    @Deprecated
    public TextComponent playerHeaderMsg(String msg) {
        return playerHeaderMsg(LegacyComponentSerializer.legacySection().deserialize(msg));
    }

    /**
     * Get the subdued header.
     *
     * @param msg the message to prefix.
     * @return String.
     */
    public TextComponent playerSubduedHeaderMsg(TextComponent msg) {
        if (msg != null) {
            TextComponent coloredMessage = msg.colorIfAbsent(NamedTextColor.GRAY);
            return TextComponent.builder()
                    .content(pluginName + " ")
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .append(coloredMessage)
                    .build();
        }
        return TextComponent.empty();
    }

    @Deprecated
    public TextComponent playerSubduedHeaderMsg(String msg) {
        return playerSubduedHeaderMsg(LegacyComponentSerializer.legacySection().deserialize(msg));
    }

    /**
     * Get the message.
     *
     * @param msg the message to prefix.
     * @return String.
     */
    @Deprecated
    public TextComponent playerMsg(String msg) {
        if (msg != null) {
            TextComponent component = LegacyComponentSerializer.legacySection().deserialize(msg);
            return playerMsg(component);
        }
        return TextComponent.empty();
    }

    /**
     * Get the message colored white by default.
     *
     * @param msg TextComponent
     * @return TextComponent
     */
    public TextComponent playerMsg(TextComponent msg) {
        if (msg != null) {
            return msg.colorIfAbsent(NamedTextColor.WHITE);
        }
        return TextComponent.empty();
    }

    /**
     * Get the help message.
     *
     * @param cmd  cmd to get help.
     * @param help - a message.
     * @return String.
     */
    public TextComponent playerHelp(String cmd, String help) {
        return TextComponent.builder()
                .content("/prism").color(NamedTextColor.GRAY)
                .build()
                .append(TextComponent.of(cmd).color(NamedTextColor.LIGHT_PURPLE))
                .append(TextComponent.of(" - " + help).color(NamedTextColor.WHITE));
    }

    /**
     * Get the error message.
     *
     * @param msg the message to prefix.
     * @return String.
     */
    public TextComponent playerError(TextComponent msg) {
        TextComponent coloredMessage = msg.colorIfAbsent(NamedTextColor.RED);
        return TextComponent.builder()
                .content(pluginName + " ")
                .color(NamedTextColor.LIGHT_PURPLE)
                .append(coloredMessage)
                .build();
    }

    public TextComponent playerError(String msg) {
        return playerError(TextComponent.of(msg));
    }

    /**
     * Get the Success message.
     *
     * @param msg the message to prefix.
     * @return String.
     */
    public TextComponent playerSuccess(String msg) {
        if (msg != null) {
            return playerSuccess(TextComponent.of(msg));
        }
        return TextComponent.empty();
    }

    /**
     * Get the Success message.
     *
     * @param msg the message to prefix.
     * @return String.
     */
    public TextComponent playerSuccess(TextComponent msg) {
        if (msg != null) {
            TextComponent coloredMessage = msg.colorIfAbsent(NamedTextColor.GREEN);
            return TextComponent.builder()
                    .content(pluginName + " ")
                    .color(NamedTextColor.LIGHT_PURPLE)
                    .append(coloredMessage)
                    .build();
        }
        return TextComponent.empty();
    }

}