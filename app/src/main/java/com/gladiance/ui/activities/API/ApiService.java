package com.gladiance.ui.activities.API;

import com.gladiance.ui.models.ActiveSceneRes;
import com.gladiance.ui.models.AddSpaceUserFavourite;
import com.gladiance.ui.models.DeviceInfo;
import com.gladiance.ui.models.LoginRequestModel;
import com.gladiance.ui.models.LoginResponseModel;
import com.gladiance.ui.models.LogoutRequestModel;
import com.gladiance.ui.models.LogoutResponseModel;
import com.gladiance.ui.models.NodeResponseModel;
import com.gladiance.ui.models.ProjectSpaceGroupResModel;
import com.gladiance.ui.models.ProjectSpaceLandingResModel;
import com.gladiance.ui.models.ProjectSpaceResponseModel;
import com.gladiance.ui.models.RemoveSpaceUserFavorite;
import com.gladiance.ui.models.RequestModel;
import com.gladiance.ui.models.ResetResponse;
import com.gladiance.ui.models.ResponseModel;
import com.gladiance.ui.models.ResponseModelNode;
import com.gladiance.ui.models.SpaceSpaceGroupResModel;
import com.gladiance.ui.models.ac.Thermostat;
import com.gladiance.ui.models.ac.ThermostatResponse;
import com.gladiance.ui.models.allocateSingleId.AllocateSingleIdResponse;
import com.gladiance.ui.models.arealandingmodel.ProjectAreaLandingResModel;
import com.gladiance.ui.models.favoritelist.FavoriteListRes;
import com.gladiance.ui.models.guestlandingpage.GuestLandingResModel;
import com.gladiance.ui.models.lnstallerlandingpage.InstallerLandingResModel;
import com.gladiance.ui.models.provisioninglabel.ProvisioningRequest;
import com.gladiance.ui.models.provisioninglabel.ProvisioningResponse;
import com.gladiance.ui.models.saveScene.SaveSceneRequest;
import com.gladiance.ui.models.saveSchedule.SaveScheduleRequest;
import com.gladiance.ui.models.scene.SceneResModel;
import com.gladiance.ui.models.scenelist.SceneListResModel;
import com.gladiance.ui.models.schedule.ScheduleResModel;

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
}

