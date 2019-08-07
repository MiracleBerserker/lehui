package miracle.cherry.lehui.common.tools;



import java.io.*;

/**
 * @Description:
 * @Copyright: Dist
 * @Author: MengHui
 * @Date: 2019-07-16 17:00
 * @Modified:
 * @Description:
 */
public class FileTools {

    public static void saveFile(String filePath, InputStream in) throws IOException {
        System.out.println(filePath);
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            if(!file.getParentFile().mkdir()){
                throw new IOException("无法创建目录 数据上传失败");
            }
        }
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedOutputStream buff = new BufferedOutputStream(new FileOutputStream(file));
        BufferedInputStream bin = new BufferedInputStream(in);
        byte [] bf = new byte[1024];
        int len = -1;
        while ((len = bin.read(bf)) != -1){
            buff.write(bf,0,len);
        }
        in.close();
        buff.flush();
        buff.close();


    }

    /**
     * 获取文件名
     *
     * @param fullFileName
     * @return
     */
    public static String getFileName(String fullFileName) {
        int slantIndex = fullFileName.lastIndexOf("/");
        int backslashIndex = fullFileName.lastIndexOf("\\");

        int index = slantIndex > backslashIndex ? slantIndex : backslashIndex;
        return fullFileName.substring(index + 1);
    }

}
