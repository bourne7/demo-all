import os
import random
import re

import urllib.request


def read_web_page():
    web_page_artist = "https://www.example.com/t/1382/"

    content = load_page(web_page_artist)
    print('content length: ' + str(content.__len__()))

    # 获取 artist
    regex = re.compile('<p>(.*?)，.*')

    artist = regex.findall(content)

    path_artist = '/Downloads/' + str(artist[0]) + '/'

    if not os.path.exists(path_artist):
        os.mkdir(path_artist)

    regex = re.compile('www\\.example\\.com/item/(\\d+)\.html.*?alt="(.*?)".*?\\s+<p.*?(\\d+)')

    albums_to_save = regex.findall(content)

    existed_albums = []
    for walk_path, walk_dir, walk_file in os.walk(path_artist):
        for dir_name in walk_dir:
            existed_albums.append(dir_name)
    existed_albums.sort()

    for item in albums_to_save:

        web_page_index = item[0]

        # filter by page index
        if int(web_page_index) < 10000:
            continue

        folder_name = item[1].replace('/', '')
        item_count = item[2]

        new_album_folder_name = web_page_index + '_' + folder_name + '_' + item_count

        if new_album_folder_name not in existed_albums:
            print('')
            print('')
            print('prepare to download : ' + new_album_folder_name)
            download_url(path_artist, new_album_folder_name, web_page_index, item_count)
        else:
            print('in existed albums: ' + new_album_folder_name)


def download_url(path_artist, new_album_folder_name, web_page_path, item_count) -> None:
    # 这里添加时间是为了做好最后一个的区分
    path_new_album = path_artist + new_album_folder_name

    if not os.path.exists(path_new_album):
        os.mkdir(path_new_album)

    url_template = "https://www.example.com/images/img/{}/{}.jpg"

    for i in range(1, int(item_count) + 1):
        source_file = url_template.format(web_page_path, i)
        locals_file = '{}{}{}.jpg'.format(path_new_album, '/', i)

        try:
            urllib.request.urlretrieve(source_file, locals_file)
        except IOError:
            print('[%d]' % i, end=" ")
        else:
            print(i, end=" ")

        # time.sleep(random.randint(1, 3))


def load_page(url) -> str:
    user_agents = [
        "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
        "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
        "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
        "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
        "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
        "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5"
    ]

    headers = {
        'User-Agent': random.choice(user_agents)
    }

    request = urllib.request.Request(url, headers=headers)

    response = urllib.request.urlopen(request)
    return response.read().decode('utf-8')


if __name__ == '__main__':
    read_web_page()
