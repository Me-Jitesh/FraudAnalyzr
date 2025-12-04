package com.jitesh.fraudanalyzr.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jitesh.fraudanalyzr.models.Transaction;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class TransactionDeserializer implements Deserializer<Transaction> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Transaction deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, Transaction.class);
        } catch (Exception e) {
            throw new SerializationException("Error While Deserializing Transaction !", e);
        }
    }
}
