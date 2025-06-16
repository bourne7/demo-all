import os

import pymysql

# 用于从数据库表名生成 Mybatis Generator 配置文件可用 xml 信息
my_db = pymysql.connect(
    host="localhost",
    user="root",
    passwd="root",
    database="world"
)

my_cursor = my_db.cursor()

my_cursor.execute("show tables")

my_result = my_cursor.fetchall()

output_content = ''

for data_row in my_result:
    table_name = data_row[0]
    table_name_list = list(table_name)
    # print(table_name_list)
    for i in range(len(table_name_list)):
        char = table_name_list[i]
        # print(i, ' - ', char)
        if i == 0:
            table_name_list[i] = table_name_list[i].upper()
        if char == '_':
            table_name_list[i + 1] = table_name_list[i + 1].upper()

    object_name = ''.join(table_name_list).replace('_', '')
    print(object_name)

    xml_item = '<table tableName="{}" domainObjectName ="{}">\n' \
               + '\t<generatedKey column="id" sqlStatement="MySql" identity="true"/>\n' \
               + '</table>\n'

    output_content = output_content + xml_item.format(table_name, object_name)

current_script_name = os.path.basename(__file__)

open(os.getcwd() + os.sep + current_script_name[:-3] + '.xml', 'w').write(output_content)
