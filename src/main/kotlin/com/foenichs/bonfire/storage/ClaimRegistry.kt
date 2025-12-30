package com.foenichs.bonfire.storage

import com.foenichs.bonfire.model.Claim
import org.bukkit.Chunk
import java.util.*

class ClaimRegistry(private val claims: MutableList<Claim>) {
    fun getAll() = claims
    fun getAt(w: UUID, k: Long) = claims.find { c -> c.chunks.any { it.worldUuid == w && it.chunkKey == k } }
    fun getAt(chunk: Chunk) = getAt(chunk.world.uid, chunk.chunkKey)
    fun getOwnedChunks(u: UUID) = claims.filter { it.owner == u }.sumOf { it.chunks.size }
    fun getOwnedClaimsCount(u: UUID) = claims.count { it.owner == u }
    fun add(c: Claim) = claims.add(c)
    fun remove(c: Claim) = claims.remove(c)
}