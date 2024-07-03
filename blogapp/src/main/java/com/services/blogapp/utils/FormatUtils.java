package com.services.blogapp.utils;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class FormatUtils {
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public static DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	public static SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

}
