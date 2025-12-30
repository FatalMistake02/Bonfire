package com.foenichs.bonfire.listener

import com.foenichs.bonfire.storage.ClaimRegistry
import com.foenichs.bonfire.ui.Messenger
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import java.util.*

class PlayerListener(private val registry: ClaimRegistry, private val msg: Messenger) : Listener {
    private val lastOwners = mutableMapOf<UUID, UUID?>()

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val p = event.player
        val c = registry.getAt(event.to.chunk)
        val curr = c?.owner
        if (lastOwners[p.uniqueId] != curr || !lastOwners.containsKey(p.uniqueId)) {
            lastOwners[p.uniqueId] = curr; p.updateCommands()
            if (curr != null) msg.actionBar(p, Bukkit.getOfflinePlayer(curr).name ?: "Unknown")
            else msg.unclaimedBar(p)
        }
        p.updateCommands()
    }
}