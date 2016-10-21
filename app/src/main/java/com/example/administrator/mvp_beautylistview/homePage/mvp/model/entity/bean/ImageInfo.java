package com.example.administrator.mvp_beautylistview.homePage.mvp.model.entity.bean;

/**
 * Created by Mr.Egg on 2016/10/6.
 */
public class ImageInfo {
    /*
            "ctype": "picture",
            "impid": "385806_1475764507251_8627",
            "pageid": "SC_u241",
            "meta": "385806_1475764507251_8627",
            "dtype": 6,
            "title": "她说想起爸妈有罪恶感，我该怎么劝她",
            "date": "2016-10-06 11:37:13",
            "docid": "0EaWRbDD",
            "itemid": "0EaWRbDD",
            "source": "一点资讯",
            "image": "http://static.yidianzixun.com/beauty/imgs/i_000byqzo.jpg",
            "width": 658,
            "height": 789,
            "like": 70,
            "up": 3822,
            "down": 2835,
            "comment_count": 4,
            "content_type": "picture",
            "b_political": false,
            "is_gov": false
        },
     */
    private String ctype;
    private String impid;
    private String pageid;
    private String meta;
    private int dtype;
    private String title;
    private String date;
    private String docid;
    private String itemid;
    private String source;
    private String image;
    private int width;
    private int height;
    private int like;
    private int up;
    private int down;
    private int comment_count;
    private String content_type;
    private boolean b_political;
    private boolean is_gov;
    //应该在屏幕上显示的高度
    private int pxScreenHeight;
    //多加一个比例属性
    private float scale;
    public String getCtype() {
        return ctype;
    }
    public ImageInfo setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public int getPxScreenHeight() {
        return pxScreenHeight;
    }

    public float getScale() {
        return scale;
    }

    public ImageInfo setPxScreenHeight(int pxScreenHeight) {
        this.pxScreenHeight = pxScreenHeight;
        return this;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getImpid() {
        return impid;
    }

    public void setImpid(String impid) {
        this.impid = impid;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public int getDtype() {
        return dtype;
    }

    public void setDtype(int dtype) {
        this.dtype = dtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public ImageInfo setImage(String image) {
        this.image = image;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public boolean isB_political() {
        return b_political;
    }

    public void setB_political(boolean b_political) {
        this.b_political = b_political;
    }

    public boolean is_gov() {
        return is_gov;
    }

    public void setIs_gov(boolean is_gov) {
        this.is_gov = is_gov;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "ctype='" + ctype + '\'' +
                ", impid='" + impid + '\'' +
                ", pageid='" + pageid + '\'' +
                ", meta='" + meta + '\'' +
                ", dtype=" + dtype +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", docid='" + docid + '\'' +
                ", itemid='" + itemid + '\'' +
                ", source='" + source + '\'' +
                ", image='" + image + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", like=" + like +
                ", up=" + up +
                ", down=" + down +
                ", comment_count=" + comment_count +
                ", content_type='" + content_type + '\'' +
                ", b_political=" + b_political +
                ", is_gov=" + is_gov +
                '}';
    }
}
