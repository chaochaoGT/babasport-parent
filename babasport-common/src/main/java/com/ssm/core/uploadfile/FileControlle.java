package com.ssm.core.uploadfile;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * 上传文件控制器
 * Created by Administrator on 2017/8/16.
 */
public class FileControlle {

    /**
     *上传文件的工具
     * @param mpfByte       上传文件的byte数组
     * @param fileName  上传文件名
     * @param confPath  fastDFS的conf配置文件
     * @return          上传图片后返回文件的路径
     */
    public static String uploadTool(byte[] mpfByte,String fileName,String confPath)  {
        try {
            //获取conf文件路径
            ClassPathResource pathResource = new ClassPathResource(confPath);

            //初始化客户端
            ClientGlobal.init(pathResource.getClassLoader().getResource(confPath).getPath());
            //设置管理器
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer connection = trackerClient.getConnection();
            //设置存储器
            StorageClient1 storageClient=new StorageClient1(connection,null);


            String imgPath = storageClient.upload_file1(mpfByte, fileName, null);
            return imgPath;
        } catch (Exception e) {
           throw new RuntimeException("上传图片异常"+e);
        }
    }
}
