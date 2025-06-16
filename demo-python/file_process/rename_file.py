import datetime
import hashlib
import os

# 在文件名前面加上需要的文字。
import re
import shutil
import time

# from idna import unicode


def add_before_name(pattern: str, path_src: str) -> None:

    file_dict = {}
    for file in os.listdir(path_src):
        if file[0] == ".":
            continue
        file_dict[file] = file

    i = 109
    for k in sorted(file_dict):
        file_name_old = path_src + os.sep + file_dict[k]
        file_name_new = (
            path_src
            + os.sep
            + pattern
            + "{0:0>3d}".format(i)
            # + file_dict[k].replace(" ", "_").lower()
            + file_dict[k]
        )
        # file_name_new = file_name_new + '.mp4'
        file_name_new = path_src + os.sep + file_dict[k][:-26]+".mp4"
        # file_name_new = file_name_new[:-26]
        print(file_name_old + " -> " + file_name_new)
        os.rename(file_name_old, file_name_new)
        i += 1


# 格式化成为 字符+序号
def rename_index(pattern: str, path_src: str) -> None:
    i = 0
    for files in os.listdir(path_src):
        file_name_old = path_src + os.sep + files
        file_type = os.path.splitext(files)[1]
        file_name_new = (
            path_src + os.sep + pattern + "{0:0>4d}".format(i) + file_type.lower()
        )
        i += 1
        print(file_name_old + " -> " + file_name_new)
        os.rename(file_name_old, file_name_new)

# 根据输入的正则来替换文件名中的文本
def rename_regex(path_src: str, pattern: str, replace: str) -> None:
    for files in os.listdir(path_src):
        file_name_old = path_src + os.sep + files
        file_name_new = path_src + os.sep + re.sub(pattern, replace, files)
        print(file_name_old + " -> " + file_name_new)
        os.rename(file_name_old, file_name_new)

# md5 命名所有文件
def rename_md5(path_src: str) -> None:
    # 新建目录
    path_src_md5 = path_src + "_md5"
    print(path_src_md5)

    # 如果有存在目录就删除
    if os.path.exists(path_src_md5):
        shutil.rmtree(path_src_md5)

    time.sleep(0.0000000000000001)
    os.mkdir(path_src_md5)

    i = 0
    for files in os.listdir(path_src):
        file_name_old = path_src + os.sep + files

        with open(file_name_old, "rb") as f:
            md5 = hashlib.md5(f.read()).hexdigest()

            file_type = os.path.splitext(files)[1]
            file_name_new = path_src_md5 + os.sep + md5 + file_type
            i += 1
            print(file_name_old + " -> " + file_name_new)
            shutil.copyfile(file_name_old, file_name_new)


# 添加后缀给所有的文件
def rename_add_extension(path, ext):
    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            print(os.path.join(dir_path, dir_name))
        for pure_file_name in file_names:
            if pure_file_name[0] == ".":
                continue
            # if current file extension is not target ext, change it.
            if os.path.splitext(pure_file_name)[1] != "." + ext:
                old_file_name = os.path.join(dir_path, pure_file_name)
                new_file_name = os.path.join(
                    dir_path, os.path.splitext(pure_file_name)[0] + "." + ext
                )
                print(os.path.splitext(pure_file_name))
                os.rename(old_file_name, new_file_name)


# 替换后缀
def rename_replace_extension(path, ext, ext_new):
    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            print(os.path.join(dir_path, dir_name))
        for pure_file_name in file_names:
            if pure_file_name[0] == ".":
                continue
            # if current file extension is not target ext, change it.
            if os.path.splitext(pure_file_name)[1] == "." + ext:
                old_file_name = os.path.join(dir_path, pure_file_name)
                new_file_name = os.path.join(
                    dir_path, os.path.splitext(pure_file_name)[0] + "." + ext_new
                )
                print(os.path.splitext(pure_file_name))
                os.rename(old_file_name, new_file_name)


# 根据文件的修改日期来重命名文件
def rename_modify_time(path):
    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            rename_modify_time(dir_name)
            print(os.path.join(dir_path, dir_name))
        for pure_file_name in file_names:
            if pure_file_name[0] == ".":
                continue

            if re.match("\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}.*", pure_file_name):
                print("Formatted name: " + pure_file_name)
                continue
            full_file_name = os.path.join(dir_path, pure_file_name)
            print(pure_file_name)

            time_stamp = os.path.getmtime(full_file_name)
            local_time = time.localtime(time_stamp)
            str_time = time.strftime("%Y-%m-%d_%H-%M-%S", local_time)
            print(str_time)

            new_file_name = os.path.join(dir_path, str_time + "_" + pure_file_name)

            os.rename(full_file_name, new_file_name)
            print(new_file_name)
            print()


# 根据文件名，给每个文件夹里面的文件加一个递增的序号。
def rename_modify_index(path):
    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            rename_modify_time(dir_name)
            # print(os.path.join(dir_path, dir_name))

        file_names = list(filter(lambda x: (x[0] != "."), file_names))
        file_names.sort()
        print(dir_path)
        print(len(file_names))
        if file_names:
            print(file_names)

        i = 0
        for pure_file_name in file_names:
            i += 1
            full_file_name = os.path.join(dir_path, pure_file_name)
            extension = os.path.splitext(pure_file_name)[1]
            new_file_name = os.path.join(
                dir_path,
                os.path.basename(dir_path)
                + " - "
                + "{0:0>4d}".format(i)
                + extension.lower(),
            )
            os.rename(full_file_name, new_file_name)


# 删除小文件
def delete_small_files(path):
    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            delete_small_files(dir_name)
            print(os.path.join(dir_path, dir_name))
        for pure_file_name in file_names:
            if pure_file_name[0] == ".":
                continue

            full_file_name = os.path.join(dir_path, pure_file_name)

            if get_file_size(full_file_name) > 0.5:
                print(get_file_size(full_file_name))
            else:
                os.remove(full_file_name)


# 删除包含特定字符串的文件
def delete_files_include_str(path):
    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            delete_files_include_str(dir_name)
            print(os.path.join(dir_path, dir_name))
        for pure_file_name in file_names:
            if pure_file_name[0] == ".":
                continue

            full_file_name = os.path.join(dir_path, pure_file_name)

            if "partial" in pure_file_name:
                print(full_file_name)
                os.remove(full_file_name)


# get file size in MB


def get_file_size(file_path):
    # file_path = unicode(file_path, 'utf8')
    file_size = os.path.getsize(file_path)
    file_size = file_size / float(1024 * 1024)
    return round(file_size, 2)


# 修改文件的创建时间和修改时间
def change_file_time(path):
    year = 1970
    month = 1
    day = 1
    hour = 0
    minute = 0
    second = 0
    fix_date = datetime.datetime(
        year=year, month=month, day=day, hour=hour, minute=minute, second=second
    )

    for dir_path, dir_names, file_names in os.walk(path):
        for dir_name in dir_names:
            print(os.path.join(dir_path, dir_name))
        for pure_file_name in file_names:
            if pure_file_name[0] == ".":
                continue

            # file_name_number = os.path.splitext(pure_file_name)[0]
            # file_time = fix_date + datetime.timedelta(days=int(file_name_number))
            # now_time = time.mktime(file_time.timetuple())

            now_time = time.mktime(fix_date.timetuple())
            full_file_name = os.path.join(dir_path, pure_file_name)
            print(full_file_name)
            os.utime(full_file_name, (now_time, now_time))


if __name__ == "__main__":
    print("start")
    # folder = r'/1'
    folder = r"/Users/aac/Desktop/111"
    # add_before_name("", folder)
    # rename_index('', 'D:\1')
    # AAA - 2024-07-21T22-57-06-07-00.mp4 替换为 AAA.mp4
    rename_regex(folder, r"^\*", r"")
    # rename_md5(r'D:\1')
    # rename_add_extension(folder, 'mp4')
    # rename_replace_extension(folder, "jpg", "png")
    # rename_modify_time(folder)
    # change_file_time(folder)
    # delete_small_files(folder)
    # delete_files_include_str(folder)
    # rename_modify_index(folder)
