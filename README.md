# ZthWelcomeMessage

一款用于在登入服务器时为玩家发送聊天框提示消息的插件。

## 特性

支持仅 Spigot 服务端，支持 PlaceholderAPI 变量，支持完全的游戏内可视化编辑。

## 问题

Q：为什么要又造一个这么常见的插件？

A：翻了半天现有的插件，没有找到能在服务器内通过指令编辑的欢迎消息插件，遂开坑......

## 使用

主指令 `/welcome-msg`
可用参数：

* `/welcome-msg list` 列出所有的欢迎消息的行，支持可视化点击
* `/welcome-msg add <message>` 在最后一行插入新消息
* `/welcome-msg set <index> <new-message>` 根据消息的序号索引（从 0 开始）来编辑消息。您也可以直接点击列表中已有的消息来直接向聊天栏填充此指令。
* `/welcome-msg remove <index>` 根据序号索引删除消息
* `/welcome-msg preview` 用当前 Player 的身份预览效果