package linkedlist.josepfu;

import javax.sound.midi.VoiceStatus;

/**
 *
 * 今天我们解决经典的约瑟夫问题，也就是丢手绢问题
 *
 * Josephu(约瑟夫、约瑟夫环)  问题
 * Josephu  问题为：
 * 设编号为1，2，… n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，数到m 的那个人出列，它的下一位又从1开始报数，数到m的那个人又出列，依次类推，
 * 直到所有人出列为止，由此产生一个出队编号的序列。
 *
 * 提示：用一个不带头结点的循环链表来处理Josephu 问题：先构成一个有n个结点的单循环链表，然后由k结点起从1开始计数，计到m时，对应结点从链表中删除，
 * 然后再从被删除结点的下一个结点又从1开始计数，直到最后一个结点从链表中删除算法结束。
 */
public class Josepfu {
    public static void main(String[] args) {

        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(41);
        circleSingleLinkedList.showList();
        circleSingleLinkedList.goBoy(1,3,41);
    }
}


class CircleSingleLinkedList{

    //开始创建环形链表

    //先默认声明第一个小孩的变量
    private Boy first = null;
    //然后添加一个方法 告诉我要添加的小孩的个数
    public void  addBoy(int num){
        //简单判断
        if (num<1){
            System.out.println("添加小孩有误");
            return;
        }
        //开始添加
        //辅助指针
        Boy currBoy=null;
        for (int i = 1; i <= num; i++) {
            //创建小孩
            Boy boy = new Boy(i);
//            if (i==1){
//                //如果是第一个小孩  我们让first指向第一个小孩
//                first=boy;
//                //让first的指针指向自己构成环状
//                first.setNext(boy);
//                //让辅助指针指向first,因为辅助指针不能动  后面还有最后的节点还要指向回去
//                currBoy=first;
//            }else {
//                //如果不是第一个节点 直接让最后的一个节点 也就是currBoy现在指向的这个节点(最后节点)先指向每次创建的boy节点
//                currBoy.setNext(boy);
//                //让最后一个节点指向头结点 就是first
//                boy.setNext(first);
//                //让currBoy时时刻刻指向后面一个节点
//                currBoy=boy;
//            }
//


            //测试成功
            //下面提供一个新的方案 可以看看可不可以实现环状单链表
            //如果是第一个小孩就让头结点和辅助节点都指向第一个小孩 然后每次添加都是移动辅助节点 到添加完毕再统一首尾相连
            //省的每加入一个节点都要成环
            if (i==1){
                //头结点和辅助节点都指向第一个节点小孩
                currBoy=first=boy;
                //就算全程只要一个小孩也就不成环了 无所谓的
            }else {
                currBoy.setNext(boy);
                currBoy=boy;
            }
            //如果是最后一个 那就让收尾相接
            if (i==num){
                currBoy.setNext(first);
                currBoy=first;
            }
        }
    }

    //遍历链表
    public void showList(){
        if (first==null){
            System.out.println("链表为空");
            return;
        }

        Boy temp = first;
        while (true){
            //输出结果
            System.out.println(temp.getNo());
            //因为是循环链表 所以如果一直遍历 遍历到first说明遍历结束了
            if (temp.getNext()==first){
                System.out.println("循环遍历完毕");
                break;
            }
            temp=temp.getNext();
        }

    }

    //开始让小孩出圈

    /**
     *
     * @param startNo  表示从第几个小孩开始数数
     * @param stepNo  表示数几下 就是移动步数
     * @param nums 表示最初有几个小孩
     */
    public void goBoy(int startNo,int stepNo,int nums){

        //简单校验
        if (first==null||first.getNext()==null||startNo>nums||startNo<1){
            System.out.println("参数有误");
        }


        //首先我们需要定位
        //我们默认添加小孩的编号是1,2,3,4,5这种递增的 所以我们如果不是从一号小孩开始报数 我们就要先让头结点指向要报数的小孩先
        //那就是让first一直后移就行了 后移startNo-1次
        for (int i=0;i<startNo-1;i++){
            first=first.getNext();
        }

        //需要辅助指针帮我们 上面已经让头结点换了位置 去了应该去的地方了，接下来就是让辅助指针去此时头结点的 后面一个位置就是当前链表的最后 然后让temp辅助指针指向最后一个所以我们先遍历找到最后一个指针的位置
        Boy temp = first;
        while (true){
            //由于是环形链表 所以最后一个指针的下一个就是头  所以我们循环找到temp该有的位置就行了
            //说明当前指向的节点就是最后一个节点了
            if (temp.getNext()==first){
                break;
            }
            temp=temp.getNext();
        }


        //好了现在开始出圈
        //什么时候就剩下最后一个人那就是temp==first的时候
        while (true){
            if (temp==first){
                System.out.println("最后一个小孩的编号是"+first.getNo());
                break;
            }
            //如果圈内还有人 就让temp 和head按照上面的移动步数开始 移动
            //由于当前的头结点也要报数 所以first实际只要移动stepNo-1 就到了要出圈的小伙伴那个节点
            for (int j=0;j<stepNo-1;j++){
                first=first.getNext();
                temp=temp.getNext();
            }

            //此时first就是要出圈的小孩
            System.out.println("小孩编号为"+first.getNo()+"出圈了");
            //现在重新构建出圈后的小孩的新循环链表
            first=first.getNext();
            temp.setNext(first);
        }


    }
}


//小孩类
class Boy{
    private int no;
    private Boy next;//表示指向下一个节点

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "no=" + no +
                ", next=" + next +
                '}';
    }
}