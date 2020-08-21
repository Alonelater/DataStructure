package sparsearray;



import java.io.*;
import java.util.Arrays;


/**
 *
 * 今天开始学习数据结构 首先先来学习稀疏数组
 *
 */
public class SparseArray {

    public static void main(String[] args) throws IOException {





        //模拟一个场景就是两个人在下棋  现在我要求保存这两个人下的棋 方便到时候复盘
        //首先我们定义一个9*9的棋盘  那就是一个二维数组 如果他们两个人只下了3个字就有事就走了 准备有时间接着下
        //定义棋盘
        int chessArr[][] = new int[9][9];
        //开始下棋 规定在2行3列下了一个黑棋（用1表示）在3行4列下了一个白棋（用2表示）在3行6列下了一个黑棋（用1表示）
        chessArr[1][2]=1;
        chessArr[2][3]=2;
        chessArr[2][5]=1;

        //看看我们制作的棋盘
        for (int[] col : chessArr) {
            for (int i : col) {
                System.out.print(i+"  ");
            }
            System.out.println();
        }

        System.out.println("====================");//或者下面的方式
//        for (int i = 0; i < chessArr.length; i++) {
//            for (int i1 = 0; i1 < chessArr[i].length; i1++) {
//                System.out.print(chessArr[i][i1]);
//            }
//            System.out.println();
//        }


        Reader reader=null;
        try {
            reader = new FileReader("cc.txt");
            int len = 0;
            while ((len=reader.read())!=-1){
                System.out.println((char) len);
            }
        } finally {
            reader.close();
        }



        Writer writer=null;
        try {
            writer = new FileWriter("cc.txt",true);

            writer.write(2555);

            writer.flush();

        } finally {

            writer.close();
        }

        //棋盘制作完毕 现在我们制作稀疏数组 稀疏数组的含义就是将二维数组中单独存在的  其实在这里面就是
        //棋子的位置用稀疏数组表示出来 稀疏数组的第一行表示原数组是几行几列有几个有效数组的数组 也就是说稀疏数组总共有三列
        //行数是有原数组的行数加1得到的   所以我们需要知道原来共有几个有效数字
        int sum=0;
        for (int[] col : chessArr) {
            for (int i : col) {
                if (i!=0){
                    sum++;
                }
            }
        }
        System.out.println("总共有"+sum+"个有效数字");




        //将创建稀疏数组的方法封装起来

        //
         int[][] sparseArray = createSparseArray(chessArr, sum);


        System.out.println("稀疏数组的效果输出如下");
        //上面已经制作好了稀疏数组了  看看效果
        //看看我们制作的棋盘
        for (int[] col : sparseArray) {
            for (int i : col) {
                System.out.print(i+"  ");
            }
            System.out.println();
        }

        //接下来将数组从内存中存放在我们的硬盘中进行保存
        //首先告诉我们的目的地是将数据是什么 还有是放在那里
        //save(sparseArray,"D:\\JavaCode\\DataStructure\\bb.txt");
        //save(sparseArray,"D:\\JavaCode\\DataStructure\\src\\sparsearray\\123.txt");


        //上面已经将数据保存在硬盘了 就下来就是将它读取出来还原成原来的数组

        //System.out.println("数据已经保存");
        //read("D:\\JavaCode\\DataStructure\\bb.txt");




        //扩展：现在复制一张电脑上的图片到另一个磁盘上面去
        copyPicture("D:\\电脑壁纸\\38d1539b428cf9a9484eb99944e828f5.jpg","C:\\1.jpg");
    }

    private static void copyPicture(String source, String dest) throws IOException {
//        //创建输入流对象
//        FileInputStream fis = new FileInputStream(source);
//        //创建输出流对象
//        FileOutputStream fos = new FileOutputStream(dest);
//        //读取的有效字节个数
//        int len = 0;
//
//        while ((len=fis.read())!=-1){
//            fos.write(len);
//        }
//
//        fos.close();
//        fis.close();


        //但是上面的太慢了 所以我们加入缓冲提高效率
        //创建输入流对象
        FileInputStream fis = new FileInputStream(source);
        //创建输出流对象
        FileOutputStream fos = new FileOutputStream(dest);
        //读取的有效字节个数
        int len = 0;
        byte [] bytes = new byte[1024];
        while ((len=fis.read(bytes))!=-1){
            fos.write(bytes,0,len);
        }

        fos.close();
        fis.close();


    }

    private static void read(String path) throws IOException {
        //首先判断这个文件对象是否存在或者这个文件对象是不是一个文件
        File file = new File(path);
        //创建文件输入流
        FileInputStream fis =null;
        if (!file.exists()||!file.isFile()){
            throw new RuntimeException();
        }else {
            fis= new FileInputStream(file);

            int len = 0;
            byte [] bytes = new byte[1024];
            //为了提高拼接的效率我们用StringBuffer
            StringBuffer sb =new StringBuffer();
            while ((len=fis.read(bytes))!=-1){
                //由于我们每次在存取二进制的时候我们还使用了换行符就是上面的save方法中的 fos.write("\n\r".getBytes());
                //  所以我们接下来就要判断一下
                //先得到所有的读取的值
                fis.read(bytes,0,len);
                String s = new String(bytes, 0, len);
                sb.append(s);

            }
            System.out.println(sb);
        }
    }

    /**
     *
     *
     * @param source  原数组
     * @param sum 原数组有效数字
     * @return 稀疏数组
     */

    public static int[][] createSparseArray(int [][] source,int sum){


        //开始创建稀疏数组  行数为有效数字加1
        int sparseArray[][]= new int[sum+1][3];
        //开始对稀疏数组赋值
        sparseArray[0][0]=sparseArray.length;//多少行
        sparseArray[0][1]=sparseArray[0].length;//多少列
        sparseArray[0][2]=sum;//多少有效数字
        int count=1;
        //遍历原数组 有效数字就记录
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                if (source[i][j] != 0) {
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = source[i][j];
                    count++;
                }

            }
        }
        return sparseArray;
    }


    /**
     *
     *
     * @param source
     * @param path
     */
    public static void save(int [][] source, String path){


        //注意这一行代码只是创建了一个File对象  这个文件对象可以用来操作我们的文件或者文件夹 这个对象里面拥有一系列方法
        //但是并不是创建文件或者文件夹
        //new File(String path)  这个方法只是为了将指定路径封装成一个File对象  不管你给的文件的路径或者文件夹的路径是否正确
        //只是将它封装成一个File对象  之后你能用这个对象去判断这个你给的路径封装出来的对象是不是文件对象 还是文件夹对象 到底存不存在
        //还有他的父路径是什么 文件的名称是什么 等等等等  那就是File类的方法的使用了
        File file = new File(path);
        //也可以不创建  因为我们待会儿使用的输出流会帮我们创建的文件  只要保证文件的文件夹路径正确就行了 如果文件夹的错误那么就会抛异常
        //所以为了保险起见我们将我们可以将我路径的盘符截取出来用来创建文件夹 然后在在这个文件夹下面创建文件
        //判断这个文件对象是否存在  不存在就创建 存在就不创建
        FileOutputStream fos =null;
        if (!file.exists()){

            try {
                //创建文件对象  但是一定要保证所给的路径是正确的  也就是说这个路径是一定要存在 这个方法只会创建文件
                //不会根据你有没有文件夹帮你创建文件夹
                //所以恰当的方式应该这样写
                //找到最后一个"/"的位置
                int i = path.lastIndexOf("\\");
                //然后截取出来前面的是文件夹 后面的是文件
                String substring = path.substring(0, i);
                //创建文件夹
                File file1 = new File(substring);
                boolean mkdirs = file1.mkdirs();

                String substring1 = path.substring(i+1);
                //利用构造方法直接创建一个新的文件对象
                File file2 = new File(file1, substring1);
                boolean newFile = file2.createNewFile();
                //好了上面已经有了具体的保存内容的文件 接下来就是将数组存到硬盘中了
                //获得文件输出流用于写文件  file2这个文件对象中的信息就是我们文件输出流的目的地
                //创建文件输出流
                 fos = new FileOutputStream(file2);
                 //将实际的写入操作封装起来了
               writeInFile(source,fos);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }else {
            try {
                fos = new FileOutputStream(file);
                //将实际的写入操作封装起来了
                writeInFile(source,fos);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }


     }

    private static void writeInFile(int [][] source,OutputStream fos) throws IOException {
        //BufferedOutputStream bos = new BufferedOutputStream(fos);
        //遍历集合
        for (int[] ints : source) {
            for (int anInt : ints) {
                fos.write((anInt+"").getBytes());
            }
            fos.write("\n\r".getBytes());
        }
    }


}
