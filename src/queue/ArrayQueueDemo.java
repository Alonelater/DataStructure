package queue;

import java.util.Scanner;

/**
 * 现在我们学习队列  简单回顾一下 什么是队列
 * 队列是一个有序的列表 遵循先进先出FIFO(First in First Out)
 * 实现队列有几种方式 我们先用数组实现队列的方式 先介绍几个基本概念  不然待会儿看不懂
 * <p>
 * <p>
 * 首先就是
 * maxSize  指的就是队列的最大容量
 * front    指的是队列的最先进去的元素
 * rear     指的是现在指向的元素位置
 * <p>
 * 初始化时的时候front 和rear都是指向-1的位置 也就是说
 * 初始化时   front=rear=-1;
 * 每增加一个元素时 rear = rear+1  front 不动还是front=-1;
 * 每取出一个元素时 rear不动 还是当时指向的最后的一个元素  front=front+1;
 * 所以我们就能得出什么时候队列是满的 什么是空的时候
 * 队列满了就是rear==maxSize-1;
 * 队列空的就是rear==front(但此时两个人都不一定等于-1)
 */
//使用数组模拟队列
public class ArrayQueueDemo {



    public static void main(String[] args) {

        //创建一个大小为3的数据
        ArrayQueue arrayQueue = new ArrayQueue(3);
        char key = ' ' ;
        Scanner scanner = new Scanner(System.in);

        boolean loop = true;
        //下面做一个简单的控制台
        while (loop) {
            System.out.println("a(add)");//添加
            System.out.println("g(get)");//取出
            System.out.println("s(showQueue)");//展示整个队列
            System.out.println("f(front)");//展示头元素
            System.out.println("e(add)");//退出

            String next = scanner.next();
            key = next.charAt(0);
            switch (key){
                case 'a':
                    System.out.println("请输入一个数");
                    int value= scanner.nextInt();
                    arrayQueue.add(value);
                    break;
                case 'g':
                    arrayQueue.get();
                    break;
                case 's':
                    arrayQueue.showQueue(arrayQueue.arr);
                    break;
                case 'f':
                    arrayQueue.showFront();
                    break;
                case 'e':
                    loop = false;
                    break;
            }

        }
    }



    /**
     *
     *
     * 经过上面的一系列操作我们发现我们的数组模拟队列只是能够模拟  你实际上根本没有把数组里面的值进行取出
     * 所以等到你添加完 然后又取完 你会发现还是不能够在添加 因为他会模拟出对队列已满的状态
     * 导致假象你已经把所有添加的数据都堆满了 你还是没法回到最初的指针状态去进行重新开始 除非另外再增加判断条件
     * 所以引出新的知识 环形队列
     */



}


class ArrayQueue{
    //首先准备上面的东西
    private int maxSize;//队列容量
    private int front;//队列第一个进去的元素的更前一个位置 不是指向的是元素的所在位置
    //而是比他还要前 比如-1 因为我们的元素插入是0的位置
    private int rear;//队列的最后指针  相当于跟着元素下标走 元素下标是什么rear就是什么
    public int[] arr;//最重要的数组

    //先通过构造器初始化数组
    public ArrayQueue(int num) {
        arr = new int[num];
        this.maxSize = num;
        this.front = -1;
        this.rear = -1;
    }

    //接下来就是通过一系列的方法对队列进行操作

    //判断队列是否满了
    public boolean isFull() {
        return rear==maxSize-1;
    }

    //判断队列是否是空的
    public boolean isEmpty() {

        //由于队列可能不是初始化那样是空的  也可能是之后增加了数据但是把数据取空了导致front后移
        //那样rear和front 就不是都等于-1了
        //所以那时候front=rear 但不一定就是等于-1;
        return rear==front;
    }

    //添加元素
    public void add(int event) {
        //添加元素首先看看是否满了
        if (isFull()) {
            //表示满了 直接退出
            System.out.println("队列满了，不能添加");
            return;
        } else {
            //如果没满  首先先将添加的位置定下来 由于初始化时-1  所以每次添加都要先加一
            rear++;
            arr[rear] = event;
            System.out.println("添加的元素是" + event + "位置是arr[" + rear + "]");
        }
    }


    //展示最前面一个元素  但是不取出来  就只是就只是展示
    public void get() {
        if (isEmpty()) {
            //表示空了 直接退出 不展示
            System.out.println("队列为空，无法取出数据");
            return;
        } else {
            front++;
            int temp = arr[front];
            System.out.println("取出的元素是" + temp + "位置是arr[" + front + "]");
        }
    }

    //展示最前面一个元素  但是不取出来  就只是就只是展示
    public void showFront() {
        if (isEmpty()) {
            //表示空了 直接退出 不展示
            System.out.println("队列为空，无法展示");
            return;
        } else {
            //因为只是查看 所以你不能自增front  只是在展示的时候看的是后面一个 所以还是得保持一开始的front
            //只有get才能front往后加一
            System.out.println(arr[front+1]);
        }
    }
    //展示整个队列
    public void showQueue(int[] arr) {
        if (isEmpty()){
            System.out.println("队列为空，无法遍历数据");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }

    }

}







