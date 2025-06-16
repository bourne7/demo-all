import random


def test_except():
    i = 1
    while True:
        try:
            if i == 5:
                raise NameError
            elif i == 21:
                break
        except NameError:
            print('Error catched')
        else:
            print(i)
        finally:
            i += 1


if __name__ == '__main__':
    # test_except()
    a = [1, 2, 3, 4, 5, 6, 7, 8, 9]
    print(random.choice(a))
    print(random.choices(a, [0, 0, 0, 0, 0, 0, 0, 0, 1], k=1))
