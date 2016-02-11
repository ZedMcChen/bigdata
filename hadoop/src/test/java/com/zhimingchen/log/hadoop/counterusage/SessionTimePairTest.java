package com.zhimingchen.log.hadoop.counterusage;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SessionTimePairTest {
    @Test
    public void shouldWriteAndReadCorrectly() throws IOException {
        String expectedSessionId = "jsessionid";
        long   expectedEpochTime = 123456789L;
        SessionTimePair expectedStPair = new SessionTimePair(expectedSessionId, expectedEpochTime);
        
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteStream);
        expectedStPair.write(dataOutputStream);
        dataOutputStream.close();
        
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteStream.toByteArray());
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        SessionTimePair actualStPair = new SessionTimePair();
        actualStPair.readFields(dataInputStream);
        
        assertThat(actualStPair, is(expectedStPair));
        assertThat(actualStPair.getSessionId().toString(), is(expectedSessionId));
        assertThat(actualStPair.getEpochTime().get(), is(expectedEpochTime));
    }
    
    @Test
    public void shouldSortInCorrectOrder() {
        SessionTimePair stp1 = new SessionTimePair("x", 6l);
        SessionTimePair stp2 = new SessionTimePair("z", 1l);
        SessionTimePair stp3 = new SessionTimePair("x", 2l);
        SessionTimePair stp4 = new SessionTimePair("y", 3l);
        SessionTimePair[] stPairs = {stp1, stp2, stp3, stp4};
        
        Arrays.sort(stPairs, (a, b) -> (a.compareTo(b)));
        
        assertThat(stPairs[0], is(stp3));
        assertThat(stPairs[1], is(stp1));
        assertThat(stPairs[2], is(stp4));
        assertThat(stPairs[3], is(stp2));
    }
}
