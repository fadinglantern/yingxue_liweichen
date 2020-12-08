package com.weichen.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GenericResult;
import com.aliyun.oss.model.ProcessObjectRequest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.web.multipart.MultipartFile;

import javax.management.ObjectName;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;

public class AliyunUtil {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "";
    private static String accessKeySecret = "";
    private final static String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    private final static String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
    public static String sendPhoneCode(String phoneNum,String signName,String templateCode,String code){
//        HashMap<String,Object> map=new HashMap<>();
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou",product, domain);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
        request.setPhoneNumbers(phoneNum);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
        request.setTemplateParam("{ \"code\":\""+code+"\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String message=null;
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            /*map.put("status","200");
            map.put("message",sendSmsResponse.getCode());*/
            message="发送成功";
        }else {
           /* map.put("status","201");
            map.put("message",sendSmsResponse.getCode());*/
            message="发送失败";
        }
//        System.out.println(map);
        return message;
    }
    /**向 aliyun 上传 视频/图片
     * @param videoObject 文件对象
     * @param bucketName aliyun 桶容器名字
     * @param dirFile 目录
     * @return 返回 更改后的视频/图片 名字
     * */
    public static String uploadVideoAliyun(MultipartFile videoObject,String bucketName,String dirFile){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);
        // 上传Byte数组。
        byte[] content = new byte[0];
        try {
            content = videoObject.getBytes();//文件转字节流
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName=videoObject.getOriginalFilename();
        String newName=(new Date().getTime()) + "-" +fileName;
        ossClient.putObject(bucketName,dirFile+newName, new ByteArrayInputStream(content));
        // 关闭OSSClient。
        ossClient.shutdown();
        return newName;
    }
    /**获取aliyun指定位置的视频文件,使用aliyun SDK处理获得视频第一帧
     *   @param bucketName aliyun 桶容器名字
     *   @param objectName 文件原始名
     * @param dirFile 目录
     * @return 返回处理好的图片 网络路径
     * */
    public static URL cutVideoFirstFrame(String bucketName,String objectName,String dirFile){
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置视频截帧操作。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, dirFile+objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
//        System.out.println(signedUrl);
        // 关闭OSSClient。
        ossClient.shutdown();
        return signedUrl;
    }
    /**向aliyun 上传图片
     *   @param bucketName aliyun 桶容器名字
     *   @param fileName 文件原始名
     *   @param url 网络文件的资源路径
     * @param dirFile 目录
     *   @return 返回修改后图片文件名
    * */
    public static String uploadVideoFirstFrame(String bucketName, String fileName,String url,String dirFile) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;//上传路径
        try {
            inputStream = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String realName=fileName.replace(".mp4",".jpg");//更改文件后缀
        String objectName=(new Date().getTime()) + "-" +realName;//更改文件名称 格式 时间戳-文件名.jpg
        ossClient.putObject(bucketName, dirFile+objectName, inputStream);
        ossClient.shutdown();
        return objectName;
    }
    /**判断 云上文件是否存在
     *   @param bucketName aliyun 桶容器名字
     *   @param objectName 文件原始名
     * @param dirFile 目录
     *   @return 返回存在结果
     * */
    public static boolean ifFileExist(String bucketName,String objectName,String dirFile){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(bucketName, dirFile+objectName);
//        System.out.println(found);
        // 关闭OSSClient。
        ossClient.shutdown();
        return found;
    }
    /** 删除aliyun 内的文件
     *   @param bucketName aliyun 桶容器名字
     *   @param objectName 文件名
     * @param dirFile 目录
     * */
    public static void deleteFile(String bucketName,String objectName,String dirFile){
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName,dirFile+ objectName);
        ossClient.shutdown();

    }
}
