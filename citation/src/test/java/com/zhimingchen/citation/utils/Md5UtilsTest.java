package com.zhimingchen.citation.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Md5UtilsTest {

    @Test
    public void shouldGenerateMd5HashWithLengthOf32() {
        String hashShort = Md5Utils.getMD5("short");
        assertThat(hashShort.length(), is(32));
        assertThat(hashShort, is("4f09daa9d95bcb166a302407a0e0babe"));

        String hashLong = Md5Utils.getMD5("longlonglong long long long longlonglong long long long ");
        assertThat(hashLong.length(), is(32));
        assertThat(hashLong, is("44c1093bc81b74a265e70a9d0657a678"));


        String hashVeryLong = Md5Utils.getMD5("very very long long long long long longlong long long "
                                            + "very very long long long long long longlong long long "
                                            + "very very long long long long long longlong long long "
                                            + "very very long long long long long longlong long long "
                                            + "very very long long long long long longlong long long ");
        assertThat(hashVeryLong.length(), is(32));
        assertThat(hashVeryLong, is("56c4d4f07d349a337f7cc951574b317d"));
}
}
