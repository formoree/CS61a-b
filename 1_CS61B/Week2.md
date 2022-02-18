# 3 测试

#### 课程目标

> 1. 我们需要完成一个目标--写一个快速排序
> 2. 将快速排序算法分为三个步骤，用三个函数进行操作
> 3. 进行测试
>    1. 使用print语句
>    2. 使用JUnit单元
> 4. 简化JUnit测试开发
>    1. import org.junit.Assert.*;
>    2. import org.junit.Test;--@Test;
> 5. 测试的哲学--下图是测试驱动项目TTD--新的编程方法

> ![Week2-1](E:\3_code\3_py_algorithm\5_课程\1_CS61B\pictures\Week2-1.png)

#### 注意

Junit的安装问题：如果需要完成视频中的运行方式，需要下载4.1.1版本或者4.1.2版本+hamcrest

# 4 引用和递归

#### 数据类型

+ 基本类型： byte, short, int, long, float, double, boolean, and char
  + 以0，1进行存储--值传递
  + 方法和构造函数都是按值传递
+ 引用类型：class,interface,[]数组
  + 地址进行代表(箭头)--地址传递

#### 另外

python中的 **is**相当于Java中的**==**---都是检查内存地址

# 5 基于节点的列表

#### **From IntList to SLList**

> IntList被称为是“Naked”数据结构，很难使用(对于使用者来说需要掌握递归的思想)。

因此我们将IntList改进，使用SLList。**“SLList 类充当用户和原始数据结构之间的中间人”**

```java
public class SLList {
   public IntNode first;
 
   public SLList(int x) {
      first = new IntNode(x, null);
   }
 
   public void addFirst(int x) {
       first = new IntNode(x, first);
   }
 
   public int getFirst() {
  	  return first.item;
   }
}

public class IntNode {
   public int item;
   public IntNode next;
 
   public IntNode(int i, IntNode n) {
   	  item = i;
   	  next = n;
   }
}
//使用方法
SLList L = new SLList(15);
L.addFirst(10);
L.addFirst(5); 	
int x = L.getFirst();

//SLList VS IntList
IntList L = new IntList(15, null);
L = new IntList(10, L);
L = new IntList(5, L);
int x = L.first;
```

#### **Public vs. Private**

+ static：静态修饰符，被static修饰的变量和方法类似于全局变量和全局方法，可以在不创建对象时调用，当然也可以在创建对象之后调用。常见的可以用于工具类的工具方法中等，譬如：Math类中的绝大多数方法都是静态方法，他们扮演了工具方法的作用。

+ public：声明当前被修饰的对象、方法、变量为公有的。这里的公有指的是可以被公有访问。
+ private：声明当前被修饰的变量、方法为私有的。这里的私有指的是仅仅可以被私有访问。你在使用这个类时那些这个类的确有但是你访问是非法的方法或者变量是被声明为private的，私有不可见且不可访问的
  + private static是合法的，且有着其独到的用处：为静态方法提供私有静态属性。
  + public static常用的是为该类提供对外暴露即可以被类名直接调用的静态常量。

#### **Sentinel Nodes--哨兵节点、头节点**

> Sentinel Nodes的由来：
>
> 我们认为单独考虑特殊情况的代码是不简单了，为了简化代码，我们假设存在一个节点，它一直都存在