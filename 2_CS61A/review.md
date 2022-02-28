## HW01 A Plus Abs B

> 第一题就出问题简直好气。实际上python很灵活，我学了C以为赋值只能是值赋值，要么就函数指针，结果这个居然可以f = add  和 f = sub！惊呆了

==高阶函数的应用==

```python
from operator import add, sub


def a_plus_abs_b(a, b):
    """Return a+abs(b), but without calling abs.

    >>> a_plus_abs_b(2, 3)
    5
    >>> a_plus_abs_b(2, -3)
    5
    >>> # a check that you didn't change the return statement!
    >>> import inspect, re
    >>> re.findall(r'^\s*(return .*)', inspect.getsource(a_plus_abs_b), re.M)
    ['return f(a, b)']
    """
    if b < 0:
        f = sub
    else:
        f = add
    return f(a, b)

```

## HW02 Make Repeater

==对于环境的理解==

代码对比：

```python
def curried_pow(x):
	def h(y):
        return pow(x,y)
	return h
```

```python
def square(x):
    return x * x

def make_repeater(func, n):
    """Return the function that computes the nth application of func.

    >>> add_three = make_repeater(increment, 3)
    >>> add_three(5)
    8
    >>> make_repeater(triple, 5)(1) # 3 * 3 * 3 * 3 * 3 * 1
    243
    >>> make_repeater(square, 2)(5) # square(square(5))
    625
    >>> make_repeater(square, 4)(5) # square(square(square(square(5))))
    152587890625
    >>> make_repeater(square, 0)(5) # Yes, it makes sense to apply the function zero times!
    5
    """
    def repeat(x):
        nonlocal n #能不能除去这个代码
		while n > 0:
    		n -= 1
    		x = func(x)
   		return x
    return repeat

print(make_repeater(square, 4)(5))
```

仿照上面一个代码完成的作业却发现有报错

Q: 我认为n为全局函数，在第一次调用make_repeater时就已经放入repeat中，为什么还存在报错的情况？

A: 在嵌套函数repeat中出现`while n > 0:`。<font color='red'>在编译阶段，会把内部的n解释为本地变量</font>，所以我们需要nonlocal将count和total变成自由变量

[python作用域、装饰器、闭包](https://zhuanlan.zhihu.com/p/65747821)

## HW02 Church numerals

[CS61A Church numerals](https://zhuanlan.zhihu.com/p/267917164)

对lambda的重新认识：

​	如上题中对于以下代码(repeat函数的作用相当于lambda):

```python
def zero(f):
	return lambda x:x 
# 可以转换为
def zero(f):
    def g(x):
        return x
    return g

#另外就是对这个函数的理解
#在这里 lambda是作为了一个运算方式
def church_to_int(n):
    return n(lambda x: x + 1)(0)
```

那对于```successor(one)```->```lambda f: lambda x:f(zero(f)(x))```

==考察知识点==:

1. n(f)(x)就相当于n
2. 函数嵌套的关系，或者说没想明白丘奇数的本质
3. 使用纯粹的函数进行定义

## Lab02   Q2： WWPD： 高阶函数
==函数调用顺序问题==

```python
def cake():
    print('beets')
    def pie():
        print('sweets')
        return 'cake'
    return pie

chocolate = cake()
"""
执行了cake()函数后返回pie 因此只有一个输出
# beets
"""

chocolate
"""
Function
也就是返回了pie
"""

chocolate()
"""
pie()
#sweets
"""
more_chocolate, more_cake = chocolate(), cake
more_chocolate
"""
‘cake’
"""
def snake(x, y):
    if cake == more_cake:
        return lambda y: x + y
    else:
        return x + y
snake(10, 20)
"""
Function
"""
snake(10, 20)(30)
"""
#40
"""
cake = 'cake'
snake(10, 20)
"""

"""
```

## Disc 03 Q2: Merge Numbers

==递归函数的高级使用方法==

```python
#按照降序回合数字
def merge(n1, n2):
    """ Merges two numbers by digit in decreasing order
    >>> merge(31, 42)
    4321
    >>> merge(21, 0)
    21
    >>> merge (21, 31) 
    3211
    """
    "*** YOUR CODE HERE ***"
    # 最小数字一定是两个数字个位中的一个
    if n1 == 0 and n2: #基本情况
        return n2
    elif n2 == 0 and n1: #基本情况 
        return n1
    elif n1 % 10 <= n2 % 10: #每次确定最小数字并乘10
        return merge(n1 // 10, n2) * 10 + n1 % 10
    else:
        return merge(n1, n2 // 10) * 10 + n2 % 10

```

## Lab 03Q5: Repeated, repeated

![img](https://raw.githubusercontent.com/formoree/PicGO-Picture/master/202202241320792.png)

下面是我的答案：

```python
def repeated(f, n):
    """Returns a function that takes in an integer and computes 
    the nth application of f on that integer.
    Implement using recursion!

    >>> add_three = repeated(lambda x: x + 1, 3)
    >>> add_three(5)
    8
    >>> square = lambda x: x ** 2
    >>> repeated(square, 2)(5) # square(square(5))
    625
    >>> repeated(square, 4)(5) # square(square(square(square(5))))
    152587890625
    >>> repeated(square, 0)(5)
    5
    >>> from construct_check import check
    >>> # ban iteration
    >>> check(HW_SOURCE_FILE, 'repeated',
    ...       ['For', 'While'])
    True
    """
    def repeating(x):
        if n == 0:
            return x
        return repeated(f,n-1)(f(x))
    return repeating
```

标准答案：

```python
def repeated(f, n):
    """Returns a function that takes in an integer and computes 
    the nth application of f on that integer.
    Implement using recursion!

    >>> add_three = repeated(lambda x: x + 1, 3)
    >>> add_three(5)
    8
    >>> square = lambda x: x ** 2
    >>> repeated(square, 2)(5) # square(square(5))
    625
    >>> repeated(square, 4)(5) # square(square(square(square(5))))
    152587890625
    >>> repeated(square, 0)(5)
    5
    >>> from construct_check import check
    >>> # ban iteration
    >>> check(HW_SOURCE_FILE, 'repeated',
    ...       ['For', 'While'])
    True
    """
    "*** YOUR CODE HERE ***"
    if n == 0:
        return lambda x: x 
    elif n == 1:
        return f
    else:
        return compose1(f, repeated(f, n - 1))

```

角度差别：

1. 标准答案把repeated函数的返回值都定为Function，代码简洁思路清晰
2. 我用的高阶函数(HOF)，稍显复杂，但是没有用compose1函数，也不错

## HW 03 Q2: Ping-pong

![image-20220224135408354](https://raw.githubusercontent.com/formoree/PicGO-Picture/master/202202241354382.png)

问题：

   难以确定第n个元素的增减方向

````python
#题目
def pingpong(n):
    """Return the nth element of the ping-pong sequence.

    >>> pingpong(8)
    8
    >>> pingpong(10)
    6
    >>> pingpong(15)
    1
    >>> pingpong(21)
    -1
    >>> pingpong(22)
    -2
    >>> pingpong(30)
    -2
    >>> pingpong(68)
    0
    >>> pingpong(69)
    -1
    >>> pingpong(80)
    0
    >>> pingpong(81)
    1
    >>> pingpong(82)
    0
    >>> pingpong(100)
    -6
    >>> from construct_check import check
    >>> # ban assignment statements
    >>> check(HW_SOURCE_FILE, 'pingpong', ['Assign', 'AugAssign'])
    True
    """
    "*** YOUR CODE HERE ***"
#先考虑赋值语句+while循环的常规解法：
def pingpong(n):
    i = 1
    ppvalue = 0
    flag = 1
    while i <= n:
        ppvalue += flag
        if num_eights(i) or i % 8 == 0:
            flag = - flag
        i += 1
    return ppvalue
#再用辅助函数替代while循环中的flag，递归调用
def pingpong(n):
    def flag(x):
        if x == 1:
            return 1
        if num_eights(x) or x % 8 == 0:
            return -flag(x-1)
        return flag(x-1)
    if n == 1:
        return 1
    return pingpong(n-1) + flag(n-1)
````

## HW03 Q4: Count coins

![image-20220225130225150](https://raw.githubusercontent.com/formoree/PicGO-Picture/master/202202251302143.png)

````python
def get_next_coin(coin):
    """Return the next coin. 
    >>> get_next_coin(1)
    5
    >>> get_next_coin(5)
    10
    >>> get_next_coin(10)
    25
    >>> get_next_coin(2) # Other values return None
    """
    if coin == 1:
        return 5
    elif coin == 5:
        return 10
    elif coin == 10:
        return 25

def count_coins(change):
    """Return the number of ways to make change using coins of value of 1, 5, 10, 25.
    >>> count_coins(15)
    6
    >>> count_coins(10)
    4
    >>> count_coins(20)
    9
    >>> count_coins(100) # How many ways to make change for a dollar?
    242
    >>> from construct_check import check
    >>> # ban iteration
    >>> check(HW_SOURCE_FILE, 'count_coins', ['While', 'For'])                                          
    True
    """
    "*** YOUR CODE HERE ***
````

我的代码更改了函数get_next_coins()[算是投巧]：

```python
def get_next_coin(coin): #更改题目
    """Return the next coin. 
    >>> get_next_coin(1)
    5
    >>> get_next_coin(5)
    10
    >>> get_next_coin(10)
    25
    >>> get_next_coin(2) # Other values return None
    """
    if coin == 25:
        return 10
    elif coin == 10:
        return 5
    elif coin == 5:
        return 1

def count_coins(change):
    """Return the number of ways to make change using coins of value of 1, 5, 10, 25.
    >>> count_coins(15)
    6
    >>> count_coins(10)
    4
    >>> count_coins(20)
    9
    >>> count_coins(100) # How many ways to make change for a dollar?
    242
    >>> from construct_check import check
    >>> # ban iteration
    >>> check(HW_SOURCE_FILE, 'count_coins', ['While', 'For'])
    True
    """
    biggest_coins = 25 if change >= 25 else 10 if change >= 10 else 5 if change >= 5 else 1
    def helper(change,biggest_coins):
        if change == 0:
            return 1
        elif biggest_coins == 1:
            return 1
        elif change < 0:
            return 0
        return helper(change-biggest_coins,biggest_coins)+helper(change,get_next_coin(biggest_coins))
    return helper(change,biggest_coins)
```

大佬答案：

```python
# 换个思维 z
def count_change(amount):
    """Return the number of ways to make change for amount.
    >>> count_change(7)
    6
    >>> count_change(10)
    14
    >>> count_change(20)
    60
    >>> count_change(100)
    9828
    """
    def helper(min_coin, amount):
        if amount < 0:
            return 0
        elif amount == 0:
            return 1
        elif min_coin > amount:
            return 0
        else: 
            with_min_coin = helper(min_coin, amount - min_coin)
            without_min_coin = helper(2 * min_coin, amount)
            return with_min_coin + without_min_coin
    
    return helper(1, amount)
```



## HW03 Q5: Anonymous factoria

![image-20220225125212432](https://raw.githubusercontent.com/formoree/PicGO-Picture/master/202202251252505.png)

==不能调用make_anonymous_factorial函数，不能赋值、定义函数、递归==

```python
from operator import sub, mul

def make_anonymous_factorial():
    """Return the value of an expression that computes factorial.

    >>> make_anonymous_factorial()(5)
    120
    >>> from construct_check import check
    >>> # ban any assignments or recursion
    >>> check(HW_SOURCE_FILE, 'make_anonymous_factorial', ['Assign', 'AugAssign', 'FunctionDef', 'Recursion'])
    True
    """
    #不可调用make_anonymous_factorial函数
    #return lambda n: 1 if n == 1 else mul(n, make_anonymous_factorial()(sub(n, 1)))
    return (lambda f: lambda k: f(f, k))(lambda f, k: k if k == 1 else mul(k, f(f, sub(k, 1))))
```

这道题要点，把函数当做参数传入，构造call, 在call里用lambda构造这个函数的behavior，这样就解决了迭代时需要函数名的问题。主要分为两部分。

1. 第一个括号： (lambda f: lambda k: f(f, k))
   构造一个需要f函数作为参数的函数，返回值是需要k为参数的函数，返回f函数的值，并且规定了f是一个需要2个参数的函数，f自身和k
2. 第二个括号：(lambda f , k: k if k == 1 else k * f(f, k-1) )
   这部分作为第一个括号的第一个call，构造f函数的行为，需要2个参数：f, k. 返回值为k或else后的值，if给出最简式，else给出，k和k-1的关系
3. 注意环境变量的变化，写成相同的名字便于理解，实际并不相同
