## 1 Function

数据类型、运算符、函数、环境

#### 1.1 doctests

在python的`def`关键词下的一行用`"""`包裹的文字是叫做`docstring`，用来解释这个函数所做的事情。

在`docstring`中如果加入了`>>>`，这就是`doctest`，用来测试函数的正确性，比如:

```python
# ex.py
from operator import floordiv, mod
def divide_exact(n, d):
    """Return the quotient and remainder of dividing N by D
    >>> q, r = divide_exact(2013,10)
    >>> q
    201
    >>> r
    3
    """
    return floordiv(n, d), mod(n, d)
```

在shell中运行

```
python3 -m doctest ex.py
```

如果没有任何输出，说明结果正确，即q确实是201，r是3

#### 1.2 **decorator**

`@`是一个decorator，其作用是后面跟一个函数`fn1`，放在另一个函数`fn2`上方，等效于调用`fn2 = fn1(fn2)`

```python
def trace1(fn):
    def traced(x):
        print('Calling', fn, 'on argument', x)
        return fn(x)#理解装饰器含义再来解释返回类型
    return traced

@trace1
def square(x):
    return x * x

>>> square(2)
Calling <function square at 0x1006ee170> on argument 2
4
```




#### 1.3 函数式编程

> 在Python中，函数是**一等公民**。这意味着函数与字符串和数字等值具有相同的特征
>
> 也就是与values相同的使用方法

```python
>>> def func():
...     print("I am function func()!")
...

>>> print("cat", func, 42)
cat <function func at 0x7f81b4d29bf8> 42

>>> objects = ["cat", func, 42]
>>> objects[1]
<function func at 0x7f81b4d29bf8>
>>> objects[1]()
I am function func()!

>>> d = {"cat": 1, func: 2, 42: 3}
>>> d[func]
2
```

#### 1.4 纯函数与非纯函数

> 非纯函数就会有副作用(side effects)

1. 大多数副作用会更改变量的值。比如random.ranint函数

2. 纯函数受到限制，因为它们不会产生副作用或随时间改变行为
   1. 纯函数可以更可靠地组合到复合调用表达式中
   2. 纯函数往往更容易测试
   3. 纯函数对于编写*并发*程序至关重要

#### 1.5 环境

> <font color='red'>将名称绑定到值并在以后按名称检索这些值的可能性意味着解释器必须维护某种内存来跟踪名称、值和绑定。此内存称为*环境*。</font>

名称也可以绑定到函数。

## 2 Control && HOF

#### 2.1 错误类型总结

1. `SyntaxError`--代码语法错误
2. `IndentationError`--缩进不当
3. `TypeError`
   1. 基元运算符的操作数类型无效。您可能正在尝试添加/减法/乘法/除以不兼容的类型。
   2. 在函数调用中使用非函数对象。
   3. 向函数传递不正确数量的参数。
4. `NameError`--变量未分配给任何内容或它不存在。这包括函数名称。
5. `IndexError`--尝试使用超过序列大小的数字对序列（例如元组、列表、字符串）编制索引。

#### 2.2 高阶函数

> 输入或者输出其中一个为函数的函数就是高次函数

1. 将函数作为参数进行使用
2. 使用单参数函数代表多参数函数
3. 可以作为函数的结果返回
4. 可以包含在数据结构中

```python
def curried_pow(x):
    ```pow(x,y) -> curried_pow(x)```
	def h(y):
        return pow(x,y)
	return h

son = curried_pow(3)
son(1)
```

==son是由curried_pow创建，也就是说curried_pow是son的parent frame==

==因此son可以访问到这个parent frame环境中所有本地变量，包括x的值==

##### 注意：

````python
# Redefinition after Assignment
def f():
	return 1

g = f

def f():
    return 0
print(g())

""">>>0
类似 
	x = 0;y = x; x = 1;"""

````

````python
"""
函数值与最开始创建的环境值相绑定
自己不会创造新值，只会复制旧值
"""
def f():
	return 0

def g():
	return f()

def h(k):
	def f():
		return 1
	p = k
	return p()

print(h(g)) #0
````

#### 2.3 函数组合在环境中的执行过程

![image](https://raw.githubusercontent.com/formoree/PicGO-Picture/master/202202280840069.png)

## 3 Recursion

#### 3.1 递归

+  If P(b) (the base case), 
   +  找到最基本的例子，也可以说是递归的终点
+  P(k − 1) implies P(k) for all k > b
   +  寻找一个通式能够使其应用于除去k的所有情况
   +  这也是递归信仰条约的例子--将情况拆分为更小的情况
+  then P(k) for all k ≥ b.

#### 3.2 lambda

==观察下面代码，纯lambda就是一个函数==

> ```python
> lambda x:x 
> #等同于
> def lam(x):
> 	return x
> ```

+ 对于嵌套lambda 注意传参顺序

```python
higher_order_lambda = lambda f: lambda x: f(x)
g = lambda x: x * x
higher_order_lambda(2)(g) #Error 顺序错误
higher_order_lambda(g)(2) #4
```

+ 注意嵌套函数的调用顺序

```python
def cake():
	print('1')
	def pie():
		print('2')
		return '3'
	return pie
chocolate = cake()
#1 因为return pie
print(chocolate)
# function-->也就是pie
chocolate()
#2 因为pie() 
```
+ lambda 表达式可用作调用表达式的运算符或操作数。在下面的示例中，是运算符，并且是操作数。

```python
>>> (lambda y: y + 5)(4)
9
>>> (lambda f, x: f(x))(lambda y: y + 1, 10)
11
```
+ lambda应用：逆函数的实现

```python
def search(f):
    x = 0
    while not f(x):
        x + =1
    return x
def inverse(f):
    """return g(y) such that g(f(x)) = x"""
    return lambda y: search(lambda x: f(x) == y)
```

[lambda基础测试]([实验 2：高阶函数、Lambda 表达式|CS 61A 2021年春季刊 (berkeley.edu)](https://inst.eecs.berkeley.edu/~cs61a/sp21/lab/lab02/))

#### 3.3 Tree Recursion

> 一个函数体内有吵过一次对该函数自身的调用

```python
# 斐波那契数列
def fib(n):
    """Compute the nth Fibonacci number.

    >>> fib(8)
    21
    """
    if n == 0:
        return 0
    elif n == 1:
        return 1
    else:
        return fib(n-2) + fib(n-1)
```

==相关应用可以看review中HW 03例题==

## 4 Container && Sequences

#### 4.1 List

+ for

```python
for <name> in <expression>:
    <suite>
```

+ list comprehension

> 列表推导式：`[output_expression() for(iterable value) if (conditional filter)]`

```python
>>> odds = [1, 3, 5, 7, 9]
>>> [x+1 for x in odds if 25 % x == 0]
[2, 6]
```

#### 4.2 Dictionary

`{'key1': val1, 'key2': val2, 'key3': val3}`

```
.key()  .items() .values()
```

#### 4.3 Iterator

`iter(iterable)`：返回一个迭代器

`next(iterator)`：返回迭代器中指向的下一个元素

#### 4.4 Built-in Functions for iteration

返回一个迭代器的函数：

- `map(func, iterable)`：对iterable中的x进行f(x)迭代
- `filter(func, iterable)`：在func(x)==True的情况下对iterable中的x进行迭代
- `zip（first_iter, second_iter)`：返回一个同时迭代两个可迭代对象的迭代器，next的返回对象是一个包含这两个可迭代对象元素的元组

## 5 Iterators && Generators

#### 5.1 Tuples

> 元组是一组数列，**<font color='red'>不能</font>**改变，因此可以作为字典的键（list不能作为字典的键）

但是如果元组中包含了一个list，可以通过改变这个list来改变元组:

```python
>>> s = ([1, 2], 3)
>>> s[0][0] = 4
>>> s
([4, 2], 3 )
```

#### 5.2 Identity Operator

`<exp0> is <exp1>` 如果`<exp1>`和`<exp0>`指向同一个对象，则返回True

```python
list1 = [1,2,3]
list2 = [1,2,3]
identical = list1 is list2 #False
are_equal = list1 == list2 #True
```

#### 5.3 nonlocal 和 global

+ 区别
  + 功能不同
    + global关键字修饰变量后标识该变量是==全局变量==，对该变量进行修改就是==修改全局变量==
    + nonlocal关键字修饰变量后标识该变量是上一级函数中的==局部变量==，如果上一级函数中不存在该局部变量，nonlocal位置会发生错误（最上层的函数使用nonlocal修饰变量必定会报错）
  + 使用范围不同
    + global关键字可以用在==任何地方，包括最上层函数中和嵌套函数中==，即使之前未定义该变量
    + nonlocal关键字==只能用于嵌套函数中==，并且外层函数中定义了相应的局部变量，否则会发生错误
+ global
  + 如果局部要对全局变量修改，则在局部声明该全局变量
  + 如果局部不声明全局变量，并且不修改全局变量，则可以正常使用
+ nonlocal
  + nonlocal声明的变量不是局部变量,也不是全局变量,而是外部嵌套函数内的变量

[global和nonlocal用法的详细说明](https://zhuanlan.zhihu.com/p/341378844)

#### 5.4 Generator

> 使用原因：**按需生成并“返回”结果**，而不是一次性产生所有的返回值
>
> 实现==异步==

```python
def hailstone(n):
    """Yields the elements of the hailstone sequence starting at n.
    
    >>> for num in hailstone(10):
    ...     print(num)
    ...
    10
    5
    16
    8
    4
    2
    1
    """
    yield n
    while n != 1:
        if n % 2 == 0:
            n = n // 2
        else:
            n = n * 3 + 1
        yield n
# 在 for 循环执行时，每次循环都会执行 hailstone 函数内部的代码，执行到 yield b 时，hailstone 函数就返回一个迭代值，下次迭代时，代码从 yield n 的下一条语句继续执行，而函数的本地变量看起来和上次中断执行前是完全一样的，于是函数继续执行，直到再次遇到 yield。
```

[python之generator详解_--雪飘时吻你---CSDN博客_generator python](https://blog.csdn.net/zhong_jay/article/details/91799459)

## 6 Class

#### 6.1 Class

类是其所有实例(object)的模板，每个类中有很多属性(instance variables)和方法(methods)

```python
class <name>:
    <suite>
```

##### object construction

> _init__方法调用新对象作为其第一个参数（命名为self），以及调用表达式中提供的任何其他参数

```python
class Account:
    def __init__(self, account_holder):
        self.balance = 0
        self.holder = account_holder
>>> a = Account('Jim')
>>> a.holder
'Jim'
```

##### Method invocation(调用)

```python
class Product:
	def increase_inventory(self, amount):
        self._inventory += amount
        
pina_bar.increase_inventory(2)
#等价于
Product.increase_inventory(pina_bar, 2)

```

#### 6.2 Inheritance

```python
class <name>(<base class>):
    <suite>
```

==新的子类将从父类继承所有属性，也可以覆盖(overriding)继承来的属性( 类变量、构造器和方法定义)，在子类中重新定义原先的方法覆盖继承来的方法，但是还可以在父类中访问原来的属性或方法==

+ 使用父类方法super()

```python
super().attribute()->BaseClassName.attribute(self)
```

+ 当查找一个类中的名称时，如果这个名称是在当前类中的，就返回这个类中的名称的值，否则查找父类中是否有这个名称

#### 6.3 Special Object Methods

+ `__new__`和`__init__`

  `__new__`是用来构造实例的，而`__init__`只是用来对返回的实例进行一些属性的初始化

  我们继承基类object(例如，class Animal(object))时同时就从基类中继承了`__new__`方法，所以就不需要重新在子类中实现。

+ `__str__`和`__repr__`:**把类的实例变为字符串**
  + `__str__`:用于用户调用(==用些Python脚本(.py)时使用，用print语句输出字符串信息==)
  + `__repr__`:用于开发人员调用(==在交互式环境下测试使用，例如cmd下的Python、ipython==)

+ `__ setattr__`、`__ getattr__`、`__ getattribute__`与`__delattr__`
  + **setattr**：设置属性
  + **getattr**：访问不存的属性时调用，可能会有同学有疑问，访问不存的属性要它干吗？可以用来做异常处理！
  + **getattribute**：访问存在的属性，如果访问属性不存在的时候随后会调用__getattr__
  + **delattr**：删除属性

参考：[【python】类的特殊方法](https://blog.csdn.net/momoschweigen/article/details/106784347#:~:text=Python是一种面向对象的语言，而特殊方法又是Python类中一个重点，因此学习Python类的特殊方法能够有助于设计出更加简洁、规范的代码架构。 Python类的特殊方法又称为 魔术方法 ，它是以,双下划线包裹 一个词的形式出现，例如 init 。) 

​			[python面向对象中的一些特殊方法--列表](https://www.cnblogs.com/angelyan/p/10366961.html)

#### 6.4 private



## 7 Others

#### 7.1 Declarative Programming Definition

- *declatative languages*：一个程序是对希望得到的结果的描述，怎样得到结果是由解释器负责的。申诉式语言包括SQL、Prolog等
- *imperative languages*：一个程序是对计算过程的描述，解释器负责执行这些计算过程。命令式语言包括Python、C等

#### 7.2  Parallel Computing

[4.8 Parallel Computing](http://composingprograms.com/pages/48-parallel-computing.html)

