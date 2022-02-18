### HW01 A Plus Abs B

> 第一题就出问题简直好气。实际上python很灵活，我学了C以为赋值只能是值赋值，要么就函数指针，结果这个居然可以f = add  和 f = sub！惊呆了

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

### HW02 Make Repeater

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
		while n > 0:
    		n -= 1
    		x = func(x)
   		return x
    return repeat

print(make_repeater(square, 4)(5))
```

> 仿照上面一个代码完成的作业却发现有报错
>
> 1. 我认为n为全局函数，在第一次调用make_repeater时就已经放入repeat中，为什么还存在报错的情况？

## HW02 Church numerals

[CS61A Church numerals](https://zhuanlan.zhihu.com/p/267917164)

