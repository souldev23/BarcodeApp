from urllib.request import Request, urlopen
import ssl

def sendRequest(url, decode):
    ssl._create_default_https_context = ssl._create_unverified_context
    req = Request(url)
    req.add_header("Authorization","Basic bGVhcm5pbmc6bGVhcm5pbmc=")
    response = urlopen(req)
    response_str = response.read().decode(decode)
    print(response_str)
    response.close()

def run():
    url = "https://cmxlocationsandbox.cisco.com/api/config/v1/maps/info/DevNetCampus/DevNetBuildig/DevNetZone"
    decode = "utf-8"
    sendRequest(url, decode)


if __name__ == "__main__":
    run()