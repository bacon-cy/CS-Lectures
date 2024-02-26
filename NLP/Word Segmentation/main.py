# -*- coding: utf-8 -*-
def is_numbers_or_alphabets(s):
    if u'0' <= s <= u'9' or u'a' <= s <= u'z' or u'A' <= s <= u'Z' :
        return True
    return False

# input the dictionary #
dict = {}  # we use dictionary since it is made as a hashmap, the time complexity of searching key is O(1)
dict_words_max_len = 0
dict_index = 0  # only use for removing BOM of UTF-8 in line 8 to 10
for word in open("C:\\NCKU\\python filess\\NLP word segmentation\\160000_ch_dictionary.txt", "r",encoding="utf-8").readlines():
    # the first word contains the BOM of UTF-8, remove the BOM by this line
    if dict_index == 0:
        word = word.encode('utf-8').decode('utf-8-sig')
        dict_index = -1
    # add the word into the dictionary, the (key:value) pair is (word, length of the word)
    length = len(word)
    dict[word] = length
    # record the max length of all words in the dictionary
    if dict_words_max_len < length:
        dict_words_max_len = length

# BMM segmentation algorithm #
input_string = input("請輸入句子或文章:\n ")
input_string_len = len(input_string)  # the length of input string
output_list = []  # the list records all word segments
pointer = input_string_len  # the pointer points out the current scanning end
while pointer > 0:
    for length in range(dict_words_max_len + 1, 0, -1):
        if pointer - length < 0:
            continue  # meaning that the testing-argument would be null or repeated, thus, skip it
        str = input_string[(pointer - length):pointer]  # the current testing-segment
        exists = dict.get(str + '\n', -1)
        if exists > 0:  # if the testing-segment str exists, the variable exists must > 0, since the value in dict is
            # the length of words
            output_list.append(str)  # append the existing segment to output list
            pointer = pointer - length  # move the pointer over the current testing-segment
            break
        # process numbers and english words
        if is_numbers_or_alphabets(input_string[pointer - 1]):
            tmp_pointer = pointer - 1
            while is_numbers_or_alphabets(input_string[tmp_pointer - 1]):
                tmp_pointer -= 1
            # input_string[tmp_pointer + 1 : pointer - 1] is numbers and english words
            output_list.append(input_string[tmp_pointer : pointer])
            pointer = tmp_pointer
            break
        if length == 1:
            output_list.append(input_string[pointer - 1])  # if there is none of word end with input_string[pointer - 1],
            # let add the character to the output list anyway
            pointer -= 1

# output the result #
output_list.reverse()  # since we use BMM, reverse the output list
print(output_list)
