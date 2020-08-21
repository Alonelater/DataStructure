package linkedlist.single;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.w3c.dom.ls.LSInput;
import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TooManyListenersException;


//今天我们开始学习数据结构的单链表 这个是在大学学过的 应该有点印象
public class SingleLinkedListDemo {


    //下面的英雄节点和我们的单链表我们已经定义好了 接下来就是我们插入节点了

    public static void main(String[] args) {

//        //创建单链表  给与头结点
        SingleLinkedList singleLinkedList = new SingleLinkedList(new HeroNode(0, "头结点", "小头"));

        SingleLinkedList singleLinkedList1 = new SingleLinkedList(new HeroNode(0, "有序头结点", "有序小头"));
//
//        //创建节点 插入单链表
        singleLinkedList.add(new HeroNode(1, "宋江", "及时雨"));
        singleLinkedList.add(new HeroNode(3, "吴用", "智多星"));
        singleLinkedList.add(new HeroNode(2, "林冲", "豹子头"));
        singleLinkedList.add(new HeroNode(4, "鲁智深", "花和尚"));
//
        singleLinkedList.showList();
        System.out.println("------------------------------");
//
//        //创建节点 插入单链表
        singleLinkedList1.addOrderBy(new HeroNode(1, "宋江", "及时雨2"));
        singleLinkedList1.addOrderBy(new HeroNode(3, "林冲", "豹子头2"));
        singleLinkedList1.addOrderBy(new HeroNode(4, "鲁智深", "花和尚"));
        singleLinkedList1.addOrderBy(new HeroNode(2, "离骚", "鼓上骚"));
//
        singleLinkedList1.showList();
//        singleLinkedList1.addOrderBy(new HeroNode(2, "林冲", "豹子头3"));
//        singleLinkedList1.showList();
//        System.out.println("修改节点信息之后链表信息如下");
//        singleLinkedList1.updateList(new HeroNode(1, "小宋", "小雨"));
//        singleLinkedList1.showList();
//        System.out.println("删除节点信息之后链表信息如下");
//        singleLinkedList1.del(1);
//        singleLinkedList1.showList();
//        System.out.println("计算链表实际长度");
//        int listLength = singleLinkedList1.getListLength();
//        int listLength2 = singleLinkedList.getListLength();
//        System.out.println(listLength);
//        System.out.println(listLength2);
        System.out.println("得到倒数第几个节点");
        HeroNode heroNodeByLastIndex = singleLinkedList1.findHeroNodeByLastIndex(2);
        System.out.println(heroNodeByLastIndex);
        System.out.println("反转单链表");
        SingleLinkedList singleLinkedList2 = SingleLinkedList.reverseListByTeacher(singleLinkedList);
        singleLinkedList2.showList();
        System.out.println("反转打印单链表");
        SingleLinkedList.reversePrint(singleLinkedList1);


    }
}


//单链表的定义
class SingleLinkedList {

    /**
     *链表是有序的列表，
     * 链表分为单向链表 还有双向链表
     * 认识一下链表的基本概念
     * 1）链表是以节点的方式来存储,是链式存储
     * 2）每个节点包含 data 域， next 域：指向下一个节点.所以他的next 域至关重要
     * 3）发现链表的各个节点不一定是连续存储.
     * 4）链表分带头节点的链表和没有头节点的链表，根据实际的需求来确定
     *
     */


    /**
     * 模拟在单链表中添加数据
     * 在单链表中添加水浒传的英雄人物  要求显示人物编号 姓名 昵称等等情况
     * <p>
     * <p>
     * <p>
     * 分析 现在我们设计一个有头结点的单链表 将水浒装的人物封装成一个类
     */


    //节点类已经写好了  接下来就是创将头结点 将人物英雄添加到节点里面去了
    //创建头结点  将它作为一个成员变量
    private final HeroNode headNode;

    public SingleLinkedList(HeroNode headNode) {
        this.headNode = headNode;
    }

    public HeroNode getHeadNode() {
        return headNode;
    }

    //我们在写一些方法方便我们调用
    //第一种方法在添加英雄时，直接添加到链表的尾部  待会儿我们再改进我们的单链表
    public void add(HeroNode element) {

        //第一种方法在添加英雄时，直接添加到链表的尾部  待会儿我们再改进我们的单链表
        //思路分析 想要直接添加到单链表的结尾 说明最后一个元素的next 域一定要为空
        //所以我们要借助头结点一个个找下去 找不到就下一个  那就要利用循环
        //将头结点使用一个变量给拿出来
        HeroNode temp = headNode;
        while (true) {
            //从头结点的next域开始查询 没有就下一个  一旦为空说明查到了最后一个 就退出循环 然后开始设置新节点
            if (temp.getNext() == null) {
                //找到了最后
                break;
            }
            //没有进去说明当前这个节点不是最后一个 那就将其后移 将后面的一个节点的信息(整个英雄信息)给临时变量
            //前面的一个temp是新的节点 后面一个temp是当前指向的节点 这个节点拥有下一个节点的位置信息还有其他完整信息
            temp = temp.getNext();

        }
        //如果走到这了  说明已经找到了最后一个节点了 只要这个节点的next让他指向传下来的新节点
        temp.setNext(element);

    }


    //接下来再写一个遍历的方法
    public void showList() {
        System.out.println("开始遍历============");
        //首先判断这个链表是不是为空
        if (headNode.getNext() == null) {
            System.out.println("单链表没有数据，请添加");
            return;
        }
        //将头结点拿出来  作为起始点查询
        // 从头结点开始往下查 查到最后
        HeroNode temp = headNode;
        while (true) {
            if (temp.getNext() == null) {
                //如果某个节点的下一个为空 那就直接跳出循环
                //这个是因为最后一个节点因为他的节点为空 就遍历不到了
                System.out.println(temp);
                System.out.println("遍历结束=============");
                break;
            } else {
                System.out.println(temp);
                temp = temp.getNext();
            }
        }
    }


    //现在增加一个方法 那就是有序添加我们的数据 就是我们需要按照将我们的单链表按照英雄的编号进行顺序排列
    //保证该英雄下面一个英雄的编号是从小到大的
    public void addOrderBy(HeroNode element) {

        //在这里我们要明白我们是从空链表开始插入的 所以我们直接开始从头结点开始比较就行了

        //从头结点开始比较
        HeroNode temp = headNode;
        while (true) {
            //如果当前节点存的后一个节点中的number的 值比我们传进来的大 说明我们的数据应该插在当前这个节点之后
            //原来的那个节点应该插入在我们的新节点之后 明白了这个就好做了
            //如果遍历到已经没有节点或者当前比较完所有还没有找到比自己大的节点那么自己就是就是最后一个
            if (temp.getNext() == null) {
                temp.setNext(element);
                break;
            } else {
                if (temp.getNext().getNumber() > element.getNumber()) {//当前节点存的后一个节点中的number的 值比我们传进来的大 说明我们的数据应该插在当前这个节点之后 然后原来存的往后排
                    //现将我们的后面节点绑定回去  防止丢失
                    element.setNext(temp.getNext());
                    //再讲信息携带好了的节点绑在当前的temp上面
                    temp.setNext(element);
                    break;
                }
                if (temp.getNext().getNumber() == element.getNumber()) {
                    System.out.println("已经存在编号" + element.getNumber() + "了,请勿重复添加");
                    break;
                }
            }
            temp = temp.getNext();
        }


    }


    //现在增加一个修改节点信息的方法
    public void updateList(HeroNode newHeroNode) {
        //先直接拿到头结点进行比较
        HeroNode temp = headNode.getNext();
        while (true) {
            if (temp == null) {
                System.out.println("查无此节点");
                break;
            }
            if (temp.getNumber() == newHeroNode.getNumber()) {
                temp.setName(newHeroNode.getName());
                temp.setNickName(newHeroNode.getNickName());
                break;
            }
            temp = temp.getNext();
        }


    }

    //增加一个删除节点的方法
    public void del(int number) {
        //直接将头结点赋值给一个变量
        HeroNode temp = headNode;
        while (true) {
            if (temp.getNext() == null) {
                System.out.println("节点遍历完成未找到删除节点");
                break;
            }
            if (temp.getNext().getNumber() == number) {
                //如果找到了就直接当前这个节点的next里面的值替换成当前节点的下下个节点 也就是下二个节点
                temp.setNext(temp.getNext().getNext());
                break;
            }
            temp = temp.getNext();
        }
    }


    //增加一个计算单链表中实际节点的个数的方法  传入一个头结点
    public int getListLength() {
        //如果头结点没有下一个元素 直接返回0;
        if (headNode.getNext() == null) {
            return 0;
        }
        //定义临时变量
        int sum = 0;
        HeroNode temp = headNode.getNext();
        while (true) {
            if (temp.getNext() != null) {
                sum++;
            }
            //由于上面的逻辑代代码sum中只是计算了从第一个到倒数第二个有几个有效节点 但是到了最后一个节点还是没有计算
            //所以我们要多算一次
            if (temp.getNext() == null) {
                sum++;
                break;
            }
            temp = temp.getNext();
        }
        return sum;
    }


    //查找到单链表中的倒数第几个元素的信息
    public HeroNode findHeroNodeByLastIndex(int index) {

        if (index <= 0) {
            System.out.println("输入格式有误请保证下标最少大于0");
            return null;
        }

        //在这里我们为了快速的找到我们所需要的集合 我们需要借助一个map集合和上面我们定义好的方法
        //首先我们为了找到倒数第几个 由于我们是单链表 只能从头开始  所以我们先借助临时变量遍历一遍 然后将每次遍历的结果存在map集合里面
        //键就是在单链表的位置
        Map<Integer, HeroNode> map = new HashMap();
        //赋予临时变量直接拿到头结点的下一个变量
        HeroNode temp = headNode.getNext();
        int count = 1;
        while (true) {
            if (temp == null) {
                break;
            } else {
                map.put(count++, new HeroNode(temp.getNumber(),temp.getName(),temp.getNickName()));//记住 这里的count++表示退出后拿到的值多了一
            }
            temp = temp.getNext();

        }
        //经过上面的遍历我们的集合里面已经有了所有的数据 现在只要拿到那个count，用count-1 就知道总共有多少个有效节点的个数
        System.out.println("有效节点的个数为" + (count - 1));
        if (count == 1) {
            System.out.println("没有有效节点，请先插入元素");
            return null;
        }
        count = count - 1;//理由看上面
        //接下来计算要得到的倒数第几个节点在map中对应的事的第几个编号
        //可以用假设map中共有三个有效节点 我要去倒数第二个那就是
        if (index > count) {
            System.out.println("没有这么多元素，请核对查询的元素");
            return null;
        }
        //查到当前的所找到的倒数第几个的位置对应的map中的集合元素的下标
        int location = (count) - (index - 1);
        HeroNode heroNode = map.get(location);
        return heroNode;
    }

    //将单链表反转  自己写的 还是不好 因为总是第二遍插入的时候还是在遍历 使用尾插法  看看老师的思路他是直接使用头插法  每次遍历一个就插入到新头结点的最前面
    public static  SingleLinkedList reverseList(SingleLinkedList list) {
        //我们这里是将整个单链表传下来了
        //先去判断单链表是不是只有头结点
        HeroNode headNode = list.getHeadNode();
        if (headNode.getNext()==null||headNode.getNext().getNext()==null){
            System.out.println("单链表为空，或者单链表的有效节点只有一个不用反转");
            return list;
        }

        //在这里我们为了快速的找到我们所需要的集合 我们需要借助一个map集合和上面我们定义好的方法
        //首先我们为了找到倒数第几个 由于我们是单链表 只能从头开始  所以我们先借助临时变量遍历一遍 然后将每次遍历的结果存在map集合里面
        //键就是在单链表的位置
        Map<Integer, HeroNode> map = new HashMap();
        //准备新的链表
        SingleLinkedList newList = new SingleLinkedList(new HeroNode(0,"新头结点","新小头"));
        //开始遍历旧节点
        HeroNode temp = headNode.getNext();
        int count = 1;
        while (true) {
            if (temp == null) {
                break;
            } else {
                map.put(count++, new HeroNode(temp.getNumber(),temp.getName(),temp.getNickName()));//记住 这里的count++表示退出后拿到的值多了一
            }
            temp = temp.getNext();

        }
       //现在已经有了全部的节点信息 就是反向遍历拼装了
        for (int i = count - 1; i > 0; i--) {
            HeroNode heroNode = map.get(i);
            newList.add(heroNode);
        }
        return newList;

    }

    public static  SingleLinkedList reverseListByTeacher(SingleLinkedList list){

        //先去遍历传下来的节点是不是只有一个头结点或者是只有一个有效节点元素  有就直接退出不用反转
        //我们这里是将整个单链表传下来了
        //先去判断单链表是不是只有头结点
        HeroNode headNode = list.getHeadNode();
        if (headNode.getNext()==null||headNode.getNext().getNext()==null){
            System.out.println("单链表为空，或者单链表的有效节点只有一个不用反转");
            return list;
        }
        //如果不是准备新头节点
        HeroNode newHeadNode = new HeroNode(0, "", "");
        //原来的头结点赋值给新的变量  每次遍历一个就按照头插法放入新头结点
        HeroNode temp = headNode.getNext();
        HeroNode tempNext;
        while (true){
            if (temp==null){
                break;
            }else {
                //注意我们这里要有一个变量帮我们保存当前节点的后面节点
                tempNext = temp.getNext();
                //由于我们每次是放在新链表的头部  所以我们需要先将得到的当前的节点的next域先和原来的新链表已有的数据相关关联  不然先跟头结点关联就会丢失数据
                //不要将下面的这个代码想象成是添加第一个数据 那样很容易混乱 我们要想这这时候新链表有很多数据了 所以我们在添加在这些数据头部的时候我们就要先将后面的关联到当前节点
                //好好体会这个代码  以前学数据结构总是不明白这里 其实只是往多了情况想  要先关联
                temp.setNext(newHeadNode.getNext());
                //上面关联好了 我们就要关联新节点和当前节点的关系了
                newHeadNode.setNext(temp);
                //指针往后
                temp = tempNext;
            }

        }
        //全部完成之后让新的节点和旧节点重新关联
        headNode.setNext(newHeadNode.getNext());
        return list;
    }

    //反转打印单链表 但是不能破换原来结构
    public static  void  reversePrint(SingleLinkedList list){
        //其实解决这个有好多种办法 就是我们遍历单链表 然后再压栈出栈
        //如果单链表为空或者只要一个元素 直接输出
        HeroNode headNode = list.getHeadNode();
        if (headNode.getNext()==null||headNode.getNext().getNext()==null){
            System.out.println(headNode.getNext()==null?"单链表为空，无需打印":headNode.getNext().getNext());
        }
        //开始遍历
        HeroNode temp = headNode.getNext();
        Stack<Object> stack = new Stack<>();
        while (true){
            if (temp!=null){
                stack.add(temp);
            }else{
                break;
            }
            temp=temp.getNext();
        }
        //打印
        while (true){
            if (stack.size()>0){
                System.out.println(stack.pop());
            }else {
                break;
            }

        }
    }

}


//这个是一个英雄节点类 表示这个英雄
class HeroNode {
    //上面的那些需求就是他的属性
    private int number;//编号
    private String name;//姓名
    private String nickName;//昵称
    private HeroNode next;//这个属性代表的是下一个节点 不是自己套自己 是下一个节点信息

    public HeroNode(int number, String name, String nickName) {
        this.number = number;
        this.name = name;
        this.nickName = nickName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public HeroNode getNext() {
        return next;
    }

    public void setNext(HeroNode next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", next=" + next +
                '}';
    }
}