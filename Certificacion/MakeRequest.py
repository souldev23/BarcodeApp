import requests

def saveAsFile(r, filename):
    with open(filename,'wb') as fd:
        for chunk in r.iter_content(chunk_size=128):
            fd.write(chunk)


def makeReq():
    r = requests.get("https://api.github.com/events", stream=True)
    print(r.raw)
    print(r.raw.read(10))
    print(r.headers)
    print(r.headers['content-type'])
    print(r.headers.get('content-type'))

if __name__ == "__main__":
    makeReq()

