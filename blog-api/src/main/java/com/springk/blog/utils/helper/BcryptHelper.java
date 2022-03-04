package com.springk.blog.utils.helper;

import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@NoArgsConstructor
public class BcryptHelper {
    private static Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    public static boolean checkPassEncodeByBcrypt(String passwordEncode){
        return BCRYPT_PATTERN.matcher(passwordEncode).matches();
    }
}
