import re

from bs4 import BeautifulSoup

import requests

import random


url = 'http://patft.uspto.gov/netacgi/nph-Parser?Sect1=PTO2&Sect2=HITOFF&p=1&u=%2Fnetahtml%2FPTO%2Fsearch-adv.htm&r=1&f=G&l=50&d=PTXT&S1=20130101.PD.&OS=ISD/1/1/2013&RS=ISD/20130101'
user_agent_list = [
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/22.0.1207.1 Safari/537.1",
            "Mozilla/5.0 (X11; CrOS i686 2268.111.0) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.57 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1092.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.6 (KHTML, like Gecko) Chrome/20.0.1090.0 Safari/536.6",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/19.77.34.5 Safari/537.1",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.9 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.0) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.36 Safari/536.5",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_0) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1063.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1062.0 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.1 Safari/536.3",
            "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/536.3 (KHTML, like Gecko) Chrome/19.0.1061.0 Safari/536.3",
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24",
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.24 (KHTML, like Gecko) Chrome/19.0.1055.1 Safari/535.24"
        ]
UA = random.choice(user_agent_list)
proxies = { "http": "http://113.118.97.119:9797", }
randomIP = str(random.randint(0, 255)) + '.' + str(random.randint(0, 255)) + '.' + str(
        random.randint(0, 255)) + '.' + str(random.randint(0, 255))
headers = {
        'User-Agent': UA,
        "Accept-Language": "zh-CN,zh;q=0.8,en;q=0.6",
        'X-Forwarded-For': randomIP,
    }
html = requests.get(url,headers = headers)
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

   t = yyyyy[i]
   print(t.string)