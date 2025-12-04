package com.jitesh.fraudanalyzr.serdes;

import com.jitesh.fraudanalyzr.models.Transaction;
import org.apache.kafka.common.serialization.Serdes;

public class TransactionSerde extends Serdes.WrapperSerde<Transaction> {
    public TransactionSerde() {
        super(new TransactionSerializer(), new TransactionDeserializer());
    }
}
