import requests

def makePOST(url, data):
    r = requests.post(url, data)
    print(r.json())


def run():
    url = 'https://httpbin.org/post'
    data = {'nombre': 'Saul'}
    makePOST(url, data)



if __name__ == "__main__":
    run()