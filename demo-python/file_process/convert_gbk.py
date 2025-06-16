# coding=utf-8
import codecs
import os


def convert(path_src):
    converted = 0
    total = 0

    for files in os.listdir(path_src):
        total += 1
        file_name_old = path_src + os.sep + files
        file_name_new = path_src + os.sep + os.path.splitext(files)[0] + '-utf8' + os.path.splitext(files)[1]
        encode_method = detect_code(file_name_old)
        print(files, ' 编码格式是： ', encode_method)
        print()
        if encode_method == 'gbk':
            converted += 1
            print("正在转换【 " + files + " 】到 utf8 。")
            file_old = codecs.open(file_name_old, 'r', 'gbk')
            file_new = codecs.open(file_name_new, 'w', 'utf-8')

            content = file_old.readline(1)
            while content:
                file_new.write(content)
                content = file_old.readline(1)

    print('一共有 ', total, ' 个文件，其中 ', converted, ' 个已经转换成功。')


def detect_code(file):
    encode_method = 'utf8'
    try:
        # f = open(file, 'rb')
        f = codecs.open(file, 'r', 'utf8')
        content = f.readline(100)
    except Exception as e:
        encode_method = 'gbk'
        print(e)
    return encode_method


if __name__ == '__main__':
    convert(r'\gbk_file')
