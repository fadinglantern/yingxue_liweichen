package com.weichen.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.weichen.controller.YxUserController;
import com.weichen.dao.YxUserDao;
import com.weichen.entity.*;
import com.weichen.log.annotation.YingxLog;
import com.weichen.util.AliyunUtil;
import com.weichen.util.Md5Utils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author weichenLi
 * @create 2020-11-18
 * @changeTime 22:02
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class YxUserServiceImpl implements YxUserService{
    private static final Logger log = LoggerFactory.getLogger(YxUserController.class);
    @Resource
    private YxUserDao dao;
    @Resource
    private HttpServletRequest request;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String,Object> findByPage(Integer page, Integer rows) {
        HashMap<String,Object> map= new HashMap<>();
        List<YxUser> userList= dao.selectByPage((page-1)*rows, rows);
        Long totalCounts = dao.findTotalCounts();
        Long totalPage = totalCounts%rows==0?totalCounts/rows:(totalCounts/rows)+1;
        map.put("page",page);
        map.put("total",totalPage);
        map.put("records",totalCounts);
        map.put("rows",userList);
        request.getSession().setAttribute("InfoUserMap",map);
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String pOIExpdpUser() {
        List<YxUser> list=dao.selectAll();

        //创建 Excel 工作薄对象
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建工作表   参数：表名（相当于Excel的sheet1,sheet2...）
        HSSFSheet sheet = workbook.createSheet("用户信息1");


        //设置文本居中
        HSSFFont font1=workbook.createFont();
        font1.setColor(Font.COLOR_RED);
        CellStyle fontStyle1=workbook.createCellStyle();
        fontStyle1.setFont(font1);
        fontStyle1.setAlignment(HorizontalAlignment.CENTER);

        HSSFFont font2=workbook.createFont();
        font2.setBold(true); //加粗
        CellStyle fontStyle2=workbook.createCellStyle();
        fontStyle2.setFont(font2);
        fontStyle2.setAlignment(HorizontalAlignment.CENTER);

        //创建日期对象
        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置日期格式
        dateStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日 HH:mm:ss"));

        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title={"ID","昵称","密码","salt","手机号","头像","描述","学分","注册时间","状态"};

        //处理单元格对象
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);    //单元格下标
            cell.setCellValue(title[i]);   //单元格内容
            cell.setCellStyle(fontStyle1);  //标题行使用样式
        }
        //设置单元格宽度   参数：列索引，列宽
        sheet.setColumnWidth(0, 13*733);
        sheet.setColumnWidth(1, 13*133);
        sheet.setColumnWidth(2, 13*733);
        sheet.setColumnWidth(3, 13*233);
        sheet.setColumnWidth(4, 13*333);
        sheet.setColumnWidth(5, 13*333);
        sheet.setColumnWidth(6, 13*333);
        sheet.setColumnWidth(7, 13*133);
        sheet.setColumnWidth(8, 13*333);
        sheet.setColumnWidth(9, 13*133);


        //处理数据行
        for (int i = 0; i < list.size(); i++) {
            //遍历一次创建一行
            HSSFRow row2 = sheet.createRow(i+1);

            //设置行高   参数：short类型的值
            row2.setHeightInPoints((float)21);

            //每行对应放的数据
            Cell cell0 = row2.createCell(0);
            cell0.setCellValue(list.get(i).getId());
            cell0.setCellStyle(fontStyle2);
            Cell cell1 = row2.createCell(1);
            cell1.setCellValue(list.get(i).getNickName());
            cell1.setCellStyle(fontStyle2);
            Cell cell2 = row2.createCell(2);
            cell2.setCellValue(list.get(i).getUserPassword());
            cell2.setCellStyle(fontStyle2);
            Cell cell3 = row2.createCell(3);
            cell3.setCellValue(list.get(i).getSalt());
            cell3.setCellStyle(fontStyle2);
            Cell cell4 = row2.createCell(4);
            cell4.setCellValue(list.get(i).getPhone());
            cell4.setCellStyle(fontStyle2);
            Cell cell5 = row2.createCell(5);
            cell5.setCellValue(list.get(i).getPicImg());
            cell5.setCellStyle(fontStyle2);
            Cell cell6 = row2.createCell(6);
            cell6.setCellValue(list.get(i).getBrief());
            cell6.setCellStyle(fontStyle2);
            Cell cell7 = row2.createCell(7);
            cell7.setCellValue(list.get(i).getScore());
            cell7.setCellStyle(fontStyle2);
            //设置单元格日期格式
            Cell cell8 = row2.createCell(8);
//            cell8.setCellValue(list.get(i).getCreateDate());   //添加数据
            try {
                cell8.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(list.get(i).getCreateDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cell8.setCellStyle(dateStyle);    //添加日期样式

            Cell cell9 = row2.createCell(9);
            cell9.setCellValue(list.get(i).getState());
            cell9.setCellStyle(fontStyle2);

        }
        try{
            //创建输出流  从内存中写入本地磁盘
            workbook.write(new FileOutputStream(new File("D:/用户表1.xls")));
            //关闭资源
            workbook.close();
            return "sussess";

        }catch (IOException e){
            e.printStackTrace();
            return "error";
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String ePOIExpdpUser() {
        List<YxUser> list=dao.selectAll();
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(
                new ExportParams("yingx用户表","用户"),YxUser.class, list);
        try {
            workbook.write(new FileOutputStream(new File("D:/yingx用户表.xls")));
            workbook.close();
            return "e sussess";
        }catch (IOException e){
            e.printStackTrace();
            return "e error";
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String,Object> getSexCounts() {
        HashMap<String,Object> map=new HashMap<>();
        List<MyDate> boysCount=dao.selectSexCount("男");
        List<MyDate> girlsCount=dao.selectSexCount("女");
        List<Integer> blist=Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        List<Integer> glist=Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0);
        for (int i = 0; i < boysCount.size(); i++) {
             MyDate date=boysCount.get(i);
            for (int j = 0; j < 12; j++) {
                if ((j+1)==date.getMonths()){
                    blist.set(j,date.getCounts());
                }
            }
        }
        for (int i = 0; i < girlsCount.size(); i++) {
            MyDate date=girlsCount.get(i);
            for (int j = 0; j < 12; j++) {
                if ((j+1)==date.getMonths()){
                    glist.set(j,date.getCounts());
                }
            }
        }
        map.put("boys", blist);
        map.put("girls", glist);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SexCity> getSexCityCounts() {
        List<SexCity> sexCities=new ArrayList<>();
        List<MyCity> boysCities=dao.selectSexCityCount("男");
        List<MyCity> girlsCities=dao.selectSexCityCount("女");
        sexCities.add(new SexCity("男性用户",boysCities));
        sexCities.add(new SexCity("女性用户",girlsCities));
        return sexCities;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Long findTotalCounts() {
        return dao.findTotalCounts();
    }

    @YingxLog(value = "用户添加",tableName = "yx_user",operation = "add")
    @Override
    public String addUser(YxUser user) {
        String id=UUID.randomUUID().toString().replace("-", "");

        String salt=Md5Utils.getSalt(6);
        String password= Md5Utils.getMd5Code(user.getUserPassword()+salt);

        user.setId(id);
        user.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        user.setState(1);
        user.setSalt(salt);
        user.setUserPassword(password);
        dao.insertOneUser(user);
        return id;
    }

    @YingxLog(value = "用户修改",tableName = "yx_user",operation = "modify")
    @Override
    public String modifyOneUser(YxUser user) {
        YxUser getUser=dao.selectOneData(user.getId());//获取更改前数据 用作比对
        //赋以前的值
        String salt=getUser.getSalt();
        String password=user.getUserPassword();
        //判断密码是否更改 不对就更改为新的
        if (!(getUser.getUserPassword().equals(user.getUserPassword()))){
            //不相同
            salt=Md5Utils.getSalt(6);//生成新的salt
            password= Md5Utils.getMd5Code(user.getUserPassword()+salt);//加盐加密
        }
        //更改 密码 和 salt
        user.setSalt(salt);
        user.setUserPassword(password);
        user.setPicImg(getUser.getPicImg());
        dao.updateUser(user);
        return user.getId();
    }

    @YingxLog(value = "用户删除",tableName = "yx_user",operation = "delete")
    @Override
    public void deleteOneUser(YxUser user) {
        YxUser getUser=dao.selectOneData(user.getId());
        int index=getUser.getPicImg().lastIndexOf("/");
        String picName=getUser.getPicImg().substring(index+1);
        AliyunUtil.deleteFile("yxmyvideo",picName,"pic/");
        dao.deleteOneData(user);
    }

    @YingxLog(value = "用户状态修改",tableName = "yx_user",operation = "modify")
    @Override
    public void modifyState(YxUser user) {
        dao.updateUserState(user);
    }

    @Override
    public void photoUpload(MultipartFile picName, String id) {
        //文件上传
        //1.根据相对路径获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFiles/photo");
        String dateDir = new SimpleDateFormat("yyyy-MM").format(new Date()); //创建月份目录
        //2.判断上传文件夹是否存在
        File file = new File(realPath,dateDir);
        if (!file.exists()) {
            file.mkdirs();//创建文件夹
        }
        //3.获取文件名
        String filename = picName.getOriginalFilename();
        //给文件拼接时间戳
        String newName = (new Date().getTime()) + "-" + filename;
        //4.文件上传
        try {
            picName.transferTo(new File(realPath+"\\"+dateDir, newName));//到路径
        } catch (IOException e) {
            e.printStackTrace();
        }
        //5.数据修改
        YxUser user=new YxUser();
        user.setId(id);
        user.setPicImg(newName);
        dao.updatePicImg(user);
    }

    @YingxLog(value = "用户头像修改",tableName = "yx_user",operation = "modify")
    @Override
    public void photoUploadAliyun(MultipartFile picImg, String id) {
        if (Objects.requireNonNull(picImg.getOriginalFilename()).length()!=0){//是否上传图片
            YxUser user=dao.selectOneData(id);
            if (user.getPicImg()==null){//空的 证明 为第一次添加
                System.out.println("进入 添加图片");
                String newName = AliyunUtil.uploadVideoAliyun(picImg,"yxmyvideo","pic/");
                user.setId(id);
                user.setPicImg("https://yxmyvideo.oss-cn-beijing.aliyuncs.com/pic/"+newName);
                dao.updatePicImg(user);
            }else {//不是空的 证明 不是添加操作 是修改
                String picName=user.getPicImg().substring(user.getPicImg().lastIndexOf("/")+1);//获取图片名字
                String originalName=picName.substring(picName.indexOf("-")+1);//获取时间戳后面的名字
                if (!(originalName.equals(picImg.getOriginalFilename()))){//判断上传的图片与原来是否一样
                    System.out.println("进入 修改图片");
                    //不一样 上传新的 删除旧的
                    String newName = AliyunUtil.uploadVideoAliyun(picImg,"yxmyvideo","pic/");
                    AliyunUtil.deleteFile("yxmyvideo",picName,"pic/");
                    user.setId(id);
                    user.setPicImg("https://yxmyvideo.oss-cn-beijing.aliyuncs.com/pic/"+newName);
                    dao.updatePicImg(user);
                }
                //一样 就不上传图片 不修改数据库数据
                System.out.println("进入 修改图片 但是图片相同");
            }
        }
    }
}
