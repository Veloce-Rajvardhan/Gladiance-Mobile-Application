package com.gladiance.ui.activities.API;

import com.gladiance.ui.models.ActiveSceneRes;
import com.gladiance.ui.models.AddSpaceUserFavourite;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.EmergencyResponse;
import com.gladiance.ui.models.LoginRequestModel;
import com.gladiance.ui.models.LoginResponseModel;
import com.gladiance.ui.models.LogoutRequestModel;
import com.gladiance.ui.models.LogoutResponseModel;
import com.gladiance.ui.models.NodeResponseModel;
import com.gladiance.ui.models.Privacy.PrivacyListRes;
import com.gladiance.ui.models.PrivacyOnOffResponse;
import com.gladiance.ui.models.SafetyResponse;
import com.gladiance.ui.models.SecurityResponse;
import com.gladiance.ui.models.ServiceOnOffResponse;
import com.gladiance.ui.models.emergencystatus.EmergencyStatusRes;
import com.gladiance.ui.models.privacystatus.PrivacyStatusResponse;
import com.gladiance.ui.models.ProjectSpaceGroupResModel;
import com.gladiance.ui.models.ProjectSpaceLandingResModel;
import com.gladiance.ui.models.ProjectSpaceResponseModel;
import com.gladiance.ui.models.RemoveSpaceUserFavorite;
import com.gladiance.ui.models.RequestModel;
import com.gladiance.ui.models.ResetResponse;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.ui.models.ResponseModelNode;
import com.gladiance.ui.models.SpaceSpaceGroupResModel;
import com.gladiance.ui.models.ac.ThermostatResponse;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;
import com.gladiance.ui.models.favoritelist.FavoriteListRes;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerLandingResModel;
import com.gladiance.ui.models.provisioninglabel.ProvisioningRequest;
import com.gladiance.ui.models.provisioninglabel.ProvisioningResponse;
import com.gladiance.ui.models.safetystatus.SafetyStatusRes;
import com.gladiance.ui.models.saveScene.SaveSceneRequest;
import com.gladiance.ui.models.saveSchedule.SaveScheduleRequest;
import com.gladiance.ui.models.scene.SceneResModel;
import com.gladiance.ui.models.scenelist.SceneListResModel;
import com.gladiance.ui.models.schedule.ScheduleResModel;
import com.gladiance.ui.models.securitystatus.SecurityStatusRes;
import com.gladiance.ui.models.servicestatus.ServiceStatusResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("mqtt/publishmqttmessage")
   // Call<Void> postApiRequest(@Body ApiRequest apiRequest);
    Call<ResponseModel> sendSwitchState(@Body RequestModel requestModel);

    @GET("gladiancedev-gladiance-web-api/mqtt/nodeid/{macId}")
    Call<ResponseModelNode> getData(@Path("macId") String macId);

//localcontrolnodeconfig
    @GET("mqtt/nodeconfig/{NodeId}")
    Call<DeviceInfo> getAllData(@Path("NodeId") String NodeId);

    @GET("mqtt/nodestatus/{NodeId}")
    Call<ThermostatResponse> getNodeStatus(@Path("NodeId") String NodeId);

    @GET("mqtt/localcontrolnodeconfig/{NodeId}")
    Call<Object> getAllLocalControlData(@Path("NodeId") String NodeId);

    @GET("mqtt/nodeid/{MacId}")
    Call<NodeResponseModel> getData2(@Path("MacId") String macId);

    @POST("mobileapp/loginuser")
    Call<LoginResponseModel> loginUser(@Body LoginRequestModel request);

    @GET("mobileapp/loginlandingpagedata/{LoginToken}/{LoginDeviceId}")
    Call<ProjectSpaceResponseModel> getLoginData(@Path("LoginToken") String loginToken, @Path("LoginDeviceId") String loginDeviceId);

    @GET("mobileapp/projectlandingpagedata/{GAAProjectRef}/{LoginToken}/{LoginDeviceId}")
    Call<ProjectSpaceGroupResModel> getProjectData(
            @Path("GAAProjectRef") String projectRef,
            @Path("LoginToken") String loginToken,
            @Path("LoginDeviceId") String loginDeviceId);

    @GET("mobileapp/spacegrouplandingpagedata/{GAAProjectSpaceGroupRef}/{LoginToken}/{LoginDeviceId}")
    Call<SpaceSpaceGroupResModel> getSpaceGroupData(@Path("GAAProjectSpaceGroupRef") String gaaProjectSpaceGroupRef,
                                                    @Path("LoginToken") String loginToken,
                                                    @Path("LoginDeviceId") String loginDeviceId);

    @GET("mobileapp/spacegrouplandingpagedata/{GAAProjectSpaceGroupRef}/{LoginToken}/{LoginDeviceId}")
    Call<ProjectSpaceLandingResModel> getSpaceNameData(
            @Path("GAAProjectSpaceGroupRef") String GAAProjectSpaceGroupRef,
            @Path("LoginToken") String LoginToken,
            @Path("LoginDeviceId") String LoginDeviceId
    );

    @GET("mobileapp/spacelandingpagedata/{GAAProjectSpaceRef}/{LoginToken}/{LoginDeviceId}")
    Call<ProjectAreaLandingResModel> getAreaLandingPageData(
            @Path("GAAProjectSpaceRef") String projectSpaceRef,
            @Path("LoginToken") String loginToken,
            @Path("LoginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/arealandingpageinstallercontrols/{GAAProjectSpaceRef}/{GAAProjectSpaceTypeAreaRef}/{LoginToken}/{LoginDeviceId}")
    Call<InstallerLandingResModel> getDevices(
            @Path("GAAProjectSpaceRef") String projectSpaceRef,
            @Path("GAAProjectSpaceTypeAreaRef") Long gAAProjectSpaceTypeAreaRef,
            @Path("LoginToken") String loginToken,
            @Path("LoginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/arealandingpageguestcontrols/{GAAProjectSpaceRef}/{GAAProjectSpaceTypeAreaRef}/{LoginToken}/{LoginDeviceId}")
    Call<GuestLandingResModel> getControlTypeName(
            @Path("GAAProjectSpaceRef") String projectSpaceRef,
            @Path("GAAProjectSpaceTypeAreaRef") Long gAAProjectSpaceTypeAreaRef,
            @Path("LoginToken") String loginToken,
            @Path("LoginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/associatenodetoplanneddevice")
    Call<ProvisioningResponse> postAssociateNodeToPlannedDevice(@Body ProvisioningRequest request);


    //Reset device which is provision
    @POST("mqtt/factoryresetnode/{nodeId}/{loginToken}")
    Call<ResetResponse> factoryResetNode(
            @Path("nodeId") String nodeId,
            @Path("loginToken") String loginToken
           // @Body ResetResponse requestBody
    );

    @POST("mobileapp/logoutuser")
    Call<LogoutResponseModel> logoutUser(@Body LogoutRequestModel request);


    @GET("mobileapp/scenelist/{gaaProjectSpaceTypeRef}/{loginToken}/{loginDeviceId}")
    Call<SceneListResModel> getSceneList(
            @Path("gaaProjectSpaceTypeRef") String gaaProjectSpaceTypeRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/scene/{gaaProjectSceneRef}/{loginToken}/{loginDeviceId}")
    Call<SceneResModel> getScene(
            @Path("gaaProjectSceneRef") Long gaaProjectSceneRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/savescene")
    Call<SceneResModel> saveScene(@Body SaveSceneRequest saveSceneRequest);

    @POST("mobileapp/activatescene/{gaaProjectSceneRef}/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<ActiveSceneRes> activateScene(@Path("gaaProjectSceneRef") String gaaProjectSceneRef,
                                       @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
                                       @Path("loginToken") String loginToken,
                                       @Path("loginDeviceId") String loginDeviceId);

    @GET("mobileapp/schedulelist/{gaaProjectSpaceTypeRef}/{loginToken}/{loginDeviceId}")
    Call<SceneListResModel> getScheduleList(
            @Path("gaaProjectSpaceTypeRef") String gaaProjectSpaceTypeRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/schedule/{gaaProjectNodeScheduleRef}/{loginToken}/{loginDeviceId}")
    Call<ScheduleResModel> getSchedule(
            @Path("gaaProjectNodeScheduleRef") Long gaaProjectNodeScheduleRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/saveschedule")
    Call<SceneResModel> saveSchedule(@Body SaveScheduleRequest saveScheduleRequest);


    @POST("request/allocatesingleid/50000102")
    Call<AllocateSingleIdResponse>  allocateSingleId();

    @POST("mobileapp/addspaceuserfavourite/{gaaProjectSpaceRef}/{gaaProjectSpaceTypePlannedDeviceConnectionRef}/{loginToken}/{loginDeviceId}")
    Call<AddSpaceUserFavourite> addSpaceUserFavourite(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("gaaProjectSpaceTypePlannedDeviceConnectionRef") String gaaProjectSpaceTypePlannedDeviceConnectionRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/removespaceuserfavourite/{gaaProjectSpaceRef}/{gaaProjectSpaceTypePlannedDeviceConnectionRef}/{loginToken}/{loginDeviceId}")
    Call<RemoveSpaceUserFavorite> removeSpaceUserFavourite(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("gaaProjectSpaceTypePlannedDeviceConnectionRef") String gaaProjectSpaceTypePlannedDeviceConnectionRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/spaceuserfavouritelist/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<FavoriteListRes> getUserFavouriteList(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/privacymodeavailabilityinformation/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<PrivacyListRes> getPrivacyModeAvailability(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/activateprivacymode/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<PrivacyOnOffResponse> activatePrivacyMode(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/deactivateprivacymode/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<PrivacyOnOffResponse> deactivatePrivacyMode(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/privacystatus/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<PrivacyStatusResponse> getPrivacyStatus(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/raiseemergencyrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<EmergencyResponse> raiseEmergencyRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/cancelemergencyrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<EmergencyResponse> cancelEmergencyRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/pendingemergencyrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<EmergencyStatusRes> getPendingEmergencyRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/raisesecurityrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<SecurityResponse> raiseSecurityRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/cancelsecurityrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<SecurityResponse> cancelSecurityRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/pendingsecurityrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<SecurityStatusRes> getPendingSecurityRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/raisesafetyrequest/{gaaProjectSpaceRef}/{safetyRequestType}/{loginToken}/{loginDeviceId}")
    Call<SafetyResponse> raiseSafetyRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("safetyRequestType") String safetyRequestType,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/cancelsafetyrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<SafetyResponse> cancelSafetyRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/pendingsafetyrequest/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<SafetyStatusRes> getPendingSafetyRequest(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/activateservicemode/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<ServiceOnOffResponse> activateServiceMode(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @POST("mobileapp/deactivateservicemode/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<ServiceOnOffResponse> deactivateServiceMode(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );

    @GET("mobileapp/servicestatus/{gaaProjectSpaceRef}/{loginToken}/{loginDeviceId}")
    Call<ServiceStatusResponse> getServiceStatus(
            @Path("gaaProjectSpaceRef") String gaaProjectSpaceRef,
            @Path("loginToken") String loginToken,
            @Path("loginDeviceId") String loginDeviceId
    );


}

