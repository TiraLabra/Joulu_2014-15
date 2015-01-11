import gen
import dist
import stats
 
def main():
    n = 6240
    s = 0x0
    g = gen.mt19937(s)
    d = dist.exponential(g.randarray(n),0,1)
    stats.uinfo(d)

if __name__ == '__main__':
    main()
