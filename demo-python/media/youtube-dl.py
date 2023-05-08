# !/usr/bin/python
# -*- coding: utf-8 -*-


def process(target_folder):
    command_best = "youtube-dl --proxy socks5://127.0.0.1:1080/ -o '{0}\\%(title)s.%(ext)s' -f bestvideo+bestaudio https://www.youtube.com/watch?v={1}"
    i = 0
    set_to_do = ('111')
    for item in set_to_do:
        command = command_best.format(target_folder, item)
        print(command)
        # os.system(command)
        i = i + 1
        print('已完成 ', i, ' 个操作')


if __name__ == "__main__":
    target_folder = "D:\\1"
    process(target_folder)
