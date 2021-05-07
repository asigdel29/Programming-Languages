#Enter directory path when compiling --> python3 main.py (Directory)
import os
import sys

def wc(path):
    for filename in os.listdir(path):
        os.system("wc -l " + path + "/" + filename)

testpath = sys.argv[1]
wc(testpath)


