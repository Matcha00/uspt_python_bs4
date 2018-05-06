import re

from bs4 import BeautifulSoup

import requests

import random

from download.mongodb_help import MogodbHelp

from download.download import request


def save_allmessage():

 re = request(url,3)
 soup = BeautifulSoup(html.text,'lxml')
 test = BeautifulSoup(html.text,'lxml').find_all('table')[2]

UnitedStatesPatent = test.find_all('b')[1].get_text()
Sugaya = test.find_all('b')[3].get_text()
Sugaya = ' '.join(Sugaya.split())
print(UnitedStatesPatent)
print(Sugaya)
name = BeautifulSoup(html.text,'lxml').find('font', size="+1").text[:-1]
print(name)
Abstract = soup.find('p').get_text()
Abstract = ' '.join(Abstract.split())
print(Abstract)
Inventorstable = soup.find_all('table')[4]
#print(Inventorstable)
trall = Inventorstable.find_all('td')
#print(trall)
for i in range(len(trall)) :
    b_text = trall[i].text
    b_text = ' '.join(b_text.split())
    #print(trall[i].find('b'))
    print(b_text)
center = soup.find_all('center')[3].text
print(center)

coma = soup.find('coma').text
coma = ' '.join(coma.split())
table5 = soup.find_all('table')[5]
#print(table5)
tr2 = table5.find_all('tr')[2]
print(tr2)
for td in tr2 :
    print(td.string)
tr3 = table5.find_all('tr')[3]
print(tr3)

for td2 in tr3 :
    print(td2.string)

#print(type(UnitedStatesPatent.string))
#print(UnitedStatesPatent,Sugaya)
# for a in test.find_all('b') :
#  print(a.string)
#/html/body/p[2]/table

table7 = soup.find_all('table')[7]
#print(table7)

# for td4 in table7.fin :
#     print(td4.string)

table7_tr0 = table7.find_all('tr')[0]
table7_tr2 = table7.find_all('tr')[1]
table7_tr3 = table7.find_all('tr')[2]
table7_tr4 = table7.find_all('tr')[3]
#tttt = table7.find_all('tr')[0]
#print(tttt)
# for td3 in table7_tr0 :
#     print(td3)
#     #print(table7_tr0.string)



yyyyy = soup.find_all('td',  align = "right" ,valign = "top" ,width = "70%")
#print(yyyyy)
for i in range(len(yyyyy)) :

   t = yyyyy[i].text
   #y = t.string
   #y = ' '.join(y.split())
   #print(t)

table8 = soup.find_all('table')[8]
#print(table8)

tr10 = table8.find_all('tr')[1:]
#print(tr10)
for td in tr10 :

    #print(td.find_all('td'))

    for text12 in td.find_all('td') :

        if text12.string != None :
            print(text12.string)




        if text12.find('a') :

            aurl = text12.find('a')

            aurli = aurl['href']
            aurlstr = text12.find('a').get_text()

            print(aurli)

