package com.bestdata.demo.utils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DeletVliaImg {
	
  //根据session中存储的值，删除使用过的验证码
	public static void delectImg(String workspaceRootPath){
		
		File file =new File(workspaceRootPath);
		if(file.exists() && file.isFile()){
			boolean result = file.delete();
			//System.out.println("------" + result);
			int tryCount = 0;
			// 删除失败强制删除
            while (!result && tryCount++ < 5) {
                System.gc(); // 回收资源
                result = file.delete();
                //System.out.println(tryCount + ":------" + result);
            }
		}
			   
	}
	//设置执行时间间隔fixedRate = 2000 cron="0 0 2 ? * * "
	@Scheduled(cron="0 0 2 ? * * ")
	public void deletImgByDay(){
		String workspaceRootPath= "";
		String path1=DeletVliaImg.class.getResource("").getPath().toString();
		//linux下
		String path2=path1.substring(0, path1.indexOf("WEB-INF"));
		//windows下
		//String path2=path1.substring(1, path1.indexOf("WEB-INF"));
		workspaceRootPath = path2+"indentyCode";
		
		//System.out.println(workspaceRootPath);
		File file = new File(workspaceRootPath);
		if(file.exists()){
			String[] tempList = file.list();
		       File temp = null;
               for (int i = 0; i < tempList.length; i++) {
                  if (workspaceRootPath.endsWith(File.separator)) {
                     temp = new File(workspaceRootPath + tempList[i]);
                  } else {
                      temp = new File(workspaceRootPath + File.separator + tempList[i]);
                  }
                  if (temp.isFile()) {
                     temp.delete();
		          }
		          
		          /*if (temp.isDirectory()) {
		             delAllFile(workspaceRootPath + "/" + tempList[i]);//先删除文件夹里面的文件
		             delFolder(path + "/" + tempList[i]);//再删除空文件夹
		             flag = true;
		          }*/
		       }
		      /* System.out.println("已经删除旧验证码图片");*/
		
		}else{
			/*System.out.println("未成功删除验证码图片");*/
		}
		 
	}
	
	
	
	
}
