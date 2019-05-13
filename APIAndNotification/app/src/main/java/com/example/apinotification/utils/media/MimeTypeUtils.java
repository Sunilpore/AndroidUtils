package com.example.apinotification.utils.media;

import android.webkit.MimeTypeMap;

import com.example.apinotification.utils.constants.Constants;

public class MimeTypeUtils {

    public static String getMimeType(String path) {
        int index;
        if (path == null || (index = path.lastIndexOf('.')) == -1)
            return Constants.MIME_TYPE.UNKNOWN_MIME_TYPE;

        String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(path.substring(index + 1).toLowerCase());
        return mime != null ? mime : Constants.MIME_TYPE.UNKNOWN_MIME_TYPE;
    }

    public static String getGenericMIME(String mime) {
        return mime.split("/")[0] + "/*";
    }

    public static String getTypeMime(String mime) {
        return mime.split("/")[0];
    }

}
