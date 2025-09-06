package com.juan.apirestjuan.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimeUtil {

    public static String ValidCreated_at(){
        // dd-mm-yyyy HH:mm
        ZoneId Madagascar_ZoneId = ZoneId.of("Indian/Antananarivo");
        ZonedDateTime zonedDateTime = ZonedDateTime.now(Madagascar_ZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm");
        String Madagascar_Date_Format = zonedDateTime.format(formatter);
        return Madagascar_Date_Format;
    }
}