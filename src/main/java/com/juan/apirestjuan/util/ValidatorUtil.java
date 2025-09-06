package com.juan.apirestjuan.util;

import com.juan.apirestjuan.model.User;
import java.util.*;
import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final String RFC_REGEX = "^([A-ZÑ&]{4}|[A-ZÑ&]{3}-)[0-9]{6}[A-Z0-9]{3}$";
    private static final Pattern RFC_PATTERN = Pattern.compile(RFC_REGEX);
    private static final String PHONE_REGEX = "^\\+[1-9]\\d{1,14}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    public static boolean isValidFormatTax_id(String tax_id){
        if (tax_id == null || tax_id.isEmpty()) return false;
        return RFC_PATTERN.matcher(tax_id).matches();
    }

    public static boolean isValidUniqueTax_id(List<User> users, String tax_id){
        return users.stream().anyMatch(user -> user.getTax_id().equals(tax_id));
    }

    public static boolean isValidFormatPhone(String phone){
        if(phone == null || phone.isEmpty()) return false;
        return PHONE_PATTERN.matcher(phone).matches();
    }
}