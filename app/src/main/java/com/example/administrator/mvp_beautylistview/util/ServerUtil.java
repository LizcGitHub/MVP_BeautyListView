package com.example.administrator.mvp_beautylistview.util;
/**
 *  服务器
 */
public class ServerUtil {
    //一点资讯
    public final static String BEAUTY_API = "http://mb.yidianzixun.com/api/q/?path=channel|news-list-for-channel&channel_id=u241&fields=docid&fields=category&fields=date&fields=image&fields=image_urls&fields=like&fields=source&fields=title&fields=url&fields=comment_count&fields=summary&fields=up&cstart=0&cend=10&version=999999&infinite=true";

    public final static String LOGIN_SERVLET_PATH = "http://2c0cdjdflxglb8c:8080/BeautyListView/servlet/LoginServlet";
    //用于测试的下载文件（图片路径）
    public final static String TEST_PIC_PATH = "http://2c0cdjdflxglb8c:8080/BeautyListView/upload_head/pictest.jpg";
    //用于上传的文件夹
    public final static String UPLOAD_PIC_FOLDER = "http://2c0cdjdflxglb8c:8080/AndroidStudyProject/upload/";
}
