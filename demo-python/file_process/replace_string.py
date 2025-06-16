import os
import sys
import time


# 用于替换一个文本中的字符串
def delete_text(file, str_old, str_new):
    f = open(file, 'r+', encoding='UTF-8')
    file_content = f.read()
    # 需要加上f.seek(0)，把文件定位到position 0，没有这句的话，文件是定位到数据最后，truncate也是从这里删除，所以感觉就是没起作用。
    # https://segmentfault.com/q/1010000011564820/a-1020000011570894
    f.seek(0)
    f.truncate()
    content = file_content.replace(str_old, str_new)
    print(content)
    f.write(content)
    f.write('\n============= end ============\n')
    f.close()


if __name__ == '__main__':
    working_path = os.getcwd()
    print(working_path)
    delete_text(working_path + os.sep + 'replace_string.txt', '  ', '')

    c = input("\ndone\n")
    time.sleep(1)
    sys.exit()
