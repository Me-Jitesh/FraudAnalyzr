package com.jitesh.fraudanalyzr.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jitesh.fraudanalyzr.models.Transaction;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class TransactionSerializer implements Serializer<Transaction> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Transaction data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error While Serializing Transaction !", e);
        }
    }
}
