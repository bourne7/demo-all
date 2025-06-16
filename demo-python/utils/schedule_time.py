import time

import schedule


def job():
    str = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime(time.time()))
    print(str)


schedule.every().seconds.do(job)

while True:
    schedule.run_pending()
    time.sleep(1)
