from bs4 import BeautifulSoup
from download.mongodb_help import MogodbHelp
from download.download import request

def save_allurl():

    allpage = MogodbHelp('uspto','allmessage')

    def nowpagesave_url():

        while True:
            try:
                url = allpage.pop()
                print(url)

            except:
                print('no')
                break


            else:

                req = request.get(url,3)
                req.encoding = 'utf-8'
                test = BeautifulSoup(req.text, 'lxml').find_all('table')[1]
                d = test.find_all('a')
                hh = []
                for i in d:
                    url = i['href']
                    # print(i['href'])
                    hh.append(url)
                    # print(hh)
                    hh = list(set(hh))

                print(hh)

                for url in hh:

                    url = 'http://patft.uspto.gov' + url
                    mongodbhelp = MogodbHelp('uspto', 'all_ererypageurl')
                    mongodbhelp.push(url)

    save_allurl()
