# Default values are computed once, then re-used.
# https://blog.csdn.net/x_r_su/article/details/54730654


class MyList:
    def __init__(self, init_list=[]):
        self.list = init_list
        print(id(init_list))

    def add(self, ele):
        self.list.append(ele)


def appender(ele):
    # obj = MyList()
    obj = MyList(init_list=[])
    obj.add(ele)
    print(obj.list)


if __name__ == '__main__':
    print('start')
    for i in range(5):
        appender(i)
