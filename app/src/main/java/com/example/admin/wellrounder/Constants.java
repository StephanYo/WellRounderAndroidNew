package com.example.admin.wellrounder;

/**
 * Created by admin on 9/15/2017.
 * <p>
 * This class is the class that holds all the links for api calls, as well as data
 */

//change ip when
// working in the certain location that you are at.
public class Constants {


    //School ip address
//   private static final String ROOT_URL = "http://172.20.1.254/WellRounderLogin/v1/";

    //second school ip?

    //private static final String ROOT_URL = "http://172.20.7.90/WellRounderLogin/v1/";
    //private static final String ROOT_URL2 = "http://172.20.7.90/WellRounderLogin/v1/Api.php?apicall=";

    //home ip address
//    private static final String ROOT_URL = "http://192.168.0.13/WellRounderLogin/v1/";
//
//    private static final String ROOT_URL2=  "http://192.168.0.12/WellRounderLogin/v1/Api.php?apicall=";

    private static final String ROOT_URL = "http://18.237.109.51/WellRounderLogin/v1/";

    private static final String ROOT_URL2=  "http://18.237.109.51/WellRounderLogin/v1/Api.php?apicall=";



    //private static final String ROOT_URL = "http://192.168.0.11/WellRounderLogin/v1/";

    //private static final String ROOT_URL2=  "http://192.168.0.11/WellRounderLogin/v1/Api.php?apicall=";


    public static final String URL_REGISTER = ROOT_URL + "registerUser.php";
    public static final String URL_LOGIN = ROOT_URL + "userLogin.php";
    public static final String URL_SEND_POST = ROOT_URL + "createPost.php";
    public static final String URL_GET_ALL_USERS = ROOT_URL2 + "getuser";
    public static final String URL_GET_ALL_POSTS = ROOT_URL2 + "getposts";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateUser.php";
    public static final String URL_DELETE_USER = ROOT_URL2 + "deleteuser";
    public static final String URL_GET_SPECIFIC_USER = ROOT_URL + "getSpecificUser.php?username=";
    public static final String URL_GET_POST_COMMENTS = ROOT_URL + "getPostComments.php?post_id=";
    public static final String URL_POST_COMMENT = ROOT_URL2 + "postComment&post_id=";
    public static final String URL_GET_ALL_SPECIFIC_MESSAGES = ROOT_URL2 + "getAllSpecificMessages&chat_id=";
    public static final String URL_GET_CHAT_ROOMS = ROOT_URL2 + "getChatRooms";
    public static final String URL_SEND_MESSAGE = ROOT_URL + "sendMessage.php";
    public static final String URL_GET_SPECIFIC_USER_BY_ID = ROOT_URL + "getSpecificUser.php?id=";
    public static final String URL_GET_ALL_SPECIFIC_POSTS = ROOT_URL2 + "getAllSpecificPosts&username=";

}