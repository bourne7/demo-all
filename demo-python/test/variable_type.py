def do_loop():
    print('Being Invoked.')


# * 表示参数为 元组

def fun1(*args):  # 相当于 def fun1(1,2,3) ==> args 就相当于（1,2,3）
    for a in args:
        print(a)


# ** 表示参数为 字典

def fun2(**args):  # 相当于 def fun2({a:1,b:2,c:3}) ==>args 就相当于{a:1,b:2,c:3}
    for k, v in args:
        print(k, ":", v)


# Python3 的六个标准数据类型
def show_type():
    # 不可变对象
    var_int = 123
    # 注意 isinstance(1, int) 这种可以判断父类，type不行
    print('Number 数字', type(var_int))

    var_str = 'Hello'
    print('String 字符串', type(var_str))

    var_tuple = ('Hi', 786, 2.23, 'john', 70.2)
    print('Tuple 元组', type(var_tuple))

    # 可变对象
    var_set = {1, 2, 3, 4, 5}
    print('Sets 集合', type(var_set))

    var_list = [1, 2, 3, 4, 5, 6]
    print('List 列表', type(var_list))

    var_dict = {'a': 'apple', 'b': 'banana', 'z': 1000}
    print('Dictionary 字典', type(var_dict))


def test_mutable():
    a1 = [1, 2, 3]
    a2 = a1
    print(id(a1), id(a2))
    # 这3种都不会导致对象id变化，因为都是调用内部函数。
    a2.append(4)
    a2 += [4]
    a2.extend([4])
    # 会导致对象id变化，因为创建了新的对象。
    # a2 = a2 + [4]
    print(id(a1), id(a2))
    print(a1)
    print(a2)


if __name__ == '__main__':
    print('Start test as main.')
    show_type()
    test_mutable()
