package ink.magma.zthwelcomemessage.command;

import ink.magma.zthwelcomemessage.reader.MessageReader;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.List;

public class WelcomeCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        if (args[0].equals("list")) {
            printEditorToSender(sender, label);

            return true;
        }
        if (args[0].equals("add")) {
            if (args.length < 2) return false;
            StringBuilder newMessage = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i >= 1) {
                    newMessage.append(' ');
                    newMessage.append(args[i]);
                }
            }
            MessageReader.addRawWelcomeMessage(newMessage.toString().trim());
            sender.sendMessage("添加成功");
            printEditorToSender(sender, label);

            return true;
        }
        if (args[0].equals("set")) {
            if (args.length < 3) return false;
            int setIndex;
            try {
                setIndex = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                sender.sendMessage("输入的序号有误，无法解析为数字");
                return true;
            }

            StringBuilder newMessage = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i >= 2) {
                    newMessage.append(' ');
                    newMessage.append(args[i]);
                }
            }
            int result = MessageReader.setRawWelcomeMessage(setIndex, newMessage.toString().trim());
            if (result == 1) {
                sender.sendMessage("未能找到指定消息, 请确认序号索引正确 (自 0 开始，不可超出列表长度)");
            } else {
                sender.sendMessage("设置成功");
                printEditorToSender(sender, label);
            }
            return true;
        }
        if (args[0].equals("remove")) {
            if (args.length < 2) return false;
            int removeIndex;
            try {
                removeIndex = Integer.parseInt(args[1]);
            } catch (NumberFormatException nfe) {
                sender.sendMessage("输入的序号有误，无法解析为数字");
                return true;
            }

            MessageReader.setRawWelcomeMessage(removeIndex, null);
            sender.sendMessage("删除成功");
            printEditorToSender(sender, label);

            return true;
        }
        if (args[0].equals("preview")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("您必须是一个玩家");
                return true;
            }
            List<String> welcomeMessage = MessageReader.getWelcomeMessage((Player) sender);
            for (String messageLine : welcomeMessage) {
                sender.sendMessage(messageLine);
            }
            return true;
        }
        return false;
    }

    private static void printEditorToSender(@NotNull CommandSender sender, @NotNull String label) {
        List<String> rawWelcomeMessage = MessageReader.getRawWelcomeMessage();

        sender.sendMessage("正在编辑欢迎消息 - 下方信息行可点击");
        int index = 0;
        for (String rawMessage : rawWelcomeMessage) {
            TextComponent delBtn = new TextComponent("[X] ");
            delBtn.setColor(ChatColor.RED);
            delBtn.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                    MessageFormat.format("/{0} remove {1}", label, index)
            ));

            TextComponent message = new TextComponent(rawMessage);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                    MessageFormat.format("/{0} set {1} {2}", label, index, rawMessage)
            ));

            sender.spigot().sendMessage(delBtn, message);
            index++;
        }
        TextComponent addBtn = new TextComponent("[+] 添加新行");
        addBtn.setColor(ChatColor.GREEN);
        addBtn.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                MessageFormat.format("/{0} add", label)
        ));
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 2) return List.of("list", "add", "set", "remove", "preview");
        else return List.of();
    }
}
