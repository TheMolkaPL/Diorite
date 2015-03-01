package diorite.impl.connection.packets.play.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.EnumProtocol;
import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.packets.PacketClass;
import diorite.impl.connection.packets.PacketDataSerializer;
import diorite.impl.connection.packets.play.PacketPlayOutListener;
import diorite.impl.map.chunk.ChunkImpl;
import diorite.impl.map.chunk.ChunkPartImpl;
import diorite.map.chunk.ChunkPos;


@PacketClass(id = 0x26, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketPlayOutMapChunkBulk implements PacketPlayOut
{
    private boolean     skyLight;
    private ChunkMeta[] metas;
    private ChunkImpl[] chunks;

    public PacketPlayOutMapChunkBulk()
    {
    }

    public PacketPlayOutMapChunkBulk(final boolean skyLight, final ChunkImpl[] chunks)
    {
        this.skyLight = skyLight;
        this.chunks = chunks;

        this.metas = new ChunkMeta[chunks.length];
        for (int k = 0, chunksLength = chunks.length; k < chunksLength; k++)
        {
            final ChunkImpl chunk = chunks[k];
            int mask = chunk.getMask();

            final ChunkPartImpl[] chunkParts = chunk.getChunkParts();
            for (int i = 0, chunkPartsLength = chunkParts.length; i < chunkPartsLength; i++)
            {
                final ChunkPartImpl part = chunkParts[i];
                if ((part == null) || part.isEmpty())
                {
                    mask &= ~ (1 << i);
                }
            }

            this.metas[k] = new ChunkMeta(chunk.getPos(), mask);
        }
    }

    public PacketPlayOutMapChunkBulk(final boolean skyLight, final ChunkImpl[] chunks, final ChunkMeta[] metas)
    {
        this.skyLight = skyLight;
        this.chunks = chunks;
        this.metas = metas;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        // TODO: implement
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeBoolean(this.skyLight);
        data.writeVarInt(this.chunks.length);
        for (final ChunkMeta meta : this.metas)
        {
            data.writeInt(meta.getPos().getX());
            data.writeInt(meta.getPos().getZ());
            data.writeShort(meta.getMask());
        }
        final ChunkImpl[] chunks1 = this.chunks;
        for (int i = 0, chunks1Length = chunks1.length; i < chunks1Length; i++)
        {
            final ChunkImpl chunk = chunks1[i];
            data.writeChunkSimple(chunk, this.metas[i].getMask(), this.skyLight, true, false);
        }
    }

    @Override
    public void handle(final PacketPlayOutListener listener)
    {
        listener.handle(this);
    }

    public boolean isSkyLight()
    {
        return this.skyLight;
    }

    public void setSkyLight(final boolean skyLight)
    {
        this.skyLight = skyLight;
    }

    public ChunkMeta[] getMetas()
    {
        return this.metas;
    }

    public void setMetas(final ChunkMeta[] metas)
    {
        this.metas = metas;
    }

    public ChunkImpl[] getChunks()
    {
        return this.chunks;
    }

    public void setChunks(final ChunkImpl[] chunks)
    {
        this.chunks = chunks;
    }

    public static class ChunkMeta
    {
        private ChunkPos pos;
        private int      mask;

        public ChunkMeta(final ChunkPos pos, final int mask)
        {
            this.pos = pos;
            this.mask = mask;
        }

        public ChunkPos getPos()
        {
            return this.pos;
        }

        public void setPos(final ChunkPos pos)
        {
            this.pos = pos;
        }

        public int getMask()
        {
            return this.mask;
        }

        public void setMask(final int mask)
        {
            this.mask = mask;
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pos", this.pos).append("mask", this.mask).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("skyLight", this.skyLight).append("metas", this.metas).append("chunks", this.chunks).toString();
    }
}
