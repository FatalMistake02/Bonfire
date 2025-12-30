package com.foenichs.bonfire.ui

import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.`object`.ObjectContents
import org.bukkit.entity.Player

class Messenger {
    private fun icon() = Component.`object`(ObjectContents.sprite(Key.key("items"), Key.key("item/campfire")))
        .hoverEvent(HoverEvent.showText(Component.text("Bonfire")))

    fun head(name: String) = Component.`object`(ObjectContents.playerHead(name))

    fun send(p: Player, content: Component) {
        p.sendMessage(
            Component.text().append(Component.newline()).append(icon()).append(Component.space()).append(content)
                .append(Component.newline()).build()
        )
    }

    fun actionBar(p: Player, ownerName: String) {
        p.sendActionBar(
            Component.text().append(head(ownerName)).append(Component.space()).append(Component.text(ownerName)).build()
        )
    }

    fun unclaimedBar(p: Player) {
        p.sendActionBar(
            Component.text().append(icon()).append(Component.space())
                .append(Component.text("Unclaimed", NamedTextColor.WHITE)).build()
        )
    }

    fun sendNoAccess(p: Player) {
        send(
            p,
            Component.text().append(Component.text(" You can't claim chunks yet. "))
                .append(Component.text("Claim limits increase depending on your playtime.", NamedTextColor.GRAY))
                .build()
        )
    }
}