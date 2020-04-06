package com.zhenyiwang.mall.bean.client;

//"data": [{
//		"id": 297,
//		"content": "这个衣服怎么样",
//		"asker": "admin",
//		"time": "2019-10-12 11:22:18",
//		"reply": {
//			"content": "很不错的",
//			"time": "2019-10-12 11:22:55"
//		}
//	}]
public class ClientMsg {
    private Integer id;
    private String content;
    //userName
    private String asker;
    private String time;
    private Reply reply;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "ClientMsg{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", asker='" + asker + '\'' +
                ", time='" + time + '\'' +
                ", reply=" + reply +
                ", userId='" + userId + '\'' +
                '}';
    }
}
