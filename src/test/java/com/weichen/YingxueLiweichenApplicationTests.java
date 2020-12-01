package com.weichen;

import com.weichen.dao.YxUserDao;
import com.weichen.entity.MyDate;
import com.weichen.entity.Student;
import org.apache.http.client.utils.DateUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

//import org.apache.commons.math3.util.ArithmeticUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import com.sjdf.erp.common.utils.PlatformUtils;
@SpringBootTest
class YingxueLiweichenApplicationTests {
	@Resource
	private YxUserDao dao;
	@Test
	void test02() throws IOException {
		//创建集合
		Student user1 = new Student("1", "小黑", 12, new Date());
		Student user2 = new Student("2", "小红", 26, new Date());
		Student user3 = new Student("3", "小绿", 23, new Date());
		Student user4 = new Student("4", "小紫", 17, new Date());
		Student user5 = new Student("5", "小蓝", 31, new Date());
		Student user6 = new Student("6", "小黄", 18, new Date());
		List<Student> users = Arrays.asList(user1,user2,user3,user4,user5,user6);

		//创建 Excel 工作薄对象
		HSSFWorkbook workbook = new HSSFWorkbook();

		//创建工作表   参数：表名（相当于Excel的sheet1,sheet2...）
		HSSFSheet sheet = workbook.createSheet("用户信息1");

		//创建标题行
		HSSFRow row = sheet.createRow(0);
		String[] title={"ID","名字","年龄","生日"};

		//处理单元格对象
		HSSFCell cell = null;
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);    //单元格下标
			cell.setCellValue(title[i]);   //单元格内容

//			cell.setCellStyle("");  //标题行使用样式
		}

		//处理数据行
		for (int i = 0; i < users.size(); i++) {
			//遍历一次创建一行
			HSSFRow row2 = sheet.createRow(i+1);

			//每行对应放的数据
			row2.createCell(0).setCellValue(users.get(i).getId());
			row2.createCell(1).setCellValue(users.get(i).getName());
			row2.createCell(2).setCellValue(users.get(i).getAge());
			row2.createCell(3).setCellValue(users.get(i).getBir());

			//设置单元格日期格式
			Cell cell2 = row2.createCell(3);
//			cell2.setCellStyle(cellStyle2);    //添加日期样式
			cell2.setCellValue(users.get(i).getBir());   //添加数据
		}

		//创建输出流  从内存中写入本地磁盘
		workbook.write(new FileOutputStream(new File("D:/测试poi.xls")));
		//关闭资源
		workbook.close();
	}
	@Test
	void test03() throws IOException {
		//查询数据库返回集合
		Student stu1 = new Student("1","小可爱",16,new Date());
		Student stu2 = new Student("2","小蛋黄",16,new Date());
		Student stu3 = new Student("3","小狗蛋",12,new Date());
		Student stu4 = new Student("4","小嘿嘿",10,new Date());
		Student stu5 = new Student("5","小小小",23,new Date());

		List<Student> students = Arrays.asList(stu1, stu2, stu3, stu4, stu5);

		//创建一个Excel文档
		Workbook workbook = new HSSFWorkbook();

		//创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
		Sheet sheet = workbook.createSheet("用户信息表1");

		//创建一个标题行  参数：行下标(下标从0开始)
		Row row0 = sheet.createRow(0);

		//设置内容
		row0.createCell(0).setCellValue("学生信息");

		//合并行   参数：起始行,结束行,起始单元格,结束单元格
		CellRangeAddress addresses = new CellRangeAddress(2,7,5,5);
		sheet.addMergedRegion(addresses);

		//设置列宽  参数：列索引，列宽(注意：单位为1/256)
		sheet.setColumnWidth(3,20*256);

		//设置字体样式
		Font font = workbook.createFont();
		font.setBold(true); //加粗
		font.setColor(Font.COLOR_RED);  //设置字体颜色
		font.setFontHeightInPoints((short) 24);  //设置字体大小
		font.setFontName("宋体");  //设置字体
		font.setItalic(true);  //设置斜体

		//创建样式对象
		CellStyle cellStyle1 = workbook.createCellStyle();
		cellStyle1.setFont(font);

		//创建一行  参数：行下标(下标从0开始)
		Row row = sheet.createRow(1);

		//设置行高   参数：行高(注意：单位为1/20,short类型)
		row.setHeight((short) 900);

		//给目录行设置数据
		String[] title={"ID","名字","年龄","生日"};
		for (int i = 0; i < title.length; i++) {
			//创建单元格
			Cell cell = row.createCell(i);
			//设置单元格内容
			cell.setCellValue(title[i]);
			//给字体设置样式
			cell.setCellStyle(cellStyle1);
		}

		//创建一个日期格式对象
		DataFormat dataFormat = workbook.createDataFormat();
		//创建一个样式对象
		CellStyle cellStyle = workbook.createCellStyle();
		//将日期格式放入样式对象中
		cellStyle.setDataFormat(dataFormat.getFormat("yyyy年MM月dd日"));

		//处理数据行数据
		for (int i = 0; i < students.size(); i++) {
			//创建行
			Row row1 = sheet.createRow(i + 2);

			//创建单元格
			Cell cell = row1.createCell(0);
			//设置单元格内容
			cell.setCellValue(students.get(i).getId());
			//创建单元格并设置单元格内容
			row1.createCell(1).setCellValue(students.get(i).getName());
			row1.createCell(2).setCellValue(students.get(i).getAge());
			//处理日期数据
			Cell cell1 = row1.createCell(3);
			cell1.setCellValue(students.get(i).getBir());  //设置单元格内容
			cell1.setCellStyle(cellStyle);  //设置单元格日期样式
		}

		//导出单元格
		try {
			workbook.write(new FileOutputStream(new File("d://test.xls")));

			//释放资源
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test

	public String test05() {
		String url="https://yxmyvideo.oss-cn-beijing.aliyuncs.com/pic/1.jpg";
		String filePath="D:";
		FileOutputStream fos = null;
		/*  BufferedInputStream bis = null;*/
		HttpURLConnection httpUrl = null;
		URL netUrl = null;
		String fileName = "";
		try {
			netUrl = new URL(url);
			httpUrl = (HttpURLConnection) netUrl.openConnection();
			httpUrl.connect();
			//bis = new BufferedInputStream(httpUrl.getInputStream());

			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();

			BufferedImage bufferImg = ImageIO.read(httpUrl.getInputStream());
			ImageIO.write(bufferImg, "jpg", byteArrayOut);

			int width = bufferImg.getWidth();//原始宽度
			int height = bufferImg.getHeight();//原始高度

			System.out.println("图片的高度:" + height + "\t 宽度:" + width);


			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet("图片导出测试");
			// 设置A列的宽度为30*256；
			sheet.setColumnWidth(0, 200 * 256);
			Row row = sheet.createRow(0);
			row.setHeight((short) (height / 2 * 100));

			// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			// anchor主要用于设置图片的属性
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 0, 0, (short) 6, 16 );
			anchor.setAnchorType(ClientAnchor.AnchorType.byId(3));
			// 插入图片
			patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));


			String time = "123";
			fileName = time + ".xls";//图片的类型，我默认设定为jpg格式；可以自定义文件类型的，网络图片地址应该会有图片类型的，这里就需要你自己去看一下网络图片地址的规则了


			filePath = filePath + "/" + fileName;
			File outFile = new File(filePath);
			outFile.getParentFile().mkdirs();
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			fos = new FileOutputStream(outFile);
			// 写入excel文件
			workbook.write(fos);

			fos.close();
          /*  byte[] buffer = new byte[3042];
            int bytesRead = 0;

            while ((bytesRead = workbook.read!= -1) {
                fos.write(buffer, 0, bytesRead);
            }*/




		} catch (Exception e) {

			e.getMessage();
			//System.out.print("请确认网络图片是否正确！");
		}
		return fileName;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(""+1);
		System.out.println((""+1).length());
		System.out.println((""+1).equals("1"));
	}
	@Test
	public void test10(){
		System.out.println(dao.selectSexCount("女"));
	}

}
