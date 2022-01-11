package com.ceylon.trade.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommandConstructor extends CommandFrame implements CommandExecutor, TabCompleter {
    private final List<SubCommand> commands = new ArrayList<>();

    public CommandConstructor(Plugin plugin, String command) {
        super(plugin, command);
    }

    protected final SubCommand getSubCommandFromString(String command) {
        for(SubCommand subCommand : this.commands) {
            if(subCommand.getCommand().equalsIgnoreCase(command)) {
                return subCommand;
            }
        }
        return null;
    }

    protected final boolean registerSubCommand(SubCommand subCommand) {
        if(this.commands.contains(subCommand) || this.getSubCommandFromString(subCommand.getCommand()) != null) {
            return false;
        }
        this.commands.add(subCommand);
        return true;
    }

    protected final boolean unregisterSubCommand(SubCommand subCommand) {
        if(this.commands.contains(subCommand)) {
            this.commands.remove(subCommand);
            return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            return onCommandEmpty(sender, command, label);
        }
        boolean before = this.onBeforeCommand(sender, command, label, args);
        SubCommand sub = this.getSubCommandFromString(args[0]);
        boolean sub_result;
        if(before && sub != null) {
            List<String> args_list = new ArrayList<>(Arrays.asList(args));
            args_list.remove(0);
            sub_result = sub.onCommand(sender, args_list);
        } else {
            sub_result = false;
        }
        return onAfterCommand(sender, command, label, args, sub_result);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null; // 개발하기 귀찮아
    }

    public abstract boolean onCommandEmpty(CommandSender sender, Command command, String label);

    public abstract boolean onBeforeCommand(CommandSender sender, Command command, String label, String[] args);

    public abstract boolean onAfterCommand(CommandSender sender, Command command, String label, String[] args, boolean sub_result);
}
