import pandas as pd
import os
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

    passive_matcher = Matcher(nlp.vocab)
    active_matcher = Matcher(nlp.vocab)
    doc = nlp(sentence)
    for sents in doc.sents:
        for token in sents:
            # print(token.dep_, token.tag_, end=" ")
            # print()
            pass

    passive_rule = [{'DEP': 'nsubjpass'}]
    active_rule = [{'DEP': 'nsubj'}]
    passive_matcher.add('Passive', [passive_rule])
    active_matcher.add('Active', [active_rule])
    matches_passive = passive_matcher(doc)
    matches_active = active_matcher(doc)

    # print(matches_passive)
    # print (matches_active)

    if matches_passive:
        return "Passive"
    elif matches_active:
        return "Active"
    else:
        return "NaN"

def voice2(sentence):
    try:
        nlp = spacy.load('en_core_web_sm')
    except:
        nlp = spacy.load('en')

    sentence_voice = "Active"

    doc = nlp(sentence)
    for sents in doc.sents:
        for token in sents:
            if token.dep_ == "nsubjpass" or token.dep_ == "agent":
                sentence_voice = "Passive"

    # print("sentence_voice: {}".format(sentence_voice))
    return sentence_voice

root = tk.Tk()
root.withdraw()

# open file generated from app
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
    voice2(sentence)
    voiceResult = voice2(sentence)
    data = {"Date": str(sentence_array[1]), "Time": str(sentence_array[0]), "Sentence": sentence_array[2].strip("\n"), "Voice": voiceResult}
    df = df.append(data, ignore_index=True)
print(df)

time = datetime.datetime.now()
time_formatted = time.strftime('%m-%d-%Y - Hour-%H-Min-%M Sec-%S')

df.to_csv('./' + '\DAMN Analysis Result on ' + time_formatted  + '.csv', index = False)