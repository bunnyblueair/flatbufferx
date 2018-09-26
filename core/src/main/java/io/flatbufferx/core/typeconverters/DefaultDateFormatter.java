package io.flatbufferx.core.typeconverters;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Attempt at making a DateFormat that can actually parse ISO 8601 dates correctly
 */
public class DefaultDateFormatter extends SimpleDateFormat {

    public DefaultDateFormatter() {
        super("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Replace ending Z's with +0000 so Java's SimpleDateFormat can handle it correctly
     */
    private String getFixedInputString(String input) {
        return input != null ? input.replaceAll("Z$", "+0000") : null;
    }

    @Override
    public Date parse(String string) throws ParseException {
        return super.parse(getFixedInputString(string));
    }

    @Override
    public Object parseObject(String string, ParsePosition position) {
        return super.parseObject(getFixedInputString(string), position);
    }

    @Override
    public Object parseObject(String string) throws ParseException {
        return super.parseObject(getFixedInputString(string));
    }

    @Override
    public Date parse(String string, ParsePosition position) {
        return super.parse(getFixedInputString(string), position);
    }
}