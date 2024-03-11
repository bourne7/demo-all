import os
import re


def walk_path(path):
    # 这里的遍历需要注意的是 path 是包含了当前目录在内的所有目录。
    for root_full_path, dirs_in_root, files_in_root in os.walk(path):

        # 通过正则过滤掉不需要的文件夹，比如 ".git" 这些文件夹。
        # '\\\\\\.' 这个表达式用于匹配 "\." 这个有可能在路径里面的字符。
        match_object = re.search('\\\\\\.', root_full_path)
        if match_object:
            continue

        # 找到所有的文件夹
        for dir_name in dirs_in_root:
            if dir_name[0] == ".":
                continue
            # print(os.path.join(root_full_path, dir_name))

        # 找到所有的文件
        for pure_file_name in files_in_root:
            # print(pure_file_name)
            if pure_file_name[0] == ".":
                continue
            full_file_name = os.path.join(root_full_path, pure_file_name)
            print(full_file_name)
            # do something with the file


# 根据当前工作目录，获取父文件夹，当输入参数是0的时候，代表当前目录
def get_parent_path(level: int = 0) -> str:
    path_appender = ""
    if level > 0:
        for i in range(level):
            path_appender = path_appender + "../"
    # 如果不用 abspath 来去掉 ".." 的话，也能用，不过路径会没有那么好看
    return os.path.abspath(os.path.join(os.getcwd(), path_appender))


# 这个脚本的作用是将某个目录下面所有的 文件 或者 文件夹 找出来。比如排除了 点 开头的文件和文件夹。
if __name__ == '__main__':
    parent_path = get_parent_path(1)
    walk_path(parent_path)
