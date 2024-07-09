package com.jefferson.api_gestao_voluntarios.modules.utils;


import jakarta.persistence.AttributeConverter;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter implements AttributeConverter<UUID, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]); // Cria um buffer de bytes de 16 bytes
        byteBuffer.putLong(uuid.getMostSignificantBits()); // Adiciona os 8 bytes mais significativos do UUID
        byteBuffer.putLong(uuid.getLeastSignificantBits()); // Adiciona os 8 bytes menos significativos do UUID
        return byteBuffer.array(); // Retorna o array de bytes resultante
    }

    @Override
    public UUID convertToEntityAttribute(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes); // Envolve os bytes em um ByteBuffer
        long high = byteBuffer.getLong(); // Lê os 8 bytes mais significativos
        long low = byteBuffer.getLong(); // Lê os 8 bytes menos significativos
        return new UUID(high, low); // Cria um novo UUID com esses bits
    }
}
