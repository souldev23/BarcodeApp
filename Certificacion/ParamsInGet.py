import requests

def sendGET(url, playload):
    r = requests.get(url, params = playload)
    print(r.text)


def run():
    url = 'https://httpbin.org/'
    playload = {'username':'soul23', 'age':'32'}
    sendGET(url, playload)


if __name__ == "__main__":
    run()