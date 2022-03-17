package com.zwm.service;

import java.util.Date;

public interface DataService {
    //记录单日UV ---> 某天某个独立IP访问了该网站
    public abstract void recordUV(String ip);

    //记录单日DAU ---> 某天某个用户登录了该网站
    public abstract void recordDAU(int userId);

    //统计区间UV ---> 某段时间多少个独立IP访问了该网站 ---> 合并单日UV ---> 返回个数
    public abstract long calculateUV(Date startDate, Date endDate);

    //统计区间DAU ---> 某段时间多少个个用户登录了该网站 ---> 合并单日DAU ---> 返回个数
    public abstract long calculateDAU(Date startDate, Date endDate);
}
