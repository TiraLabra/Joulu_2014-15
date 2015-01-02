from math import log, sqrt, exp, sin, pi

def uniform(v,min,max): # continuous uniform distribution
    w = map(lambda x: min+(max-min)*x, v)
    return list(w)

def exponential(v,xm,l): # exponential distribution; xm > 0 (minimum), l > 0 (rate)
    w = map(lambda x: xm-log(1-x)/l, v)
    return list(w)

def pareto(v,xm,a): # Pareto distribution; xm > 0 (scale), a > 0 (shape)
    w = map(lambda x: xm*((1-x)**(-1/a)), v)
    return list(w)

def normal(v,m,s): # normal distribution
    w = map(lambda x: m+s*probit(x), v)
    return list(w)

def lognormal(v,m,s): # log-normal distribution
    w = map(lambda x: exp(m+s*probit(x)), v)
    return list(w)

def arcsine(v): # arcsine distribution (beta(0.5,0.5))
    w = map(lambda x: sin(x*pi/2)**2, v)
    return list(w)

def erfinv(x): # inverse error function (approximation)
    w = -log(1-x**2)
    if (w < 5):
        w = w - 2.5
        p = 2.81022636*10**(-8)
        p = 3.43273939*10**(-7) + p*w
        p = -3.5233877*10**(-6) + p*w
        p = -4.39150654*10**(-6) + p*w
        p = 0.00021858087 + p*w
        p = -0.00125372503 + p*w
        p = -0.00417768164 + p*w
        p = 0.246640727 + p*w
        p = 1.50140941 + p*w
    else:
        w = sqrt(w) - 3
        p = -0.000200214257
        p = 0.000100950558 + p*w
        p = 0.00134934322 + p*w
        p = -0.00367342844 + p*w
        p = 0.00573950773 + p*w
        p = -0.0076224613 + p*w
        p = 0.00943887047 + p*w
        p = 1.00167406 + p*w
        p = 2.83297682 + p*w
    return p*x

def probit(x): # probit function
    return sqrt(2)*erfinv(2*x-1)
