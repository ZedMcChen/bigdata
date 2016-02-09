package com.zedmcchen.log.parsers.utils;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class GzipFilesTest {
    
    @Test
    public void shouldCreateStringStreamFromGzipFile() throws URISyntaxException {
        String filename = "testdata/text.txt.gz";
        Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());
        
        long lineCount = GzipFiles.lines(path).count();
        assertThat(lineCount, is(5L));
        
        List<String> texts = GzipFiles.lines(path).collect(Collectors.toList());
        assertThat(texts, is(Arrays.asList("this", "is", "a", "test", "file")));
    }
}
