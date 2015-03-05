package diorite.impl.map.chunk;

import diorite.map.chunk.Chunk;
import diorite.map.chunk.ChunkPos;

public class ChunkImpl implements Chunk
{
    private final ChunkPos pos;
    private final ChunkPartImpl[] chunkParts; // size of 16, parts can be null
    private final byte[]          biomes;

    public ChunkImpl(final ChunkPos pos, final byte[] biomes, final ChunkPartImpl[] chunkParts)
    {
        this.pos = pos;
        this.biomes = biomes;
        this.chunkParts = chunkParts;
    }

    public ChunkImpl(final ChunkPos pos, final ChunkPartImpl[] chunkParts)
    {
        this.pos = pos;
        this.chunkParts = chunkParts;
        this.biomes = new byte[CHUNK_SIZE * CHUNK_SIZE];
    }

    public ChunkImpl(final ChunkPos pos)
    {
        this.pos = pos;
        this.chunkParts = new ChunkPartImpl[CHUNK_PARTS];
        this.biomes = new byte[CHUNK_SIZE * CHUNK_SIZE];
    }

    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
    {
        final byte chunkPosY = (byte) (y / CHUNK_PART_HEIGHT);
        ChunkPartImpl chunkPart = this.chunkParts[chunkPosY];
        if (chunkPart == null)
        {
            chunkPart = new ChunkPartImpl(chunkPosY);
            this.chunkParts[chunkPosY] = chunkPart;
        }
        chunkPart.setBlock(x, y, z, id, meta);
    }

    public ChunkPos getPos()
    {
        return this.pos;
    }

    public byte[] getBiomes()
    {
        return this.biomes;
    }

//    public void setBlock(final int x, final int y, final int z, final int id, final int meta)
//    {
//        this.setBlock(x, y, z, id, meta);
//    }

    // set bit to 1: variable |= (1 << bit)
    // switch bit  : variable ^= (1 << bit)
    // set bit to 0: variable &= ~(1 << bit)
    public int getMask()
    {
        int mask = 0x0;
        for (int i = 0, chunkPartsLength = this.chunkParts.length; i < chunkPartsLength; i++)
        {
            if ((this.chunkParts[i] != null) && ! this.chunkParts[i].isEmpty())
            {
                mask |= (1 << i);
            }
        }
        return mask;
    }

    public void recalculateBlockCounts()
    {
        for (final ChunkPartImpl chunkPart : this.chunkParts)
        {
            if (chunkPart == null)
            {
                continue;
            }
            chunkPart.recalculateBlockCount();
        }
    }

    public ChunkPartImpl[] getChunkParts()
    {
        return this.chunkParts;
    }
}
