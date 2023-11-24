def convert_xnk(int n, int k):
    return n*((len(str(k))+1)*10)+k

x = convert_xnk(678,23)
print(x)