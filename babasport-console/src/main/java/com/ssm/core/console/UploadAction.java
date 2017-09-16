package com.ssm.core.console;

import com.ssm.core.dictionary.Constants;
import com.ssm.core.uploadfile.FileControlle;
import org.apache.commons.io.FilenameUtils;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
public class UploadAction {

    //from异步上传图片
    @RequestMapping("uploadfile.do")
    @ResponseBody
    public Map<String, String> uploadfile(MultipartFile mpf) throws Exception {
        //返回存储数据map
        //设置上传需要提供文件名
        String extension = FilenameUtils.getExtension(mpf.getOriginalFilename());
        System.out.println("from异步上传图片" + mpf.getOriginalFilename());
        String imgPath = FileControlle.uploadTool(mpf.getBytes(), extension, "fdfs_client.conf");
        System.out.println("from异步上传图片" + imgPath);
        Map<String, String> map = new HashMap<>();
        map.put("imgPath", Constants.fastDFS_service + imgPath);
        return map;
    }

    //from异步上传多张图片
    @RequestMapping("uploadPics.do")
    @ResponseBody
    public List<String> uploadPics(@RequestParam("mpfs") MultipartFile[] mpfs) throws Exception {
        //返回存储数据map
        //设置上传需要提供文件名
        List<String> list = new ArrayList<>();
        if (mpfs.length != 0) {
            for (MultipartFile mpf : mpfs) {

                String extension = FilenameUtils.getExtension(mpf.getOriginalFilename());
                System.out.println("from异步上传图片" + mpf.getOriginalFilename());
                String imgPath = FileControlle.uploadTool(mpf.getBytes(), extension, "fdfs_client.conf");
                System.out.println("from异步上传图片" + imgPath);
                list.add(Constants.fastDFS_service + imgPath);
            }
        }
        return list;
    }


    //富文本from异步上传多张图片
    @RequestMapping("uploadFck1.do")
    @ResponseBody
    public Map<String, Object> uploadPics1(@RequestParam("uploadFile") MultipartFile[] Fcks) throws Exception {
        //返回存储数据map
        Map<String, Object> hashMap = new HashMap<>();
        if (Fcks.length != 0) {
            for (MultipartFile mpf : Fcks) {
                String extension = FilenameUtils.getExtension(mpf.getOriginalFilename());
                //设置上传需要提供文件名
                System.out.println("富文本from异步上传多张图片" + mpf.getOriginalFilename());
                String imgPath = FileControlle.uploadTool(mpf.getBytes(), extension, "fdfs_client.conf");
                System.out.println("富文本from异步上传多张图片" + imgPath);
                hashMap.put("error", 0);
                hashMap.put("url", Constants.fastDFS_service + imgPath);
                return hashMap;
            }
        }
        return null;
    }

    //富文本异步上传多张图片
    @RequestMapping("uploadFck.do")
    @ResponseBody
    public Map<String, Object> uploadFck(HttpServletRequest request, HttpServletResponse response) {
        try {
            //返回存储数据map
            List<String> list = new ArrayList<>();
            Map<String, Object> hashMap = new HashMap<>();
            MultipartRequest mfs = (MultipartRequest) request;
            Set<Map.Entry<String, MultipartFile>> entFiles = mfs.getFileMap().entrySet();
            //循环上传图片
            if (entFiles.size() != 0) {
                for (Map.Entry<String, MultipartFile> entFile : entFiles) {
                    MultipartFile Fck = entFile.getValue();
                    //设置上传需要提供文件名
                    String extension = FilenameUtils.getExtension(Fck.getOriginalFilename());
                    System.out.println("富文本异步上传多张图片" + Fck.getOriginalFilename());
                    String imgPath = FileControlle.uploadTool(Fck.getBytes(), extension, "fdfs_client.conf");
                    System.out.println("富文本异步上传多张图片" + imgPath);
                    list.add(Constants.fastDFS_service + imgPath);
                    hashMap.put("error", 0);
                    hashMap.put("url", Constants.fastDFS_service + imgPath);
                }

            }
            return hashMap;
        } catch (IOException e) {
            throw new RuntimeException("富文本异步上传多张图片" + e);
        }
    }
}
