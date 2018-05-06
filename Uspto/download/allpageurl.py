
from download.mongodb_help import MogodbHelp

mongodbhelp = MogodbHelp('uspto','allmessage')

def cun(zongshu,keywordurl = 'ISD/11/1/1997-%3E5/12/1998&RS=ISD/19971101-%3E19980512'):

    int1 = zongshu / 50  + 1

    #print(type(int1))

    url1 = 'http://patft.uspto.gov/netacgi/nph-Parser?Sect1=PTO2&Sect2=HITOFF&p='
    url2 = '&u=%2Fnetahtml%2FPTO%2Fsearch-adv.htm&r=0&f=S&l=50&d=PTXT&S1=%40PD%3E%3D19971101%3C%3D19980512&Page=Next&OS='
    #url3 = 'ISD/11/1/1997-%3E5/12/1998&RS=ISD/19971101-%3E19980512'

    for i in range(1,int1) :

        url = url1 + str(i) + url2 + keywordurl

        mongodbhelp.push(url)

        print(url)



if __name__ == "__main__" :

    cun(300)


