package com.foenichs.bonfire

import com.foenichs.bonfire.command.ChunkCommand
import com.foenichs.bonfire.listener.PlayerListener
import com.foenichs.bonfire.service.ClaimService
import com.foenichs.bonfire.service.LimitService
import com.foenichs.bonfire.storage.ClaimRegistry
import com.foenichs.bonfire.storage.DatabaseManager
import com.foenichs.bonfire.ui.Messenger
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Bonfire : JavaPlugin() {
    private lateinit var db: DatabaseManager

    override fun onEnable() {
        saveDefaultConfig()

        db = DatabaseManager(dataFolder)
        val registry = ClaimRegistry(db.loadAll().toMutableList())

        val msg = Messenger()
        val limits = LimitService(config, registry)
        val service = ClaimService(registry, db, msg, limits)

        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            ChunkCommand(service, registry, limits, msg).register(event.registrar())
        }

        Bukkit.getPluginManager().registerEvents(PlayerListener(registry, msg), this)
    }

    override fun onDisable() {
        if (::db.isInitialized) {
            db.close()
        }
    }
}