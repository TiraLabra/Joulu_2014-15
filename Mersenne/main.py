import gen
import dist
import stats
 
def main():
    n = 3120
    s = 0x12345
    g = gen.mt19937(s)
    d = dist.normal(g.vectorize(n),0,1)
    print(stats.expectation(d))
    print(stats.variance(d))

if __name__ == '__main__':
    main()
