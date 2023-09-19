package ink.magma.zthwelcomemessage.reader;

import ink.magma.zthwelcomemessage.ZthWelcomeMessage;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;

public class MessageReader {
    /**
     * 获取解析后的欢迎消息列表
     *
     * @param player PAPI 变量玩家
     * @return 结果
     */
    public static List<String> getWelcomeMessage(@Nullable Player player) {
        List<String> welcome = ZthWelcomeMessage.instance.getConfig().getStringList("welcome");

        for (int i = 0; i < welcome.size(); i++) {
            String welcomeLine = welcome.get(i);
            welcomeLine = ChatColor.translateAlternateColorCodes('&', welcomeLine);
            if (ZthWelcomeMessage.enablePlaceholder && player != null) {
                welcomeLine = PlaceholderAPI.setPlaceholders(player, welcomeLine);
            }
            welcome.set(i, welcomeLine); // 将修改后的 welcomeLine 设置回列表中
        }

        return welcome;
    }

    /**
     * 获取未解析的欢迎消息
     *
     * @return 结果
     */
    public static List<String> getRawWelcomeMessage() {
        return ZthWelcomeMessage.instance.getConfig().getStringList("welcome");
    }

    /**
     * 设置或删除某个欢迎消息
     *
     * @param index       数组中的索引
     * @param messageLine 消息字符串, null 为删除
     * @return 1 为失败
     */
    public static int setRawWelcomeMessage(int index, @Nullable String messageLine) {
        List<String> welcome = ZthWelcomeMessage.instance.getConfig().getStringList("welcome");
        if (index < 0 || index >= welcome.size()) {
            return 1;
        }
        if (messageLine == null) {
            welcome.remove(index);
        } else {
            welcome.set(index, messageLine);
        }
        ZthWelcomeMessage.instance.getConfig().set("welcome", welcome);
        ZthWelcomeMessage.instance.saveConfig();
        return 0;
    }

    /**
     * 向列表添加一条信息
     *
     * @param message 信息
     */
    public static void addRawWelcomeMessage(String message) {
        List<String> welcome = ZthWelcomeMessage.instance.getConfig().getStringList("welcome");
        welcome.add(message);
        ZthWelcomeMessage.instance.getConfig().set("welcome", welcome);
        ZthWelcomeMessage.instance.saveConfig();
    }
}
