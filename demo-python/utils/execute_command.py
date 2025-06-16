import os
import re


# use os rather than commands module
def execute_command(command):
    r = os.popen(command)
    text = r.read()
    r.close()
    return text


if __name__ == '__main__':
    command = "ping 192.168.100.1"
    # command = "ping 192.168.100.1 -c 5"
    result = execute_command(command)
    print(result)
    search_result = re.search('(ttl|TTL)=255', result)
    print(search_result)
    if search_result:
        print('OK')
