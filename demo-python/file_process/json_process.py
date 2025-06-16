import json
import os
import random
import time
import uuid


def now():
    return time.strftime("%Y_%m_%d__%H_%M_%S")


# random from [min, max]
def rd(min=1, max=20):
    return random.randint(min, max)


def read_json(file_path):
    with open(file_path, 'r') as loaded_file:
        json_data = json.load(loaded_file)
    # print(beauty_json(json_data))
    return json_data

# beautify json with 4 indent space; sort by key; separate by "," and ": "
def beauty_json(json_data):
    return json.dumps(json_data, indent=4, sort_keys=True, separators=(',', ': '))


def get_local_flag(url):
    tuple_flag = ("CN", "US", "HK", "JP", "US", "GB", "NL", "AU", "DE", "RU")
    for flag in tuple_flag:
        if flag in url.upper():
            return flag

    # special flags
    if "UK" in url.upper():
        return "GB"

    return ''


def function_copy_properties(file_path):
    file_folder = os.path.split(file_path)[0]
    print(file_folder)
    file_name = os.path.split(file_path)[1]
    print(file_name)
    file_name_new = file_folder + os.sep + os.path.splitext(file_name)[0] + '.iOS.' \
                    + now() + os.path.splitext(file_name)[1]
    print(file_name_new)

    json_data = read_json(file_path)

    if isinstance(json_data, dict):
        print("json_data is a dict")
    elif isinstance(json_data, list):
        print("json_data is a list")

    json_data = json_data["configs"]

    for item in json_data:
        # print(beauty_json(item))
        new_item = {
            "allowInsecure": False,
            "cert": "",
            "created": 1550000000,
            "data": "",
            "file": "",
            "flag": get_local_flag(item["server"]),
            "folded": False,
            "host": item["server"],
            "method": item["method"],
            "obfs": "",
            "obfsParam": "",
            "ota": False,
            "password": item["password"],
            "peer": "",
            "ping": 104,
            "plugin": "",
            "pluginParam": {},
            "port": item["server_port"],
            "proto": "",
            "protoParam": "",
            "selected": False,
            "tfo": False,
            "title": item["remarks"],
            "tls": False,
            "type": "",
            "updated": 1550000000,
            "user": "",
            "uuid": str(uuid.uuid5(uuid.NAMESPACE_DNS, item["server"])).upper(),
            "weight": 1550000000
        }
        # print(beauty_json(new_item))
        print(new_item)
        new_json_array.append(new_item)

    print(now())
    content = json.dumps(new_json_array, indent=2, ensure_ascii=False)
    open(file_name_new, 'w').write(content)
    # open(__file__[:-3] + '_123.json', 'w').write(content)


new_json_array = []

if __name__ == '__main__':
    file_path = r'E:\123.json'
    function_copy_properties(file_path)
