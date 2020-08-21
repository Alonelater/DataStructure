package io;

import java.io.*;

/**
 *
 * 今天主要学习了一个流的主要应用 现在我么将流的相关进行梳理比较
 * 首先找一个文件比较大的文件 进行复制
 *
 */
public class CopyFile {

    public static void main(String[] args) throws IOException {
        //首先用一个字节一个字节去读取
        String source = "dd.txt";
        String dest = "D:\\Downloads\\11.txt";
        String dest1 = "D:\\Downloads\\33.txt";
        //copyWithNoArray(source,dest);
        //copyWithArray(source,dest);
        //copyWithBufferButNoArray(source,dest);
        //copyWithBufferAndArray(source,dest);
        //第一个太久了就没有跑了 所以综上所述 带缓冲的且有数组的最快  有数组和有数组不一定谁快 得看情况的
        //没数组的共花费1457毫秒
        //有缓冲的没数组共花费7919毫秒
        //有缓冲的且有数组共花费361毫秒




        //上面介绍的全是字节  下面我们介绍一下字符的常用方法  其实大同小异的

        //charCopyWithNoArray(source,dest1);
        //charCopyWithArray(source,dest1);
        charCopyWithBufferButNoArray(source,dest1);
        //charCopyWithBufferAndtransferIO(source,dest1);


    }


    //为了防止乱码 在创建缓冲流时传入字符字节转换流  设置编码格式
    private static void charCopyWithBufferAndtransferIO(String source, String dest) throws IOException {
        /**
         *
         * 凡是看到什么只有Reader或者Writer结尾的都是字符流  记住只有Reader或者Writer结尾
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以Reader或者Writer结尾的都是字符流 而Reader 是他们的顶级父类  称为字符输入流
         */

        //创建有缓冲的字符输入流   中间传入的是一个字符字节转换流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(source),"gbk"));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest),"gbk"));
        //接下来就是读取了并且输出了
        //由于字符流新增的方法是读取一行的 而且返回的是一个字符串  所以我们这里是定义字符串的
        String line =null;
        long startTime = System.currentTimeMillis();
        //由于字符流新增的方法是读取一行的 而且返回的是一个字符串
        while ((line=bufferedReader.readLine())!=null){
            //每次写完一行记得换行
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("缓冲字符流没数组的共花费"+(endTime-startTime)+"毫秒");
        bufferedWriter.close();
        bufferedReader.close();
    }


    //字符IO流设置一个缓冲  测量复制文件看看会不会快点  这里要注意 我们在idea里面创建的文件它的格式是utf-8的 所以我们的
    //FileReader 可以读取idea默认的字符编码 所以不会产生乱码
    //但是我们如果在电脑上直接创建文本文件 那样就是默认使用gbk编码格式 而FileReader读取 就会乱码
    //所以需要上面的方法charCopyWithBufferAndtransferIO  在传入字符编码的转换流设置编码格式进行gbk转换
    private static void charCopyWithBufferButNoArray(String source, String dest) throws IOException {
        /**
         *
         * 凡是看到什么只有Reader或者Writer结尾的都是字符流  记住只有Reader或者Writer结尾
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以Reader或者Writer结尾的都是字符流 而Reader 是他们的顶级父类  称为字符输入流
         */

        //创建字符输入流
        FileReader fileReader = new FileReader(source);
        //创建有缓冲的字符输入流  也是传入低级流
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        //创建字符输出流
        FileWriter fileWriter = new FileWriter(dest);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        //接下来就是读取了并且输出了
        //由于字符流新增的方法是读取一行的 而且返回的是一个字符串  所以我们这里是定义字符串的
        String line =null;
        long startTime = System.currentTimeMillis();
        //由于字符流新增的方法是读取一行的 而且返回的是一个字符串
        while ((line=bufferedReader.readLine())!=null){
            //每次写完一行记得换行
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("缓冲字符流没数组的共花费"+(endTime-startTime)+"毫秒");
        bufferedWriter.close();
        bufferedReader.close();
    }
    //字符IO流设置一个数组  测量复制文件看看会不会快点
    private static void charCopyWithArray(String source, String dest) throws IOException {
        /**
         *
         * 凡是看到什么只有Reader或者Writer结尾的都是字符流  记住只有Reader或者Writer结尾
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以Reader或者Writer结尾的都是字符流 而Reader 是他们的顶级父类  称为字符输入流
         */

        //创建字符输入流
        FileReader fileReader = new FileReader(source);
        //创建字符输出流
        FileWriter fileWriter = new FileWriter(dest);
        //接下来就是读取了并且输出了
        //创建一个读取的有效个数
        int len =0;
        char [] chars = new char[1024];
        long startTime = System.currentTimeMillis();
        while ((len=fileReader.read(chars))!=-1){
            fileWriter.write(chars,0,len);
            fileWriter.flush();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("字符流有数组的共花费"+(endTime-startTime)+"毫秒");
        fileWriter.close();
        fileReader.close();
    }
    //字符IO流用最简单的方式一个个的读取看看需要多少时间
    private static void charCopyWithNoArray(String source, String dest) throws IOException {
        /**
         *
         * 凡是看到什么只有Reader或者Writer结尾的都是字符流  记住只有Reader或者Writer结尾
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以Reader或者Writer结尾的都是字符流 而Reader 是他们的顶级父类  称为字符输入流
         */

        //创建字符输入流
        FileReader fileReader = new FileReader(source);
        //创建字符输出流
        FileWriter fileWriter = new FileWriter(dest);
        //接下来就是读取了并且输出了
        //创建一个读取的有效个数
        int len =0;
        long startTime = System.currentTimeMillis();
        while ((len=fileReader.read())!=-1){
            fileWriter.write(len);
            fileWriter.flush();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("字符流没数组的共花费"+(endTime-startTime)+"毫秒");
        fileWriter.close();
        fileReader.close();
    }

    //字节IO流开启缓冲并且设置数组
    private static void copyWithBufferAndArray(String source, String dest) throws IOException {
        /**
         *
         * 凡是看到什么只有InputStream结尾或者OutputStream的都是字节流 记住只有只有InputStream结尾或者OutPutStream的
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以InputStream的就是字节输入流 而InputStream 是他们的顶级父类 称为字节输入流
         *              以OutputStream的就是字节输出流 而OutputStream 是他们的顶级父类 称为字节输出流
         */
        //创建文件字节输入流
        FileInputStream fis = new FileInputStream(source);
        //创建缓冲文件字符输入流对象  需要低级流的支持
        /**
         *
         * 这里的低级流指的是所有而InputStream 或者outPutStream的子类  所以不一定是FileInputStream或者FileOutputStream
         */
        BufferedInputStream bis = new BufferedInputStream(fis);
        //创建文件字符输出流
        FileOutputStream fos = new FileOutputStream(dest);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        //开始复制
        int len = 0;
        //创建字节数组
        byte [] bytes = new byte[1024];
        long startTime = System.currentTimeMillis();
        while ((len=bis.read(bytes))!=-1){
            bos.write(bytes,0,len);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("有缓冲的且有数组共花费"+(endTime-startTime)+"毫秒");
        //记得关闭流对象  这里会帮我们关闭低级流的 保险起见还是关掉
        bos.close();
        bis.close();
    }

    //字节IO流开启缓冲 但是没有增加数组
    private static void copyWithBufferButNoArray(String source, String dest) throws IOException {
        /**
         *
         * 凡是看到什么只有InputStream结尾或者OutputStream的都是字节流 记住只有只有InputStream结尾或者OutPutStream的
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以InputStream的就是字节输入流 而InputStream 是他们的顶级父类 称为字节输入流
         *              以OutputStream的就是字节输出流 而OutputStream 是他们的顶级父类 称为字节输出流
         */
        //创建文件字节输入流
        FileInputStream fis = new FileInputStream(source);
        //创建缓冲文件字符输入流对象  需要低级流的支持
        /**
         *
         * 这里的低级流指的是所有而InputStream 或者outPutStream的子类  所以不一定是FileInputStream或者FileOutputStream
         */
        BufferedInputStream bis = new BufferedInputStream(fis);
        //创建文件字符输出流
        FileOutputStream fos = new FileOutputStream(dest);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        //开始复制
        int len = 0;
        long startTime = System.currentTimeMillis();
        while ((len=bis.read())!=-1){
            bos.write(len);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("有缓冲的没数组共花费"+(endTime-startTime)+"毫秒");
        //记得关闭流对象
        bos.close();
        bis.close();
    }

    //字节IO流设置一个数组  测量复制文件看看会不会快点
    private static void copyWithArray(String source, String dest) throws IOException {

        /**
         *
         * 凡是看到什么只有InputStream结尾或者OutputStream的都是字节流 记住只有只有InputStream结尾或者OutPutStream的
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以InputStream的就是字节输入流 而InputStream 是他们的顶级父类 称为字节输入流
         *              以OutputStream的就是字节输出流 而OutputStream 是他们的顶级父类 称为字节输出流
         */
        //创建文件字节输入流
        FileInputStream fis = new FileInputStream(source);
        //创建文件字符输出流
        FileOutputStream fos = new FileOutputStream(dest);
        //开始复制
        int len = 0;
        byte[] bytes = new byte[1024];
        long startTime = System.currentTimeMillis();
        while ((len=fis.read(bytes))!=-1){
            fos.write(bytes,0,len);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("有数组的共花费"+(endTime-startTime)+"毫秒");
        //记得关闭流对象
        fos.close();
        fis.close();
    }

    //字节IO流用最简单的方式一个个的读取看看需要多少时间

    private static void copyWithNoArray(String source, String dest) throws IOException {


        /**
         *
         * 凡是看到什么只有InputStream结尾或者OutputStream的都是字节流 记住只有只有InputStream结尾或者OutPutStream的
         * 因为还有一个记住只有InputStreamReader和OutputStreamWriter 是字符字节转换的用的
         * 他是最特殊的
         * 所以记住只有唯一以InputStream的就是字节输入流 而InputStream 是他们的顶级父类 称为字节输入流
         *              以OutputStream的就是字节输出流 而OutputStream 是他们的顶级父类 称为字节输出流
         */
        //创建文件字节输入流
        FileInputStream fis = new FileInputStream(source);
        //创建文件字符输出流
        FileOutputStream fos = new FileOutputStream(dest);
        //开始复制
        int len = 0;
        long startTime = System.currentTimeMillis();
        while ((len=fis.read())!=-1){
            fos.write(len);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("有数组的共花费"+(endTime-startTime)+"毫秒");
        //记得关闭流对象
        fos.close();
        fis.close();
    }
}
