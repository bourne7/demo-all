import calendar
import time


# 格式化时间, date format string
def date_format_string():
    current_time = time.time()
    # 时间戳(float)
    print("时间戳为: ", current_time, " 类型: ", type(current_time))

    # 时间类:
    localtime = time.localtime(current_time)
    print("本地时间为: ", localtime, " 类型: ", type(localtime))

    # 时间string
    time_string = time.strftime("%Y-%m-%d %H:%M:%S", localtime)
    print("格式化的时间为: ", time_string, " 类型: ", type(time_string))
    print("===\n")


# 反格式化时间, string phase date
def string_phase_date():
    string = "2099-01-01 01:01:01"
    # 时间类:
    time_type = time.strptime(string, "%Y-%m-%d %H:%M:%S")
    print("time_array: ", time_type, " 类型: ", type(time_type))
    # 时间戳:
    time_stamp = time.mktime(time_type)
    print("时间戳为: ", time_stamp, " 类型: ", type(time_stamp))
    print("===\n")


# 获取日历，直接打印的话，可以得到一个格式化了的日历。
def get_calendar():
    cal = calendar.month(2099, 1)
    print("以下输出2099年1月份的日历:")
    print(cal)
    print("===\n")


if __name__ == '__main__':
    date_format_string()
    string_phase_date()
    get_calendar()
