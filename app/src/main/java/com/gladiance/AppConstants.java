// Copyright 2020 Espressif Systems (Shanghai) PTE LTD
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.gladiance;

import com.gladiance.BuildConfig;

import java.util.ArrayList;

public class AppConstants {

    // Keys used to pass data between activities and to store data in SharedPreference.
    public static final String KEY_WIFI_SECURITY_TYPE = "wifi_security";
    public static final String KEY_PROOF_OF_POSSESSION = "proof_of_possession";
   // public static String MAC = "mac";
    public static final String KEY_WIFI_DEVICE_NAME_PREFIX = "wifi_network_name_prefix";
    public static final String KEY_BLE_DEVICE_NAME_PREFIX = "ble_device_name_prefix";
    public static final String KEY_DEVICE_NAME = "device_name";
    public static final String KEY_STATUS_MSG = "status_msg";
    public static final String KEY_WIFI_SSID = "ssid";
    public static final String KEY_WIFI_PASSWORD = "password";
    public static final String KEY_DEVICE_TYPES = "device_types";
    public static final String KEY_SECURITY_TYPE = "security_type";
    public static final String KEY_USER_NAME = "sec2_username";
    public static final String CURRENT_VERSION = "v1";
    public static final String PATH_SEPARATOR = "/";
    public static final String KEY_NODE_DETAILS = "node_details";
    public static final String KEY_ID = "id";
    public static final String NODE_TABLE = "node_table";
    public static final String KEY_FAILURE_RESPONSE = "failure";


   public static final String KEY_DEVICE_NAME_PREFIX = "device_prefix";
   public static final String KEY_ESP_DEVICE = "esp_device";
   public static final String KEY_NODE_ID = "node_id";
   public static final String KEY_EMAIL = "email";
   public static final String KEY_USER_ID = "user_id";
   public static final String KEY_ID_TOKEN = "id_token";
   public static final String KEY_ACCESS_TOKEN = "access_token";
   public static final String KEY_REFRESH_TOKEN = "refresh_token";
   public static final String KEY_IS_OAUTH_LOGIN = "is_github_login";
   public static final String KEY_ERROR_MSG = "err_msg";
   public static final String KEY_SSID = "ssid";
   public static final String KEY_PASSWORD = "password";
   public static final String KEY_SHOULD_SAVE_PWD = "save_password";
   public static final String KEY_NEW_PASSWORD = "newpassword";
   public static final String KEY_REQUEST = "request";
   public static final String KEY_VERIFICATION_CODE = "verification_code";
   public static final String KEY_MOBILE_DEVICE_TOKEN = "mobile_device_token";
   public static final String KEY_PLATFORM = "platform";
   public static final String KEY_GCM = "GCM";
   public static final String KEY_MESSAGE_BODY = "message_body";
   public static final String KEY_ALERT_STRING = "esp.alert.str";
   public static final String KEY_PARAM_NAME = "param_name";
   public static final String KEY_AGGREGATE = "aggregate";
   public static final String KEY_AGGREGATION_INTERVAL = "aggregation_interval";
   public static final String KEY_WEEK_START = "week_start";
   public static final String KEY_START_TIME = "start_time";
   public static final String KEY_END_TIME = "end_time";
   public static final String KEY_NUM_INTERVALS = "num_intervals";
   public static final String KEY_NUM_RECORDS = "num_records";
   public static final String KEY_TIMEZONE = "timezone";
   public static final String KEY_OTA_JOB_ID = "ota_job_id";
   public static final String KEY_OTA_DETAILS = "ota_details";
   public static final String KEY_OTA_AVAILABLE = "ota_available";

    public static final String URL_CLAIM_INITIATE = BuildConfig.CLAIM_BASE_URL + "/claim/initiate";

    public static final String URL_USER_NODE_GROUP = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/node_group";

    public static final String URL_USER_NODE_AUTOMATION = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/node_automation";

    public static final String URL_USER_NODES_SHARING_REQUESTS = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/sharing/requests";

    public static final String URL_USER_NODES_SHARING = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/sharing";

    public static final String URL_USER_NODES_TS = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/tsdata";

    public static final String URL_NODE_OTA_UPDATE = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/ota_update";

    public static final String URL_NODE_OTA_STATUS = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/ota_status";

    // Cloud API End point Urls
    public static final String URL_LOGIN = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/login";

    public static final String URL_USER = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user";

    public static final String URL_LOGIN_2 = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/login2";
    public static final String URL_USER_2 = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user2";

    public static final String MDNS_SERVICE_TYPE = "_esp_local_ctrl._tcp.";

   public static final String LOCAL_CONTROL_ENDPOINT = "esp_local_ctrl/control";

 public static final String LOCAL_SESSION_ENDPOINT = "esp_local_ctrl/session";

    public static final String KEY_DEVICES = "devices";


    public static final String GROUP_TABLE = "group_table";

    public static final String KEY_EVENT_VERSION = "event_version";
    public static final String KEY_EVENT_TYPE = "event_type";
    public static final String KEY_EVENT_DATA = "event_data";

    public static final String ESP_PREFERENCES = "Esp_Preferences";

    public static final String DEVICE_TYPE_SOFTAP = "softap";
    public static final String DEVICE_TYPE_BLE = "ble";
    public static final String DEVICE_TYPE_BOTH = "both";
    public static final String DEVICE_TYPE_DEFAULT = DEVICE_TYPE_BOTH;
    public static final String KEY_ROLE = "role";
    public static final String KEY_CONFIG = "config";
    public static final String KEY_PARAMS = "params";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_NOTIFICATION_MSG = "notification_msg";

    public static final String KEY_STATUS = "status";
    public static final String KEY_TIME = "Time";
    public static final String KEY_CONNECTIVITY = "connectivity";
    public static final String KEY_CONNECTED = "connected";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String ESP_DATABASE_NAME = "esp_db";
    public static final String NOTIFICATION_TABLE = "notification_table";

    public enum UpdateEventType {

        EVENT_DEVICE_ADDED,
        EVENT_DEVICE_REMOVED,
        EVENT_DEVICE_ONLINE,
        EVENT_DEVICE_OFFLINE,
        EVENT_ADD_DEVICE_TIME_OUT,
        EVENT_DEVICE_STATUS_UPDATE,
        EVENT_STATE_CHANGE_UPDATE,
        EVENT_LOCAL_DEVICE_UPDATE
    }

    public static final String KEY_NAME = "name";
    public static final String KEY_TYPE = "type";
    public static final String KEY_DATA_TYPE = "data_type";
    public static final String KEY_UI_TYPE = "ui_type";

    public static final int SEC_TYPE_0 = 0;
    public static final int SEC_TYPE_1 = 1;
    public static final int SEC_TYPE_2 = 2;
    public static final int SEC_TYPE_DEFAULT = SEC_TYPE_2;
    public static final String DEFAULT_USER_NAME = "wifiprov";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String URL_USER_NODES = "https://api.rainmaker.espressif.com" + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes";
   public static final String PREF_FILE_WIFI_NETWORKS = "wifi_networks";

    public static final String URL_USER_NODES_PARAMS = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/params";

    public static final String URL_CLAIM_VERIFY = BuildConfig.CLAIM_BASE_URL + "/claim/verify";


    // Service Types
    public static final String SERVICE_TYPE_SCHEDULE = "esp.service.schedule";
    public static final String SERVICE_TYPE_SCENES = "esp.service.scenes";
    public static final String SERVICE_TYPE_TIME = "esp.service.time";
    public static final String SERVICE_TYPE_LOCAL_CONTROL = "esp.service.local_control";
    public static final String SERVICE_TYPE_SYSTEM = "esp.service.system";


    public static final String KEY_SUPPORTED_VERSIONS = "supported_versions";
    public static final String KEY_ADDITIONAL_INFO = "additional_info";
    public static final String KEY_CONFIG_VERSION = "config_version";
    public static final String KEY_FW_VERSION = "fw_version";
    public static final String KEY_INFO = "info";
    public static final String KEY_PRIMARY = "primary";
    public static final String KEY_MAX = "max";
    public static final String KEY_MIN = "min";
    public static final String KEY_STEP = "step";
    public static final String KEY_VALID_STRS = "valid_strs";
    public static final String KEY_VALUE = "value";
    public static final String KEY_SCHEDULE = "Schedule";
    public static final String KEY_SCENE = "Scene";
    public static final String KEY_ACTION = "action";
    public static final String KEY_ACTIONS = "actions";
    public static final String KEY_EVENTS = "events";
    public static final String KEY_CHECK = "check";
    public static final String KEY_DAYS = "d";
    public static final String KEY_MINUTES = "m";
    public static final String KEY_ENABLED = "enabled";
    public static final String KEY_PROPERTY_WRITE = "write";
    public static final String KEY_PROPERTY_TS = "time_series";
    public static final String KEY_SECRET_KEY = "secret_key";
    public static final String KEY_PROPERTY_COUNT = "property_count";
    public static final String KEY_CLAIM_VERIFY_RESPONSE = "claim_verify_response";
    public static final String KEY_CLAIM_INIT_RESPONSE = "claim_initiate_response";
    public static final String KEY_REQ_ID = "request_id";
    public static final String KEY_REQ_STATUS = "request_status";
    public static final String KEY_REQ_TIME = "request_timestamp";
    public static final String KEY_REQ_CONFIRMED = "confirmed";
    public static final String KEY_REQ_TIMEDOUT = "timedout";
    public static final String KEY_REQ_TIMESTAMP = "request_timestamp";
    public static final String KEY_USER_REQUEST = "user_request";
    public static final String KEY_GROUP_ID = "group_id";
    public static final String KEY_GROUP_NAME = "group_name";
    public static final String KEY_GROUPS = "groups";
    public static final String KEY_NODES = "nodes";
    public static final String KEY_NODE_LIST = "node_list";
    public static final String KEY_GROUP = "group";
    public static final String KEY_NODE_IDS = "node_ids";
    public static final String KEY_NODE_SHARING = "node_sharing";
    public static final String KEY_USERS = "users";
    public static final String KEY_PRIMARY_USER = "primary_user";
    public static final String KEY_START_REQ_ID = "start_request_id";
    public static final String KEY_START_USER_NAME = "start_user_name";
    public static final String KEY_NEXT_REQ_ID = "next_request_id";
    public static final String KEY_NEXT_USER_NAME = "next_user_name";
    public static final String KEY_PRIMARY_USER_NAME = "primary_user_name";
    public static final String KEY_SECONDARY_USER_NAME = "secondary_user_name";
    public static final String KEY_REQ_ACCEPT = "accept";
    public static final String KEY_SHARING_REQUESTS = "sharing_requests";
    public static final String KEY_METADATA = "metadata";
    public static final String KEY_START_ID = "start_id";
    public static final String KEY_NEXT_ID = "next_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static final String KEY_EVENT_DATA_PAYLOAD = "event_data_payload";
    public static final String KEY_PAYLOAD = "payload";
    public static final String KEY_LOCAL_CONTROL = "Local Control";
    public static final String KEY_REDIRECT_URI = "redirect_uri";
    public static final String KEY_CODE = "code";
    public static final String KEY_AUTH_CODE = "authCode";
    public static final String KEY_ACCOUNT_LINK_REQUEST = "accountLinkRequest";
    public static final String KEY_STAGE = "stage";
    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_CLIENT_SECRET = "client_secret";
    public static final String KEY_GRANT_TYPE = "grant_type";
    public static final String KEY_CONTENT_TYPE = "Content-type";
    public static final String KEY_ENDPOINTS = "endpoints";
    public static final String KEY_ACCOUNTLINK = "accountLink";
    public static final String KEY_SKILL = "skill";
    public static final String KEY_STATUS_LINKED = "LINKED";
    public static final String KEY_RESPONSE = "response";
    public static final String KEY_AUTOMATION_ID = "automation_id";
    public static final String KEY_AUTOMATION_NAME = "automation_name";
    public static final String KEY_AUTOMATION_TRIGGER_ACTIONS = "automation_trigger_actions";
    public static final String KEY_AUTOMATION = "automation";
    public static final String KEY_LOAD_AUTOMATION_PAGE = "load_automation";
    public static final String KEY_SYSTEM = "System";

    public static final String KEY_OPERATION = "operation";
    public static final String KEY_OPERATION_ADD = "add";
    public static final String KEY_OPERATION_EDIT = "edit";
    public static final String KEY_OPERATION_REMOVE = "remove";
    public static final String KEY_OPERATION_ENABLE = "enable";
    public static final String KEY_OPERATION_DISABLE = "disable";
    public static final String KEY_OPERATION_ACTIVATE = "activate";

    public static final String KEY_REQ_STATUS_DECLINED = "declined";
    public static final String KEY_REQ_STATUS_PENDING = "pending";

    public static final String KEY_USER_ROLE_PRIMARY = "primary";
    public static final String KEY_USER_ROLE_SECONDARY = "secondary";
    public static final String KEY_PRIMARY_USERS = "primary_users";
    public static final String KEY_SECONDARY_USERS = "secondary_users";

    // Device capability
    public static final String CAPABILITY_WIFI_SACN = "wifi_scan";
    public static final String CAPABILITY_NO_POP = "no_pop";
    public static final String CAPABILITY_CLAIM = "claim";

    public static final String WIFI_SCAN_FROM_DEVICE = "Device";
    public static final String WIFI_SCAN_FROM_PHONE = "Phone";

    // Event types
    public static final String EVENT_NODE_CONNECTED = "rmaker.event.node_connected";
    public static final String EVENT_NODE_DISCONNECTED = "rmaker.event.node_disconnected";
    public static final String EVENT_NODE_ADDED = "rmaker.event.user_node_added";
    public static final String EVENT_NODE_REMOVED = "rmaker.event.user_node_removed";
    public static final String EVENT_NODE_SHARING_ADD = "rmaker.event.user_node_sharing_add";
    public static final String EVENT_NODE_PARAM_MODIFIED = "rmaker.event.node_params_changed";
    public static final String EVENT_ALERT = "rmaker.event.alert";
    public static final String EVENT_NODE_AUTOMATION_TRIGGER = "rmaker.event.node_automation_trigger";

    // Notification channel IDs
    public static final String CHANNEL_NODE_ONLINE_ID = "notify_node_online_id";
    public static final String CHANNEL_NODE_OFFLINE_ID = "notify_node_offline_id";
    public static final String CHANNEL_NODE_ADDED = "notify_node_added_id";
    public static final String CHANNEL_NODE_REMOVED = "notify_node_removed_id";
    public static final String CHANNEL_ALERT = "notify_alert_id";
    public static final String CHANNEL_NODE_SHARING = "notify_node_sharing_id";
    public static final String CHANNEL_NODE_AUTOMATION_TRIGGER = "notify_node_automation_trigger";

    public static final String ACTION_ACCEPT = "com.gladiance.rainmaker.ACTION_ACCEPT";
    public static final String ACTION_DECLINE = "com.gladiance.rainmaker.ACTION_DECLINE";

    public static final String ALEXA_PACKAGE_NAME = "com.amazon.dee.app";
    public static final String KEY_ALEXA_ACCESS_TOKEN = "alexa_access_token";
    public static final String KEY_ALEXA_REFRESH_TOKEN = "alexa_refresh_token";

    public static final String EVENT_ENABLE_SKILL = "enable_skill";
    public static final String EVENT_DISABLE_SKILL = "disable_skill";
    public static final String EVENT_GET_STATUS = "get_status";

    // Constants for Alexa account linking
    public static final String ALEXA_API_ENDPOINTS_URL = "https://api.amazonalexa.com/v1/alexaApiEndpoint";
    public static final String ALEXA_REFRESH_TOKEN_URL = "https://api.amazon.com/auth/o2/token";
    public static final String ALEXA_APP_URL = "https://alexa.amazon.com/spa/skill-account-linking-consent?fragment=skill-account-linking-consent";
    public static final String LWA_URL = "https://www.amazon.com/ap/oa";
    public static final String LWA_SCOPE = "alexa::skills:account_linking";
    public static final String STATE = "temp"; // Place holder string

    public static final int ACTION_SELECTED_NONE = 0;
    public static final int ACTION_SELECTED_PARTIAL = 1;
    public static final int ACTION_SELECTED_ALL = 2;

    public static final String OTA_STATUS_TRIGGERED = "triggered";
    public static final String OTA_STATUS_IN_PROGRESS = "in-progress";
    public static final String OTA_STATUS_STARTED = "started";
    public static final String OTA_STATUS_COMPLETED = "completed";
    public static final String OTA_STATUS_SUCCESS = "success";
    public static final String OTA_STATUS_REJECTED = "rejected";
    public static final String OTA_STATUS_FAILED = "failed";
    public static final String OTA_STATUS_UNKNOWN = "unknown";

    public static final int USER_POOL_1 = 1;
    public static final int USER_POOL_2 = 2;
    public static final int MIN_THROTTLE_DELAY = 50;
    public static final int MAX_THROTTLE_DELAY = 300;
    public static final int MID_THROTTLE_DELAY = 100;

    public static final String KEY_SEC_VER = "sec_ver";

    public static final String DEFAULT_SEC2_USER_NAME = "wifiprov";

    // Keys used in JSON responses and used to pass data between activities.

    public static final String KEY_PROPERTIES = "properties";
    public static final String KEY_ATTRIBUTES = "attributes";
    public static final String KEY_SERVICES = "services";
    public static final String KEY_SCHEDULES = "Schedules";
    public static final String KEY_SCENES = "Scenes";
    public static final String KEY_TRIGGERS = "triggers";
    public static final String KEY_BOUNDS = "bounds";
    public static final String KEY_SELECTED_DEVICES = "selected_devices";

    // UI Types of Device
    public static final String UI_TYPE_TOGGLE = "esp.ui.toggle";
    public static final String UI_TYPE_PUSH_BTN_BIG = "esp.ui.push-btn-big";
    public static final String UI_TYPE_SLIDER = "esp.ui.slider";
    public static final String UI_TYPE_HUE_SLIDER = "esp.ui.hue-slider";
    public static final String UI_TYPE_HUE_CIRCLE = "esp.ui.hue-circle";
    public static final String UI_TYPE_DROP_DOWN = "esp.ui.dropdown";
    public static final String UI_TYPE_HIDDEN = "esp.ui.hidden";
    public static final String UI_TYPE_TRIGGER = "esp.ui.trigger";
    // Param Types
    public static final String PARAM_TYPE_NAME = "esp.param.name";
    public static final String PARAM_TYPE_CCT = "esp.param.cct";
    public static final String PARAM_TYPE_POWER = "esp.param.power";
    public static final String PARAM_TYPE_SATURATION = "esp.param.saturation";
    public static final String PARAM_TYPE_BRIGHTNESS = "esp.param.brightness";
    public static final String PARAM_TYPE_TEMPERATURE = "esp.param.temperature";
    public static final String PARAM_TYPE_TZ = "esp.param.tz";
    public static final String PARAM_TYPE_TZ_POSIX = "esp.param.tz_posix";
    public static final String PARAM_TYPE_LOCAL_CONTROL_POP = "esp.param.local_control_pop";
    public static final String PARAM_TYPE_LOCAL_CONTROL_TYPE = "esp.param.local_control_type";
    public static final String PARAM_TYPE_REBOOT = "esp.param.reboot";
    public static final String PARAM_TYPE_FACTORY_RESET = "esp.param.factory-reset";
    public static final String PARAM_TYPE_WIFI_RESET = "esp.param.wifi-reset";

    public static final String URL_USER_NODES_DETAILS = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes?node_details=true";

    public static final String URL_USER_NODE_STATUS = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/status";

    public static final String URL_USER_NODE_MAPPING = BuildConfig.BASE_URL + AppConstants.PATH_SEPARATOR
            + AppConstants.CURRENT_VERSION + "/user/nodes/mapping";

    //Edit Scene
    public static String Ref_dyn;
    public static String Name_dyn;
    public static String SceneRef;
    public static String Space_dyn;
    public static String Ref_Scene_Object = ""; //new
    public static String projectSpaceTypePlannedDeviceName;
    public static String GaaProjectSpaceTypePlannedDeviceRef;
    public static String powerState;
    public static String power;
    public static boolean DataEdit;
    public static boolean DataEnterIntoViewModel;




    // Create Scene
    public static String Create_Ref_dyn = "null"; // We get null ref and to get ref we hit allocateid post api
    public static String Create_Name_dyn;
    public static String Create_SceneRef;
    public static String Create_Space_dyn;
    public static String Create_Ref_Scene = ""; //new
    public static String Create_projectSpaceTypePlannedDeviceName;
    public static String Create_GaaProjectSpaceTypePlannedDeviceRef;
    public static String Create_powerState;
    public static String Create_power;


    public static final ArrayList<String> MY_CONSTANT_LIST = new ArrayList<String>();


    //Create Schedule
    public static String Create_Ref_dyn_Schedule = "null"; // We get null ref and to get ref we hit allocateid post api
    public static String Create_Name_dyn_Schedule = "";
    public static String Create_ScheduleRef_Schedule = "";
    public static String Create_Ref_Schedule; //new
    public static String Create_Space_dyn_Schedule = "";
    public static String Create_projectSpaceTypePlannedDeviceName_Schedule = "";
    public static String Create_GaaProjectSpaceTypePlannedDeviceRef_Schedule = "";
    public static String Create_powerState_Schedule = "";
    public static String Create_power_Schedule = "";


    //Edit Schedule
    public static String Edit_Ref_dyn_Schedule;
    public static String Edit_Name_dyn_Schedule;
    public static String Edit_ScheduleRef_Schedule;
    public static String Edit_Space_dyn_Schedule;
    public static Long Edit_Ref_Schedule = null; //new
    public static String Edit_projectSpaceTypePlannedDeviceName_Schedule;
    public static String Edit_GaaProjectSpaceTypePlannedDeviceRef_Schedule;
    public static String Edit_powerState_Schedule;
    public static String Edit_power_Schedule;

    //for EditSchedule Local Data
    public static boolean Data;
    public static boolean DataEditSchedule;


    //for CreateSchedule Local Data
    public static boolean CreateScheduleData;

    public static boolean DataEnterIntoViewModelEditSchedule;



    public static final ArrayList<String> MY_CONSTANT_LIST_SCHEDULE = new ArrayList<String>();



    public static Boolean Monday=false;
    public static Boolean Tuesday=false;
    public static Boolean Wednesday=false;
    public static Boolean Thursday=false;
    public static Boolean Friday=false;
    public static Boolean Saturday=false;
    public static Boolean Sunday = false;

    public static int hour;
    public static int minute;
    public static int second;
    public static int dayofmonth=0;

    public static Boolean January=false;
    public static Boolean February=false;
    public static Boolean March=false;
    public static Boolean April=false;
    public static Boolean May=false;
    public static Boolean June=false;
    public static Boolean July=false;
    public static Boolean August=false;
    public static Boolean September=false;
    public static Boolean October=false;
    public static Boolean November=false;
    public static Boolean December=false;

    public static int Year;

    public static Boolean RepeatEveryYear = false;

    public static Boolean StoredData = false;

    //Laundry Post

    public static String LaundryRef;
    public static String LineItemRef;

    //Room Service Post
    public static String RoomServiceRef;
    public static String LineItemRoomServiceRef;


//    private static AppConstants instance;
//    private String yourString;
//
//    private AppConstants() {
//        // Private constructor to prevent instantiation
//    }
//
//    public static AppConstants getInstance() {
//        if (instance == null) {
//            instance = new AppConstants();
//        }
//        return instance;
//    }
//
//    public String getYourString() {
//        return yourString;
//    }
}
