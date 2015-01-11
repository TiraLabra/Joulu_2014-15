import matplotlib.pyplot as plt

def expectation(v): # computes expectation of a sample
    e = 0
    for i in range(0, len(v)):
        e += v[i]
    return e/len(v)

def variance(v): # computes variance of a sample
    e = expectation(v)
    var = 0
    for i in range(0, len(v)):
        var += (v[i]-e)**2
    return var/len(v)

def ugraph(v): # plots a histogram (positive values)
    plt.hist(v,bins=1000,range=(0,50))
    plt.show()

def graph(v): # plots a histogram (negative and positive values)
    plt.hist(v,bins=1000,range=(-25,25))
    plt.show()

def uinfo(v):
    print("Expected value:",end="")
    print(expectation(v))
    print("Variance:",end="")
    print(variance(v))
    ugraph(v)

def info(v):
    print("Expected value:",end="")
    print(expectation(v))
    print("Variance:",end="")
    print(variance(v))
    graph(v)
