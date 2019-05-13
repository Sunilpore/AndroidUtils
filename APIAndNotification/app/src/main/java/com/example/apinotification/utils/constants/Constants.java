package com.example.apinotification.utils.constants;

public class Constants {

    public static class SharedPreferenceTag {
        public static final String myPrefK = "PreferenceRecord";

        public static final String mLoginKey = "login_key_for_employee";
        public static final String mSignUpKey = "sign_up_key_for_employee";
        public static final String mUserIdKey = "user_id_key_for_employee";
        public static final String mAddressKey = "address_key_for_employee";
        public static final String mDateKey = "date_key_for_employee";

    }

    //changed
    public static class MAP {
        public static int CAMERA_ANIMATION_DELAY_TIME = 1000;

    }

    //changed
    public static class DATETIME {
        public static final String TAG = "DateTime";
        public static final String DATETIME_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss";
        public static final String DATETIME_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        public static final String DATETIME_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        public static final String DATETIME_FORMAT_4 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZ";
        public static final String DATETIME_FORMAT_5 = "yyyy-MM-dd'T'HH:mm:ssZZZ";
        public static final String DATETIME_FORMAT_6 = "yyyyMMdd_HHmmss";
        public static final String DATETIME_FORMAT_7 = "dd/MM HH:mm";
        public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static final String TIMEZONE_UTC = "UTC";
        public static final String D = "DAYS";
        public static final String H = "HOURS";
        public static final String M = "MINUTES";
        public static final String S = "SECONDS";


    }

    //created
    public static class PRIVATE_FILE {
        public static final String CRASH_LOG_DIR_NAME = "CrashLogData";
        public static final String JSON_DATA_DIR_NAME = "JsonData";
        public static final String JSON_LIST_FILE_NAME = "json_dashboard_list_ui_file";
        public static final String JSON_DEVICE_DETAILS_FILE_NAME = "json_device_details_ui_file";
        public static final String JSON_MORE_MENU_OPTIONS_FILE_NAME = "json_more_menu_options_ui_file";
        public static final String CRASH_LOG_FILE_NAME = "crash_logs";
    }

    public static class DATABASE {

        public static final String DATABASE_NAME = "Vts.db";


    }

    public static class MIME_TYPE {

        public static final String UNKNOWN_MIME_TYPE = "unknown/unknown";

    }

    public static class NETWORK {

        public static final String TYPE_WIFI = "WIFI";
        public static final String TYPE_MOBILE = "MOBILE_DATA";
        public static final String TYPE_NOT_CONNECTED = "NO_CONNECTION";

        //changes
        public static final int connectTimeout = 3;
        public static final int readTimeout = 3;
        public static final int writeTimeout = 3;

    }

    public static class PhotoActivityTag {

        public static final String bottomAction = "bottom_sheet_action";
    }

    public static class EnlargeActivityTag {

        public static final String enlargeImage = "enlarge_image_action";
        public static final String imageDetails = "detail_image_action";
    }


    public static class DocumentDetailsTag {
        public static final String documentDetails = "document_detail_key";
        public static final String docPosition = "document_position";
    }


    public static class AbstractVarTag {

        public static final String NULL_STRING = "";
        public static final String SUB_CLASS_INITIALIZE = "assign_sub_class";
        public static final String API_CALL = "api_access_permission";
    }

    public static class ACTION {

        public static final String MAIN_ACTION = "music.action.main";
        public static final String PAUSE_ACTION = "music.action.pause";
        public static final String PLAY_ACTION = "music.action.play";
        public static final String START_ACTION = "music.action.start";
        public static final String STOP_ACTION = "music.action.stop";

    }

    public static class STATE_SERVICE {

        public static final int PREPARE = 30;
        public static final int PLAY = 20;
        public static final int PAUSE = 10;
        public static final int NOT_INIT = 0;
    }

    //changes
    public static class VehicleStatus {

        public static final String base_url_icon = "http://192.168.0.104/my_account_db/src/routes/icons_data/";

        public static final String ALL = "ALL";
        public static final String ONLINE = "ONLINE";
        public static final String OFFLINE = "OFFLINE";
        public static final String INACTIVE = "INACTIVE";
        public static final String IDLING = "IDLING";
        public static final String MOVING = "MOVING";
        public static final String STATIC = "STATIC";
        public static final String LIST_ICON_DIR_NAME = "list_icons_data";
        public static final String DEVICE_DETAILS_ICON_DIR_NAME = "device_details_icons_data";
        /*public static final String STATIC_TRUE = "STATIC_TRUE";
        public static final String STATIC_FALSE = "STATIC_FALSE";*/

    }



    public static class GeofencingTAG {
        public static final String GeoFencingTAG = "geo_fencing_key";

        public static final int DeviceReqCode = 123;
        public static final String GeoFencingAction = "geo_fencing_action_key";
        public static final String GeoFencingDeviceListTAG = "geo_fencing_device_key";
        public static final String GeoFencingDeviceList2TAG = "geo_fencing_device2_key";

    }

    public static class RequestPermissionTAG{

        public static final int GPS_PERMISSION = 101;
    }

    public static class DeviceInfo{

        public static final String DEVICE_ID = "device_id";
    }

    public static class  VehicleMangment{

        public static final String VehicleDeviceIdAction = "vehicle_id_action_key";
    }

}
