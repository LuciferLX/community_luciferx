package life.majiang.community.enums;

public enum NotificationStatusEnum {//设立枚举的意义在于之后回顾代码时可以明确1，0都代表什么
    UNREAD(0),READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
