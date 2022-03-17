package com.zwm.service.impl;

import com.zwm.service.DataService;
import com.zwm.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private RedisTemplate redisTemplate;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Override
    //记录单日UV
    public void recordUV(String ip) {
        //获取 key
        String redisUVKey = RedisKeyUtil.getUVKey(simpleDateFormat.format(new Date()));
        //记录 key:ip
        redisTemplate.opsForHyperLogLog().add(redisUVKey, ip);
    }

    @Override
    //记录单日 DAU
    public void recordDAU(int userId) {
        //获取 key
        String redisDAUKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(new Date()));
        //记录 key:ip
        redisTemplate.opsForValue().setBit(redisDAUKey, userId, true);
    }

    @Override
    //统计区间 UV
    public long calculateUV(Date startDate, Date endDate) {
        //判断参数是否为空，为空抛出异常
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        //传统的 Date 的类型无法做加减运算，这里借助 Calendar ---> Calendar.getInstance() 可以获取指定时间点的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        String redisUVAllKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
        List<String> redisUVKeyList = new ArrayList<>();
        //开始时间不能晚于结束时间，在这一范围之内的 UV 添加到 Redis 统计 UV 的 Key 中
        while (!calendar.getTime().after(endDate)) {
            String redisUVKey = RedisKeyUtil.getUVKey(simpleDateFormat.format(startDate));
            //获取这一天的数据key ---> 到时候统一存储在 redisUVAllKey 中【使用 union】 ---> 用一个 List 保存
            redisUVKeyList.add(redisUVKey);
            //起始日期加 1
            calendar.add(Calendar.DATE, 1);
        }
        //统计，将redisUVKeyList中的数据合并到redisUVAllKey中
        redisTemplate.opsForHyperLogLog().union(redisUVAllKey, redisUVKeyList.toArray());
        return redisTemplate.opsForHyperLogLog().size(redisUVAllKey);
    }

    @Override
    public long calculateDAU(Date startDate, Date endDate) {
        //判断参数是否为空，为空抛出异常
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        //传统的 Date 的类型无法做加减运算，这里借助 Calendar ---> Calendar.getInstance() 可以获取指定时间点的日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        String redisDAUAllKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(startDate), simpleDateFormat.format(endDate));
        List<byte[]> redisDAUKeyList = new ArrayList<>();
        //开始时间不能晚于结束时间，在这一范围之内的 UV 添加到 Redis 统计 UV 的 Key 中
        while (!calendar.getTime().after(endDate)) {
            String redisDAUKey = RedisKeyUtil.getDAUKey(simpleDateFormat.format(startDate));
            //获取这一天的数据key ---> 到时候统一存储在 redisUVAllKey 中【使用 union】 ---> 用一个 List 保存
            redisDAUKeyList.add(redisDAUKey.getBytes());
            //起始日期加 1
            calendar.add(Calendar.DATE, 1);
        }
        //统计，一段时间内只要有登录就为 true
        return (long) redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.bitOp(RedisStringCommands.BitOperation.OR, redisDAUAllKey.getBytes(), redisDAUKeyList.toArray(new byte[0][0]));
                return redisConnection.bitCount(redisDAUAllKey.getBytes());
            }
        });
    }
}
