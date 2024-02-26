# -*- coding: utf-8 -*-
import time
dict = {}
dict_words_max_len = 0
a = 0
for line in open("160000_ch_dictionary.txt", "r", encoding="utf-8").readlines():
    if a == 0:
        line = line.encode('utf-8').decode('utf-8-sig')
        a = line
    length = len(line)
    dict[line] = length
    if dict_words_max_len < length:
        dict_words_max_len = length
input_string = input("請輸入句子或文章:\n ")
time1 = time.time()
input_string_len = len(input_string)
output_list = []
pointer = input_string_len # scan from the end
while pointer > 0:
    for length in range(dict_words_max_len + 1, 0, -1):
        if pointer - length < 0:
            continue
        str = input_string[(pointer - length):pointer]
        exists = dict.get(str + '\n', -1)
        if exists > 0:
            output_list.append(str)
            pointer = pointer - length
            break
        if length == 1:
            output_list.append(input_string[pointer - 1])
            pointer -= 1
output_list.reverse()
c = 0
print('[', end='')
for s in output_list:
    if c == 0:
        c = 1
    else:
        print(', ', end='')
    print('\'' + s + '\'', end='')
print(']', end='')

time2 = time.time()
# print(f"執行時間：{time2-time1:.4f}秒")
