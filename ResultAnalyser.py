import sys
import pandas as pd
import os
import numpy as np
import datetime
import spacy
from spacy.matcher import Matcher
import tkinter as tk
from tkinter import filedialog

def voice(sentence):
    try:
        nlp = spacy.load('en_core_web_sm')
    except:
        nlp = spacy.load('en')
    matcher = Matcher(nlp.vocab)

    result = "empty"
    doc = nlp(sentence)
    passive_rule = [{'DEP': 'nsubjpass'}, {'DEP': 'aux', 'OP': '*'}, {'DEP': 'auxpass'}, {'TAG': 'VBN'}]
    matcher.add('Passive', [passive_rule])
    matches = matcher(doc)
    if matches:
        return "Passive"
    else:
        return "Active"

root = tk.Tk()
root.withdraw()
#open file generated from app
file_pathname = filedialog.askopenfilename(initialdir = "./", title = "Select a File",
                                          filetypes = (("Text files", ".txt"),
                                                       ("all files",".")))


print(file_pathname)
file_pathname_array = file_pathname.rsplit('/', 1)
path = file_pathname_array[0]
filename = file_pathname_array[1]
print("\n path = ", path + "\n filename = " + filename + "\n")

input_text = open(os.path.join(path, filename), "r")

data = ['Date',	'Time',	'Sentence',	'Voice']

df = pd.DataFrame(columns=data)
for i in input_text:
    sentence_array = i.split(";")
    sentence = sentence_array[2]
    voiceResult = voice(sentence)
    data = {"Date": str(sentence_array[1]), "Time": str(sentence_array[0]), "Sentence": sentence_array[2].strip("\n"), "Voice": voiceResult}
    df = df.append(data, ignore_index=True)
print(df)
#filename = "Analysis Result on: " +  str(datetime.datetime.date(datetime.datetime.month, datetime.datetime.day)) # + str(datetime.datetime.time()))

time = datetime.datetime.now()
time_formatted = time.strftime('%m-%d-%Y - Hour-%H-Min-%M Sec-%S')

df.to_csv('./' + '\DAMN Analysis Result on ' + time_formatted  + '.csv', index = False)


