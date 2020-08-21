package queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {

    public static void main(String[] args) {

        //创建一个大小为3的数据
        CircleArrayQueue arrayQueue = new CircleArrayQueue(4);
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
}
class CircleArrayQueue{
    //首先准备上面的东西
    private int maxSize;//队列容量
    private int front;//这个时候由于是环形队列 所以和之前的front含义不一样  这里front就是直接指向第一个元素的位置
    //而且设置的初始值为0  表示的就是将一开始插入的值设置为最前面的元素
    private int rear;//队列的最后指针  但是这个时候的rear表示的指向最后一个元素的后一个位置 一定要仔细理解这句话的意思
    //比如我的数组大小只有3的容量  也就是判断队列满的时候就是数组存了两个的时候就表示已经满了 因为我们要留一个位子做缓冲 方便我们后面的操作
    //所以我们判断满的条件实施(rear+1)%maxSize==front 这个时候就满了
    // 相当于就是我们容量为3的数组 此时的front==0 rear==2 当我们判断是否队列满了没有我们经过我们自己的规定
    //即(rear(此时为2)+1)%maxSize(队列的最大容量为3)==0  所以即使最后的下标为2的位置是空的 我们也说他满了

    public int[] arr;//最重要的数组
    //先通过构造器初始化数组
    public CircleArrayQueue(int num) {
        arr = new int[num];
        this.maxSize = num;
        this.front = 0;
        this.rear = 0;
    }


    //接下来就是通过一系列的方法对队列进行操作

    //判断队列是否满了
    public boolean isFull() {
        //如果看不懂看上面的初始化
        return (rear+1)%maxSize==front;
    }

    //判断队列是否是空的
    public boolean isEmpty() {
        //虽然这个front 和rear 含义已经变化 但是这个还是不变的
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
            //如果没满  首先先将添加的位置定下来 由于初始化时0 所以增加的时候直接就是增加在这里
            arr[rear] = event;

            System.out.println("添加的元素是" + event + "位置是arr[" + rear + "]");
            //指向最后一个元素位置（其实也就是现在的位置）的后一个位置 取出之后指针向移动一位 为了防止越界  所以对容量取余
            rear = (rear+1)%maxSize;
        }
    }
    //展示最前面一个元素  但是不取出来  就只是就只是展示
    public void get() {
        if (isEmpty()) {
            //表示空了 直接退出 不展示
            System.out.println("队列为空，无法取出数据");
            return;
        } else {
            int temp = arr[front];
            System.out.println("取出的元素是" + temp + "位置是arr[" + front + "]");
            //取出之后指针向移动一位 为了防止越界  所以对容量取余
            front = (front+1)%maxSize;

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
            System.out.println(arr[front]);
        }
    }

    //展示整个队列
    public void showQueue(int[] arr) {
        if (isEmpty()){
            System.out.println("队列为空，无法遍历数据");
            return;
        }
        //这里的遍历就有点东西了 要仔细想我们实际的数据有几个
        //举个例子 假如我们实际的数据有2个 队列容量为4 所以我们的rear就是为2 front为0
        //所以得出这个公式 实际数量等于(rear-front+maxSize)%maxSize=等于实际存储的数量
        for (int i = front; i < front+getNum(); i++) {
            System.out.println("元素的位置是arr["+i+"]数据是"+arr[(i%maxSize)]);
        }

    }

    //得到实际存储的数量
    public int getNum(){
        return (rear-front+maxSize)%maxSize;
    }
}
