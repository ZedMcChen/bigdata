/**
 * Copyright Zhiming Chen 2016
 */
package com.zedmcchen.weblog.parsers.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

/**
 * @author zhiming
 *
 */
public class GzipFiles {
	private static int BUFFER_SIZE = 8 * 1024;
    public static Stream<String> lines(Path path) {
		InputStream inputStream = null;
		BufferedInputStream bufferedInputStream = null;
		GZIPInputStream gzipInputStream = null;

		try {
			inputStream = Files.newInputStream(path);
    		bufferedInputStream = new BufferedInputStream(inputStream, BUFFER_SIZE);
    		gzipInputStream = new GZIPInputStream(bufferedInputStream);
		}
		catch (IOException e) {
			closeInput(gzipInputStream);
			closeInput(bufferedInputStream);
			closeInput(inputStream);
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gzipInputStream));	
		return bufferedReader.lines().onClose(() -> closeInput(bufferedReader));
     }

	private static void closeInput(Closeable input) {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				System.err.println(e);;
			}
		}
	}
}
