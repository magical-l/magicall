package me.magicall.util;

/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of Sun Microsystems nor the names of its
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatDemo {

	static public void displayDate(final Locale currentLocale) {
		final DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
		final Date today = new Date();
		final String dateOut = dateFormatter.format(today);
		System.out.println(dateOut + ' ' + currentLocale.toString());
	}

	static public void showBothStyles(final Locale currentLocale) {
		final int[] styles = { DateFormat.DEFAULT, DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL };
		System.out.println();
		System.out.println("Locale: " + currentLocale.toString());
		System.out.println();
		final Date today = new Date();
		for (final int style : styles) {
			final DateFormat formatter = DateFormat.getDateTimeInstance(style, style, currentLocale);
			final String result = formatter.format(today);
			System.out.println(result);
		}
	}

	static public void showDateStyles(final Locale currentLocale) {
		final Date today = new Date();
		String result;
		DateFormat formatter;
		final int[] styles = { DateFormat.DEFAULT, DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL };
		System.out.println();
		System.out.println("Locale: " + currentLocale.toString());
		System.out.println();
		for (final int style : styles) {
			formatter = DateFormat.getDateInstance(style, currentLocale);
			result = formatter.format(today);
			System.out.println(result);
		}
	}

	static public void showTimeStyles(final Locale currentLocale) {
		final Date today = new Date();
		String result;
		DateFormat formatter;
		final int[] styles = { DateFormat.DEFAULT, DateFormat.SHORT, DateFormat.MEDIUM, DateFormat.LONG, DateFormat.FULL };
		System.out.println();
		System.out.println("Locale: " + currentLocale.toString());
		System.out.println();
		for (final int style : styles) {
			formatter = DateFormat.getTimeInstance(style, currentLocale);
			result = formatter.format(today);
			System.out.println(result);
		}
	}

	static public void main(final String... args) {
		final Locale[] locales = { new Locale("zh", "CH"), new Locale("de", "DE"), new Locale("en", "US") };
		for (final Locale locale : locales) {
			displayDate(locale);
		}
		showDateStyles(new Locale("en", "US"));
		showDateStyles(new Locale("zh", "CH"));
		showTimeStyles(new Locale("en", "US"));
		showTimeStyles(new Locale("de", "DE"));
		showBothStyles(new Locale("en", "US"));
		showBothStyles(new Locale("fr", "FR"));
	}
}