package com.atguigu.gmall.product;

import com.atguigu.gmall.model.product.BaseTrademark;
import com.atguigu.gmall.product.mapper.BaseTrademarkMapper;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author lg
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/2 19:21
 */
@SpringBootTest
public class ReadWriteSpliteTest {

    @Autowired
    BaseTrademarkMapper baseTrademarkMapper;

    @Test
    public void testReadWriteSplite(){
        BaseTrademark baseTrademark = baseTrademarkMapper.selectById(4L);
        BaseTrademark baseTrademark1 = baseTrademarkMapper.selectById(4L);
        BaseTrademark baseTrademark2 = baseTrademarkMapper.selectById(4L);
        BaseTrademark baseTrademark3 = baseTrademarkMapper.selectById(4L);
        System.out.println(baseTrademark);
        System.out.println(baseTrademark1);
        System.out.println(baseTrademark2);
        System.out.println(baseTrademark3);


        baseTrademark.setTmName("小米1");
        baseTrademarkMapper.updateById(baseTrademark);

        //改完后，再去查询，很可能查不到最新结果

        //让刚改完的下次查询强制走主库
//        HintManager.getInstance().setWriteRouteOnly(); //强制走主库
        BaseTrademark baseTrademark4 = baseTrademarkMapper.selectById(4L);
        System.out.println("改完后查到的是："+baseTrademark4);
    }
}
