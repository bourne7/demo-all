import os
import shutil
import typing

from PIL import Image


def process(
    path_src: str,
    path_collect: str,
    enable_replace: bool,
    process_method: typing.Callable,
) -> None:

    count = 0

    if enable_replace:
        path_dst = path_src
    else:
        if not path_collect:
            path_collect = "%s%s" % (path_src, "_processed")
            if os.path.exists(path_collect):
                shutil.rmtree(path_collect)
            os.mkdir(path_collect)
        path_dst = path_collect

    file_list = os.listdir(path_src)
    for file in file_list:
        # 原图完整地址
        file_src = path_src + os.sep + file

        if file[0] == ".":
            os.remove(file_src)
            continue

        if is_picture(file):
            # 新图完整地址
            file_dst = path_dst + os.sep + file
            try:
                src_img = Image.open(file_src)
                # 调用不同的压缩方法
                new_image = process_method(src_img)
                if new_image:
                    new_image.save(file_dst)
                    count += 1
                    print(count, " ", src_img.size, " -> ", new_image.size)
                    # print(file, ': ', src_img.size, ' -> ', new_image.size)
                else:
                    print(src_img.size, " PASS")
                    continue
            except IOError:
                print("convert fail: ", file)

        if os.path.isdir(file_src):
            process(file_src, path_collect, enable_replace, process_method)


# 直接压缩到 1920*1080，可能会失去原图比例，但是保留所有原图图像，这个相当于windows壁纸的“拉伸”
def resize_to_fhd(src_img: Image) -> Image:
    return src_img.resize((1920, 1080), Image.ANTIALIAS)


# fit 到 1920*1080，可能会失去部分原图图像，但是会保留原图比例，这个相当于windows壁纸的“填充”
def fit_to_fhd(src_img: Image) -> Image:
    width_old = src_img.size[0]
    height_old = src_img.size[1]

    # 采用了先缩放到宽和高都不小于HD尺寸，并且保持比例
    if width_old / height_old / (16 / 9) > 1:
        width_new = int(width_old / height_old * 1080)
        height_new = 1080
    else:
        width_new = 1920
        height_new = int(height_old / width_old * 1920)
    # print(id(src_img)) 每次操作图像，都会返回一个新的对象，原来的对象不会改变
    temp_image = src_img.resize((width_new, height_new), Image.Resampling.LANCZOS)
    # 裁剪一下，只有2种情况的需要裁剪，分别是胖的和瘦的。。
    if width_new == 1920 and height_new > 1080:
        # 注意，这里的4个值是构成的2个点的绝对坐标，分别是左上角的点和右下角的点。
        first_point_to_top = int((height_new - 1080) / 2)
        temp_image = temp_image.crop(
            (0, first_point_to_top, 1920, height_new - first_point_to_top)
        )
    elif height_new == 1080 and width_new > 1920:
        first_point_to_left = int((width_new - 1920) / 2)
        temp_image = temp_image.crop(
            (first_point_to_left, 0, width_new - first_point_to_left, 1080)
        )
    # 这里是防止出现 1081 这种上面除以2的时候产生的异常值。
    return temp_image.resize((1920, 1080), Image.Resampling.LANCZOS)


# fit 到 特定宽度，保持比例
def fit_to_fix_width(src_img: Image) -> Image:

    # 目标宽度
    target_width = 1300

    width_old = src_img.size[0]
    height_old = src_img.size[1]

    # 已经达到了宽度，直接返回
    if width_old >= target_width:
        return None
    # 缩放高度
    height_new = int(height_old / width_old * target_width)

    return src_img.resize((target_width, height_new), Image.Resampling.LANCZOS)


# Clear transparency in PNG
def remove_transparency(src_img: Image) -> Image:

    # Check if the image has an alpha channel (transparency)
    if src_img.mode in ("RGBA", "LA") or (
        src_img.mode == "P" and "transparency" in src_img.info
    ):
        # Create a white background image
        background = Image.new("RGB", src_img.size, (255, 255, 255))
        # Paste the image on the background
        background.paste(src_img, mask=src_img.split()[3])  # 3 is the alpha channel
        src_img = background
    elif src_img.mode != "RGB":
        # Convert non-RGB modes (e.g., 'L', 'CMYK') to 'RGB'
        src_img = src_img.convert("RGB")

    # Return the converted image
    return src_img


def extend_image_to_size(
    src_img: Image, new_size=(800, 800), background_color=(255, 255, 255)
) -> Image:
    """
    Extends the image to a specified size with a white background.

    :param src_img: The source image to extend.
    :param new_size: A tuple (width, height) specifying the new size.
    :param background_color: The color of the background (default is white).
    :return: A new image extended to the specified size with a white background.
    """
    new_size = (max(new_size[0], src_img.size[0]), max(new_size[1], src_img.size[1]))

    # Create a new image with the specified size and background color
    new_img = Image.new("RGB", new_size, background_color)

    # Calculate the position to paste the original image onto the new image
    # so that it is centered
    x1 = (new_size[0] - src_img.size[0]) // 2
    y1 = (new_size[1] - src_img.size[1]) // 2

    # Paste the original image onto the new image
    new_img.paste(src_img, (x1, y1))

    return new_img


def is_picture(file_name: str) -> bool:
    file_type = os.path.splitext(file_name)[1]
    if file_type.lower() in (".jpg", ".png", ".jpeg", ".svg"):
        return True
    else:
        return False


if __name__ == "__main__":
    # resize_to_fhd
    # fit_to_fhd
    # fit_to_fix_width
    # process(r'F:\\2', "", True, fit_to_fix_width)
    process(
        r"1",
        "",
        True,
        # remove_transparency,
        extend_image_to_size,
    )
