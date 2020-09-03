package linkedlist.doubly;

public class DoubleLinkedListDemo {
    public static void main(String[] args) {

        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.add(new HeroNode(1, "aa", "小a"));
        doubleLinkedList.add(new HeroNode(3, "bb", "小b"));
        doubleLinkedList.add(new HeroNode(2, "cc", "小c"));
        doubleLinkedList.showList();
        doubleLinkedList.del(1);
        doubleLinkedList.showList();


    }
}

class DoubleLinkedList {

    //初始化头结点

    //节点类已经写好了  接下来就是创将头结点 将人物英雄添加到节点里面去了
    //创建头结点  将它作为一个成员变量  而且和前面单链表有所区别 我们已经将其在内部赋值了
    private final HeroNode headNode = new HeroNode(0, "双向链表头结点", "小双");

    //提供一个获取get的方法获取属性
    public HeroNode getHeadNode() {
        return headNode;
    }


    //现在模仿单链表改改我们的双链表
    //我们在写一些方法方便我们调用
    //第一种方法在添加英雄时，直接添加到链表的尾部  待会儿我们再改进我们的双链表
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
        //除此之外，我们还需要新添加的链表指向当前最后一个节点
        element.setPre(temp);

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


    //现在增加一个修改节点信息的方法
    public void updateList(HeroNode newHeroNode) {
        //先直接拿到头结点的后面一个结点进行比较
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
        if (headNode.getNext() == null) {
            System.out.println("链表为空不能删除");
            return;
        }

        HeroNode temp = headNode.getNext();
        while (true) {
            if (temp.getNumber() == number) {
                //如果找到了表明当前节点将就是我们要删除的节点 那就获得当前节点的前一个节点指向当前节点的后一个节点
                temp.getPre().setNext(temp.getNext());

                //但是这下面这句话有风险，如果刚好我们删除的是最后一个节点那么下面就会报控制针异常了 所以要判断一下
                if (temp.getNext() != null) {
                    temp.getNext().setPre(temp.getPre());
                }

            }
            if (temp.getNext() == null) {
                System.out.println("节点遍历完成未找到删除节点");
                break;
            }

            temp = temp.getNext();
        }
    }


}

//双向链表
class HeroNode {
    //上面的那些需求就是他的属性
    private int number;//编号
    private String name;//姓名
    private String nickName;//昵称
    private HeroNode pre;//这个属性代表的是前一个节点 不是自己套自己 是前一个节点信息
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

    public HeroNode getPre() {
        return pre;
    }

    public void setPre(HeroNode pre) {
        this.pre = pre;
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
                '}';
    }
}