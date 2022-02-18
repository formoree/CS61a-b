## Week1

数据类型、运算符、函数、环境

#### 函数式编程

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

#### 纯函数与非纯函数

> 非纯函数就会有副作用(side effects)

1. 大多数副作用会更改变量的值。比如random.ranint函数

2. 纯函数受到限制，因为它们不会产生副作用或随时间改变行为
   1. 纯函数可以更可靠地组合到复合调用表达式中
   2. 纯函数往往更容易测试
   3. 纯函数对于编写*并发*程序至关重要

#### 环境

> <font color='red'>将名称绑定到值并在以后按名称检索这些值的可能性意味着解释器必须维护某种内存来跟踪名称、值和绑定。此内存称为*环境*。</font>

名称也可以绑定到函数。

## Week2

#### 错误类型总结

1. `SyntaxError`--代码语法错误
2. `IndentationError`--缩进不当
3. `TypeError`
   1. 基元运算符的操作数类型无效。您可能正在尝试添加/减法/乘法/除以不兼容的类型。
   2. 在函数调用中使用非函数对象。
   3. 向函数传递不正确数量的参数。
4. `NameError`--变量未分配给任何内容或它不存在。这包括函数名称。
5. `IndexError`--尝试使用超过序列大小的数字对序列（例如元组、列表、字符串）编制索引。

#### 高阶函数

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
```

##### 注意：

````python
# Redefinition after Assignment
def f():
	return 1

g = f

def f():
    return 0
print(g())
```
>>>0
类似 
	x = 0;y = x; x = 1;
```
````

````python
```函数值与最开始创建的环境值相绑定
	自己不会创造新值，只会复制旧值
```
def f():
	return 0

def g():
	return f()

def h(k):
	def f():
		return 1
	p = k
	return p()

print(h(g))
````

#### 递归

+  If P(b) (the base case), and
+  P(k − 1) implies P(k) for all k > b,
+  then P(k) for all k ≥ b.