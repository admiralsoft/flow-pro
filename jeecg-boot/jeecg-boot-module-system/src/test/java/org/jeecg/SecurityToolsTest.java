package org.jeecg;

import cn.hutool.json.JSONObject;
import io.xjar.XCryptos;
import org.jeecg.common.util.security.SecurityTools;
import org.jeecg.common.util.security.entity.*;
import org.junit.Test;

public class SecurityToolsTest {
    @Test
    public void Test(){
        MyKeyPair mkeyPair = SecurityTools.generateKeyPair();

        JSONObject msg = new JSONObject();
        msg.put("name", "党政辉");
        msg.put("age", 50);
        JSONObject identity = new JSONObject();
        identity.put("type", "01");
        identity.put("no", "210882165896524512");
        msg.put("identity", identity);

        // 签名加密部分
        SecuritySignReq signReq = new SecuritySignReq();
        // data为要加密的报文字符串
        signReq.setData(msg.toString());
        // 为rsa私钥
        signReq.setPrikey(mkeyPair.getPriKey());
        // 调用签名方法
        SecuritySignResp sign = SecurityTools.sign(signReq);
        // 打印出来加密数据
        // signData为签名数据
        // data为aes加密数据
        // asekey为ras加密过的aeskey
        System.out.println(new JSONObject(sign).toStringPretty());

        // 验签解密部分
        SecurityReq req = new SecurityReq();
        //对方传过来的数据一一对应
        req.setAesKey(sign.getAesKey());
        req.setData(sign.getData());
        req.setSignData(sign.getSignData());
        //我们的公钥
        req.setPubKey(mkeyPair.getPubKey());
        //验签方法调用
        SecurityResp securityResp = SecurityTools.valid(req);
        //解密报文data为解密报文
        //sucess 为验签成功失败标志 true代码验签成功，false代表失败
        System.out.println(new JSONObject(securityResp).toStringPretty());
    }
    @Test
    public void XCryptosTest() throws Exception {
        XCryptos.encryption()
                .from("/Users/mac/IdeaProjects/activiti/activiti-jeecg-boot/jeecg-boot/jeecg-boot-module-system/target/jeecg-boot-module-system-2.4.5.jar")        //指定加密的jar包路径
                .use("dongjianbin")			//指定加密密码
//				.include("/io/xjar/**/*.class")	//指定要加密的资源相对于classpath的ANT路径表达式
//				.include("/mapper/**/*Mapper.xml")//指定要加密的资源相对于classpath的正则路径表达式
//				.exclude("/static/**/*")		//指定不加密的资源相对于classpath的ANT路径表达式
//				.exclude("/conf/*")			//指定不加密的资源相对于classpath的正则路径表达式
                .to("/Users/mac/soft/tmp/jeecg-boot-module-system.jar");	//指定加密后JAR包输出路径, 并执行加密.
    }

}
