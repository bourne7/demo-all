import calendar
import time


# 用于替换一个文本中的字符串
def test_time():
    current_time = time.time()
    print(type(current_time))
    print("当前时间戳为:", current_time)
    localtime = time.localtime(current_time)
    print("本地时间为 :", localtime)
    # 时间对象 -> string
    print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))
    print()

    string = "2018-01-01 01:01:01"
    # string -> 时间对象
    time_array = time.strptime(string, "%Y-%m-%d %H:%M:%S")
    print(type(time_array))
    # 转换为时间戳:
    time_stamp = time.mktime(time_array)
    print(type(time_stamp))
    print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(time_stamp)))

    cal = calendar.month(2016, 1)
    print("\n\r以下输出2016年1月份的日历:")
    print(cal)


if __name__ == '__main__':
    test_time()
