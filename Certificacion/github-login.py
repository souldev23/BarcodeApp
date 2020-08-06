import requests

def login(url, user, password):
    r = requests.get(url, auth=(user,password))
    print(r.json())
    print(r.headers['Content-Type'])

def run():
    url = "https://api.github.com/user"
    user = "soulware23"
    password = "s0ulw4r3/"
    login(url, user, password)



if __name__ == "__main__":
    run()
