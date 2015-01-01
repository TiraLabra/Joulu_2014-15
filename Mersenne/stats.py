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
